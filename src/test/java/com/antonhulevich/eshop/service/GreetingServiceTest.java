package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.ws.greeting.Greeting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

public class GreetingServiceTest {

    @Test
    void generatingGreeting() throws DatatypeConfigurationException {
        //have
        GreetingService greetingService = new GreetingService();

        String name = "Bill";
        LocalDate expectedDate = LocalDate.now();

        //execute
        Greeting greeting = greetingService.generateGreeting(name);

        //check
        Assertions.assertNotNull(greeting);
        Assertions.assertArrayEquals(("Hello, " + name).toCharArray(), greeting.getText().toCharArray());

        XMLGregorianCalendar date = greeting.getDate();
        Assertions.assertEquals(expectedDate.getYear(), date.getYear());
        Assertions.assertEquals(expectedDate.getMonthValue(), date.getMonth());
        Assertions.assertEquals(expectedDate.getDayOfMonth(), date.getDay());
    }
}
