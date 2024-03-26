package com.project.tailoredleisureappdevelopment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.project.tailoredleisureappdevelopment.entities.Need;
import com.project.tailoredleisureappdevelopment.entities.Person;
import com.project.tailoredleisureappdevelopment.models.Database;
import com.project.tailoredleisureappdevelopment.models.NeedModel;
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
    private Person person;
    private PersonModel personModel;
    private Database db;
    private Person personDetails;
    private ArrayList<String> needIds;
    private Button saveBtnPrfId;
    private final static  int USER_NEEDS_LIMIT = 50;
    private String checkBoxPrfTxt;
    private NeedModel needModel;
    private ArrayList<String> userNeeds;
    private CheckBox checkBoxIdPrf;
    private ArrayList<Need> needsList;
    private Boolean BooleanFlgCheckBoxLimit = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        needModel = new NeedModel();
        personDetails = PersonModel.personDetails;
        needIds = PersonModel.needIds;
        firstName = findViewById(R.id.firstNameEditTextId);
        lastName = findViewById(R.id.lastNameEditTextId);
        email = findViewById(R.id.emailEditTextId);
        phoneNumber = findViewById(R.id.phoneEditTextId);
        saveBtnPrfId = (Button) findViewById(R.id.saveBtnPrfId);
        profileBtnId = (Button) findViewById(R.id.saveBtnPrfId);
        userNeeds = new ArrayList<String>();
        person = new Person();
        personModel = new PersonModel();
        db = new Database();
        checkBoxPrfTxt = "userNeedsCheckBox";
        try {
            needsList = needModel.getNeeds(db);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int counter = 0;

        for(int i=1;i<=USER_NEEDS_LIMIT;i++){
            String checkBoxPrfTxtId = checkBoxPrfTxt + i;
            Log.d("DEBUG: PROFILEACTIVITY", "checkBoxPrfTxtId: "+checkBoxPrfTxtId);
            int resID = getResources().getIdentifier(checkBoxPrfTxtId, "id", getPackageName());
            checkBoxIdPrf = (CheckBox) findViewById(resID);
            String checkBoxTxt = checkBoxIdPrf.getText().toString();
            Log.d("DEBUG: PROFILEACTIVITY", "checkBoxTxt: "+checkBoxTxt);
            if(i == needsList.size()+1){
                BooleanFlgCheckBoxLimit = true;
            }
            if(BooleanFlgCheckBoxLimit){
                checkBoxIdPrf.setVisibility(View.GONE);
                continue;
            }else{
                if(!needsList.isEmpty()){
                        checkBoxIdPrf.setText(needsList.get(counter).getNeed_short_desc());
                        counter++;
                }
            }
        }


        if(personDetails!= null){
            firstName.setText(personDetails.getFirstName());
            lastName.setText(personDetails.getLastName());
            email.setText(personDetails.getEmail());
            phoneNumber.setText(personDetails.getNumber());
            for(int i=1;i<=USER_NEEDS_LIMIT;i++){
                String checkBoxPrfTxtId = checkBoxPrfTxt + i;
                Log.d("DEBUG: PROFILEACTIVITY", "checkBoxPrfTxtId: "+checkBoxPrfTxtId);
                int resID = getResources().getIdentifier(checkBoxPrfTxtId, "id", getPackageName());
                checkBoxIdPrf = (CheckBox) findViewById(resID);
                Log.d("DEBUG: PROFILEACTIVITY", "checkBoxIdPrf TEXT: "+checkBoxIdPrf.getText().toString());
                Log.d("DEBUG: PROFILEACTIVITY", "needIds: "+needIds);
                for(int j=0; j<needIds.size();j++){
                    if(checkBoxIdPrf.getText().toString().trim().equals(needIds.get(j).trim())){
                        checkBoxIdPrf.setChecked(true);
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

                for(int i=1;i<=USER_NEEDS_LIMIT;i++){
                    String checkBoxPrfTxtId = checkBoxPrfTxt + i;
                    Log.d("DEBUG: PROFILEACTIVITY", "checkBoxPrfTxtId: "+checkBoxPrfTxtId);
                    int resID = getResources().getIdentifier(checkBoxPrfTxtId, "id", getPackageName());
                    checkBoxIdPrf = (CheckBox) findViewById(resID);
                    Log.d("DEBUG: PROFILEACTIVITY", "checkBoxIdPrf TEXT: "+checkBoxIdPrf.getText().toString());

                    if(checkBoxIdPrf.isChecked()){
                        Log.d("DEBUG: PROFILEACTIVITY", checkBoxIdPrf.getText().toString().trim()+" is Checked.");
                        userNeeds.add(checkBoxIdPrf.getText().toString().trim());
                    }
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
                        Toast.makeText(getApplicationContext(), "Profile Updated Successfully.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(), "Profile Created Successfully.", Toast.LENGTH_SHORT).show();
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