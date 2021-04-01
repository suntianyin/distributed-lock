package com.msb.lock.etcd;

import javafx.concurrent.ScheduledService;
import lombok.Data;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 锁对象封装
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact zeroming@163.com
 * @date: 2020年06月04日 10时28分
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
public class LockData {
    private String lockKey;
    private boolean lockSuccess;
    private long leaseId;
    private ScheduledExecutorService service;
    private Thread owningThread;
    private String lockPath;
    final AtomicInteger lockCount = new AtomicInteger(1);

    public LockData (){
    }

    public LockData(Thread owningThread, String lockPath)
    {
        this.owningThread = owningThread;
        this.lockPath = lockPath;
    }
}
