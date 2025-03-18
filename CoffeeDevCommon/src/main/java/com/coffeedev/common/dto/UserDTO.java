package com.coffeedev.common.dto;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String photo;
    private boolean enabled;
    private Set<String> roles = new HashSet<>();
    
    public UserDTO() {
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public Set<String> getRoles() {
        return roles;
    }
    
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
    
    public String getPhotosImagePath() {
        if (id == null || photo == null) return "/images/default-user.png";
        return "/user-photos/" + this.id + "/" + this.photo;
    }
} 