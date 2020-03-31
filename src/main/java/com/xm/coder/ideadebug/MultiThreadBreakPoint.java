package com.xm.coder.ideadebug;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @auther xgs
 * @date 2020/3/8 11:13 下午
 */
public class MultiThreadBreakPoint {

    @Test
    public void answer() {
        Runnable runnable = () -> {
            System.out.println("老师，我会！");
            System.out.println(Thread.currentThread().getName().concat("举手"));
        };
        new Thread(runnable, "小明").start();
        new Thread(runnable, "狗蛋").start();
        new Thread(runnable, "小花").start();
        new Thread(runnable, "胖虎").start();
    }
    @Test
    public void changeValueAtAnyTime() {
        // 小明于1995年出生，那2020年的小明多少岁？
        int thisYear = 2020;
        int birthYear = 1995;
        int age = thisYear - birthYear;
        System.out.println(String.format("小明今年的实际年龄为：",age));
    }

}
