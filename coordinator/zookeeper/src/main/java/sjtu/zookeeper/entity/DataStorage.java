package sjtu.zookeeper.entity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

// if possible, we should organise these data into persistent storage
// Here I will use RAM storage for performance and simplicity
public class DataStorage {

    private static ConcurrentMap<String, String> dataList = new ConcurrentHashMap<>();;

    public static Map<String, String> getDataListFromStorage() {
        HashMap<String, String> copy = new HashMap<>();
        copy.putAll(dataList);
        return copy;
    }

    public static void setData(String key, String value) {
        dataList.put(key, new String(value));
    }

    public static String getData(String key) {
//        for (KeyValue i : dataList) {
//            if (i.getKey() == key) {
//                return i.getValue();
//            }
//        }
//
//        return null;

        return dataList.getOrDefault(key, null);
    }

    public static void deleteData(String key) {
//        Iterator<KeyValue> iter = dataList.iterator();
//
//        while (iter.hasNext()) {
//            KeyValue p = iter.next();
//            if (p.getKey() == key) iter.remove();
//        }

        dataList.remove(key);
    }

    public static void syncData(Map<String, String> data) {
        for (String key : data.keySet()) {
            dataList.put(key, data.get(key));
        }
    }

    private DataStorage() {}

}
