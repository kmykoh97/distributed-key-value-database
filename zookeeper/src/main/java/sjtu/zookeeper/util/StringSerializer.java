package sjtu.zookeeper.util;

import java.nio.charset.StandardCharsets;

public class StringSerializer {

    @Override
    public byte[] serialize(Object data) {
        return ((String) data).getBytes();
    }

    @Override
    public Object deserialize(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
