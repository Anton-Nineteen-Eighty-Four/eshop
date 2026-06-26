package com.antonhulevich.eshopClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
public class EshopClientApplication {

	public static void main(String[] args) {
        SpringApplication.run(EshopClientApplication.class, args);
	}

}
