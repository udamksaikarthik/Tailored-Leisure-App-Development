package com.project.tailoredleisureappdevelopment;
/*
Authors: Saikarthik Uda (Technical Lead), Ebere Janet Eboh, Prathyusha Kamma.
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.project.tailoredleisureappdevelopment.entities.Person;
import com.project.tailoredleisureappdevelopment.models.Database;
import com.project.tailoredleisureappdevelopment.models.PersonModel;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    //Global Variables
    private Person person;
    private Button venuesBtn;
    private Button profileBtn;
    private Button editNeedsBtn;
    private Button signUpBtn;
    private Button loginBtn;
    private EditText loginEmail;
    private PersonModel personModel;
    private Boolean authenticationFlg = false;
    private Database db;
    private ConstraintLayout buttons_layout_id;
    private ConstraintLayout authentication_section_container_id;

    //MainActivity Starts here...
    /*
    The onCreate method is the start of the Layout Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Helps to resolve NullPointerException on SQL Connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        signUpBtn = (Button) findViewById(R.id.signup_dashboard_btn_id);
        loginBtn = (Button) findViewById(R.id.login_dashboard_btn_id);
        editNeedsBtn = (Button) findViewById(R.id.editNeedsBtn);
        personModel = new PersonModel();
        db = new Database();
        buttons_layout_id = (ConstraintLayout) findViewById(R.id.buttons_layout);
        authentication_section_container_id = (ConstraintLayout) findViewById(R.id.authentication_section_container_id);
        profileBtn = (Button) findViewById(R.id.profileBtn);
        venuesBtn = (Button) findViewById(R.id.venuesBtn);
        authenticationFlg = (Boolean) getIntent().getSerializableExtra("AUTHENTICATOR_FLG") != null?
                (Boolean) getIntent().getSerializableExtra("AUTHENTICATOR_FLG"): false;
        editNeedsBtn.setVisibility(View.GONE);





        Log.d("DEBUG: MainActivity", "authenticationFlg: "+authenticationFlg);

        if(authenticationFlg){
            buttons_layout_id.setVisibility(View.VISIBLE);
            authentication_section_container_id.setVisibility(View.GONE);
            venuesBtn.setEnabled(true);
            Log.d("DEBUG: MainActivity", "PersonModel.personDetails.getRole(): "+PersonModel.personDetails.getRole());
            if(PersonModel.personDetails.getRole().equals("ROLE_ADMIN")){
                editNeedsBtn.setVisibility(View.VISIBLE);
                editNeedsBtn.setEnabled(true);
            }else{
                editNeedsBtn.setVisibility(View.GONE);
            }
        }

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfileActivity();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEmail = (EditText) findViewById(R.id.loginEmailEditTextId);
                String loginEmailTxt = loginEmail.getText().toString();
                Log.d("DEBUG: MainActivity", "User Email: "+loginEmailTxt);
                try {
                    authenticationFlg = personModel.authenticateUser(db, loginEmailTxt);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                if(authenticationFlg){
                    buttons_layout_id.setVisibility(View.VISIBLE);
                    authentication_section_container_id.setVisibility(View.GONE);
                    venuesBtn.setEnabled(true);
                    Log.d("DEBUG: MainActivity", "PersonModel.personDetails.getRole(): "+PersonModel.personDetails.getRole());
                    if(PersonModel.personDetails.getRole().equals("ROLE_ADMIN")){
                        editNeedsBtn.setVisibility(View.VISIBLE);
                        editNeedsBtn.setEnabled(true);
                    }else{
                        editNeedsBtn.setVisibility(View.GONE);
                    }
                    Toast.makeText(getApplicationContext(), "Logged In Successfully.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Credentials.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        person = null;

        try{
            person = (Person) getIntent().getSerializableExtra("USER_OBJECT");
        } catch (Exception e){
            Log.d("DEBUG: MainActivity", e.getMessage());
        }


        if(person !=null){
            if(!person.getUserNeeds().isEmpty()){
                venuesBtn.setEnabled(true);
            }
        }


        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG: MainActivity","Profile Button Clicked.");
                openProfileActivity();
            }
        });

        venuesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG: MainActivity","Venues Button Clicked.");
                openVenuesActivity();
            }
        });


        editNeedsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG: MainActivity","Edit Needs Button Clicked.");
                openEditNeedsActivity();
            }
        });
    }

    private void openEditNeedsActivity() {
        Intent intent = new Intent(this, EditNeedsActivity.class);
        startActivity(intent);
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
