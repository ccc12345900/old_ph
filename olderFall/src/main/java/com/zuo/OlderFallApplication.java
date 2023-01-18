package com.zuo;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.zuo.mapper")
public class OlderFallApplication {


    public static void main(String[] args) {
        SpringApplication.run(OlderFallApplication.class, args);
    }

}
