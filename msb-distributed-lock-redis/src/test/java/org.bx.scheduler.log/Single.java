package org.bx.scheduler.log;

import com.msb.lock.redis.lock.RedisLock;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

import java.util.concurrent.*;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020/6/3  17:49
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public class Single{

    private RedissonClient getClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://xxxx:xxxx").setPassword("xxxxxx");//.setConnectionMinimumIdleSize(10).setConnectionPoolSize(10);//.setConnectionPoolSize();//172.16.10.164
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Test
    public void test() throws Exception {
        int[] count = {0};
        for (int i = 0; i < 10; i++) {
            RedissonClient client = getClient();
            final RedisLock redisLock = new RedisLock(client,"lock_key");
            executorService.submit(() -> {
                try {
                    redisLock.lock();
                    count[0]++;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        redisLock.unlock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        System.out.println(count[0]);
    }
}