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

    private static List<String> clusterInfo = new ArrayList<>();

    public static List<String> getClusterInfo() {
        return clusterInfo;
    }

    private Clusters() {
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader(System.getProperty("serverlist")));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;

        // A JSON array. JSONObject supports java.util.List interface.
        JSONArray serverList = (JSONArray) jsonObject.get("servers");

        Iterator<JSONObject> iterator = serverList.iterator();
        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
            clusterInfo.add(iterator.next().toString());
        }
    };

}
