package com.antonhulevich.eshop.dto;

import com.antonhulevich.eshop.domain.Role;

public class UserDto {
    private Long id;
    private Role role;
    private String username;
    private String password;
    private String matchingPassword;
    private String email;
    private boolean activated;

    public UserDto() {
    }

    public UserDto(Long id, Role role, String username, String password, String matchingPassword, String email, boolean activated) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.password = password;
        this.matchingPassword = matchingPassword;
        this.email = email;
        this.activated = activated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
