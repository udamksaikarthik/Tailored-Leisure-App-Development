package com.project.tailoredleisureappdevelopment.entities;
/*
Authors: Saikarthik Uda (Technical Lead), Ebere Janet Eboh, Prathyusha Kamma.
 */
import java.io.Serializable;
/*
This is similar to a pojo class in java for PLACES information.
 */
public class Place implements Serializable{
    private String address;
    private String name;
    private Double rating;
    private String phoneNumber;
    private Integer userRatingsTotal;
    private String websiteUri;
    private String placeId;

    public Place() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getUserRatingsTotal() {
        return userRatingsTotal;
    }

    public void setUserRatingsTotal(Integer userRatingsTotal) {
        this.userRatingsTotal = userRatingsTotal;
    }

    public String getWebsiteUri() {
        return websiteUri;
    }

    public void setWebsiteUri(String websiteUri) {
        this.websiteUri = websiteUri;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
