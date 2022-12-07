package com.zuo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 橙宝cc
 * @date 2022/12/7 - 13:05
 */
@Configuration
public class RabbitMqConfig {
    //1.工作队列模式
    //声明队列，同时交给spring
    @Bean(name = "work-queue")
    public Queue queue0(){
        return new Queue("work-queue");
    }
}
