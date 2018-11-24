package com.example.thier.park;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;



public class Configuration extends AppCompatActivity {
    private RadioGroup radioGroupAnge;
    private RadioGroup radioGroupLionel;

    private boolean isChecking = true;
    private int angeCheckedId = R.id.thierry;
    private int lionelCheckId = R.id.imena;

    //public static final String EXTRA_NUMBER = "com.example.application.ParkingMap.EXTRA_NUMBER";

    private static final String TAG = "ConfigActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        radioGroupAnge = (RadioGroup) findViewById(R.id.ange);
        radioGroupLionel = (RadioGroup) findViewById(R.id.lionel);


        //RadioGroup.OnCheckedChangeListener
        radioGroupAnge.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i != -1 && isChecking)
                {
                    isChecking = false;
                    angeCheckedId = i;
                    check();
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

                }
                isChecking = true;
                //radioGroupAnge.clearCheck();
            }
        });
    }

    public void buttonClicked(View view) {
        if (angeCheckedId == R.id.ishimwe) {
            Toast.makeText(this, "ishimwe", Toast.LENGTH_SHORT).show();
        } else if (angeCheckedId == R.id.ange1) {
            Toast.makeText(this, "ange1", Toast.LENGTH_SHORT).show();
        } else if (angeCheckedId == R.id.thierry) {
            Toast.makeText(this, "thierry", Toast.LENGTH_SHORT).show();
        }

    }
    public void check()
    {
        if (angeCheckedId == R.id.ishimwe) {
            Toast.makeText(this, "ishimwe", Toast.LENGTH_SHORT).show();
        } else if (angeCheckedId == R.id.ange1) {
            Toast.makeText(this, "ange1", Toast.LENGTH_SHORT).show();
        } else if (angeCheckedId == R.id.thierry) {
            Toast.makeText(this, "thierry", Toast.LENGTH_SHORT).show();
        }

    }

    public void buttonClicked1(View view) {
        if (lionelCheckId == R.id.lionel1) {
            Toast.makeText(this, "lionel1", Toast.LENGTH_SHORT).show();
        } else if (lionelCheckId == R.id.imena) {
            Toast.makeText(this, "imena", Toast.LENGTH_SHORT).show();
        } else if (lionelCheckId == R.id.kirenga) {
            Toast.makeText(this, "kirenga", Toast.LENGTH_SHORT).show();
        }
    }

}
