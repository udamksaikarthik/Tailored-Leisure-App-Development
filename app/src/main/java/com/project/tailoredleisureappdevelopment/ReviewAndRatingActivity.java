package com.project.tailoredleisureappdevelopment;
/*
Authors: Saikarthik Uda (Technical Lead), Ebere Janet Eboh, Prathyusha Kamma.
 */
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Rating;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.project.tailoredleisureappdevelopment.entities.Place;
import com.project.tailoredleisureappdevelopment.entities.Review;
import com.project.tailoredleisureappdevelopment.models.Database;
import com.project.tailoredleisureappdevelopment.models.PersonModel;
import com.project.tailoredleisureappdevelopment.models.ReviewModel;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;


public class ReviewAndRatingActivity extends AppCompatActivity {
    //Global Variables
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
    private Button addImageBtnId;
    private ImageView imageView;

    private TextView loading_review_id;
    private RatingBar review_rating_id;
    /*
    The onCreate method is the start of the Layout Activity
     */
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_rating);

        final Handler handler = new Handler();
        placeObj = (Place) getIntent().getSerializableExtra("PLACE_OBJECT");
        rateTheVenueBtnId = (Button) findViewById(R.id.rateTheVenueBtnId);
        review_rating_id = (RatingBar) findViewById(R.id.review_rating_id);
        rateTheVenueViewId = (ConstraintLayout) findViewById(R.id.rateTheVenueViewId);
        loading_review_id = (TextView) findViewById(R.id.loading_review_id);
        submitUserReviewBtnId = (Button) findViewById(R.id.submitUserReviewBtnId);
        personId = PersonModel.personDetails.getPerson_id();
        personName = PersonModel.personDetails.getFirstName();
        usersReviewsPlaceListViewId = (ListView) findViewById(R.id.usersReviewsPlaceListViewId);
        reviewModel = new ReviewModel();
        reviewsList = new ArrayList<>();
        db = new Database();
        addImageBtnId = (Button) findViewById(R.id.addImageBtnId);
        imageView = (ImageView) findViewById(R.id.imageView);
        rateTheVenueBtnId.setVisibility(View.GONE);

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
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
                            ReviewAdapter reviewAdapter = new ReviewAdapter(ReviewAndRatingActivity.this, R.layout.activity_list_view, reviewsList);
                            rateTheVenueBtnId.setVisibility(View.VISIBLE);
                            loading_review_id.setVisibility(View.GONE);

                            // on below line we are setting adapter for our list view.
                            usersReviewsPlaceListViewId.setAdapter(reviewAdapter);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        };

        Thread thread1 = new Thread(runnable1);
        thread1.start();



        rateTheVenueBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersReviewsPlaceListViewId.setVisibility(View.GONE);
                String BtnText = rateTheVenueBtnId.getText().toString().trim();
                rateTheVenueViewId.setVisibility(View.VISIBLE);
                rateTheVenueBtnId.setVisibility(View.GONE);
                userRatingBarId = (RatingBar) findViewById(R.id.userRatingBarId);
                userReviewEditTxtId = (EditText) findViewById(R.id.userReviewEditTxtId);

                addImageBtnId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 3);
                    }
                });

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
                imageView = (ImageView) findViewById(R.id.imageView);
                byte[] imageByte = new byte[0];
                Log.d("DEBUG: ReviewAndRatingActivity","imageView: "+imageView);
                Log.d("DEBUG: ReviewAndRatingActivity","imageView.getDrawable(): "+imageView.getDrawable());
                if(imageView.getDrawable() != null){
                    Log.d("DEBUG: ReviewAndRatingActivity","imageView is not null");
                    Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    if(bitmap!=null){
                        Log.d("DEBUG: ReviewAndRatingActivity","bitmap: "+bitmap);
                        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArray);
                        imageByte = byteArray.toByteArray();
                        Log.d("DEBUG: ReviewAndRatingActivity","imageByte: "+imageByte);
//                    String imageByteString = "";
//                    // Iterate over the byte array and print each element
//                    for (byte b : imageByte) {
//                        imageByteString = imageByteString + (b + " ");
//                    }
//                    Log.d("DEBUG: ReviewAndRatingActivity","imageByteString: "+imageByteString);
                    }else{

                    }
                }else{
                    Log.d("DEBUG: ReviewAndRatingActivity","imageView is null");
                    imageByte = null;
                }


                Log.d("DEBUG: ReviewAndRatingActivity","userReview: "+userReview);
                Log.d("DEBUG: ReviewAndRatingActivity","userRating: "+userRating);
                Log.d("DEBUG: ReviewAndRatingActivity","placeId: "+placeObj.getPlaceId());
                Log.d("DEBUG: ReviewAndRatingActivity","placeName: "+placeObj.getName());
                Log.d("DEBUG: ReviewAndRatingActivity","personId: "+personId);


                if(BtnText.equals("Update Review")){
                    ArrayList<Object> reviewDetails = new ArrayList<>();
                    try {
                        reviewModel.editReview(db, userRating ,userReview, placeObj.getPlaceId(), personId, imageByte);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    try {
                        reviewModel.addReview(db, userReview, userRating, placeObj.getPlaceId(), placeObj.getName(), personId, personName, imageByte);
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

    public void onActivityResult(int RequestCode, int resultCode, Intent data) {
        super.onActivityResult(RequestCode, resultCode, data);
        if(resultCode==RESULT_OK & data!=null){
            Uri selectedImage = data.getData();
            imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageURI(selectedImage);
        }
    }
}
