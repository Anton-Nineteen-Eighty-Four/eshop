package com.antonhulevich.eshopClient.service;

import com.antonhulevich.eshopClient.domain.Order;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

import java.io.File;

@Service
public class OrderServiceImpl implements OrderService{
    @Override
    public void save(Order order) {

        File orderFolder = new File("D:\\test");
        if (!orderFolder.exists()) {
            orderFolder.mkdirs();
        }

        File orserFile = new File(orderFolder, order.getOrderId() + ".json");

        ObjectMapper mapper = new ObjectMapper();

        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

        try {
            writer.writeValue(orserFile, order);
            System.out.println("Файл успешно сохранен: " + orserFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Ошибка при записи файла: " + e.getMessage());
            e.printStackTrace();
            // В Integration важно пробрасывать исключение, чтобы транзакция/канал знали о сбое
            throw new RuntimeException("Failed to save order to file", e);
        }

    }
}
