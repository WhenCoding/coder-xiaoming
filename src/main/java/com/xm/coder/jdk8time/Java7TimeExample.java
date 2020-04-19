package com.xm.coder.jdk8time;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @auther xgs
 * @date 2020/4/19 11:52 下午
 */

public class Java7TimeExample {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String format(Date date) {
        return formatter.format(date);
    }

    public static Date parse(String strDate) throws ParseException {
        return formatter.parse(strDate);
    }

    /**
     * 使用ThreadLocal解决
     */
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static Date parseByThreadLocal(String dateStr) throws ParseException {
        return threadLocal.get().parse(dateStr);
    }

    public static String format2ByThreadLocal(Date date) {
        return threadLocal.get().format(date);
    }

    public static void main(String[] args) throws InterruptedException, ParseException {
        ExecutorService service = Executors.newFixedThreadPool(100);
        // 20个线程
        for (int i = 0; i < 20; i++) {
            service.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println(parse("2020-04-20 17:05:20"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        // 等待上述的线程执行完
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
    }
}
