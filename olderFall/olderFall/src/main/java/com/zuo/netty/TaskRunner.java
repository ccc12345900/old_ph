package com.zuo.netty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @author xiaocg
 */
@Component
@Order(1)
public class TaskRunner implements CommandLineRunner {

    private HttpServer httpServer = new HttpServer(10050);

    @Override
    public void run(String... args) throws Exception {
        httpServer.bind();
    }
}
