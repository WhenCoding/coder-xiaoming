package com.xm.coder.thread;

/**
 * @auther xings
 * @email mynamecoder@163.com
 * @description
 * javac 编译生成class文件，然后通过 javap -c 反编译class文件， 查看汇编源码
 * @date 2021/10/8 5:39 下午
 */

public class ThreadNotSafeCount {
    private Long value;

    public Long getValue() {
        return value;
    }

    public void inc(Long value) {
        ++value;
    }
}
