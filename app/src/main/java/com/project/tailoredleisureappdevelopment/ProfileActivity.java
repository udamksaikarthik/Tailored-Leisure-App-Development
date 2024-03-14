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
    private Person personDetails;
    private ArrayList<Integer> needIds;
    private Button saveBtnPrfId;


    private ArrayList<Integer> userNeeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        needIds  = new ArrayList<>();
        personDetails = PersonModel.personDetails;
        needIds = PersonModel.needIds;
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
        saveBtnPrfId = (Button) findViewById(R.id.saveBtnPrfId);
        profileBtnId = (Button) findViewById(R.id.saveBtnPrfId);
        userNeeds = new ArrayList<Integer>();
        person = new Person();
        personModel = new PersonModel();
        db = new Database();


        if(personDetails!= null){
            firstName.setText(personDetails.getFirstName());
            lastName.setText(personDetails.getLastName());
            email.setText(personDetails.getEmail());
            phoneNumber.setText(personDetails.getNumber());
            if(!needIds.isEmpty()){
                for(int i=0; i<needIds.size(); i++){
                    if(needIds.get(i)==1){
                        wheelChairAccessBox.setChecked(true);
                    }if(needIds.get(i)==2){
                        closeToToiletAccessBox.setChecked(true);
                    }if(needIds.get(i)==3){
                        platformLiftAccessBox.setChecked(true);
                    }if(needIds.get(i)==4){
                        parkingSpaceAccessBox.setChecked(true);
                    }if(needIds.get(i)==5){
                        toiletFrameBox.setChecked(true);
                    }if(needIds.get(i)==6){
                        walkingFrameBox.setChecked(true);
                    }
                }
            }

        }


        if(!firstName.getText().toString().isEmpty()){
            Log.d("Debug: ProfileActivity", "firstName: "+firstName.getText().toString());
            saveBtnPrfId.setText("UPDATE");
        }else{
            Log.d("Debug: ProfileActivity", "firstName: "+firstName.getText().toString());
            saveBtnPrfId.setText("SAVE");
        }

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

                if(profileBtnId.getText().toString().equals("UPDATE")){
                    Log.d("Debug: ProfileActivity", "saveBtnPrfId button: UPDATE");
                    try {
                        person.setPerson_id(personDetails.getPerson_id());
                        personModel.updatePerson(db, person);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    openMainActivity(true);
                }
                else if(profileBtnId.getText().toString().equals("SAVE")){
                    Log.d("Debug: ProfileActivity", "saveBtnPrfId button: SAVE");
                    Log.d("Debug: ProfileActivity", person.toString());
                    try {
                        personModel.addPerson(db, person);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    openMainActivity(false);
                }


            }
        });
    }

    private void openMainActivity(Boolean flg){
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.putExtra("USER_OBJECT", (Serializable) person);
        intent.putExtra("AUTHENTICATOR_FLG", flg);
        startActivity(intent);
    }
}