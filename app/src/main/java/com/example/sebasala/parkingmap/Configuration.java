package com.example.sebasala.parkingmap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.TimeZone;

public class Configuration extends AppCompatActivity {



    //ID number for buttons
    private final int buttonIDNopermit = 5;
    private final int  buttonIdYellow = 1;
    private final int buttonIdRed = 2;
    private final int buttonIdGreen = 3;
    private final int buttonIdBlue = 4;

    private static final int RBP1_ID = 1000;//first radio button id
    private static final int RBP2_ID = 1001;//second radio button id
    private static final int RBP3_ID = 1002;//third radio button id
    private static final int RBP4_ID = 1003;//first radio button id
    private static final int RBT1_ID = 10000;//second radio button id
    private static final int RBT2_ID = 10001;//third radio button id

    //used in sending the ID for the button clicked
    //see openMaps() for more info
    public static final String EXTRA_NUMBER = "com.example.application.ParkingMap.EXTRA_NUMBER";

    private static final String TAG = "ConfigActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        if(isServiceAvailabel()) startApp();
    }

    /*public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.ResidentPermit:
                if (checked)
                    // Pirates are the best
                    openMaps(buttonIdRed);
                    break;
            case R.id.FacultyPermit:
                if (checked)
                    // Ninjas rule
                    openMaps(buttonIdYellow);
                    break;
            case R.id.ReservedPermit:
                if (checked)
                    // Ninjas rule
                    openMaps(buttonIdBlue);
                break;
            case R.id.StudentPermit:
                if (checked)
                    // Ninjas rule
                    openMaps(buttonIdGreen);
                break;
            default:
                openMaps(5);


        }
    }*/

    private void startApp()
    {
        Button btnLoad = (Button) findViewById(R.id.LoadParkingSpots);
        final RadioGroup radioGroupP = (RadioGroup) findViewById(R.id.AskPermit);
        final RadioGroup radioGroupT = (RadioGroup) findViewById(R.id.TimeZone);

/*
//create the RadioButton
        RadioButton rb1 = new RadioButton(this);
//set an id
        rb1.setId(RBP1_ID);
        //create the RadioButton
        RadioButton rb2 = new RadioButton(this);
//set an id
        rb1.setId(RBP2_ID);
        //create the RadioButton
        RadioButton rb3 = new RadioButton(this);
//set an id
        rb1.setId(RBP3_ID);
        //create the RadioButton
        RadioButton rb4 = new RadioButton(this);
//set an id
        rb1.setId(RBP4_ID);
*/




        btnLoad.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                int selectedPermit = radioGroupP.getCheckedRadioButtonId();
                RadioButton radioButtonP = (RadioButton) findViewById(selectedPermit);
                String textP = "default";
                if (radioGroupP.getCheckedRadioButtonId() != -1)
                    textP = radioButtonP.getText().toString();
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Permit is needed to be checked",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }

                int selectedTimeZone = radioGroupT.getCheckedRadioButtonId();
                RadioButton radioButtonT = (RadioButton) findViewById(selectedTimeZone);

                String textT = "default";
                if (radioGroupT.getCheckedRadioButtonId() != -1)
                    textT = radioButtonT.getText().toString();
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Timezone is needed to be checked",
                            Toast.LENGTH_SHORT);

                    toast.show();
                }

                /*Log.d(TAG, "selectedPermit is: " + selectedPermit);

                Log.d(TAG, "radioButton is: " + radioButton.toString());
                Log.d(TAG, "text is: " + text);*/

                //  onRadioButtonClicked(v);

                if (textP.contains("Faculty")) {
                    String permitFaculty = "Faculty";

                    if(textT.contains("7:00AM"))
                    {
                        openMaps(1, permitFaculty);
                    }
                    else if(textT.contains("5:00PM"))
                    {
                        openMaps(2, permitFaculty);
                    }
                    else if(textT.contains("8:00PM"))
                    {
                        openMaps(3, permitFaculty);
                    }
                    else
                    {

                    }
                }
                else if(textP.contains("Resident")) {
                    String permitResident = "Resident";
                    if(textT.contains("7:00AM"))
                    {
                        openMaps(1, permitResident);
                    }
                    else if(textT.contains("5:00PM"))
                    {
                        openMaps(2, permitResident);
                    }
                    else if(textT.contains("8:00PM"))
                    {
                        openMaps(3, permitResident);
                    }
                    else
                    {

                    }
                }
                else if(textP.contains("Student")) {
                    String permitStudent = "Student";
                    if(textT.contains("7:00AM"))
                    {
                        openMaps(1, permitStudent);
                    }
                    else if(textT.contains("5:00PM"))
                    {
                        openMaps(2, permitStudent);
                    }
                    else if(textT.contains("8:00PM"))
                    {
                        openMaps(3, permitStudent);
                    }
                    else
                    {

                    }
                }
                else if(textP.contains("Reserved")) {
                    String permitReserved = "Reserved";
                    if(textT.contains("7:00AM"))
                    {
                        openMaps(1, permitReserved);
                    }
                    else if(textT.contains("5:00PM"))
                    {
                        openMaps(2, permitReserved);
                    }
                    else if(textT.contains("8:00PM"))
                    {
                        openMaps(3, permitReserved);
                    }
                    else
                    {

                    }
                }
                else if(textP.contains("No Permit")) {
                    String permitNoPermit = "No Permit";
                    if(textT.contains("7:00AM"))
                    {
                        openMaps(1, permitNoPermit);
                    }
                    else if(textT.contains("5:00PM"))
                    {
                        openMaps(2, permitNoPermit);
                    }
                    else if(textT.contains("8:00PM"))
                    {
                        openMaps(3, permitNoPermit);
                    }
                    else
                    {

                    }
                }
                else
                {}
            }

        });

        /*
        //Buttons associated with each parking pass
        //private Button button;
         Button yellowButton ;
         Button blueButton;
         Button greenButton;
         Button redButton ;

        //assign button object from buttons created in the XML file
        //button = findViewById(R.id.button);
        yellowButton = findViewById(R.id.Checkboxp_2);
        yellowButton.getBackground().setColorFilter(0xFFFFFF33, PorterDuff.Mode.MULTIPLY);

        blueButton = findViewById(R.id.Checkboxp_3);
        blueButton.getBackground().setColorFilter(0xFF3385ff, PorterDuff.Mode.MULTIPLY);

        greenButton =  findViewById(R.id.Checkboxp_4);
        greenButton.getBackground().setColorFilter(0xFF66ff33, PorterDuff.Mode.MULTIPLY);

        redButton =  findViewById(R.id.Checkboxp_1);
        redButton.getBackground().setColorFilter(0xFFe60000, PorterDuff.Mode.MULTIPLY);

        */
        //calls OpenMaps when clicked and also sends the buttonID for the Button
        /*button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openMaps(buttonIdBlue);
            }

        });*/
        /*
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps(buttonIdGreen );
            }
        });
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps(buttonIdPurple);
            }
        });
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps(buttonIdRed);
            }
        });
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps(buttonIdBlue);
            }
        });*/
    }

    /*public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.ResidentPermit:
                if (checked)
                    openMaps(buttonIdRed);
                    break;
            case R.id.FacultyPermit:
                if (checked)
                    openMaps(buttonIdYellow);
                    break;
            case R.id.ReservedPermit:
                if(checked)
                    openMaps(buttonIdBlue);
                break;
            case R.id.StudentPermit:
                if(checked)
                    openMaps(buttonIdGreen);
                break;

        }
    }*/

    public boolean isServiceAvailabel()
    {
        Log.d(TAG, "Checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if(available == ConnectionResult.SUCCESS)
        {
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            //you can fix it
            Log.d(TAG, "isServicesOk: an error occured but we can fix it");

        }
        else
        {
            Toast.makeText(this, "You cannot make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void openMaps(int buttonId, String PermitType)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(EXTRA_NUMBER,buttonId);//this adds buttonId to intent which sends it to mapsActivity
        intent.putExtra(Intent.EXTRA_TEXT,PermitType);//this adds buttonId to intent which sends it to mapsActivity

        startActivity(intent);
    }
}
