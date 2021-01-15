package com.ArtOnline.ArtOnline.model;

public class Artist {
    private int artist_id;
    private String first_name;
    private String last_name;
    private String email_id;
    private String painting_style;
    private int experience; 
    private String license;

    public int getArtist_id(){
        return artist_id;
    }
    public void setArtist_id(int artist_id){
        this.artist_id= artist_id;
    }
    public String getFirst_name(){
        return first_name;
    }
    public void setFirst_name(String first_name){
        this.first_name= first_name;
    }
    public String getLast_name(){
        return last_name;
    }
    public void setLast_name(String last_name){
        this.last_name= last_name;
    }
    public String getEmail_id(){
        return email_id;
    }
    public void setEmail_id(String email_id){
        this.email_id= email_id;
    }
    public String getPainting_style(){
        return painting_style;
    }
    public void setPainting_style(String painting_style){
        this.painting_style= painting_style;
    }
    public int getExperience(){
        return experience;
    }
    public void setExperience(int experience){
        this.experience= experience;
    }
    public String getLicense(){
        return license;
    }
    public void setLicense(String license){
        this.license= license;
    }
}
