package com.project.tailoredleisureappdevelopment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.tailoredleisureappdevelopment.models.Place;

public class VenuesMapsActivityInfo extends AppCompatActivity {

    private TextView txt_venue_info_id;
    private Place placeObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_info);

        placeObj = (Place) getIntent().getSerializableExtra("PLACE_OBJECT");
        txt_venue_info_id = (TextView) findViewById(R.id.txt_venue_info_id);

        txt_venue_info_id.setText("Venue Name: "+placeObj.getName()+
                "\n\n Address: "+placeObj.getAddress()+
                "\n\n Phone Number: "+placeObj.getPhoneNumber()+
                "\n\n Website: "+placeObj.getWebsiteUri().toString()+
                "\n\n Google Total number of Users Rated: "+placeObj.getUserRatingsTotal().toString()+
                "\n\n Google Rating: "+placeObj.getRating().toString());


    }


}
