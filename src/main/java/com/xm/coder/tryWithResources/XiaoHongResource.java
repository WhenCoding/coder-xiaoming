package com.xm.coder.tryWithResources;

/**
 * @auther xgs
 * @date 2020/4/12 11:17 下午
 */

public class XiaoHongResource implements AutoCloseable {
    public XiaoHongResource() {
        System.out.println("小红：我已经上线，赶快邀请！");
    }

    public void playHonorOfKings() {
        System.out.println("小红：我玩法师");
    }
    @Override
    public void close() throws Exception {
        System.out.println("小红：小明，晚安");
    }
}
