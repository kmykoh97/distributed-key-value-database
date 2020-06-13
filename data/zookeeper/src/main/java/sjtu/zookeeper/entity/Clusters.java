package sjtu.zookeeper.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Clusters {

    private static Clusters clusterInfo = new Clusters();

    public static Clusters getClusterInfo() {
        return clusterInfo;
    }

    // these will be ephemeral znodes
    private List<String> liveNodes = new ArrayList<>();

    // these will be persistent znodes
    private List<String> allNodes = new ArrayList<>();

    private String master;

}
