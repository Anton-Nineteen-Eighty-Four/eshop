# E-Shop Microservices

Проект представляет собой онлайн-магазин, спроектированный по микросервисной архитектуре на базе Spring Boot. Система разделена на независимые модули для обеспечения слабой связанности и масштабируемости.

## 🏗 Архитектура проекта

Проект организован в виде Maven-монорепозитория (Multi-module project):

*   **eshop/** — основной бэкенд-сервис интернет-магазина. Отвечает за каталог товаров, корзину, пользователей, безопасность (Spring Security) и веб-интерфейс (Thymeleaf/JS).
*   **eshop-client/** — изолированный микросервис обработки и агрегации заказов, принимающий данные от основного сервера через механизмы интеграции.

## ⚙️ Основной функционал (Functionality)

*   Регистрация и аутентификация пользователей (User authentication and registration)
*   Защита веб-приложения и разграничение ролей (Web application protection & Spring Security)
*   Просмотр каталога(Product preview)
*   Управление корзиной: добавление и удаление (Basket management)
*   Оформление заказов и интеграция между сервисами (Order generation & Microservice integration)
*   Валидация входных данных (Validation of input data)
*   Уведомления пользователей по Email (Notification by email)

## 🛠 Стек технологий (Tech Stack)

*   **Language:** Java 17+
*   **Frameworks:** Spring Boot, Spring Data JPA, Spring Security, Spring Integration
*   **Build Tool:** Maven (Multi-module configuration)
*   **Database & Migrations:** MySQL / Flyway
*   **Frontend:** Thymeleaf, JavaScript, WebSockets
*   **Other:** SOAP (XSD code generation), AOP (Logging), MapStruct, BigDecimal for pricing

## 🚀 Инструкция по локальному запуску (How to run)

1. **Клонируйте репозиторий:**
   ```bash
   git clone https://github.com
   cd eshop
   ```

2. **Соберите весь проект целиком из корня:**
   ```bash
   mvn clean install
   ```

3. **Запуск сервисов:**
   * Запустите основной сервер: класс `EshopApplication` внутри модуля `eshop`.
   * Запустите сервис заказов: класс `EshopClientApplication` внутри модуля `eshop-client`.
