package com.antonhulevich.testServiceIntergradation.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;

@SpringBootApplication
public class DemoProductClient {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoProductClient.class);
        app.setDefaultProperties(Collections.singletonMap("server.port","8083"));
        ConfigurableApplicationContext context = app.run(args);
    }
}
