package sjtu.zookeeper.controller;

import io.swagger.annotations.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sjtu.zookeeper.entity.Clusters;
import sjtu.zookeeper.entity.DataStorage;
import sjtu.zookeeper.entity.KeyValue;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static sjtu.zookeeper.util.ZKServersUtil.*;

@RestController
@Api(tags = {"Swagger Resource"})
@SwaggerDefinition(tags = {
        @Tag(name = "Swagger Resource", description = "RPC operations in modifying database")
})
public class RPCController {

    private RestTemplate restTemplate = new RestTemplate();

    @ApiOperation(value = "PUT", notes = "insert a key value into database")
    @PutMapping("/data/put/{key}/{value}")
    public ResponseEntity<String> saveData(HttpServletRequest request,
                                           @ApiParam(
                                                   name =  "Key",
                                                   value = "key for this data",
                                                   example = "keyetest",
                                                   required = true) @PathVariable("key") String key,
                                           @ApiParam(
                                                   name =  "Value",
                                                   value = "value for this key",
                                                   example = "valuetest",
                                                   required = true) @PathVariable("value") String value) {
        List<String> liveNodes = Clusters.getClusterInfo().getLiveNodes();

        // we should do consistent hashing here to distribute data
        int noofvirtualnodes = 6; // assume there are n nodes constantly such that a new server will always take over a fail service
        int hashcode = key.hashCode() % (noofvirtualnodes); // use java native hashing method
        Collections.sort(liveNodes);
        String nodetosend = liveNodes.get(hashcode);

        // 2 phase commit here
        if (checkheartbeat(nodetosend)) { // if positive heartbeat
            String requestUrl =
                    "http://"
                            .concat(nodetosend)
                            .concat("data")
                            .concat("/")
                            .concat("put")
                            .concat("/")
                            .concat(String.valueOf(key))
                            .concat("/")
                            .concat(value);
            HttpHeaders headers2 = new HttpHeaders();
            headers2.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity2 = new HttpEntity<>(headers2);

            // this is to make sure leader get the data. Else timeout
            return restTemplate.exchange(requestUrl, HttpMethod.PUT, entity2, String.class); // committed!
        } else { // if requested server fails
            if (hashcode == 0) { // get next node
                hashcode++;
                nodetosend = liveNodes.get(hashcode);
                String requestUrl =
                        "http://"
                                .concat(nodetosend)
                                .concat("data")
                                .concat("/")
                                .concat("put")
                                .concat("/")
                                .concat(String.valueOf(key))
                                .concat("/")
                                .concat(value);
                HttpHeaders headers2 = new HttpHeaders();
                headers2.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity2 = new HttpEntity<>(headers2);

                // this is to make sure leader get the data. Else timeout
                return restTemplate.exchange(requestUrl, HttpMethod.PUT, entity2, String.class); // committed!
            } else { // get previous node
                hashcode--;
                nodetosend = liveNodes.get(hashcode);
                String requestUrl =
                        "http://"
                                .concat(nodetosend)
                                .concat("data")
                                .concat("/")
                                .concat("put")
                                .concat("/")
                                .concat(String.valueOf(key))
                                .concat("/")
                                .concat(value);
                HttpHeaders headers2 = new HttpHeaders();
                headers2.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity2 = new HttpEntity<>(headers2);

                // this is to make sure leader get the data. Else timeout
                return restTemplate.exchange(requestUrl, HttpMethod.PUT, entity2, String.class); // committed!
            }
        }
    }

    private boolean checkheartbeat(String nodeToSend) {
        String requestUrl =
                "http://"
                        .concat(nodeToSend)
                        .concat("clusters/heartbeat")
                        .concat("/");
        HttpHeaders headers = new HttpHeaders();
        headers.add("request_from", "coordinator");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        HttpStatus returnstatus = restTemplate.exchange(requestUrl, HttpMethod.PUT, entity, String.class).getStatusCode();

        if (returnstatus == HttpStatus.OK) {
            return true;
        } else {
            return false;
        }
    }

    private boolean amILeader() {
        String leader = Clusters.getClusterInfo().getMaster();
        return getHostPostOfServer().equals(leader);
    }

