package com.h5;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableEurekaClient
@MapperScan(basePackages = {"com.h5.mapper"})
public class test {
    public static void main(String args[]){
        SpringApplication.run(test.class , args);
    }
}