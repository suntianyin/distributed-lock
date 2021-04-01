package com.msb.lock.redis.lock;

import com.msb.lock.base.AbstractLock;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020/6/4  15:20
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public class RedisLock extends AbstractLock {

    private RedissonClient client;
    private String key;

    public RedisLock(RedissonClient client,String key){
        this.client = client;
        this.key = key;
    }

    @Override
    public void lock() {
        client.getLock(key).lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        client.getLock(key).lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return client.getLock(key).tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return client.getLock(key).tryLock(time,unit);
    }

    @Override
    public void unlock() {
        client.getLock(key).unlock();
    }

    @Override
    public Condition newCondition() {
        return client.getLock(key).newCondition();
    }
}
