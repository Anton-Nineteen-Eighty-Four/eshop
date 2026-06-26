package com.antonhulevich.testServiceIntergradation.test.integration;

import com.antonhulevich.testServiceIntergradation.test.domain.Product;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;


//это шлюз(куда мы хотим доставить)
@MessagingGateway
public interface ChannelGetaway {
    @Gateway(requestChannel = "channel")
    void process(Product product);
}
