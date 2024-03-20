package com.project.tailoredleisureappdevelopment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.project.tailoredleisureappdevelopment.entities.Need;
import com.project.tailoredleisureappdevelopment.entities.Person;
import com.project.tailoredleisureappdevelopment.models.Database;
import com.project.tailoredleisureappdevelopment.models.NeedModel;
import com.project.tailoredleisureappdevelopment.models.PersonModel;

import java.sql.SQLException;
import java.util.ArrayList;


public class EditNeedsActivity extends AppCompatActivity {

    private final static  int USER_NEEDS_LIMIT = 50;
    private String checkBoxPrfTxt;
    private CheckBox checkBoxIdPrf;
    private ArrayList<Need> needsList;
    private Boolean BooleanFlgCheckBoxLimit = false;
    private Database db;
    private ArrayList<String> needIds;
    private NeedModel needModel;
    private Button deleteBtnEditNeedsId;
    private Button addBtnEditNeedsId;
    private ArrayList<String> userNeeds;
    private ConstraintLayout addEventViewId;
    private Button addNeedBtnId;
    private EditText addNeedShortDescEditTxtId;
    private EditText addNeedLongDescEditTxtId;
    private ConstraintLayout yourneedsRadioGroupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_needs);
        Log.d("DEBUG: EditNeedsActivity", "Inside EditNeedsActivity Class");

        needModel = new NeedModel();
        db = new Database();
        checkBoxPrfTxt = "editNeedsCheckBox";
        needIds = PersonModel.needIds;
        deleteBtnEditNeedsId = (Button) findViewById(R.id.deleteBtnEditNeedsId);
        addBtnEditNeedsId = (Button) findViewById(R.id.addBtnEditNeedsId);
        addEventViewId = (ConstraintLayout) findViewById(R.id.addEventViewId);
        yourneedsRadioGroupId = (ConstraintLayout) findViewById(R.id.yourneedsRadioGroupId);
        addNeedShortDescEditTxtId = (EditText) findViewById(R.id.addNeedShortDescEditTxtId);
        addNeedLongDescEditTxtId = (EditText) findViewById(R.id.addNeedLongDescEditTxtId);
        addNeedBtnId = (Button) findViewById(R.id.addNeedBtnId);
        userNeeds = new ArrayList<>();
        int counter = 0;


        try {
            needsList = needModel.getNeeds(db);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

        deleteBtnEditNeedsId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                Log.d("DEBUG: EditNeedsActivity", "userNeeds: "+userNeeds);
                if(!userNeeds.isEmpty()){
                    try {
                        needModel.deleteNeeds(db, userNeeds);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                // To refresh the current Activity
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        addBtnEditNeedsId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEventViewId.setVisibility(View.VISIBLE);
                yourneedsRadioGroupId.setVisibility(View.GONE);
            }
        });

        addNeedBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Need n = new Need();
                String need_short_desc = addNeedShortDescEditTxtId.getText().toString()!=null?
                        addNeedShortDescEditTxtId.getText().toString():"";
                String need_long_desc = addNeedLongDescEditTxtId.getText().toString()!=null?
                        addNeedLongDescEditTxtId.getText().toString():"";
                if(!need_short_desc.isEmpty() && !need_long_desc.isEmpty()){
                    n.setNeed_short_desc(need_short_desc);
                    n.setNeed_long_desc(need_long_desc);
                    try {
                        needModel.addNeed(db, n);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                addEventViewId.setVisibility(View.GONE);
                yourneedsRadioGroupId.setVisibility(View.VISIBLE);
                // To refresh the current Activity
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

    }
}
