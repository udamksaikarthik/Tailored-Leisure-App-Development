package com.project.tailoredleisureappdevelopment;

import android.annotation.SuppressLint;
import android.media.Rating;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.project.tailoredleisureappdevelopment.entities.Place;
import com.project.tailoredleisureappdevelopment.entities.Review;
import com.project.tailoredleisureappdevelopment.models.Database;
import com.project.tailoredleisureappdevelopment.models.PersonModel;
import com.project.tailoredleisureappdevelopment.models.ReviewModel;

import java.sql.SQLException;
import java.util.ArrayList;


public class ReviewAndRatingActivity extends AppCompatActivity {
    private Button rateTheVenueBtnId;
    private ConstraintLayout rateTheVenueViewId;
    private Button submitUserReviewBtnId;
    private EditText userReviewEditTxtId;
    private RatingBar userRatingBarId;
    private Place placeObj;
    private int personId;
    private String personName;
    private ReviewModel reviewModel;
    private Database db;
    private ArrayList<Review> reviewsList;
    private ListView usersReviewsPlaceListViewId;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_rating);

        placeObj = (Place) getIntent().getSerializableExtra("PLACE_OBJECT");
        rateTheVenueBtnId = (Button) findViewById(R.id.rateTheVenueBtnId);
        rateTheVenueViewId = (ConstraintLayout) findViewById(R.id.rateTheVenueViewId);
        submitUserReviewBtnId = (Button) findViewById(R.id.submitUserReviewBtnId);
        personId = PersonModel.personDetails.getPerson_id();
        personName = PersonModel.personDetails.getFirstName();
        usersReviewsPlaceListViewId = (ListView) findViewById(R.id.usersReviewsPlaceListViewId);
        reviewModel = new ReviewModel();
        reviewsList = new ArrayList<>();
        db = new Database();

        try {
            if(reviewModel.checkIfUserReviewed(db, placeObj.getPlaceId().trim(),personId)){
                rateTheVenueBtnId.setText("Edit Review");
            }else{
                rateTheVenueBtnId.setText("Rate the Venue");
            }
            reviewsList = reviewModel.getPlaceReviews(db, placeObj.getPlaceId().trim());
            ArrayList<String> reviewsToStringList = new ArrayList<>();
            for(Review r: reviewsList){
                reviewsToStringList.add(r.toString());
            }
            // on the below line we are initializing the adapter for our list view.
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.activity_list_view, R.id.listViewReviewTextId, reviewsToStringList);

            // on below line we are setting adapter for our list view.
            usersReviewsPlaceListViewId.setAdapter(adapter);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        rateTheVenueBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersReviewsPlaceListViewId.setVisibility(View.GONE);
                String BtnText = rateTheVenueBtnId.getText().toString().trim();
                rateTheVenueViewId.setVisibility(View.VISIBLE);
                rateTheVenueBtnId.setVisibility(View.GONE);
                userRatingBarId = (RatingBar) findViewById(R.id.userRatingBarId);
                userReviewEditTxtId = (EditText) findViewById(R.id.userReviewEditTxtId);

                if(BtnText.equals("Edit Review")){
                    submitUserReviewBtnId.setText("Update Review");
                    ArrayList<Object> reviewDetails = new ArrayList<>();
                    try {
                        reviewDetails = reviewModel.getuserReviewDetails(db, placeObj.getPlaceId(),personId);
                        userRatingBarId.setRating((Float) reviewDetails.get(0));
                        userReviewEditTxtId.setText(reviewDetails.get(1).toString());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else{

                }
            }
        });

        submitUserReviewBtnId.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                rateTheVenueViewId.setVisibility(View.GONE);
                rateTheVenueBtnId.setVisibility(View.VISIBLE);
                userReviewEditTxtId = (EditText) findViewById(R.id.userReviewEditTxtId);
                userRatingBarId = (RatingBar) findViewById(R.id.userRatingBarId);
                String BtnText = submitUserReviewBtnId.getText().toString().trim();
                String userReview = userReviewEditTxtId.getText().toString();
                Float userRating = userRatingBarId.getRating();
                Log.d("DEBUG: ReviewAndRatingActivity","userReview: "+userReview);
                Log.d("DEBUG: ReviewAndRatingActivity","userRating: "+userRating);
                Log.d("DEBUG: ReviewAndRatingActivity","placeId: "+placeObj.getPlaceId());
                Log.d("DEBUG: ReviewAndRatingActivity","placeName: "+placeObj.getName());
                Log.d("DEBUG: ReviewAndRatingActivity","personId: "+personId);


                if(BtnText.equals("Update Review")){
                    ArrayList<Object> reviewDetails = new ArrayList<>();
                    try {
                        reviewModel.editReview(db, userRating ,userReview, placeObj.getPlaceId(), personId);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    try {
                        reviewModel.addReview(db, userReview, userRating, placeObj.getPlaceId(), placeObj.getName(), personId, personName);
                        rateTheVenueBtnId.setText("Edit Review");
                        submitUserReviewBtnId.setText("Update Review");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                usersReviewsPlaceListViewId.setVisibility(View.VISIBLE);
                // To refresh the current Activity
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
    }
}
