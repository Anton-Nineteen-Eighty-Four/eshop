package com.antonhulevich.eshopClient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("integration/http-orders-integration.xml")
public class OrderIntegrationConfig {
}
