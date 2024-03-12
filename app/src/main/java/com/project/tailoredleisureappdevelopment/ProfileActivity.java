package com.project.tailoredleisureappdevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.project.tailoredleisureappdevelopment.entities.Person;
import com.project.tailoredleisureappdevelopment.models.Database;
import com.project.tailoredleisureappdevelopment.models.PersonModel;

import java.io.Serializable;
import java.sql.SQLException;
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
    private Person person;
    private PersonModel personModel;
    private Database db;


    private ArrayList<Integer> userNeeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileBtnId = (Button) findViewById(R.id.saveBtnPrfId);
        userNeeds = new ArrayList<Integer>();
        person = new Person();
        personModel = new PersonModel();
        db = new Database();

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
                    userNeeds.add(1);
                }
                if(closeToToiletAccessBox.isChecked()){
                    userNeeds.add(2);
                }
                if(platformLiftAccessBox.isChecked()){
                    userNeeds.add(3);
                }
                if(parkingSpaceAccessBox.isChecked()){
                    userNeeds.add(4);
                }
                if(toiletFrameBox.isChecked()){
                    userNeeds.add(5);
                }
                if(walkingFrameBox.isChecked()){
                    userNeeds.add(6);
                }

                person.setFirstName(firstName.getText().toString());
                person.setLastName(lastName.getText().toString());
                person.setEmail(email.getText().toString());
                person.setNumber(phoneNumber.getText().toString());
                person.setUserNeeds(userNeeds);

                Log.d("Debug: ProfileActivity", person.toString());

                try {
                    personModel.addPerson(db, person);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                openMainActivity();

            }
        });
    }

    private void openMainActivity(){
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.putExtra("USER_OBJECT", (Serializable) person);
        startActivity(intent);
    }
}