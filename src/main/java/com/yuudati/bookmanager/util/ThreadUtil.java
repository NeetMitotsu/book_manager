package com.yuudati.bookmanager.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/**
 * 线程工具类
 *
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/14 14:04
 */
public class ThreadUtil {

    private static volatile ExecutorService threadPool = null;

    private ThreadUtil() {

    }

    public static ExecutorService getThreadPool() {
        if (threadPool == null) {
            synchronized (ThreadUtil.class) {
                if (threadPool == null) {
                    // 本质Executors.newCacheThreadPool
                    threadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                            60L, TimeUnit.SECONDS, new SynchronousQueue<>(), new DefaultThreadFactory());
                }
            }
        }
        return threadPool;
    }

    public static void main(String[] args) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        for (int i = 0; i < 1000; i++) {
            ThreadUtil.getThreadPool().submit(() -> {
                System.out.println(LocalDateTime.now().format(dateTimeFormatter) + ": 线程" + Thread.currentThread() + "开始运行");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(LocalDateTime.now().format(dateTimeFormatter) + ": 线程" + Thread.currentThread() + "被中断");
                }
                System.out.println(LocalDateTime.now().format(dateTimeFormatter) + ": 线程" + Thread.currentThread() + "结束运行");
            });
        }
        System.out.println("结束");
    }

}
