package org.bx.scheduler.log;


import com.alibaba.druid.pool.DruidDataSource;
import com.msb.lock.mysql.lock.MysqlFUDistributeLock;
import com.msb.lock.mysql.lock.MysqlIDDistributeLock;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LockTest {
    private DataSource dataSource;
    private String key = "test_lock";
    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Before
    public void before() throws Exception {
        initDataSource();
    }

    private void initDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setPassword("root");
        druidDataSource.setUsername("root");
        druidDataSource.setMaxActive(100);
        druidDataSource.setInitialSize(10);
        druidDataSource.setMaxWait(5000);
        long startTime = System.currentTimeMillis();
        druidDataSource.init();
        dataSource = druidDataSource;
        System.out.println("创建数据源耗时-》"+(System.currentTimeMillis()-startTime));
    }

    @Test
    public void testMysqlFUDistributeLock() throws Exception {
        int[] count = {0};
        long statTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                final MysqlFUDistributeLock lock = new MysqlFUDistributeLock(dataSource, key);
                try {
                    lock.lock();
//                    if (lock.tryLock(schedulerLockInfo, 1000, TimeUnit.MILLISECONDS)) {
                    count[0]++;
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.unlock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);
        System.out.println("for Update锁 耗时长-》"+(System.currentTimeMillis()-statTime));
        System.out.println(count[0]);
    }

    @Test
    public void testMysqlIDDistributeLock() throws Exception {
        int[] count = {0};
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                final MysqlIDDistributeLock lock = new MysqlIDDistributeLock(dataSource, 3000, key);
                try {
//                    lock.lock();
                    if (lock.tryLock( 1000, TimeUnit.MILLISECONDS)) {
                    count[0]++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.unlock();
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
