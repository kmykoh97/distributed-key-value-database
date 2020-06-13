package sjtu.zookeeper.watchers;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import sjtu.zookeeper.entity.Clusters;

import java.util.List;

@Slf4j
public class AllnodesListener implements IZkChildListener {

    /**
     * - This method will be invoked for changes in /allnodes
     * - After receiving notification it will update local clusters object
     *
     * @param parentPath
     * @param currentChildren current list of children, children's string value is znode name which is set as server hostname
     */
    @Override
    public void handleChildChange(String parentPath, List<String> currentChildren) {
        log.info("current all node size: {}", currentChildren.size());
        Clusters.getClusterInfo().getAllNodes().clear();
        Clusters.getClusterInfo().getAllNodes().addAll(currentChildren);
    }

}
