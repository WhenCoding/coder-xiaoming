package com.xm.coder.tryWithResources;

/**
 * @auther xgs
 * @date 2020/4/12 11:17 下午
 */

public class XiaoMingResource implements AutoCloseable {
    public XiaoMingResource() {
        System.out.println("小明：我已经打开王者荣耀，创建好房间了，在线等……");
    }

    public void playHonorOfKings() {
        System.out.println("小明：我打野！");
    }
    @Override
    public void close() throws Exception {
        System.out.println("小明：小红，晚安");
    }
}
