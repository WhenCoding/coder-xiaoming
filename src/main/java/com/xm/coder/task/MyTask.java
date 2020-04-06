package com.xm.coder.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @auther xgs
 * @date 2020/4/5 5:08 下午
 */
@EnableScheduling
@Component
@Slf4j
public class MyTask {
    @Scheduled(fixedRate = 2*1000)
    public void coding(){
        log.info("小明在coding……");
    }
}
