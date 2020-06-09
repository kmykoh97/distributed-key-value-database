package sjtu.zookeeper.watchers;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkChildListener;
import sjtu.zookeeper.entity.Clusters;

import java.util.List;

@Slf4j
public class LivenodesListener implements IZkChildListener {

    /**
     * - This method will be invoked for any change in /livenodes children
     * - After receiving notification it will update the local clusters object
     *
     * @param parentPath
     * @param currentChildren server hostname
     */
    @Override
    public void handleChildChange(String parentPath, List<String> currentChildren) {
        log.info("current live size: {}", currentChildren.size());
        Clusters.getClusterInfo().getLiveNodes().clear(); // refresh
        Clusters.getClusterInfo().getLiveNodes().addAll(currentChildren);
    }

}
