package sjtu.zookeeper.watchers;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import sjtu.zookeeper.entity.Clusters;
import sjtu.zookeeper.service.ZKService;

import java.util.Collections;
import java.util.List;

import static sjtu.zookeeper.util.ZKServersUtil.*;

@Slf4j
@Setter
public class MasterListener2 implements IZkChildListener {

    private ZKService zkService;

    /**
     * - This method will be invoked for changes in /election2
     * - After receiving notification it will update local clusters object
     *
     * @param parentPath
     * @param currentChildren
     */
    @Override
    public void handleChildChange(String parentPath, List<String> currentChildren) {
        if (currentChildren.isEmpty()) {
            throw new RuntimeException("No node exists to select master!!");
        } else {
            Collections.sort(currentChildren);
            String masterZNode = currentChildren.get(0);
            // once znode is fetched, fetch the znode data to get the hostname of new leader
            String masterNode = zkService.getZNodeData(ELECTION_NODE_2.concat("/").concat(masterZNode));
            log.info("new master is: {}", masterNode);
            Clusters.getClusterInfo().setMaster(masterNode);
        }
    }

}
