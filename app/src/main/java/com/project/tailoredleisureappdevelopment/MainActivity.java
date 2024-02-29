package com.project.tailoredleisureappdevelopment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {




    //MainActivity Starts here...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button profileBtn = (Button) findViewById(R.id.profileBtn);
        Button venuesBtn = (Button) findViewById(R.id.venuesBtn);

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
