package com.cus.jastip.bca.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.cus.jastip.bca")
public class FeignConfiguration {

}
