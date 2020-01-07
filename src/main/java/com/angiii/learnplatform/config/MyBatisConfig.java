package com.angiii.learnplatform.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 配置类
 */
@Configuration
@MapperScan("com.angiii.learnplatform.dao")
public class MyBatisConfig {
}
