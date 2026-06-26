package com.antonhulevich.testServiceIntergradation.test.domain;

import java.util.List;

public class Order {
    private Long orderId;
    private String username;
    private String address;
    private List<OrderDetails> details;

    public Order() {
    }

    public Order(Long orderId, String username, String address, List<OrderDetails> details) {
        this.orderId = orderId;
        this.username = username;
        this.address = address;
        this.details = details;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderDetails> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetails> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", details=" + details +
                '}';
    }
}
