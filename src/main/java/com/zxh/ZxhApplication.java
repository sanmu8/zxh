package com.zxh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zxh.mapper")
@SpringBootApplication
public class ZxhApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZxhApplication.class, args);
        System.out.println("后端启动完毕！！！！");
    }

}
