import com.google.common.collect.Maps;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by lishutao on 2019/3/10.
 *
 * @author lishutao
 * @date 2019/3/10
 */
public abstract class AbstractBaseTest {
    ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

    public Map<String, Integer> getKVMap(int count) {
        Map<String, Integer> map = Maps.newHashMap();
        for (int i = 0; i < count; i++) {
            map.put(UUID.randomUUID().toString(), threadLocalRandom.nextInt(count));
        }
        return map;
    }

}
