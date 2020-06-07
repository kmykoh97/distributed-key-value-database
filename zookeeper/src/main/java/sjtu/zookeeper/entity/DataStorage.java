package sjtu.zookeeper.entity;

import java.util.ArrayList;
import java.util.List;

// if possible, we should organise these data into persistent storage
// Here I will use RAM storage for performance and simplicity
public class DataStorage {

    private static List<KeyValue> dataList = new ArrayList<>();

    public static List<KeyValue> getDataListFromStorage() {
        return dataList;
    }

    public static void setData(KeyValue data) {
        dataList.add(data);
    }

    private DataStorage() {}

}
