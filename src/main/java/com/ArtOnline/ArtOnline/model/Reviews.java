package com.ArtOnline.ArtOnline.model;

import java.math.BigDecimal;

public class Reviews {

    private String comment;
    private BigDecimal rating;
    private Integer review_id;
    private Integer painting_id;

    public String getComment() {
        return comment;
    }

    public Integer getPainting_id() {
        return painting_id;
    }

    public void setPainting_id(Integer painting_id) {
        this.painting_id = painting_id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Integer getReview_id() {
        return review_id;
    }

    public void setReview_id(Integer review_id) {
        this.review_id = review_id;
    }

}
