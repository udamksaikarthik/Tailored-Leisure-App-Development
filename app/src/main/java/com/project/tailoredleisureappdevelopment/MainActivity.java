package com.project.tailoredleisureappdevelopment;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.project.tailoredleisureappdevelopment.entities.Person;
import com.project.tailoredleisureappdevelopment.models.Database;
import com.project.tailoredleisureappdevelopment.models.PersonModel;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {


    private Person person;
    private Button venuesBtn;
    private Button profileBtn;
    private Button signUpBtn;
    private Button loginBtn;
    private EditText loginEmail;
    private PersonModel personModel;
    private Boolean authenticationFlg = false;
    private Database db;
    private ConstraintLayout buttons_layout_id;
    private ConstraintLayout authentication_section_container_id;

    //MainActivity Starts here...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Helps to resolve NullPointerException on SQL Connection
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        signUpBtn = (Button) findViewById(R.id.signup_dashboard_btn_id);
        loginBtn = (Button) findViewById(R.id.login_dashboard_btn_id);
        personModel = new PersonModel();
        db = new Database();
        buttons_layout_id = (ConstraintLayout) findViewById(R.id.buttons_layout);
        authentication_section_container_id = (ConstraintLayout) findViewById(R.id.authentication_section_container_id);
        profileBtn = (Button) findViewById(R.id.profileBtn);
        venuesBtn = (Button) findViewById(R.id.venuesBtn);
        authenticationFlg = (Boolean) getIntent().getSerializableExtra("AUTHENTICATOR_FLG") != null?
                (Boolean) getIntent().getSerializableExtra("AUTHENTICATOR_FLG"): false;


        Log.d("DEBUG: MainActivity", "authenticationFlg: "+authenticationFlg);

        if(authenticationFlg){
            buttons_layout_id.setVisibility(View.VISIBLE);
            authentication_section_container_id.setVisibility(View.GONE);
            venuesBtn.setEnabled(true);
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
                }else{

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
