package sjtu.zookeeper;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sjtu.zookeeper.entity.Clusters;
import sjtu.zookeeper.entity.DataStorage;
import sjtu.zookeeper.entity.KeyValue;
import sjtu.zookeeper.service.ZKService;
import sjtu.zookeeper.watchers.MasterListener2;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static sjtu.zookeeper.util.ZKServersUtil.*;

@Component
public class OnStartUpApplication implements ApplicationListener<ContextRefreshedEvent> {

    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private ZKService zkService;
    @Autowired
    private IZkChildListener allnodeslistener;
    @Autowired
    private IZkChildListener livenodeslistener;
    @Autowired
    private IZkChildListener masterlistener;
    @Autowired
    private IZkStateListener connectstatelistener;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            // set slavenodes
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("serverlist.json"));

            // A JSON array. JSONObject supports java.util.List interface.
            JSONArray serverList = (JSONArray) jsonObject.get("servers");

            Iterator<JSONObject> iterator = serverList.iterator();
            while (iterator.hasNext()) {
//                System.out.println(String.valueOf(iterator.next()));
                Clusters.getClusterInfo().getSlaveNodes().add(String.valueOf(iterator.next()));
            }

            // create all parent nodes /election, /allnodes, /livenodes
            zkService.createAllParentNodes();

            // add this server to cluster by creating znode under /allnodes, with name as "host:port"
            zkService.addToAllNodes(getHostPostOfServer(), "cluster node");
            Clusters.getClusterInfo().getAllNodes().clear(); // refresh
            Clusters.getClusterInfo().getAllNodes().addAll(zkService.getAllNodes());

            // check which leader election algorithm used
            String leaderElectionAlgo = System.getProperty("leader.algo");

            // algorithm 2: create ephemeral sequential znode in /election then get least sequenced children of /election
            if (isEmpty(leaderElectionAlgo) || "2".equals(leaderElectionAlgo)) {
                zkService.createNodeInElectionZnode(getHostPostOfServer());
                Clusters.getClusterInfo().setMaster(zkService.getLeaderNodeData2());
            } else {
                if (!zkService.masterExists()) {
                    zkService.electForMaster();
                } else {
                    Clusters.getClusterInfo().setMaster(zkService.getLeaderNodeData());
                }
            }

            // sync data from master
            syncDataFromMaster();

            // add child znode under /live_node, to tell other servers that this server is ready to serve
            zkService.addToLiveNodes(getHostPostOfServer(), "cluster node");
            Clusters.getClusterInfo().getLiveNodes().clear(); // refresh
            Clusters.getClusterInfo().getLiveNodes().addAll(zkService.getLiveNodes());

            // register watchers for leader change, live nodes change, all nodes change and zk session
            if (isEmpty(leaderElectionAlgo) || "2".equals(leaderElectionAlgo)) {
                zkService.registerChildrenChangeWatcher(ELECTION_NODE_2, masterlistener);
            } else {
                zkService.registerChildrenChangeWatcher(ELECTION_NODE, masterlistener);
            }

            zkService.registerChildrenChangeWatcher(LIVE_NODES, livenodeslistener);
            zkService.registerChildrenChangeWatcher(ALL_NODES, allnodeslistener);
            zkService.registerZkSessionStateListener(connectstatelistener);
        } catch (Exception e) {
            throw new RuntimeException("Startup failed!!", e);
        }
    }

    private void syncDataFromMaster() {
        // do not need to do anything for leader as data are final
        if (getHostPostOfServer().equals(Clusters.getClusterInfo().getMaster())) {
            return;
        }

        String requestUrl;
        requestUrl = "http://".concat(Clusters.getClusterInfo().getMaster().concat("/data/getall"));
        Map<String, String> data = restTemplate.getForObject(requestUrl, Map.class);
        DataStorage.syncData(data);
    }
    
}
