package com.msb.lock.base;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月03日 16时59分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
public abstract class AbstractLock implements Lock {
    @Override
    public void lock() {
        throw new RuntimeException("不支持的操作");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new RuntimeException("不支持的操作");
    }

    @Override
    public boolean tryLock() {
        throw new RuntimeException("不支持的操作");
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new RuntimeException("不支持的操作");
    }

    @Override
    public void unlock() {
        throw new RuntimeException("不支持的操作");
    }

    @Override
    public Condition newCondition() {
        throw new RuntimeException("不支持的操作");
    }
}
