//package com.antonhulevich.testServiceIntergradation;
//
//import com.antonhulevich.testServiceIntergradation.orders.OrderIntegrationConfig;
//import com.antonhulevich.testServiceIntergradation.test.domain.Order;
//import com.antonhulevich.testServiceIntergradation.test.domain.OrderDetails;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//@SpringBootApplication
//public class DemoOrderClient {
//
//    public static void main(String[] args) {
//        SpringApplication app = new SpringApplication(DemoProductClient.class);
//        app.setDefaultProperties(Collections.singletonMap("server.port","8084"));
//        ConfigurableApplicationContext context = app.run(args);
//
//        OrderIntegrationConfig config = context.getBean("orderIntegrationConfig",OrderIntegrationConfig.class);
//
//        DirectChannel orderChannel = config.getOrdersChannel();
//
//        Order order = new Order();
//        order.setOrderId(23235235L);
//        order.setUsername("Bill");
//        order.setAddress("Some Address");
//
//        List<OrderDetails> list = new ArrayList<>();
//
//        list.add(new OrderDetails("Milk",10.0, 2.0, 20.0));
//        list.add(new OrderDetails("Bear", 20.0, 3.0, 15.0));
//
//        order.setDetails(list);
//
//        Message<Order> message = MessageBuilder
//                .withPayload(order)
//                .setHeader("Content-type", "application/json")
//                .build();
//        orderChannel.send(message);
//    }
//}
