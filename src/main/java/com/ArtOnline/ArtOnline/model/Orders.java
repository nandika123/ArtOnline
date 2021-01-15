package com.ArtOnline.ArtOnline.model;

import java.math.BigDecimal;
import java.util.List;

public class Orders {

    private Integer order_id;
    private String order_date;
    private BigDecimal bill_amount;
    private String order_time;

    private List<PaintingsOrdered> paintings;

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }


    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public BigDecimal getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(BigDecimal bill_amount) {
        this.bill_amount = bill_amount;
    }


    public List<PaintingsOrdered> getPaintings() {
        return paintings;
    }

    public void setPaintings(List<PaintingsOrdered> paintings) {
        this.paintings = paintings;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }
}
