package com.antonhulevich.eshop.domain;

import jakarta.persistence.*;

import java.util.List;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
@Entity
@Table(name = "buckets")
public class Bucket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "buckets_products",
            joinColumns = @JoinColumn(name = "bucket_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    public Bucket() {
    }

    public Bucket(User user, List<Product> products) {
        this.user = user;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
