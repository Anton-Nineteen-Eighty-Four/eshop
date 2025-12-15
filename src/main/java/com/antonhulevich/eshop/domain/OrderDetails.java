package com.antonhulevich.eshop.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
@Entity
@Table(name = "orders_details")
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public OrderDetails() {
    }

    public OrderDetails(BigDecimal price, BigDecimal amount, Order order, Product product) {
        this.price = price;
        this.amount = amount;
        this.order = order;
        this.product = product;
    }

    public OrderDetails(Order order, Product product, Long amount) {
        this.order = order;
        this.product = product;
        this.amount = new BigDecimal(amount);
        this.price = product.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
