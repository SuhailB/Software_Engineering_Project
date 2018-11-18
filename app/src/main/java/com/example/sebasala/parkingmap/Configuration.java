package com.example.sebasala.parkingmap;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class Configuration extends AppCompatActivity {



    //ID number for buttons
    private final int  buttonIdPurple = 1;
    private final int buttonIdRed = 2;
    private final int buttonIdGreen = 3;
    private final int buttonIdBlue = 4;

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

    private void startApp()
    {
        Button btnLoad = (Button) findViewById(R.id.LoadParkingSpots);
        btnLoad.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openMaps(5);
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

    public void openMaps(int buttonId)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(EXTRA_NUMBER,buttonId); //this adds buttonId to intent which sends it to mapsActivity
        startActivity(intent);
    }
}
