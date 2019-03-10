import com.google.common.collect.Maps;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lang.IgniteBiTuple;
import org.apache.ignite.lang.IgniteFuture;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;

import javax.cache.Cache;

/**
 * Created by lishutao on 2019/3/9.
 *
 * @author lishutao
 * @date 2019/3/9
 */
public class IgniteTest extends AbstractBaseTest{
    Ignite ignite = null;

    @Before
    public void beforeTest() {
        TcpDiscoverySpi spi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("127.0.0.1:47500..47509"));
        spi.setIpFinder(ipFinder);
        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setDiscoverySpi(spi);
        cfg.setClientMode(true);

        ignite = Ignition.start(cfg);

    }

    @Test
    public void testCluster() {
        IgniteCluster cluster = ignite.cluster();
        System.out.println(cluster.nodeLocalMap());
    }

    @Test
    public void testGet() {
        IgniteCache<String, Integer> myCache = ignite.getOrCreateCache("myCache");
        Integer value = myCache.get("MyKey");
        System.out.println(value);
    }

    @Test
    public void testBatchGet() {
        IgniteCache<String, Integer> myCache = ignite.getOrCreateCache("myCache");
        Map<String, Integer> map = getKVMap(10000);
        myCache.putAll(map);

        Map<String,Integer> resultMap = myCache.getAll(map.keySet());
        System.out.println(resultMap.size());
    }

    @Test
    public void testGetAsync() {
        IgniteCache<String, Integer> myCache = ignite.getOrCreateCache("myCache");
        IgniteFuture<Integer> igniteFuture = myCache.getAsync("MyKey");
        System.out.println(igniteFuture.get());
    }

    @Test
    public void testPut() {
        IgniteCache<String, Integer> myCache = ignite.getOrCreateCache("myCache");
        myCache.put("MyKey", 100);
    }

    @Test
    public void testPutAsyn() {
        IgniteCache<String, Integer> myCache = ignite.getOrCreateCache("myCache");
        IgniteFuture igniteFuture = myCache.putAsync("MyKey", threadLocalRandom.nextInt(1000));
        Object object = igniteFuture.get();
        System.out.println(object);
    }

    @Test
    public void testScan() {
        IgniteCache<String, Integer> myCache = ignite.getOrCreateCache("myCache");
//        QueryCursor<Cache.Entry<String, Integer>> cursor = myCache.query(new ScanQuery<String, Integer>((k, p) -> p <10));
//        QueryCursor<Cache.Entry<String, Integer>> cursor = myCache.query(new ScanQuery<String, Integer>());
        QueryCursor cursor = myCache.query(new ScanQuery<String, Integer>());

        Iterator iterator = cursor.iterator();

        int i = 0;
        while (iterator.hasNext()) {
            IgniteBiTuple<String, Integer> entry = (IgniteBiTuple<String, Integer>)iterator.next();
            System.out.println(entry.getKey() + " " + entry.getValue());
            i++;
        }

        System.out.println(i);
    }
}
