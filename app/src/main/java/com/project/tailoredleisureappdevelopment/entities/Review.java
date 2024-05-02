package com.project.tailoredleisureappdevelopment.entities;
/*
Authors: Saikarthik Uda (Technical Lead), Ebere Janet Eboh, Prathyusha Kamma.
 */
import java.sql.Blob;
import java.util.Date;
/*
This is a entity class for Review table.
This is similar to an pojo class in java.
 */
public class Review {

    private int reviewId;
    private String placeId;
    private float rating;
    private int personId;
    private Date reviewedDate;
    private boolean approved;
    private String review;
    private String placeName;
    private String personName;
    private byte[] userImage;

    public Review(){

    }

    @Override
    public String toString() {
        return  " Reviewed By: " + personName +
                "\nRating: " + rating+
                "\nReviewed Date: " + reviewedDate +
                "\nApproved: " + approved +
                "\nReview: '" + review +"'."
                ;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public Date getReviewedDate() {
        return reviewedDate;
    }

    public void setReviewedDate(Date reviewedDate) {
        this.reviewedDate = reviewedDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
