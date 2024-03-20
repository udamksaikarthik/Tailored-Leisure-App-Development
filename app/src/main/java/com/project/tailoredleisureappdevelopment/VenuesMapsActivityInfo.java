package com.project.tailoredleisureappdevelopment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.project.tailoredleisureappdevelopment.entities.Place;


public class VenuesMapsActivityInfo extends AppCompatActivity {

    private Place placeObj;

    private EditText venueName;
    private EditText venueWebsite;
    private EditText venuePhone;
    private EditText venueGoogleRating;
    private EditText venueOverallTailoredRating;
    private EditText venueTailoredRating;
    private Button bookBtn;
    private Button reviewVenueBtn;

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

        venueName.setFocusable(false);
        venueWebsite.setFocusable(false);
        venuePhone.setFocusable(false);
        venueGoogleRating.setFocusable(false);
        venueOverallTailoredRating.setFocusable(false);
        venueTailoredRating.setFocusable(false);

        venueName.setText(placeObj.getName());
        venueWebsite.setText(placeObj.getWebsiteUri().toString());
        venuePhone.setText(placeObj.getPhoneNumber());
        venueGoogleRating.setText(placeObj.getRating().toString() +"(" +placeObj.getUserRatingsTotal().toString()+")");

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
                openReviewAndRatingActivity();
            }
        });


    }

    private void openReviewAndRatingActivity() {
        Intent intent = new Intent(VenuesMapsActivityInfo.this, ReviewAndRatingActivity.class);
        startActivity(intent);
    }


}
