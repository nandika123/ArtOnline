package com.ArtOnline.ArtOnline.model;

import java.util.Date;
import java.util.List;

public class Gallery {

    private Integer gallery_id;
    private String gallery_name;
    private String license;
    private Integer address_id;
    private String email_id;
    private List<String> phone_nos;

    private Address address;

    public Integer getGallery_id() {
        return gallery_id;
    }

    public void setGallery_id(Integer gallery_id) {
        this.gallery_id = gallery_id;
    }

    public String getGallery_name() {
        return gallery_name;
    }

    public void setGallery_name(String gallery_name) {
        this.gallery_name = gallery_name;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public List<String> getPhone_nos() {
        return phone_nos;
    }

    public void setPhone_nos(List<String> phone_nos) {
        this.phone_nos = phone_nos;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
