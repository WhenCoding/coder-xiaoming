package com.xm.coder.thread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @auther xings
 * @email mynamecoder@163.com
 * @description
 * @date 2021/10/9 3:51 下午
 */

public class TestUnsafe {
    private volatile long state = 0;
    /**
     *
     *   如果获取unsafe时不存在限制，那么我们的应用程序就可以随意使用Unsafe做事情了， 而Unsafe类可以直接操作内存，这是不安全的，
     *   所以JDK开发组特意做了这个限制，不让开发人员在正规渠道使用Unsafe类，而是在rt.jar包里面的核心类中使用Unsafe功能。
     *   如果开发人员真的想要实例化Unsafe类，那该如何做？
     *   使用万能的反射！！！
     */
    // static final Unsafe unsafe = Unsafe.getUnsafe();
    static Unsafe unsafe = null;
    static long stateOffset;
    static {
        try {
            // 使用反射
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            try {
                unsafe = (Unsafe)theUnsafe.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            stateOffset = unsafe.objectFieldOffset(TestUnsafe.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    public static void main(String[] args) {
        TestUnsafe testUnsafe = new TestUnsafe();
        unsafe.compareAndSwapLong(testUnsafe, stateOffset, 0, 1);
        System.out.println(testUnsafe.state);
    }
}
