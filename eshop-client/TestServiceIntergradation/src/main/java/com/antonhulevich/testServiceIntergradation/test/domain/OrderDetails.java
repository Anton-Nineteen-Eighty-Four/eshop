package com.antonhulevich.testServiceIntergradation.test.domain;

public class OrderDetails {
    private String products;
    private Double price;
    private Double amount;
    private Double sum;

    public OrderDetails() {
    }

    public OrderDetails(String products, Double price, Double amount, Double sum) {
        this.products = products;
        this.price = price;
        this.amount = amount;
        this.sum = sum;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "products='" + products + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", sum=" + sum +
                '}';
    }
}
