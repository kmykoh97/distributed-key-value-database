package sjtu.zookeeper.watchers;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkStateListener;
import org.apache.zookeeper.Watcher;
import org.springframework.web.client.RestTemplate;
import sjtu.zookeeper.entity.Clusters;
import sjtu.zookeeper.entity.DataStorage;
import sjtu.zookeeper.entity.KeyValue;
import sjtu.zookeeper.service.ZKService;

import java.util.List;
import java.util.Map;

import static sjtu.zookeeper.util.ZKServersUtil.*;

@Slf4j
@Setter
public class ConnectStateListener implements IZkStateListener {

    private ZKService zkService;
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {
        log.info(state.name()); // 1. disconnected, 2. expired, 3. SyncConnected
    }

    @Override
    public void handleNewSession() throws Exception {
        log.info("connected to zookeeper");
        syncDataFromMaster(); // sync data from master

        // add new znode to /livenodes to make it live
        zkService.addToLiveNodes(getHostPostOfServer(), "cluster node");
        Clusters.getClusterInfo().getLiveNodes().clear();
        Clusters.getClusterInfo().getLiveNodes().addAll(zkService.getLiveNodes());

        // retry creating znode under /election. This is needed if there is only one server in cluster
        String leaderElectionAlgo = System.getProperty("leader.algo");

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
    }

    @Override
    public void handleSessionEstablishmentError(Throwable error) throws Exception {
        log.info("could not establish session");
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
