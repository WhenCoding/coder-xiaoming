package com.xm.coder.ideadebug;

import java.util.Arrays;
import java.util.List;

/**
 * @auther xgs
 * @date 2020/3/8 11:13 下午
 */

public class ConditionBreakpoint {

    public static void main(String[] args) {
        List<String> goodStudents = Arrays.asList("小明", "小红", "李狗蛋", "胖虎");
        System.out.println("========开始点名========");
        goodStudents.forEach(student -> System.out.println(student.concat("到")));
    }
}
