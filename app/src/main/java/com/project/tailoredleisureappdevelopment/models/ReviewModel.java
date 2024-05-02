package com.project.tailoredleisureappdevelopment.models;
/*
Authors: Saikarthik Uda (Technical Lead), Ebere Janet Eboh, Prathyusha Kamma.
 */

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.project.tailoredleisureappdevelopment.ReviewAndRatingActivity;
import com.project.tailoredleisureappdevelopment.entities.Review;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
/*
This is Review Table Model class.
It helps with database commands for Review Table.
 */
public class ReviewModel {
    private Connection conn;

    /*
    The addReview method helps to add a review into REVIEW table.

     * @param (db the Object of Database Class, userReview is a string value of user provided review on UI,
                userRating is a Float value of user provided rating on UI, placeId is a String value which is unique Place/Venue location ID,
                placeName is a string value which is a venue/place location name, personId is a int value which is unique person id from person table,
                personName is person name from person table, imageByte is byteArray version of image information that user provided).
     * @throws SQLException if there is an issue with database connection/ Query.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addReview(Database db, String userReview, Float userRating, String placeId, String placeName, int personId, String personName, byte[] imageByte) throws SQLException {
        LocalDate currentDate = LocalDate.now();
        conn = db.getConnection();
        Log.d("DEBUG: ReviewModel", "Inside addReview method.");
        String addReviewQuery = "INSERT INTO REVIEW (place_id, person_id, rating, review, place_name, reviewed_date, person_name, user_image) VALUES ('"+placeId.trim()+"', '"+personId+"', '"+userRating+"', '"+userReview.trim()+"', '"+placeName.trim()+"', '"+currentDate+"','"+personName+"', '"+imageByte+"')";
        Log.d("DEBUG: ReviewModel", "addReviewQuery: "+addReviewQuery);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            Boolean checkIfUserReviwedFlg = checkIfUserReviewed(db, placeId, personId);
            if (!checkIfUserReviwedFlg) {
                stmt.executeUpdate(addReviewQuery);
                Log.d("DEBUG: ReviewModel", "addReviewQuery Successful.");
            }else{
                Log.d("DEBUG: ReviewModel", "User Already Reviewed on this Place");
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        db.closeConnection(conn);
    }

    /*
    The checkIfUserReviewed method helps to check if user has already reviewed a place/ venue.

     * @param (db the Object of Database Class, placeId is a String value which is unique Place/Venue location ID,
                personId is a int value which is unique person id from person table ).
     * @return the boolean value (True/ False).
     * @throws SQLException if there is an issue with database connection/ Query.
     */
    public Boolean checkIfUserReviewed(Database db, String placeId, int personId) throws SQLException {
        Boolean flg = true;
        conn = db.getConnection();
        String checkPersonReviewQuery = "SELECT * FROM REVIEW WHERE PLACE_ID = '"+ placeId.trim()+"' AND PERSON_ID = '"+personId+"'";
        Log.d("DEBUG: ReviewModel", "checkPersonReviewQuery: "+checkPersonReviewQuery);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(checkPersonReviewQuery);
            if (!rs.isBeforeFirst() ) {
                flg = false;
            }else{
                flg = true;
                Log.d("DEBUG: ReviewModel", "User Already Reviewed on this Place");
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        db.closeConnection(conn);
        return flg;
    }

    /*
    The getuserReviewDetails method helps to fetch review details of user from database from REVIEW table.

     * @param (db the Object of Database Class, placeId is a String value which is unique Place/Venue location ID,
                personId is a int value which is unique person id from person table ).
     * @return the ArrayList of Objects.
     * @throws SQLException if there is an issue with database connection/ Query.
     */
    public ArrayList<Object> getuserReviewDetails(Database db, String placeId, int personId) throws SQLException {
        ArrayList<Object> userReviewDetails = new ArrayList<>();
        conn = db.getConnection();
        String getPersonRatingReviewQuery = "SELECT RATING, REVIEW FROM REVIEW WHERE PLACE_ID = '"+ placeId.trim()+"' AND PERSON_ID = '"+personId+"'";
        Log.d("DEBUG: ReviewModel", "getPersonRatingReviewQuery: "+getPersonRatingReviewQuery);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getPersonRatingReviewQuery);
            while (rs.next()){
                userReviewDetails.add(rs.getFloat("RATING"));
                userReviewDetails.add(rs.getString("REVIEW"));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        db.closeConnection(conn);
        return userReviewDetails;
    }


    /*
    The editReview method helps to edit the user review on a specific venue/ place.

     * @param (db the Object of Database Class, userReview is a string value of user provided review on UI,
                userRating is a Float value of user provided rating on UI, placeId is a String value which is unique Place/Venue location ID,
                personId is a int value which is unique person id from person table,
                imageByte is byteArray version of image information that user provided).
     * @throws SQLException if there is an issue with database connection/ Query.
     */
    public void editReview(Database db, Float userRating, String userReview, String placeId, int personId, byte[] imageByte) throws SQLException{
        conn = db.getConnection();
        String editReviewQuery = "UPDATE REVIEW SET RATING = '"+userRating+"', REVIEW = '"+userReview+"', USER_IMAGE = '"+imageByte+"' WHERE PLACE_ID = '"+placeId.trim()+"' AND PERSON_ID = '"+personId+"'";
        Log.d("DEBUG: ReviewModel", "editReviewQuery: "+editReviewQuery);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(editReviewQuery);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        db.closeConnection(conn);
    }

    /*
    The getPlaceReviews method helps to fetch all user reviews on a specific venue/place.

     * @param (db the Object of Database Class, placeId is a String value which is unique Place/Venue location ID).
     * @return the ArrayList of Reviews.
     * @throws SQLException if there is an issue with database connection/ Query.
     */
    public ArrayList<Review> getPlaceReviews(Database db, String placeId) throws SQLException{
        ArrayList<Review> reviewList = new ArrayList<>();
        conn = db.getConnection();
        String getPlaceReviews = "SELECT * FROM REVIEW WHERE PLACE_ID = '"+ placeId.trim()+"' AND APPROVED = 'true'";
        Log.d("DEBUG: ReviewModel", "getPlaceReviews: "+getPlaceReviews);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getPlaceReviews);
            while(rs.next()){
                Review r = new Review();
                r.setPlaceId(rs.getString("PLACE_ID"));
                r.setPersonId(rs.getInt("PERSON_ID"));
                r.setReview(rs.getString("REVIEW"));
                r.setReviewId(rs.getInt("REVIEW_ID"));
                r.setReviewedDate(rs.getDate("REVIEWED_DATE"));
                r.setApproved(rs.getBoolean("APPROVED"));
                r.setPersonName(rs.getString("PERSON_NAME"));
                r.setRating(rs.getFloat("RATING"));
                r.setPlaceName(rs.getString("PLACE_NAME"));
                r.setUserImage(rs.getBytes("USER_IMAGE"));
                Log.d("DEBUG: ReviewModel", "User Review: "+r.toString());
                reviewList.add(r);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        db.closeConnection(conn);
        return reviewList;
    }

    // Helper method to convert byte array to hexadecimal string
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // Helper method to convert hexadecimal string to byte array
    private byte[] hexToBytes(String hexString) {
        int len = hexString.length();
        byte[] byteArray = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return byteArray;
    }

    /*
    The getOverallTailoredLeisureRating method helps to calculate overall TL users rating from the database.

     * @param (db the Object of Database Class, placeId is a String value which is unique Place/Venue location ID).
     * @return the ArrayList of Rating Float values.
     * @throws SQLException if there is an issue with database connection/ Query.
     */
    public ArrayList<Float> getOverallTailoredLeisureRating(Database db, String placeId) throws SQLException{
        ArrayList<Float> getOverallTailoredLeisureRatingList = new ArrayList<>();
        conn = db.getConnection();
        String getOverallTailoredLeisureRatingQuery = "SELECT RATING FROM REVIEW WHERE PLACE_ID = '"+ placeId.trim()+"' AND APPROVED = 'true'";
        Log.d("DEBUG: ReviewModel", "getOverallTailoredLeisureRatingQuery: "+getOverallTailoredLeisureRatingQuery);
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getOverallTailoredLeisureRatingQuery);
            while(rs.next()){
                getOverallTailoredLeisureRatingList.add(rs.getFloat("RATING"));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        db.closeConnection(conn);
        return getOverallTailoredLeisureRatingList;
    }

    /*
    The getTailoredLeisureRating method helps to calculate Tailored Rating of specific user needs from the database.

     * @param (db the Object of Database Class, placeId is a String value which is unique Place/Venue location ID,
                userNeeds the ArrayList of String containing userneed values).
     * @return the ArrayList of Rating Float values.
     * @throws SQLException if there is an issue with database connection/ Query.
     */
    public ArrayList<Float> getTailoredLeisureRating(Database db, String placeId, ArrayList<String> userNeeds) throws SQLException{
        ArrayList<Float> getOverallTailoredLeisureRatingList = new ArrayList<>();
        conn = db.getConnection();
        if(userNeeds!=null){
            Log.d("DEBUG: ReviewModel", "userNeeds: "+userNeeds);
            for(String userNeed: userNeeds) {
                String getTailoredLeisureRatingQuery = "SELECT R.RATING FROM REVIEW R JOIN PERSON P ON P.PERSON_ID = R.PERSON_ID JOIN PERSON_NEED PN ON PN.PERSON_ID = R.PERSON_ID JOIN NEED N ON N.NEED_ID = PN.NEED_ID WHERE R.PLACE_ID = '"+ placeId.trim()+"' AND N.NEED_SHORT_DESC ='"+userNeed.trim()+"' AND R.APPROVED = 'true'";
                Log.d("DEBUG: ReviewModel", "getTailoredLeisureRatingQuery: "+getTailoredLeisureRatingQuery);
                Statement stmt = null;
                try {
                    stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(getTailoredLeisureRatingQuery);
                    while(rs.next()){
                        getOverallTailoredLeisureRatingList.add(rs.getFloat("RATING"));
                    }
                }catch (SQLException e) {
                    System.out.println(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
        db.closeConnection(conn);
        return getOverallTailoredLeisureRatingList;
    }

}
