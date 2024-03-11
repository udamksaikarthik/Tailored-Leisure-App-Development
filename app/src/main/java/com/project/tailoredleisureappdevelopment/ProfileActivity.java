package com.project.tailoredleisureappdevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.project.tailoredleisureappdevelopment.models.User;

import java.io.Serializable;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private Button profileBtnId;

    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText phoneNumber;
    private CheckBox wheelChairAccessBox;
    private CheckBox closeToToiletAccessBox;
    private CheckBox platformLiftAccessBox;
    private CheckBox parkingSpaceAccessBox;
    private CheckBox toiletFrameBox;
    private CheckBox walkingFrameBox;
    private User user;


    private ArrayList<String> userNeeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileBtnId = (Button) findViewById(R.id.saveBtnPrfId);
        userNeeds = new ArrayList<String>();
        user = new User();

        profileBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = findViewById(R.id.firstNameEditTextId);
                lastName = findViewById(R.id.lastNameEditTextId);
                email = findViewById(R.id.emailEditTextId);
                phoneNumber = findViewById(R.id.phoneEditTextId);
                wheelChairAccessBox = findViewById(R.id.wheelChairAccessId);
                closeToToiletAccessBox = findViewById(R.id.closeToToiletsId);
                platformLiftAccessBox = findViewById(R.id.platformLiftId);
                parkingSpaceAccessBox = findViewById(R.id.parkingSpacesId);
                toiletFrameBox = findViewById(R.id.toiletFramesId);
                walkingFrameBox = findViewById(R.id.walkingFramesId);

                if(wheelChairAccessBox.isChecked()){
                    userNeeds.add("Wheelchair Access");
                }
                if(closeToToiletAccessBox.isChecked()){
                    userNeeds.add("Close To Toilets Access");
                }
                if(platformLiftAccessBox.isChecked()){
                    userNeeds.add("Platform Lift Access");
                }
                if(parkingSpaceAccessBox.isChecked()){
                    userNeeds.add("Parking Space Access");
                }
                if(toiletFrameBox.isChecked()){
                    userNeeds.add("Toilet Frame Access");
                }
                if(walkingFrameBox.isChecked()){
                    userNeeds.add("Walking Frame Access");
                }

                user.setFirstName(firstName.getText().toString());
                user.setLastName(lastName.getText().toString());
                user.setEmail(email.getText().toString());
                user.setNumber(phoneNumber.getText().toString());
                user.setUserNeeds(userNeeds);

                Log.d("Debug: ProfileActivity", user.toString());

                openMainActivity();

            }
        });
    }

    private void openMainActivity(){
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.putExtra("USER_OBJECT", (Serializable) user);
        startActivity(intent);
    }
}