package com.project.tailoredleisureappdevelopment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.project.tailoredleisureappdevelopment.models.Place;
import com.project.tailoredleisureappdevelopment.models.User;

public class MainActivity extends AppCompatActivity {


    private User user;
    Button venuesBtn;
    Button profileBtn;

    //MainActivity Starts here...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = null;

        try{
            user = (User) getIntent().getSerializableExtra("USER_OBJECT");
        } catch (Exception e){
            Log.d("DEBUG: MainActivity", e.getMessage());
        }

        profileBtn = (Button) findViewById(R.id.profileBtn);
        venuesBtn = (Button) findViewById(R.id.venuesBtn);

        if(user!=null){
            if(!user.getUserNeeds().isEmpty()){
                venuesBtn.setEnabled(true);
            }
        }


        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfileActivity();
            }
        });

        venuesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVenuesActivity();
            }
        });
    }

    public void openProfileActivity(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void openVenuesActivity(){
        Log.d("DEBUG","Inside openVenuesActivity");
        Intent intent = new Intent(this, VenuesMapsActivity.class);
        startActivity(intent);
    }
}
