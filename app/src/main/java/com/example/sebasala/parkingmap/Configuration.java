package com.example.sebasala.parkingmap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Configuration extends AppCompatActivity {


    //on screen
    private RadioGroup radioGroupAnge;
    private RadioGroup radioGroupLionel;
    private Button button;

    //classes
    Variables variables;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Time;
    TextView dateTimeView;



    private boolean isChecking = true;
    private int angeCheckedId = R.id.reserved;
    private int lionelCheckId = R.id.FivetoEight;

    //used in sending the ID for the button clicked
    //see openMaps() for more info
    public static final String EXTRA_NUMBER = "com.example.application.ParkingMap.EXTRA_NUMBER";
    public static final String EXTRA_TIME = "com.example.application.ParkingMap.EXTRA_TIME";
    private static final String TAG = "ConfigActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        variables = new Variables();

        if(isServiceAvailabel()) startApp();
    }

    private void startApp()
    {

        dateTimeView = (TextView) findViewById(R.id.seeTime);



        radioGroupAnge = (RadioGroup) findViewById(R.id.Permit);
        radioGroupLionel = (RadioGroup) findViewById(R.id.Time);
        button = (Button) findViewById(R.id.LoadParkingSpots);


        radioGroupAnge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i != -1 && isChecking)
                {
                    isChecking = false;
                    angeCheckedId = i;
                    PermitSelected();
                }
                isChecking = true;
            }
        });


        radioGroupLionel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i != -1 && isChecking)
                {
                    isChecking = false;
                    lionelCheckId = i;
                    TimeSelected();
                }
                isChecking = true;

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaps();
            }
        });

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

    public void openMaps()
    {
        int hey = 0;
        hey = variables.PermitGetter();
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(EXTRA_NUMBER,variables.PermitGetter());//this adds buttonId to intent which sends it to mapsActivity
        intent.putExtra(EXTRA_TIME,variables.Timegetter());
        startActivity(intent);
    }

    public void PermitSelected()
    {
        if (angeCheckedId == R.id.faculty) {
            Toast.makeText(this, "Faculty", Toast.LENGTH_SHORT).show();
            variables.PermitSetter(1);
        } else if (angeCheckedId == R.id.resident) {
            Toast.makeText(this, "Resident", Toast.LENGTH_SHORT).show();
            variables.PermitSetter(2);
        } else if (angeCheckedId == R.id.reserved) {
            Toast.makeText(this, "Reserved", Toast.LENGTH_SHORT).show();
            variables.PermitSetter(4);
        } else if (angeCheckedId == R.id.student) {
            variables.PermitSetter(3);
            Toast.makeText(this, "Student", Toast.LENGTH_SHORT).show();
        }

    }

    public void TimeSelected() {
        if (lionelCheckId == R.id.SeventoFive) {
            variables.Timesetter(1);
            Toast.makeText(this, "7:00am - 4:59pm", Toast.LENGTH_LONG).show();
        } else if (lionelCheckId == R.id.FivetoEight) {
            variables.Timesetter(2);
            Toast.makeText(this, "5:00pm - 7:59pm", Toast.LENGTH_LONG).show();
        } else if (lionelCheckId == R.id.EighttoSeven) {
            variables.Timesetter(3);
            Toast.makeText(this, "8:00pm - 7:00am", Toast.LENGTH_SHORT).show();
        } else if (lionelCheckId == R.id.currentTime) {
            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            Time = simpleDateFormat.format(calendar.getTime());
            if (Time.compareTo("07:00:00") >= 0 && Time.compareTo("16:59:59") <= 0) {
                Toast.makeText(this, Time, Toast.LENGTH_SHORT).show();
                variables.Timesetter(1);
            } else if (Time.compareTo("17:00:00") >= 0 && Time.compareTo("19:59:59") <= 0) {
                Toast.makeText(this, Time, Toast.LENGTH_SHORT).show();
                variables.Timesetter(2);
            } else if (Time.compareTo("20:00:00") >= 0 && Time.compareTo("06:59:59") <= 0) {
                Toast.makeText(this, Time, Toast.LENGTH_SHORT).show();
                variables.Timesetter(3);
            }
            dateTimeView.setText(Time);
        }
    }

    public void seeTime(View view) {
    }
}

class Variables
{
    private int IDselected;
    private int TimeSelected;
    Variables() { IDselected = 0; TimeSelected =0;}
    void PermitSetter(int xx) { IDselected = xx; }
    int PermitGetter() { return IDselected; }
    void Timesetter(int xx) { TimeSelected = xx; }
    int Timegetter(){ return TimeSelected; }
}
