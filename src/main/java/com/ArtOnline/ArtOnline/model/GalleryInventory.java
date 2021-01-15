package com.ArtOnline.ArtOnline.model;

import java.math.BigDecimal;

public class GalleryInventory {

    private int painting_id;
    private BigDecimal price;

    private int gallery_id;
    private Gallery gallery;
    private String status;

    public int getPainting_id() {
        return painting_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPainting_id(int painting_id) {
        this.painting_id = painting_id;
    }

    public int getGallery_id() {
        return gallery_id;
    }

    public void setGallery_id(int gallery_id) {
        this.gallery_id = gallery_id;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

}
