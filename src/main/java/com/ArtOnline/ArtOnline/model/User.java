package com.ArtOnline.ArtOnline.model;

import java.util.List;

public class User {

    enum Address_Type {
        Home, Work, Other
    };

    private Integer user_id;
    private String first_name;
    private String last_name;
    private String email_id;
    private String password;

    private String role;

    private List<String> phone_nos;
    private List<UserAddress> user_addresses;

    private Integer gallery_id;

    public Integer getUser_id() {
        return user_id;
    }

    public Integer getGallery_id() {
        return gallery_id;
    }

    public void setGallery_id(Integer gallery_id) {
        this.gallery_id = gallery_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getPhone_nos() {
        return phone_nos;
    }

    public void setPhone_nos(List<String> phone_nos) {
        this.phone_nos = phone_nos;
    }

    public List<UserAddress> getUser_addresses() {
        return user_addresses;
    }

    public void setUser_addresses(List<UserAddress> user_addresses) {
        this.user_addresses = user_addresses;
    }

  
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
