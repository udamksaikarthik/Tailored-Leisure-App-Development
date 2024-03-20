package com.project.tailoredleisureappdevelopment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class ReviewAndRatingActivity extends AppCompatActivity {
    private Button rateTheVenueBtnId;
    private ConstraintLayout rateTheVenueViewId;
    private Button submitUserReviewId;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_rating);

        rateTheVenueBtnId = (Button) findViewById(R.id.rateTheVenueBtnId);
        rateTheVenueViewId = (ConstraintLayout) findViewById(R.id.rateTheVenueViewId);
        submitUserReviewId = (Button) findViewById(R.id.submitUserReviewId);


        rateTheVenueBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateTheVenueViewId.setVisibility(View.VISIBLE);
            }
        });

        submitUserReviewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateTheVenueViewId.setVisibility(View.GONE);
            }
        });
    }
}
