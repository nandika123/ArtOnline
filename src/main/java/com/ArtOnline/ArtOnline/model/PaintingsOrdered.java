package com.ArtOnline.ArtOnline.model;

import java.math.BigDecimal;

public class PaintingsOrdered {
    private int painting_id;
    private Integer user_id;
    private Integer order_id;
    private String painting_status;

    
    public String getPainting_status() {
        return painting_status;
    }

    public void setPainting_status(String painting_status) {
        this.painting_status = painting_status;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public int getPainting_id() {
        return painting_id;
    }

    public void setPainting_id(int painting_id) {
        this.painting_id = painting_id;
    }
}
