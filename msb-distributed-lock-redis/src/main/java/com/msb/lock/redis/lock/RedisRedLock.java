package com.msb.lock.redis.lock;

import com.msb.lock.base.AbstractLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RedissonClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020/6/4  15:54
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public class RedisRedLock extends AbstractLock {

    private RedissonRedLock redLock ;

    public RedisRedLock(){}

    public RedisRedLock(RedissonRedLock redLock) {
        this.redLock = redLock;
    }

    @Override
    public void lock() {
        redLock.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        redLock.lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        return redLock.tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return redLock.tryLock(time, unit);
    }

    @Override
    public void unlock() {
        redLock.unlock();
    }

    @Override
    public Condition newCondition() {
        return redLock.newCondition();
    }
}
