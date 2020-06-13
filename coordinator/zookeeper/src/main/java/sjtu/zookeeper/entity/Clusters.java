package sjtu.zookeeper.entity;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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

    private List<String> slaveNodes = new ArrayList<>();

}
