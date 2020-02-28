package com.guoyz.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MallAdminApplication {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){ //这里注入了就可以了
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(MallAdminApplication.class, args);
    }

}
