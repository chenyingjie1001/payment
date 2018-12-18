package com.firesoon.idaweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.firesoon.*")
@MapperScan({"com.firesoon.paymentmapper.*"})
public class IdaWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdaWebApplication.class, args);
    }
}
