package com.zuo.common;

import com.zuo.OlderFallApplication;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 橙宝cc
 * @date 2022/12/7 - 14:12
 */

public class RabbitMqSend {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(){
        rabbitTemplate.convertAndSend("work-queue","这是qq一条消息！！");
    }
}
