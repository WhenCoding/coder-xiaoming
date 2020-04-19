package com.xm.coder.jdk8time;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @auther xgs
 * @date 2020/4/16 6:33 下午
 */
public class Java8TimeExample {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(LocalDateTime date) {
        return formatter.format(date);
    }

    public static LocalDateTime parse(String dateNow) {
        return LocalDateTime.parse(dateNow, formatter);
    }

    public static void main(String[] args) throws InterruptedException, ParseException {

        ExecutorService service = Executors.newFixedThreadPool(100);

        // 20个线程
        for (int i = 0; i < 20; i++) {
            service.execute(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        System.out.println(format(LocalDateTime.now()));
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
    @Test
    public void showTime() {
        LocalDate localDateNow = LocalDate.now();
        // 今天周几
        int dayOfWeek = localDateNow.getDayOfWeek().getValue();
        System.out.println(dayOfWeek);
        // 今天零点的时间戳
        long today = localDateNow.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println(today);
        // 昨天零点时间戳
        long yestoday = localDateNow.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println(yestoday);
        // 上周零点时间戳
        long lastWeekDay = localDateNow.minusWeeks(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println(lastWeekDay);
    }
}
