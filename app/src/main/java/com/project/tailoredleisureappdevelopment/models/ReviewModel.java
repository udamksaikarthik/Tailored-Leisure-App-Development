package com.project.tailoredleisureappdevelopment.models;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.project.tailoredleisureappdevelopment.entities.Review;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReviewModel {
    private Connection conn;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addReview(Database db, String userReview, Float userRating, String placeId, String placeName, int personId, String personName) throws SQLException {
        LocalDate currentDate = LocalDate.now();
        conn = db.getConnection();
        Log.d("DEBUG: ReviewModel", "Inside addReview method.");
        String addReviewQuery = "INSERT INTO REVIEW (place_id, person_id, rating, review, place_name, reviewed_date, person_name) VALUES ('"+placeId.trim()+"', '"+personId+"', '"+userRating+"', '"+userReview.trim()+"', '"+placeName.trim()+"', '"+currentDate+"','"+personName+"')";
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


    public void editReview(Database db, Float userRating, String userReview, String placeId, int personId) throws SQLException{
        conn = db.getConnection();
        String editReviewQuery = "UPDATE REVIEW SET RATING = '"+userRating+"', REVIEW = '"+userReview+"' WHERE PLACE_ID = '"+placeId.trim()+"' AND PERSON_ID = '"+personId+"'";
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

    public ArrayList<Review> getPlaceReviews(Database db, String placeId) throws SQLException{
        ArrayList<Review> reviewList = new ArrayList<>();
        conn = db.getConnection();
        String getPlaceReviews = "SELECT * FROM REVIEW WHERE PLACE_ID = '"+ placeId.trim()+"'";
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

    public ArrayList<Float> getOverallTailoredLeisureRating(Database db, String placeId) throws SQLException{
        ArrayList<Float> getOverallTailoredLeisureRatingList = new ArrayList<>();
        conn = db.getConnection();
        String getOverallTailoredLeisureRatingQuery = "SELECT RATING FROM REVIEW WHERE PLACE_ID = '"+ placeId.trim()+"'";
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

    public ArrayList<Float> getTailoredLeisureRating(Database db, String placeId, ArrayList<String> userNeeds) throws SQLException{
        ArrayList<Float> getOverallTailoredLeisureRatingList = new ArrayList<>();
        conn = db.getConnection();
        Log.d("DEBUG: ReviewModel", "userNeeds: "+userNeeds);
        for(String userNeed: userNeeds) {
            String getTailoredLeisureRatingQuery = "SELECT R.RATING FROM REVIEW R JOIN PERSON P ON P.PERSON_ID = R.PERSON_ID JOIN PERSON_NEED PN ON PN.PERSON_ID = R.PERSON_ID JOIN NEED N ON N.NEED_ID = PN.NEED_ID WHERE R.PLACE_ID = '"+ placeId.trim()+"' AND N.NEED_SHORT_DESC ='"+userNeed.trim()+"'";
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
        db.closeConnection(conn);
        return getOverallTailoredLeisureRatingList;
    }
}
