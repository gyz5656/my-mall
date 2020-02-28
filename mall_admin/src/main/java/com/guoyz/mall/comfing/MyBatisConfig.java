package com.guoyz.mall.comfing;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description:
 * @Author:guoyz
 * @Date 2020/2/26 9:13
 */
@Configuration
@EnableTransactionManagement
@MapperScan ({"com.guoyz.mall.mapper"})
public class MyBatisConfig {
}
