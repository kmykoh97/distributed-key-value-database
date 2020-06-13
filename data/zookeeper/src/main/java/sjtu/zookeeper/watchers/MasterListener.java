package sjtu.zookeeper.watchers;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import sjtu.zookeeper.entity.Clusters;
import sjtu.zookeeper.service.ZKService;

import java.util.List;

@Slf4j
@Setter
public class MasterListener implements IZkChildListener {

    private ZKService zkService;

    /**
     * - This method will be invoked for changes in /election
     * - After receiving notification it will update local clusters object
     *
     * @param parentPath
     * @param currentChildren
     */
    @Override
    public void handleChildChange(String parentPath, List<String> currentChildren) {
        if (currentChildren.isEmpty()) {
            log.info("master deleted, recreating master!");
            Clusters.getClusterInfo().setMaster(null);

            try {
                zkService.electForMaster();
            } catch (ZkNodeExistsException e) {
                log.info("master already created");
            }
        } else { // get leader depending on election algorithm
            String leaderNode = zkService.getLeaderNodeData();
            log.info("updating new master: {}", leaderNode);
            Clusters.getClusterInfo().setMaster(leaderNode);
        }
    }

}
