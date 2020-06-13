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
        String leader = Clusters.getClusterInfo().getMaster();

        // If I am leader I will broadcast data to all live nodes, else forward request to leader
        if (amILeader()) {
            List<String> liveNodes = Clusters.getClusterInfo().getLiveNodes();
//            int noofvirtualnodes = 4; // assume there are n nodes constantly such that a new server will always take over a fail service
            int successCount = 0;
//
//            // we should do consistent hashing here to distribute data
//            int hashcode = key.hashCode() % (noofvirtualnodes); // use java native hashing method
//            Collections.sort(liveNodes);
//            List<String> placeNode = new ArrayList<>();
//            placeNode.add(liveNodes.get(hashcode));
//            placeNode.add(liveNodes.get(hashcode-1));

            for (String node : liveNodes) {
                if (getHostPostOfServer().equals(node)) { // save in memory
                    DataStorage.setData(key, value);
                    successCount++;
                } else { // broadcast to slave nodes
                    String requestUrl =
                            "http://"
                                    .concat(node)
                                    .concat("data")
                                    .concat("/")
                                    .concat("syncadd")
                                    .concat("/")
                                    .concat(String.valueOf(key))
                                    .concat("/")
                                    .concat(value);
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("request_from", leader);
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> entity = new HttpEntity<>(headers);
                    restTemplate.exchange(requestUrl, HttpMethod.PUT, entity, String.class).getBody();
                    successCount++;
                }
            }

            return ResponseEntity.ok().body("Successfully update ".concat(String.valueOf(successCount)).concat(" nodes"));
        } else { // forward to leader
            String requestUrl =
                    "http://"
                            .concat(leader)
                            .concat("data")
                            .concat("/")
                            .concat("put")
                            .concat("/")
                            .concat(String.valueOf(key))
                            .concat("/")
                            .concat(value);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // this is to make sure leader get the data. Else timeout
            return restTemplate.exchange(requestUrl, HttpMethod.PUT, entity, String.class);
        }
    }

    private boolean amILeader() {
        String leader = Clusters.getClusterInfo().getMaster();
        return getHostPostOfServer().equals(leader);
    }

    @ApiIgnore
    @PutMapping("/data/syncadd/{key}/{value}")
    public ResponseEntity<String> syncaddData(HttpServletRequest request, @PathVariable("key") String key, @PathVariable("value") String value) {
        DataStorage.setData(key, value);

        return ResponseEntity.ok("SUCCESS");
    }

    @ApiIgnore
    @GetMapping("/data/syncdelete/{key}")
    public ResponseEntity<String> syncdeleteData(HttpServletRequest request, @PathVariable("key") String key) {
        DataStorage.deleteData(key);

        return ResponseEntity.ok("SUCCESS");
    }

    @ApiOperation(value = "READ", notes = "read a value from database")
    @GetMapping("/data/get/{key}")
    public ResponseEntity<String> getData(HttpServletRequest request,
                                          @ApiParam(
                                                  name =  "Key",
                                                  value = "key of the data you wish to read",
                                                  example = "keytest",
                                                  required = true) @PathVariable("key") String key) {
        String result = DataStorage.getData(key);

        if (result == null) {
            return ResponseEntity.badRequest().body("no such key found");
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @ApiOperation(value = "DELETE", notes = "remove a data from database")
    @DeleteMapping("/data/delete/{key}")
    public ResponseEntity<String> deleteData(HttpServletRequest request,
                                             @ApiParam(
                                                     name =  "Key",
                                                     value = "key for the data you wish to remove",
                                                     example = "test1",
                                                     required = true) @PathVariable("key") String key) {
        String leader = Clusters.getClusterInfo().getMaster();

        // If I am leader I will broadcast data to all live nodes, else forward request to leader
        if (amILeader()) {
            List<String> liveNodes = Clusters.getClusterInfo().getLiveNodes();
            int successCount = 0;

            for (String node : liveNodes) {
                if (getHostPostOfServer().equals(node)) { // save in memory
                    DataStorage.deleteData(key);
                    successCount++;
                } else { // broadcast to slave nodes
                    String requestUrl =
                            "http://"
                                    .concat(node)
                                    .concat("data")
                                    .concat("/")
                                    .concat("syncdelete")
                                    .concat("/")
                                    .concat(String.valueOf(key));
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("request_from", leader);
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    HttpEntity<String> entity = new HttpEntity<>(headers);
                    restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class).getBody();
                    successCount++;
                }
            }

            return ResponseEntity.ok().body("Successfully update ".concat(String.valueOf(successCount)).concat(" nodes"));
        } else { // forward to leader
            String requestUrl =
                    "http://"
                            .concat(leader)
                            .concat("data")
                            .concat("/")
                            .concat("delete")
                            .concat("/")
                            .concat(String.valueOf(key));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // this is to make sure leader get the data. Else timeout
            return restTemplate.exchange(requestUrl, HttpMethod.DELETE, entity, String.class);
        }
    }

    @ApiOperation(value = "READALL", notes = "display all key value data in current machine")
    @GetMapping("/data/getall")
    public ResponseEntity<Map<String, String>> getData() {
        return ResponseEntity.ok(DataStorage.getDataListFromStorage());
    }

    @ApiOperation(value = "NODES", notes = "display master node, live nodes and all nodes")
    @GetMapping("/clusters/view")
    public ResponseEntity<Clusters> getClusterinfo() {
        return ResponseEntity.ok(Clusters.getClusterInfo());
    }

    @ApiIgnore
    @GetMapping("/clusters/heartbeat")
    // this method is required to do 2 phase commit
    public ResponseEntity<String> getHeartbeat() {
        return ResponseEntity.ok().body("alive");
    }

}
