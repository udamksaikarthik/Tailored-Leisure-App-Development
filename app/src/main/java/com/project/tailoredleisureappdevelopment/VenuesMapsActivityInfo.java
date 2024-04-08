package com.project.tailoredleisureappdevelopment;
/*
Authors: Saikarthik Uda (Technical Lead), Ebere Janet Eboh, Prathyusha Kamma.
 */
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.project.tailoredleisureappdevelopment.entities.Person;
import com.project.tailoredleisureappdevelopment.entities.Place;
import com.project.tailoredleisureappdevelopment.models.Database;
import com.project.tailoredleisureappdevelopment.models.NeedModel;
import com.project.tailoredleisureappdevelopment.models.PersonModel;
import com.project.tailoredleisureappdevelopment.models.ReviewModel;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class VenuesMapsActivityInfo extends AppCompatActivity {

    //Global Variables

    private Place placeObj;

    private EditText venueName;
    private EditText venueWebsite;
    private EditText venuePhone;
    private EditText venueGoogleRating;
    private EditText venueOverallTailoredRating;
    private EditText venueTailoredRating;
    private Button bookBtn;
    private Button reviewVenueBtn;
    private ReviewModel reviewModel;
    private Database db;
    private EditText venueOverallTLRatingEditTxtId;
    private EditText venueTailoredRatingEditTxtId;

    /*
    The onCreate method is the start of the Layout Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_info);

        placeObj = (Place) getIntent().getSerializableExtra("PLACE_OBJECT");
        venueName = (EditText) findViewById(R.id.venueNameEditTxtId);
        venueWebsite = (EditText) findViewById(R.id.venueWebsiteEditTxtId);
        venuePhone = (EditText) findViewById(R.id.venuePhoneEditTxtId);
        venueGoogleRating = (EditText) findViewById(R.id.venueGoogleRatingEditTxtId);
        venueOverallTailoredRating = (EditText) findViewById(R.id.venueOverallTLRatingEditTxtId);
        venueTailoredRating = (EditText) findViewById(R.id.venueTailoredRatingEditTxtId);
        bookBtn = (Button) findViewById(R.id.bookVenueBtn);
        reviewVenueBtn = (Button) findViewById(R.id.reviewVenueBtn);
        venueOverallTLRatingEditTxtId = (EditText) findViewById(R.id.venueOverallTLRatingEditTxtId);
        venueTailoredRatingEditTxtId = (EditText) findViewById(R.id.venueTailoredRatingEditTxtId);
        reviewModel = new ReviewModel();
        db = new Database();

        venueName.setFocusable(false);
        venueWebsite.setFocusable(false);
        venuePhone.setFocusable(false);
        venueGoogleRating.setFocusable(false);
        venueOverallTailoredRating.setFocusable(false);
        venueTailoredRating.setFocusable(false);
        DecimalFormat df = new DecimalFormat("#.0");

        venueName.setText(placeObj.getName());
        venueWebsite.setText(placeObj.getWebsiteUri().toString());
        venuePhone.setText(placeObj.getPhoneNumber());
        venueGoogleRating.setText(placeObj.getRating().toString() +" (" +placeObj.getUserRatingsTotal().toString()+")");

        try {
            ArrayList<Float> overallTailoredLeisureRatingList = reviewModel.getOverallTailoredLeisureRating(db, placeObj.getPlaceId().trim());
            if(!overallTailoredLeisureRatingList.isEmpty()){
                Float avg = 0.0f;
                int countUsers = 0;
                for(int i=0; i<overallTailoredLeisureRatingList.size(); i++){
                    avg = avg + overallTailoredLeisureRatingList.get(i);
                    countUsers++;
                }
                Float overallTailoredLeisureRating= avg/countUsers;
                venueOverallTLRatingEditTxtId.setText(df.format(overallTailoredLeisureRating)+" ("+countUsers+")");

            }else{
                venueOverallTLRatingEditTxtId.setText("No Ratings.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try
        {
            ArrayList<Float> TailoredLeisureRatingList = reviewModel.getTailoredLeisureRating(db, placeObj.getPlaceId().trim(), PersonModel.needIds);
            if(!TailoredLeisureRatingList.isEmpty()){
                Float avg = 0.0f;
                int countUsers = 0;
                for(int i=0; i<TailoredLeisureRatingList.size(); i++){
                    avg = avg + TailoredLeisureRatingList.get(i);
                    countUsers++;
                }
                Float TailoredLeisureRating= avg/countUsers;
                venueTailoredRatingEditTxtId.setText(df.format(TailoredLeisureRating)+" ("+countUsers+")");

            }else{
                venueTailoredRatingEditTxtId.setText("No Ratings.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        bookBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse(venueWebsite.getText().toString());

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        reviewVenueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG: VenuesMapsActivityInfo","Place Id: "+placeObj.getPlaceId().trim());
                openReviewAndRatingActivity();
            }
        });


    }

    private void openReviewAndRatingActivity() {
        Intent intent = new Intent(VenuesMapsActivityInfo.this, ReviewAndRatingActivity.class);
        intent.putExtra("PLACE_OBJECT", (Serializable) placeObj);
        startActivity(intent);
    }


}
