package com.antonhulevich.eshop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean archive;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Bucket bucked;
}
