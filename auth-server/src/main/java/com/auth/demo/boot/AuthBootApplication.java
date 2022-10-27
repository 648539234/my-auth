package com.auth.demo.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wuyuxiang
 * @version 1.0.0
 * @package com.auth.demo.boot
 * @date 2022/10/26 16:14
 */
@SpringBootApplication
@ComponentScan(basePackages={
        "com.auth.demo.oauth","com.auth.demo.security"
})
public class AuthBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthBootApplication.class, args);
    }
}
