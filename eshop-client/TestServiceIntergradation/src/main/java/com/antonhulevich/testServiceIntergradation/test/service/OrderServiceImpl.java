package com.antonhulevich.testServiceIntergradation.test.service;

import com.antonhulevich.testServiceIntergradation.test.domain.Order;
import org.springframework.stereotype.Service;
import tools.jackson.core.util.DefaultPrettyPrinter;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.cfg.DatatypeFeature;

import java.io.File;

@Service
public class OrderServiceImpl implements OrderService{
    @Override
    public void save(Order order) {

        File orderFolder = new File("D:\\test");
        if (!orderFolder.exists()) {
            orderFolder.mkdirs();
        }

        // Используем именно orserFile (файл с ID в названии)
        File orserFile = new File(orderFolder, order.getOrderId() + ".json");

        ObjectMapper mapper = new ObjectMapper();

        // Исправлено создание PrettyPrinter (стандартный способ)
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

        try {
            // ПРАВИЛЬНО: записываем в ФАЙЛ, а не в папку
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
