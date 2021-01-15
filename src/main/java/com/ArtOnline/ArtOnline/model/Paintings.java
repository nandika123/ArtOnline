package com.ArtOnline.ArtOnline.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Paintings {

    @Autowired
    JdbcTemplate jt;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jt = jdbcTemplate;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    private int painting_id;
    private String name;
    private int is_framed;
    private int artist_id;
    private float length;
    private float breadth;
    private double price;
    private String image_path;

    public int getPainting_id() {
        return painting_id;
    }

    public void setPainting_id(int painting_id) {
        this.painting_id = painting_id;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public int getIs_framed() {
        return is_framed;
    }

    public void setIs_framed(int is_framed) {
        this.is_framed = is_framed;
    }
    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }
    public float getBreadth() {
        return breadth;
    }

    public void setBreadth(float breadth) {
        this.breadth = breadth;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
