package com.antonhulevich.testServiceIntergradation.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;

import java.util.Collections;

@SpringBootApplication
@IntegrationComponentScan
public class DemoServiceIntergradationApplication {

	public static void main(String[] args) throws InterruptedException {
        //SpringApplication.run(DemoServiceIntergradationApplication.class, args);

        //ConfigurableApplicationContext context = SpringApplication.run(DemoServiceIntergradationApplication.class);
        SpringApplication app = new SpringApplication(DemoServiceIntergradationApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8090"));
        app.run(args);

//----------------------------------------------------------------------------------------------------------

        //отсюда отправляем объект имитируя сторонний сервис в шлюз ChannelGetaway
        //а активатор FirstServiceActivator берет их из шлюза и выводит в консоль
//        ChannelGetaway channelGetaway = context.getBean(ChannelGetaway.class);
//        channelGetaway.process(new Product("Milk", 34.34));
//        channelGetaway.process(new Product("Cholate", 114.34));
//----------------------------------------------------------------------------------------------------------

//        DirectChannel invokeCallGetProducts = context.getBean("invokeCallGetProducts", DirectChannel.class);
//        invokeCallGetProducts.send(MessageBuilder.withPayload("").build());//пустое сообщение для инитиализации
//
//        PollableChannel productChanel = context.getBean("get_products_channel", PollableChannel.class);
//        Message<?> receive = productChanel.receive();
//        System.out.println(receive);
//        System.out.println(receive.getPayload());
//----------------------------------------------------------------------------------------------------------

//        ChannelGetaway channelGetaway = context.getBean(ChannelGetaway.class);
//        channelGetaway.process(new Product("Milk", 34.34));
//        channelGetaway.process(new Product("Cholate", 114.34));
//
//        DirectChannel invokeCallGetProducts = context.getBean("invokeCallGetProducts", DirectChannel.class);
//        invokeCallGetProducts.send(MessageBuilder.withPayload("").build());//пустое сообщение для инитиализации
//
//        PollableChannel productChanel = context.getBean("get_products_channel", PollableChannel.class);
//        Message<?> receive = productChanel.receive();
//        System.out.println(receive);
//        System.out.println(receive.getPayload());

//----------------------------------------------------------------------------------------------------------



	}

}