    @ApiOperation(value = "READ", notes = "read a value from database")
    @GetMapping("/data/get/{key}")
    public ResponseEntity<String> getData(HttpServletRequest request,
                                          @ApiParam(
                                                  name =  "Key",
                                                  value = "key of the data you wish to read",
                                                  example = "keytest",
                                                  required = true) @PathVariable("key") String key) {
        List<String> liveNodes = Clusters.getClusterInfo().getLiveNodes();

        // we should do consistent hashing here to distribute data
        int noofvirtualnodes = 6; // assume there are n nodes constantly such that a new server will always take over a fail service
        int hashcode = key.hashCode() % (noofvirtualnodes); // use java native hashing method
        Collections.sort(liveNodes);
        String nodetosend = liveNodes.get(hashcode);

        String requestUrl =
                "http://"
                        .concat(nodetosend)
                        .concat("data")
                        .concat("/")
                        .concat("get")
                        .concat("/")
                        .concat(key);
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity2 = new HttpEntity<>(headers2);

        // this is to make sure leader get the data. Else timeout
        return restTemplate.exchange(requestUrl, HttpMethod.GET, entity2, String.class);
    }

    @ApiOperation(value = "DELETE", notes = "remove a data from database")
    @GetMapping("/data/delete/{key}")
    public ResponseEntity<String> deleteData(HttpServletRequest request,
                                             @ApiParam(
                                                     name =  "Key",
                                                     value = "key for the data you wish to remove",
                                                     example = "test1",
                                                     required = true) @PathVariable("key") String key) {
        List<String> liveNodes = Clusters.getClusterInfo().getLiveNodes();

        // we should do consistent hashing here to distribute data
        int noofvirtualnodes = 6; // assume there are n nodes constantly such that a new server will always take over a fail service
        int hashcode = key.hashCode() % (noofvirtualnodes); // use java native hashing method
        Collections.sort(liveNodes);
        String nodetosend = liveNodes.get(hashcode);

        // 2 phase commit here
        if (checkheartbeat(nodetosend)) { // if positive heartbeat
            String requestUrl =
                    "http://"
                            .concat(nodetosend)
                            .concat("data")
                            .concat("/")
                            .concat("delete")
                            .concat("/")
                            .concat(String.valueOf(key));
            HttpHeaders headers2 = new HttpHeaders();
            headers2.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity2 = new HttpEntity<>(headers2);

            // this is to make sure leader get the data. Else timeout
            return restTemplate.exchange(requestUrl, HttpMethod.DELETE, entity2, String.class); // committed!
        } else { // if requested server fails
            if (hashcode == 0) { // get next node
                hashcode++;
                nodetosend = liveNodes.get(hashcode);
                String requestUrl =
                        "http://"
                                .concat(nodetosend)
                                .concat("data")
                                .concat("/")
                                .concat("delete")
                                .concat("/")
                                .concat(String.valueOf(key));
                HttpHeaders headers2 = new HttpHeaders();
                headers2.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity2 = new HttpEntity<>(headers2);

                // this is to make sure leader get the data. Else timeout
                return restTemplate.exchange(requestUrl, HttpMethod.DELETE, entity2, String.class); // committed!
            } else { // get previous node
                hashcode--;
                nodetosend = liveNodes.get(hashcode);
                String requestUrl =
                        "http://"
                                .concat(nodetosend)
                                .concat("data")
                                .concat("/")
                                .concat("delete")
                                .concat("/")
                                .concat(String.valueOf(key));
                HttpHeaders headers2 = new HttpHeaders();
                headers2.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity2 = new HttpEntity<>(headers2);

                // this is to make sure leader get the data. Else timeout
                return restTemplate.exchange(requestUrl, HttpMethod.DELETE, entity2, String.class); // committed!
            }
        }
    }


    @ApiOperation(value = "NODES", notes = "display master node, live nodes and all nodes")
    @GetMapping("/clusters/view")
    public ResponseEntity<List> getClusterinfo() {
        return ResponseEntity.ok(Clusters.getClusterInfo());
    }

}
