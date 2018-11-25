package com.example.sebasala.parkingmap;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapsActivity
        extends FragmentActivity
        implements
        OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener
{

    private GoogleMap mMap;
    private List<Polygon> ReservedLots;
    private String TAG = "MapsActivity";

    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_BLUE_ARGB = 0xff0a9eee;
    private static final int COLOR_YELLOW_ARGB = 0xffFFFF00;
    private static final int COLOR_RED_ARGB = 0xffcc0000;
    private static final int COLOR_GREEN_ARGB = 0xff195619;



    private static final int POLYGON_STROKE_WIDTH_PX = 0;


    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private Boolean LocationPermissionsGranted = false;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //array list to hold the polygon objects of reserved type
        ReservedLots = new ArrayList<Polygon>();

        if (ContextCompat.checkSelfPermission(this, FINE_LOCATION  ) != PackageManager.PERMISSION_GRANTED
                ||  ContextCompat.checkSelfPermission(this, COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {

            // Permission is not granted
            Log.d("MapActivity", "Permission not granted");

            if(ContextCompat.checkSelfPermission(this, FINE_LOCATION  ) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            if(ContextCompat.checkSelfPermission(this, COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);


        }

        addFree5_7Polygons("Reserved");

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));



        // Add a marker in Sydney and move the camera
        LatLng UARK = new LatLng(36.0685126, -94.1729845);
        // mMap.addMarker(new MarkerOptions().position(UARK).title("Univeristy of Arkansas"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UARK, 15));
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnPolygonClickListener(this);


    }

    public void addFree5_7Polygons(String type)
    {
        Polygon polygon;

        // id:1
        // name:44
        // description:a.k.a. The Pit
        // type:lot
        // permitType:Faculty/Staff, Blue Reserved A/R
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must be vacated for athletic events.
        // latitude:36.0698261216113
        // longitude:-94.1788328961121

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.07021 , -94.178106),new LatLng(36.070323 , -94.178248),new LatLng(36.070329 , -94.178801),new LatLng(36.070223 , -94.178806),new LatLng(36.07021 , -94.178106),new LatLng(36.07021 , -94.178106)));
        polygon.setTag("id:1");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.069735 , -94.179887),new LatLng(36.069357 , -94.179889),new LatLng(36.069334 , -94.17886),new LatLng(36.069316 , -94.17784),new LatLng(36.070017 , -94.177838),new LatLng(36.070173 , -94.178085),new LatLng(36.070192 , -94.179831),new LatLng(36.070166 , -94.179847),new LatLng(36.069776 , -94.179879),new LatLng(36.069735 , -94.179887),new LatLng(36.069735 , -94.179887),new LatLng(36.069735 , -94.179887),new LatLng(36.069735 , -94.179887)));
        polygon.setTag("id:1");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.07033 , -94.178877),new LatLng(36.070176 , -94.17888),new LatLng(36.070185 , -94.179837),new LatLng(36.07035 , -94.179698),new LatLng(36.07033 , -94.178877),new LatLng(36.07033 , -94.178877),new LatLng(36.07033 , -94.178877),new LatLng(36.07033 , -94.178877)));
        polygon.setTag("id:1");
        stylePolygon(polygon, type);

        // id:36
        // name:45
        // description:Barnhill Arena
        // type:lot
        // permitType:Faculty/Staff
        // paymentType:Permit
        // fine:$25-$200
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must Be Vacated for Athletic Events
        // latitude:36.0672490491553
        // longitude:-94.1777192807055

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.067279 , -94.177596),new LatLng(36.067097 , -94.177578),new LatLng(36.067095 , -94.177632),new LatLng(36.067282 , -94.17764),new LatLng(36.067279 , -94.177596)));
        polygon.setTag("id:36");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.067383 , -94.177833),new LatLng(36.067341 , -94.177623),new LatLng(36.0673 , -94.177631),new LatLng(36.067308 , -94.177682),new LatLng(36.067206 , -94.17771),new LatLng(36.067226 , -94.177837),new LatLng(36.06733 , -94.177806),new LatLng(36.067341 , -94.177844),new LatLng(36.067383 , -94.177833)));
        polygon.setTag("id:36");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.067089 , -94.178211),new LatLng(36.067089 , -94.178258),new LatLng(36.066994 , -94.178261),new LatLng(36.066989 , -94.178215),new LatLng(36.06708 , -94.178212),new LatLng(36.067089 , -94.178211)));
        polygon.setTag("id:36");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.06699 , -94.178452),new LatLng(36.066987 , -94.178525),new LatLng(36.06674 , -94.178529),new LatLng(36.06674 , -94.178451),new LatLng(36.066982 , -94.178454),new LatLng(36.06699 , -94.178452)));
        polygon.setTag("id:36");
        stylePolygon(polygon, type);

        // id:8
        // name:46
        // description:Tennis, Intramural - Nettleship
        // type:lot
        // permitType:Student, Blue Reserved
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must be vacated for athletic events.
        // latitude:36.0610448147378
        // longitude:-94.1814737570961

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.061285 , -94.181645),new LatLng(36.060802 , -94.181667),new LatLng(36.060754 , -94.180487),new LatLng(36.061251 , -94.180492),new LatLng(36.06129 , -94.18164),new LatLng(36.061285 , -94.181645)));
        polygon.setTag("id:8");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.061327 , -94.182501),new LatLng(36.061305 , -94.18178),new LatLng(36.061014 , -94.181801),new LatLng(36.061012 , -94.181706),new LatLng(36.060965 , -94.181708),new LatLng(36.060985 , -94.182178),new LatLng(36.061097 , -94.182174),new LatLng(36.061101 , -94.182274),new LatLng(36.06099 , -94.182284),new LatLng(36.060996 , -94.182506),new LatLng(36.061327 , -94.182501)));
        polygon.setTag("id:8");
        stylePolygon(polygon, type);

        // id:32
        // name:51
        // description:Meadow Street
        // type:lot
        // permitType:Blue Reserved
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must Be Vacated for Athletic Events
        // latitude:36.0637897655137
        // longitude:-94.1771783808184

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.063832 , -94.177541),new LatLng(36.063834 , -94.177054),new LatLng(36.063806 , -94.177054),new LatLng(36.063803 , -94.177542),new LatLng(36.063826 , -94.177542),new LatLng(36.063832 , -94.177541)));
        polygon.setTag("id:32");
        stylePolygon(polygon, type);

        // id:77
        // name:52
        // description:Eastern Ave. - Botany Greenhouse
        // type:lot
        // permitType:Student, Faculty/Staff
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM, Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must be vacated for athletic events.
        // latitude:36.0579767988056
        // longitude:-94.1834305402115

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.057761 , -94.183255),new LatLng(36.058038 , -94.183236),new LatLng(36.058049 , -94.183753),new LatLng(36.058049 , -94.183762),new LatLng(36.057769 , -94.183767),new LatLng(36.057761 , -94.183255)));
        polygon.setTag("id:77");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.05771 , -94.183774),new LatLng(36.057618 , -94.183777),new LatLng(36.057614 , -94.183265),new LatLng(36.057701 , -94.183262),new LatLng(36.057709 , -94.183765),new LatLng(36.05771 , -94.183774)));
        polygon.setTag("id:77");
        stylePolygon(polygon, type);

        // id:15
        // name:56
        // description:Razorback and MLK
        // type:lot
        // permitType:Student
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must be vacated for athletic events.
        // latitude:36.0577195778974
        // longitude:-94.178769261254

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.056901 , -94.180104),new LatLng(36.056925 , -94.179469),new LatLng(36.05697 , -94.179472),new LatLng(36.056971 , -94.179526),new LatLng(36.057058 , -94.179522),new LatLng(36.057059 , -94.17946),new LatLng(36.057939 , -94.179441),new LatLng(36.05794 , -94.179493),new LatLng(36.058099 , -94.179491),new LatLng(36.058098 , -94.179426),new LatLng(36.058445 , -94.179421),new LatLng(36.058448 , -94.179751),new LatLng(36.058384 , -94.179755),new LatLng(36.058384 , -94.179896),new LatLng(36.058448 , -94.179899),new LatLng(36.058438 , -94.180162),new LatLng(36.058369 , -94.180159),new LatLng(36.058368 , -94.18023),new LatLng(36.05809 , -94.180242),new LatLng(36.058088 , -94.180179),new LatLng(36.057881 , -94.180184),new LatLng(36.057882 , -94.180248),new LatLng(36.057041 , -94.180273),new LatLng(36.057038 , -94.180211),new LatLng(36.056946 , -94.180106),new LatLng(36.056901 , -94.180104)));
        polygon.setTag("id:15");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.058421, -94.179264),new LatLng(36.058421, -94.179272),new LatLng(36.058420883135859, -94.17926400649246),new LatLng(36.05693, -94.179311),new LatLng(36.056968, -94.178303),new LatLng(36.057018, -94.178305),new LatLng(36.057016, -94.178356),new LatLng(36.057104, -94.178359),new LatLng(36.057104, -94.178295),new LatLng(36.057914, -94.178276),new LatLng(36.057913, -94.178339),new LatLng(36.058067, -94.178331),new LatLng(36.058068, -94.178265),new LatLng(36.058405, -94.178262),new LatLng(36.058406, -94.178398),new LatLng(36.058353, -94.178402),new LatLng(36.058355, -94.17852),new LatLng(36.058407, -94.178518),new LatLng(36.058414, -94.178807),new LatLng(36.058335, -94.178805),new LatLng(36.058338, -94.178932),new LatLng(36.058416, -94.17893),new LatLng(36.058420883135859, -94.17926400649246),new LatLng(36.058097, -94.179282),new LatLng(36.058091, -94.179219),new LatLng(36.057933, -94.17922),new LatLng(36.057935, -94.179286),new LatLng(36.057076, -94.179312),new LatLng(36.057074, -94.179256),new LatLng(36.056981, -94.179247),new LatLng(36.056974, -94.179316),new LatLng(36.05693, -94.179311)));
        polygon.setTag("id:15");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.056962 , -94.178157),new LatLng(36.056984 , -94.177278),new LatLng(36.057036 , -94.177283),new LatLng(36.057057 , -94.177111),new LatLng(36.057712 , -94.177111),new LatLng(36.057717 , -94.17716),new LatLng(36.057868 , -94.177176),new LatLng(36.057946 , -94.177208),new LatLng(36.05799 , -94.177278),new LatLng(36.058011 , -94.177331),new LatLng(36.058011 , -94.177428),new LatLng(36.058011 , -94.177514),new LatLng(36.058013 , -94.177585),new LatLng(36.058335 , -94.177578),new LatLng(36.05835 , -94.177619),new LatLng(36.058355 , -94.178167),new LatLng(36.056971 , -94.178168),new LatLng(36.056962 , -94.178157),new LatLng(36.056962 , -94.178157),new LatLng(36.056962 , -94.178157),new LatLng(36.056962 , -94.178157)));
        polygon.setTag("id:15");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.058445 , -94.179423),new LatLng(36.058432 , -94.178946),new LatLng(36.058328 , -94.17893),new LatLng(36.058328 , -94.178807),new LatLng(36.058423 , -94.178801),new LatLng(36.058436 , -94.178517),new LatLng(36.058345 , -94.178528),new LatLng(36.058354 , -94.178404),new LatLng(36.058415 , -94.178383),new LatLng(36.058423 , -94.17819),new LatLng(36.056966 , -94.178184),new LatLng(36.056923 , -94.179461),new LatLng(36.058423 , -94.179423),new LatLng(36.058445 , -94.179423)));
        polygon.setTag("id:15");
        stylePolygon(polygon, type);

        // id:13
        // name:57
        // description:Facilities Management
        // type:lot
        // permitType:Faculty/Staff, Blue Reserved
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must be vacated for athletic events.
        // latitude:36.0579839513231
        // longitude:-94.1817601157542

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.058018 , -94.18272),new LatLng(36.05798 , -94.181179),new LatLng(36.057908 , -94.181177),new LatLng(36.057941 , -94.18272),new LatLng(36.058018 , -94.18272)));
        polygon.setTag("id:13");
        stylePolygon(polygon, type);

        // id:140
        // name:58
        // description:Stadium Drive
        // type:lot
        // permitType:Resident Reserved Zone 8, Faculty/Staff, Blue Reserved
        // paymentType:Permit
        // fine:null
        // weekdays:Resident Reserved Z8 and Reserved Permit Required At All Times, Faculty/Staff Permit Required 8AM-5PM, Free 5:01PM-6:59AM
        // weekends:Resident Reserved Z8 and Reserved Permit Required At All Times, Faculty/Staff Free
        // meterDuration:null
        // notes:*Resident Reserved are NOT vacated for athletic events. Other designations MUST BE VACATED for Athletic Events.
        // latitude:36.062858
        // longitude:-94.176089

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.062719 , -94.176041),new LatLng(36.062711 , -94.176069),new LatLng(36.062544 , -94.17597),new LatLng(36.062549 , -94.175944),new LatLng(36.062711 , -94.176034),new LatLng(36.062719 , -94.176041)));
        polygon.setTag("id:140");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.06375 , -94.176722),new LatLng(36.064013 , -94.176901),new LatLng(36.064021 , -94.176879),new LatLng(36.063756 , -94.1767),new LatLng(36.063748 , -94.176724),new LatLng(36.06375 , -94.176722)));
        polygon.setTag("id:140");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.064589 , -94.177188),new LatLng(36.064664 , -94.177205),new LatLng(36.064779 , -94.177225),new LatLng(36.065127 , -94.17726),new LatLng(36.06513 , -94.177286),new LatLng(36.064769 , -94.177251),new LatLng(36.064643 , -94.177227),new LatLng(36.064584 , -94.177211),new LatLng(36.064591 , -94.177189),new LatLng(36.064589 , -94.177188)));
        polygon.setTag("id:140");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.065364 , -94.177279),new LatLng(36.065708 , -94.177302),new LatLng(36.06611 , -94.177326),new LatLng(36.066763 , -94.177368),new LatLng(36.067155 , -94.177384),new LatLng(36.067323 , -94.177402),new LatLng(36.067508 , -94.177429),new LatLng(36.067621 , -94.177448),new LatLng(36.067725 , -94.177478),new LatLng(36.06784 , -94.177503),new LatLng(36.068007 , -94.177547),new LatLng(36.068003 , -94.177571),new LatLng(36.067831 , -94.177528),new LatLng(36.067689 , -94.177495),new LatLng(36.067551 , -94.177465),new LatLng(36.067392 , -94.177441),new LatLng(36.067233 , -94.177421),new LatLng(36.067127 , -94.177413),new LatLng(36.067046 , -94.177408),new LatLng(36.066634 , -94.17739),new LatLng(36.066169 , -94.177358),new LatLng(36.065765 , -94.177333),new LatLng(36.065547 , -94.177323),new LatLng(36.065366 , -94.177307),new LatLng(36.065364 , -94.17728),new LatLng(36.065364 , -94.177279)));
        polygon.setTag("id:140");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.063617 , -94.176606),new LatLng(36.063349 , -94.17643),new LatLng(36.063331 , -94.176458),new LatLng(36.063604 , -94.176639),new LatLng(36.063615 , -94.176607),new LatLng(36.063617 , -94.176606)));
        polygon.setTag("id:140");
        stylePolygon(polygon, type);

        // id:34
        // name:59
        // description:Bev Lewis Center for Women\u0027s Athletics
        // type:lot
        // permitType:Faculty/Staff, Blue Reserved
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must Be Vacated for Athletic Events
        // latitude:36.0656544541889
        // longitude:-94.1782760879596

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.065869 , -94.17823),new LatLng(36.065867 , -94.178387),new LatLng(36.065917 , -94.178388),new LatLng(36.065916 , -94.178236),new LatLng(36.065869 , -94.17823)));
        polygon.setTag("id:34");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.065791 , -94.178158),new LatLng(36.065802 , -94.178367),new LatLng(36.065516 , -94.178349),new LatLng(36.065505 , -94.178166),new LatLng(36.065791 , -94.178158)));
        polygon.setTag("id:34");
        stylePolygon(polygon, type);

        // id:19
        // name:60
        // description:Bud Walton Arena
        // type:lot
        // permitType:Student, Meter Payment
        // paymentType:Permit, Coins, Visa, MC, Disc, AMEX
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM  Metered Spaces: 7AM-8PM Free 8:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:$1.75 Hourly
        // notes:*Must Be Vacated for Athletic Events
        // latitude:36.0611148251698
        // longitude:-94.17948515745

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.061091 , -94.179963),new LatLng(36.061447 , -94.179509),new LatLng(36.061405 , -94.179464),new LatLng(36.061405 , -94.17941),new LatLng(36.061449 , -94.179367),new LatLng(36.061108 , -94.178978),new LatLng(36.061039 , -94.179075),new LatLng(36.060913 , -94.178933),new LatLng(36.06082 , -94.179059),new LatLng(36.060829 , -94.179657),new LatLng(36.060861 , -94.179697),new LatLng(36.060824 , -94.179753),new LatLng(36.061015 , -94.179965),new LatLng(36.061048 , -94.179922),new LatLng(36.061091 , -94.179963)));
        polygon.setTag("id:19");
        stylePolygon(polygon, type);

        // id:20
        // name:62
        // description:Bud Walton Arena
        // type:lot
        // permitType:Faculty/Staff, Blue Reserved
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must Be Vacated for Athletic Events
        // latitude:36.0609862199496
        // longitude:-94.1771942899821

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.061142 , -94.177184),new LatLng(36.061131 , -94.177189),new LatLng(36.060972 , -94.177415),new LatLng(36.061007 , -94.177457),new LatLng(36.061176 , -94.177235),new LatLng(36.061142 , -94.177184),new LatLng(36.061142 , -94.177184)));
        polygon.setTag("id:20");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.061181 , -94.177138),new LatLng(36.060968 , -94.177411),new LatLng(36.060965 , -94.17741),new LatLng(36.060884 , -94.177425),new LatLng(36.06083 , -94.177361),new LatLng(36.060854 , -94.177327),new LatLng(36.060855 , -94.177117),new LatLng(36.060897 , -94.17706),new LatLng(36.060865 , -94.177015),new LatLng(36.060969 , -94.176878),new LatLng(36.061184 , -94.177133),new LatLng(36.061181 , -94.177138)));
        polygon.setTag("id:20");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.061304 , -94.177016),new LatLng(36.061039 , -94.176713),new LatLng(36.061019 , -94.176736),new LatLng(36.061283 , -94.177041),new LatLng(36.061304 , -94.177016)));
        polygon.setTag("id:20");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.060987 , -94.176852),new LatLng(36.060999 , -94.176789),new LatLng(36.061252 , -94.177086),new LatLng(36.061238 , -94.177147),new LatLng(36.060987 , -94.176852),new LatLng(36.060987 , -94.176852)));
        polygon.setTag("id:20");
        stylePolygon(polygon, type);

        // id:56
        // name:66
        // description:Lindell Ave. - University House, Davis Hall
        // type:lot
        // permitType:Blue Reserved Night Reserved X
        // paymentType:Permit
        // fine:$25-$200
        // weekdays:Reserved Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must Be Vacated for Athletic Events
        // latitude:36.0710039352452
        // longitude:-94.1746806996126

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.070977 , -94.174473),new LatLng(36.071027 , -94.174509),new LatLng(36.071072 , -94.174529),new LatLng(36.071121 , -94.174494),new LatLng(36.071132 , -94.17497),new LatLng(36.070777 , -94.174969),new LatLng(36.070777 , -94.174905),new LatLng(36.07068 , -94.174905999999993),new LatLng(36.070675 , -94.174768),new LatLng(36.070824 , -94.174763),new LatLng(36.070827 , -94.174844),new LatLng(36.071027 , -94.174832),new LatLng(36.071029 , -94.174813),new LatLng(36.07098 , -94.174784),new LatLng(36.070977 , -94.174473)));
        polygon.setTag("id:56");
        stylePolygon(polygon, type);

        // id:135
        // name:67
        // description:N of Indoor Practice Facility
        // type:lot
        // permitType:Faculty/Staff, Blue Reserved R
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM, Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:null
        // latitude:36.066604
        // longitude:-94.178957

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.066247 , -94.178707),new LatLng(36.06625 , -94.178863),new LatLng(36.066189 , -94.178867),new LatLng(36.066186 , -94.17871),new LatLng(36.066245 , -94.178707),new LatLng(36.066247 , -94.178707)));
        polygon.setTag("id:135");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.066254 , -94.179077),new LatLng(36.066197 , -94.179079),new LatLng(36.06619 , -94.17887),new LatLng(36.066249 , -94.178867),new LatLng(36.066256 , -94.179073),new LatLng(36.066254 , -94.179077)));
        polygon.setTag("id:135");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.066308 , -94.178835),new LatLng(36.066358 , -94.178837),new LatLng(36.066367 , -94.179103),new LatLng(36.06633 , -94.179288),new LatLng(36.06646 , -94.179345),new LatLng(36.066475 , -94.179293),new LatLng(36.066703 , -94.179276),new LatLng(36.066699 , -94.179005),new LatLng(36.066754 , -94.179004),new LatLng(36.06682 , -94.179183),new LatLng(36.06687 , -94.179185),new LatLng(36.066859 , -94.17876),new LatLng(36.066691 , -94.178761),new LatLng(36.06669 , -94.178733),new LatLng(36.066308 , -94.178719),new LatLng(36.066308 , -94.178827),new LatLng(36.066308 , -94.178835),new LatLng(36.066308 , -94.178835)));
        polygon.setTag("id:135");
        stylePolygon(polygon, type);

        // id:163
        // name:68
        // description:*Must Be Vacated for Athletic Events
        // type:null
        // permitType:Blue Reserved D
        // paymentType:Permit
        // fine:null
        // weekdays:Reserved Permit Only 7AM-5PM, Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:null
        // notes:*Must Be Vacated for Athletic Events
        // latitude:36.070821
        // longitude:-94.172056

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.070941 , -94.17172),new LatLng(36.071041 , -94.171717),new LatLng(36.071056 , -94.172138),new LatLng(36.070484 , -94.172167),new LatLng(36.070475 , -94.171969),new LatLng(36.070933 , -94.171937),new LatLng(36.070937 , -94.171741),new LatLng(36.070941 , -94.17172)));
        polygon.setTag("id:163");
        stylePolygon(polygon, type);

        // id:2
        // name:72
        // description:Markham and Razorback
        // type:lot
        // permitType:Student
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must be vacated for athletic events.
        // latitude:36.0680515473669
        // longitude:-94.1805033205694

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.068987 , -94.180324),new LatLng(36.068993 , -94.180629),new LatLng(36.068902 , -94.180627),new LatLng(36.068898 , -94.180702),new LatLng(36.066853 , -94.180745),new LatLng(36.066847 , -94.180299),new LatLng(36.067673 , -94.180275),new LatLng(36.067675 , -94.180297),new LatLng(36.068867 , -94.180262),new LatLng(36.06887 , -94.180329),new LatLng(36.068987 , -94.180324)));
        polygon.setTag("id:2");
        stylePolygon(polygon, type);

        // id:3
        // name:73
        // description:Markham and Razorback
        // type:lot
        // permitType:Student
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must be vacated for athletic events.
        // latitude:36.0661611970109
        // longitude:-94.180662408613

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.066725 , -94.180282),new LatLng(36.06676 , -94.180727),new LatLng(36.066324 , -94.180751),new LatLng(36.066335 , -94.181306),new LatLng(36.065825 , -94.181322),new LatLng(36.065803 , -94.180526),new LatLng(36.066031 , -94.18052),new LatLng(36.066031 , -94.180308),new LatLng(36.066725 , -94.180282)));
        polygon.setTag("id:3");
        stylePolygon(polygon, type);

        // id:110
        // name:76
        // description:Alumni House, Razorback Rd.
        // type:lot
        // permitType:Blue Reserved J
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must Be Vacated for Athletic Events
        // latitude:36.069442
        // longitude:-94.180523

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.068986 , -94.180486),new LatLng(36.069025 , -94.180488),new LatLng(36.069017 , -94.180291),new LatLng(36.069638 , -94.180272),new LatLng(36.069637 , -94.180446),new LatLng(36.069708 , -94.180453),new LatLng(36.069709 , -94.180588),new LatLng(36.069695 , -94.180588),new LatLng(36.069025 , -94.180606),new LatLng(36.069018 , -94.180571),new LatLng(36.068988 , -94.180545),new LatLng(36.068986 , -94.180486),new LatLng(36.068986 , -94.180486)));
        polygon.setTag("id:110");
        stylePolygon(polygon, type);

        // id:11
        // name:80
        // description:Ceramics, Library Annex
        // type:lot
        // permitType:Student, Faculty/Staff
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*May be guarded during Athletic Events
        // latitude:36.059411511516
        // longitude:-94.1837168979713

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.058969 , -94.183664),new LatLng(36.058808 , -94.183647),new LatLng(36.058799 , -94.183743),new LatLng(36.058972 , -94.183734),new LatLng(36.058973 , -94.183673),new LatLng(36.058969 , -94.183664)));
        polygon.setTag("id:11");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.059521 , -94.183697),new LatLng(36.059537 , -94.183657),new LatLng(36.059273 , -94.183665),new LatLng(36.059246 , -94.183717),new LatLng(36.059519 , -94.183702),new LatLng(36.059521 , -94.183697)));
        polygon.setTag("id:11");
        stylePolygon(polygon, type);

        // id:139
        // name:83
        // description:Graham St. Annex
        // type:lot
        // permitType:Faculty/Staff
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM, Free 5:01PM-6:59AM
        // weekends:No Permit Required
        // meterDuration:N/A
        // notes:null
        // latitude:36.062024
        // longitude:-94.181592

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.062109 , -94.181714),new LatLng(36.062107 , -94.181512),new LatLng(36.061966 , -94.18151),new LatLng(36.061971 , -94.181713),new LatLng(36.062101 , -94.181714),new LatLng(36.062109 , -94.181714)));
        polygon.setTag("id:139");
        stylePolygon(polygon, type);

        // id:155
        // name:16A
        // description:Off Center St.
        // type:lot
        // permitType:Blue Reserved Zone 11
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required
        // meterDuration:N/A
        // notes:Not Vacated for Athletic Events
        // latitude:36.06436
        // longitude:-94.171712

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.064488 , -94.171667),new LatLng(36.064496 , -94.171772),new LatLng(36.064387 , -94.171772),new LatLng(36.064384 , -94.171842),new LatLng(36.064294 , -94.171841),new LatLng(36.064291 , -94.171754),new LatLng(36.064246 , -94.171751),new LatLng(36.064246 , -94.171681),new LatLng(36.064478 , -94.171673),new LatLng(36.064488 , -94.171667)));
        polygon.setTag("id:155");
        stylePolygon(polygon, type);

        // id:7
        // name:47E
        // description:UAPD, ADSB
        // type:lot
        // permitType:Faculty/Staff, Blue Reserved
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must Be Vacated for Athletic Events
        // latitude:36.0620222079765
        // longitude:-94.1806783177766

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.062084 , -94.180711),new LatLng(36.062032 , -94.180719),new LatLng(36.062043 , -94.180888),new LatLng(36.062091 , -94.180886),new LatLng(36.062084 , -94.180711)));
        polygon.setTag("id:7");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.062079 , -94.180456),new LatLng(36.062017 , -94.180452),new LatLng(36.062032 , -94.180713),new LatLng(36.062085 , -94.180706),new LatLng(36.062079 , -94.180456)));
        polygon.setTag("id:7");
        stylePolygon(polygon, type);

        // id:158
        // name:47S
        // description:Nettleship, ADSB
        // type:lot
        // permitType:Faculty/Staff
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required
        // meterDuration:N/A
        // notes:*May be guarded during Athletic Events
        // latitude:36.061485
        // longitude:-94.181418

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.061496 , -94.181273),new LatLng(36.06151 , -94.181576),new LatLng(36.061458 , -94.181576),new LatLng(36.061442 , -94.18128),new LatLng(36.061487 , -94.181276),new LatLng(36.061496 , -94.181273)));
        polygon.setTag("id:158");
        stylePolygon(polygon, type);

        // id:138
        // name:47W
        // description:Graham and Center
        // type:lot
        // permitType:Student
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM, Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must Be Vacated for Athletic Events
        // latitude:36.062899
        // longitude:-94.181435

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.063144 , -94.181133),new LatLng(36.063165 , -94.18173),new LatLng(36.062247 , -94.181774),new LatLng(36.062242 , -94.181171),new LatLng(36.063136 , -94.181131),new LatLng(36.063144 , -94.181133)));
        polygon.setTag("id:138");
        stylePolygon(polygon, type);

        // id:16
        // name:56d
        // description:Basketball Performance Center
        // type:lot
        // permitType:Student, Faculty/Staff, Blue Reserved
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must Be Vacated for Athletic Events
        // latitude:36.0603689125473
        // longitude:-94.1766056644006

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.059887 , -94.176913),new LatLng(36.060213 , -94.176905),new LatLng(36.060209 , -94.176962),new LatLng(36.059882 , -94.176977),new LatLng(36.059885 , -94.176916),new LatLng(36.059887 , -94.176913)));
        polygon.setTag("id:16");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.060496 , -94.176783),new LatLng(36.060482 , -94.17603),new LatLng(36.060418 , -94.176025),new LatLng(36.060403 , -94.175959),new LatLng(36.060243 , -94.175988),new LatLng(36.060244 , -94.176049),new LatLng(36.06039 , -94.176029),new LatLng(36.060427 , -94.176056),new LatLng(36.060444 , -94.176779),new LatLng(36.060489 , -94.176785),new LatLng(36.060496 , -94.176783)));
        polygon.setTag("id:16");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.059884 , -94.17691),new LatLng(36.059869 , -94.17649),new LatLng(36.059823 , -94.176493),new LatLng(36.059828 , -94.176914),new LatLng(36.059878 , -94.176912),new LatLng(36.059884 , -94.17691)));
        polygon.setTag("id:16");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.059938 , -94.176784),new LatLng(36.059929 , -94.176224),new LatLng(36.059978 , -94.176084),new LatLng(36.059975 , -94.176035),new LatLng(36.060234 , -94.175992),new LatLng(36.060242 , -94.176116),new LatLng(36.06037 , -94.176103),new LatLng(36.060379 , -94.1768),new LatLng(36.059951 , -94.176794),new LatLng(36.059938 , -94.176784)));
        polygon.setTag("id:16");
        stylePolygon(polygon, type);

        // id:12
        // name:57a
        // description:Printing Services
        // type:lot
        // permitType:Faculty/Staff, Blue Reserved
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must be vacated for athletic events.
        // latitude:36.0582154488196
        // longitude:-94.1836214456841

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.058435 , -94.1829),new LatLng(36.058435 , -94.182846),new LatLng(36.058359 , -94.182857),new LatLng(36.058361 , -94.182897),new LatLng(36.058428 , -94.182901),new LatLng(36.058435 , -94.1829)));
        polygon.setTag("id:12");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.058267 , -94.183264),new LatLng(36.058262 , -94.183273),new LatLng(36.058151 , -94.183272),new LatLng(36.058151 , -94.183381),new LatLng(36.058267 , -94.183379),new LatLng(36.058268 , -94.18328),new LatLng(36.058267 , -94.183264)));
        polygon.setTag("id:12");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.058149 , -94.183388),new LatLng(36.058271 , -94.183385),new LatLng(36.058278 , -94.183564),new LatLng(36.058357 , -94.183569),new LatLng(36.05832 , -94.18362),new LatLng(36.058542 , -94.183616),new LatLng(36.058579 , -94.18356),new LatLng(36.058687 , -94.183556),new LatLng(36.058583 , -94.183672),new LatLng(36.058616 , -94.183721),new LatLng(36.058189 , -94.183729),new LatLng(36.058151 , -94.183647),new LatLng(36.058149 , -94.183388)));
        polygon.setTag("id:12");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.058696 , -94.183302),new LatLng(36.0587 , -94.183547),new LatLng(36.058726 , -94.183549),new LatLng(36.058722 , -94.183302),new LatLng(36.058703 , -94.183302),new LatLng(36.058696 , -94.183302)));
        polygon.setTag("id:12");
        stylePolygon(polygon, type);

        // id:17
        // name:56b
        // description:Leroy Pond and Razorback
        // type:lot
        // permitType:Student
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must be vacated for athletic events.
        // latitude:36.0600216740976
        // longitude:-94.1797396977807

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.059877 , -94.179919),new LatLng(36.059879 , -94.179275),new LatLng(36.060566 , -94.179349),new LatLng(36.060572 , -94.180091),new LatLng(36.06029 , -94.180082),new LatLng(36.059931 , -94.180074),new LatLng(36.059933 , -94.18),new LatLng(36.059928 , -94.179919),new LatLng(36.059877 , -94.179919)));
        polygon.setTag("id:17");
        stylePolygon(polygon, type);

        // id:4
        // name:73a
        // description:Hotz and Razorback
        // type:lot
        // permitType:Student
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must be vacated for athletic events.
        // latitude:36.0656210884622
        // longitude:-94.1808692232493

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.06568 , -94.180322),new LatLng(36.065688 , -94.180561),new LatLng(36.065638 , -94.180561),new LatLng(36.065643 , -94.180687),new LatLng(36.065684 , -94.180684),new LatLng(36.065697 , -94.180955),new LatLng(36.065647 , -94.180963),new LatLng(36.065656 , -94.181154),new LatLng(36.06571 , -94.181148),new LatLng(36.065714 , -94.181306),new LatLng(36.065552 , -94.181314),new LatLng(36.065545 , -94.181129),new LatLng(36.065591 , -94.181129),new LatLng(36.065584 , -94.181025),new LatLng(36.065539 , -94.181017),new LatLng(36.065526 , -94.180724),new LatLng(36.06558 , -94.180724),new LatLng(36.065578 , -94.180628),new LatLng(36.065537 , -94.180631),new LatLng(36.065524 , -94.180378),new LatLng(36.065578 , -94.180378),new LatLng(36.065571 , -94.180317),new LatLng(36.06568 , -94.180322)));
        polygon.setTag("id:4");
        stylePolygon(polygon, type);

        // id:103
        // name:74A
        // description:Center St.
        // type:lot
        // permitType:Student
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must Be Vacated for Athletic Events
        // latitude:36.063538
        // longitude:-94.181306

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.063878 , -94.180401),new LatLng(36.063657 , -94.180408),new LatLng(36.063689 , -94.181022),new LatLng(36.063246 , -94.181048),new LatLng(36.063275 , -94.181529),new LatLng(36.063905 , -94.181465),new LatLng(36.063883 , -94.180448),new LatLng(36.063878 , -94.180401),new LatLng(36.063878 , -94.180401),new LatLng(36.063878 , -94.180401)));
        polygon.setTag("id:103");
        stylePolygon(polygon, type);

        // id:104
        // name:74B
        // description:Center and Graham
        // type:lot
        // permitType:Student
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must be vacated for athletic events.
        // latitude:36.063929
        // longitude:-94.182041

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.063345 , -94.181886),new LatLng(36.064165 , -94.181837),new LatLng(36.064308 , -94.18217),new LatLng(36.063367 , -94.18225),new LatLng(36.063337 , -94.181929),new LatLng(36.063345 , -94.181886)));
        polygon.setTag("id:104");
        stylePolygon(polygon, type);

        // id:102
        // name:74C
        // description:Graham and Hotz
        // type:lot
        // permitType:Student
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must be vacated for athletic events.
        // latitude:36.064679
        // longitude:-94.182052

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.064351 , -94.181577),new LatLng(36.065336 , -94.181478),new LatLng(36.065349 , -94.181644),new LatLng(36.065276 , -94.181722),new LatLng(36.064883 , -94.181738),new LatLng(36.064905 , -94.182349),new LatLng(36.064727 , -94.18237),new LatLng(36.064501 , -94.182221),new LatLng(36.064292 , -94.18174),new LatLng(36.06436 , -94.181577),new LatLng(36.064351 , -94.181577)));
        polygon.setTag("id:102");
        stylePolygon(polygon, type);

        // id:149
        // name:74D
        // description:Graham Ave.
        // type:lot
        // permitType:Student
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-5PM, Free 5:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must Be Vacated for Athletic Events
        // latitude:36.063688
        // longitude:-94.18173

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.064228 , -94.181441),new LatLng(36.064557 , -94.181396),new LatLng(36.064574 , -94.181422),new LatLng(36.064241 , -94.181484),new LatLng(36.064226 , -94.181437),new LatLng(36.064228 , -94.181441)));
        polygon.setTag("id:149");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.065082 , -94.181373),new LatLng(36.065084 , -94.181404),new LatLng(36.064695 , -94.181414),new LatLng(36.064694 , -94.181391),new LatLng(36.065075 , -94.181371),new LatLng(36.065082 , -94.181373)));
        polygon.setTag("id:149");
        stylePolygon(polygon, type);

    }
    public void addFree8_7Polygons(String type)
    {
        Polygon polygon;
        // id:99
        // name:4
        // description:E of Fine Arts Center
        // type:lot
        // permitType:Blue Reserved Night Reserved O
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-8PM Free 8:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:null
        // latitude:36.067467
        // longitude:-94.174604

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.067495 , -94.174393),new LatLng(36.067335 , -94.174603),new LatLng(36.067317 , -94.174753),new LatLng(36.067367 , -94.174753),new LatLng(36.067376 , -94.174629),new LatLng(36.06753 , -94.174426),new LatLng(36.067495 , -94.174393)));
        polygon.setTag("id:99");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.067425 , -94.174815),new LatLng(36.067402 , -94.174871),new LatLng(36.067593 , -94.174874),new LatLng(36.067616 , -94.174815),new LatLng(36.067425 , -94.174815)));
        polygon.setTag("id:99");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.067553 , -94.174589),new LatLng(36.067557 , -94.174694),new LatLng(36.067598 , -94.17466),new LatLng(36.067593 , -94.17456),new LatLng(36.067553 , -94.174589)));
        polygon.setTag("id:99");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.067531 , -94.174695),new LatLng(36.067557 , -94.174756),new LatLng(36.067474 , -94.174753),new LatLng(36.067446 , -94.174697),new LatLng(36.067531 , -94.174695)));
        polygon.setTag("id:99");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.067522 , -94.174526),new LatLng(36.067533 , -94.174573),new LatLng(36.067454 , -94.174668),new LatLng(36.067442 , -94.174619),new LatLng(36.067522 , -94.174526)));
        polygon.setTag("id:99");
        stylePolygon(polygon, type);

        // id:43
        // name:5
        // description:Greek Theater
        // type:lot
        // permitType:Blue Reserved Night Reserved L
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-8PM Free 8:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:null
        // latitude:36.067346
        // longitude:-94.174358

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.066845 , -94.17459),new LatLng(36.066963 , -94.174585),new LatLng(36.067081 , -94.174558),new LatLng(36.067161 , -94.174523),new LatLng(36.06724 , -94.174473),new LatLng(36.067311 , -94.174408),new LatLng(36.067376 , -94.174327),new LatLng(36.067416 , -94.17426),new LatLng(36.067451 , -94.174176),new LatLng(36.067477 , -94.174107),new LatLng(36.067496 , -94.174039),new LatLng(36.067516 , -94.173893),new LatLng(36.067513 , -94.173814),new LatLng(36.06754 , -94.173812),new LatLng(36.067538 , -94.173891),new LatLng(36.067531 , -94.17397),new LatLng(36.067517 , -94.174046),new LatLng(36.067499 , -94.174119),new LatLng(36.067475 , -94.174188),new LatLng(36.067445 , -94.174259),new LatLng(36.067412 , -94.174322),new LatLng(36.067357 , -94.174394),new LatLng(36.067309 , -94.174447),new LatLng(36.067248 , -94.174501),new LatLng(36.067203 , -94.174534),new LatLng(36.067136 , -94.174573),new LatLng(36.067063 , -94.174595),new LatLng(36.066987 , -94.174615),new LatLng(36.066906 , -94.174618),new LatLng(36.066845 , -94.17462),new LatLng(36.066845 , -94.17459)));
        polygon.setTag("id:43");
        stylePolygon(polygon, type);

        // id:73
        // name:7
        // description:Lefler Law, Human Env. Sci., Rosen
        // type:lot
        // permitType:Blue Reserved Night Reserved B
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-8PM Free 8:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:*Must be vacated for athletic events.
        // latitude:36.0699109215757
        // longitude:-94.1742034354819


        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.069995 , -94.174253),new LatLng(36.069848 , -94.174264),new LatLng(36.069845 , -94.174198),new LatLng(36.069814 , -94.174197),new LatLng(36.069817 , -94.174266),new LatLng(36.069692 , -94.17427),new LatLng(36.06969 , -94.174202),new LatLng(36.069993 , -94.174182),new LatLng(36.069995 , -94.174253)));
        polygon.setTag("id:73");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.069565 , -94.174095),new LatLng(36.069564 , -94.174017),new LatLng(36.06952 , -94.173985),new LatLng(36.069517 , -94.174068),new LatLng(36.069557 , -94.174093),new LatLng(36.069565 , -94.174095)));
        polygon.setTag("id:73");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.069556 , -94.173907),new LatLng(36.069557 , -94.173804),new LatLng(36.069515 , -94.173772),new LatLng(36.069512 , -94.173871),new LatLng(36.069549 , -94.173901),new LatLng(36.069556 , -94.173907)));
        polygon.setTag("id:73");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.069557 , -94.1737),new LatLng(36.069557 , -94.173619),new LatLng(36.069516 , -94.173591),new LatLng(36.069517 , -94.173677),new LatLng(36.069546 , -94.173692),new LatLng(36.069557 , -94.1737)));
        polygon.setTag("id:73");
        stylePolygon(polygon, type);

        /////////////////////////////////////exception
        // id:97
        // name:16
        // description:Duncan Avenue
        // type:lot
        // permitType:Blue Reserved Zone 11, Metered
        // paymentType:Permit, Coins, Visa, MC, Disc, AMEX
        // fine:null
        // weekdays:Permit Required at All Times Metered Spaces: 7AM-8PM, Free 8PM-6:59AM
        // weekends:Permit Required at All Times Metered Spaces: 7AM-8PM, Free 8PM-6:59AM
        // meterDuration:$1.75 Hourly
        // notes:Not vacated for athletic events.
        // latitude:36.06427
        // longitude:-94.171318

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.064344 , -94.171219),new LatLng(36.064094 , -94.171227),new LatLng(36.064093 , -94.171294),new LatLng(36.064343 , -94.171284),new LatLng(36.064344 , -94.171219)));
        polygon.setTag("id:97");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.064419 , -94.171232),new LatLng(36.064419 , -94.171352),new LatLng(36.06446 , -94.171397),new LatLng(36.06446 , -94.171279),new LatLng(36.064419 , -94.171232)));
        polygon.setTag("id:97");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.064099 , -94.171364),new LatLng(36.064371 , -94.171356),new LatLng(36.064373 , -94.171417),new LatLng(36.0641 , -94.171425),new LatLng(36.064099 , -94.171364)));
        polygon.setTag("id:97");
        stylePolygon(polygon, type);

        // id:44
        // name:61
        // description:McIlroy House
        // type:lot
        // permitType:Blue Reserved Night Reserved Q
        // paymentType:Permit
        // fine:null
        // weekdays:Permit only 7AM-8PM Free 8:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:null
        // latitude:36.0642913072484
        // longitude:-94.1750306931287

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.064493 , -94.174836),new LatLng(36.06443 , -94.174977),new LatLng(36.064387 , -94.174949),new LatLng(36.064328 , -94.175037),new LatLng(36.064381 , -94.175047),new LatLng(36.064372 , -94.175174),new LatLng(36.064174 , -94.175165),new LatLng(36.0643 , -94.17487),new LatLng(36.06434 , -94.174899),new LatLng(36.064401 , -94.174781),new LatLng(36.064493 , -94.174836)));
        polygon.setTag("id:44");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.064185 , -94.175106),new LatLng(36.064016 , -94.175064),new LatLng(36.064019 , -94.175032),new LatLng(36.064188 , -94.175071),new LatLng(36.064185 , -94.175106)));
        polygon.setTag("id:44");
        stylePolygon(polygon, type);

        polygon = mMap.addPolygon(new PolygonOptions().clickable(true).add(new LatLng(36.063949 , -94.174642),new LatLng(36.064054 , -94.174596),new LatLng(36.064231 , -94.174579),new LatLng(36.064226 , -94.174545),new LatLng(36.064106 , -94.174556),new LatLng(36.064045 , -94.174564),new LatLng(36.06394 , -94.174616),new LatLng(36.063949 , -94.174642)));
        polygon.setTag("id:44");
        stylePolygon(polygon, type);


        // id:87
        // name:GAPG
        // description:Garland Ave. Parking Garage
        // type:garage
        // permitType:Garage Reserved G, Metered
        // paymentType:Permit, Coins, Visa, MC, Disc, AMEX
        // fine:null
        // weekdays:Permit only 7AM-8PM Free 8:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:$1.75 Hourly
        // notes:Level 3 and Level 3 ramp vacated for Athletic Events.
        // latitude:36.0728576357772
        // longitude:-94.1748409563643

        // id:51
        // name:HAPG
        // description:Harmon Ave. Parking Garage
        // type:garage
        // permitType:Garage Reserved H, Metered
        // paymentType:Permit, Coins, Visa, MC, Disc, AMEX
        // fine:null
        // weekdays:Permit only 7AM-8PM Free 8:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:$1.75 Hourly
        // notes:Not Vacated for Athletic Events
        // latitude:36.0651707888244
        // longitude:-94.1717169895053

        // id:52
        // name:SDPG
        // description:Stadium Drive Parking Garage
        // type:garage
        // permitType:Garage Reserved S, Metered
        // paymentType:Permit, Coins, Visa, MC, Disc, AMEX
        // fine:null
        // weekdays:Permit only 7AM-8PM Free 8:01PM-6:59AM
        // weekends:No Permit Required*
        // meterDuration:N/A
        // notes:null
        // latitude:36.0674676603565
        // longitude:-94.1764306671934




    }
    public void addReservedLotsPolygons()
    {
        Polygon polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.069539, -94.176546),
                        new LatLng(36.069535, -94.176428),
                        new LatLng(36.069372, -94.176428),
                        new LatLng(36.069365, -94.176487)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot75-1");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);
/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.069377, -94.17662),
                        new LatLng(36.069282, -94.17658),
                        new LatLng(36.06921, -94.176582),
                        new LatLng(36.06921, -94.176483),
                        new LatLng(36.069299, -94.176515),
                        new LatLng(36.06939, -94.176561)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot75-2");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.06917 ,-94.176272),
                        new LatLng(36.06917 ,-94.176154),
                        new LatLng(36.069036 ,-94.176157),
                        new LatLng(36.069034 ,-94.176272),
                        new LatLng(36.06917 ,-94.176272)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot75-3");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.070067 ,-94.17626),
                        new LatLng(36.069725 ,-94.176265),
                        new LatLng(36.069725 ,-94.176337),
                        new LatLng(36.070066 ,-94.176323),
                        new LatLng(36.070067 ,-94.17626)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot74-1");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.070059 ,-94.176474),
                        new LatLng(36.069727 ,-94.17649),
                        new LatLng(36.069729 ,-94.176552),
                        new LatLng(36.070061 ,-94.176541),
                        new LatLng(36.070059 ,-94.176474)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot74-2");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.07021 ,-94.178106),
                        new LatLng(36.070323 ,-94.178248),
                        new LatLng(36.070329 ,-94.178801),
                        new LatLng(36.070223 ,-94.178806),
                        new LatLng(36.07021 ,-94.178106),
                        new LatLng(36.07021 ,-94.178106)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot1");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.070977 ,-94.174473),
                        new LatLng(36.071027 ,-94.174509),
                        new LatLng(36.071072 ,-94.174529),
                        new LatLng(36.071121 ,-94.174494),
                        new LatLng(36.071132 ,-94.17497),
                        new LatLng(36.070777 ,-94.174969),
                        new LatLng(36.070777 ,-94.174905),
                        new LatLng(36.07068 ,-94.174905999999993),
                        new LatLng(36.070675 ,-94.174768),
                        new LatLng(36.070824 ,-94.174763),
                        new LatLng(36.070827 ,-94.174844),
                        new LatLng(36.071027 ,-94.174832),
                        new LatLng(36.071029 ,-94.174813),
                        new LatLng(36.07098 ,-94.174784),
                        new LatLng(36.070977 ,-94.174473)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot56");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.065612 ,-94.170024),
                        new LatLng(36.065612 ,-94.170149),
                        new LatLng(36.065554 ,-94.170151),
                        new LatLng(36.065557 ,-94.170303),
                        new LatLng(36.065743 ,-94.170293),
                        new LatLng(36.065754 ,-94.170709),
                        new LatLng(36.065887 ,-94.1709),
                        new LatLng(36.066356999999996 ,-94.170881),
                        new LatLng(36.06634 ,-94.169942),
                        new LatLng(36.066268 ,-94.169938),
                        new LatLng(36.066266 ,-94.16982),
                        new LatLng(36.065883 ,-94.169817),
                        new LatLng(36.065882 ,-94.170019),
                        new LatLng(36.065612 ,-94.170024)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot47");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.070901 ,-94.177279),
                        new LatLng(36.070907 ,-94.177592),
                        new LatLng(36.070959 ,-94.177589),
                        new LatLng(36.070952 ,-94.177279),
                        new LatLng(36.070901 ,-94.177279)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot84-1");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.071646 ,-94.176677),
                        new LatLng(36.071692 ,-94.176708),
                        new LatLng(36.071693 ,-94.176852),
                        new LatLng(36.071652 ,-94.176827),
                        new LatLng(36.071646 ,-94.176677)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot86-1");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.071469 ,-94.176694),
                        new LatLng(36.071424 ,-94.17676),
                        new LatLng(36.071338 ,-94.176741),
                        new LatLng(36.071378 ,-94.176681),
                        new LatLng(36.071469 ,-94.176694)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot86-2");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.07099 ,-94.176227),
                        new LatLng(36.071016 ,-94.176173),
                        new LatLng(36.070665 ,-94.176179),
                        new LatLng(36.070641 ,-94.176246),
                        new LatLng(36.07099 ,-94.176227)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot85-1");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.071473 ,-94.175149),
                        new LatLng(36.071424 ,-94.17515),
                        new LatLng(36.071422 ,-94.17509),
                        new LatLng(36.071471 ,-94.175092),
                        new LatLng(36.071473 ,-94.175149)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot55");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.072755 ,-94.179723),
                        new LatLng(36.072684 ,-94.179726),
                        new LatLng(36.072686 ,-94.179783),
                        new LatLng(36.072761 ,-94.179779),
                        new LatLng(36.072755 ,-94.179723)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot82");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.071212 ,-94.179832),
                        new LatLng(36.071214 ,-94.179488),
                        new LatLng(36.071331 ,-94.179491),
                        new LatLng(36.071336 ,-94.179832),
                        new LatLng(36.071212 ,-94.179832)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot83");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.067279 ,-94.177596),
                        new LatLng(36.067097 ,-94.177578),
                        new LatLng(36.067095 ,-94.177632),
                        new LatLng(36.067282 ,-94.17764),
                        new LatLng(36.067279 ,-94.177596)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot36");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.062084 ,-94.180711),
                        new LatLng(36.062032 ,-94.180719),
                        new LatLng(36.062043 ,-94.180888),
                        new LatLng(36.062091 ,-94.180886),
                        new LatLng(36.062084 ,-94.180711)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot7");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.066845 ,-94.17459),
                        new LatLng(36.066963 ,-94.174585),
                        new LatLng(36.067081 ,-94.174558),
                        new LatLng(36.067161 ,-94.174523),
                        new LatLng(36.06724 ,-94.174473),
                        new LatLng(36.067311 ,-94.174408),
                        new LatLng(36.067376 ,-94.174327),
                        new LatLng(36.067416 ,-94.17426),
                        new LatLng(36.067451 ,-94.174176),
                        new LatLng(36.067477 ,-94.174107),
                        new LatLng(36.067496 ,-94.174039),
                        new LatLng(36.067516 ,-94.173893),
                        new LatLng(36.067513 ,-94.173814),
                        new LatLng(36.06754 ,-94.173812),
                        new LatLng(36.067538 ,-94.173891),
                        new LatLng(36.067531 ,-94.17397),
                        new LatLng(36.067517 ,-94.174046),
                        new LatLng(36.067499 ,-94.174119),
                        new LatLng(36.067475 ,-94.174188),
                        new LatLng(36.067445 ,-94.174259),
                        new LatLng(36.067412 ,-94.174322),
                        new LatLng(36.067357 ,-94.174394),
                        new LatLng(36.067309 ,-94.174447),
                        new LatLng(36.067248 ,-94.174501),
                        new LatLng(36.067203 ,-94.174534),
                        new LatLng(36.067136 ,-94.174573),
                        new LatLng(36.067063 ,-94.174595),
                        new LatLng(36.066987 ,-94.174615),
                        new LatLng(36.066906 ,-94.174618),
                        new LatLng(36.066845 ,-94.17462),
                        new LatLng(36.066845 ,-94.17459)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot43");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.063832 ,-94.177541),
                        new LatLng(36.063834 ,-94.177054),
                        new LatLng(36.063806 ,-94.177054),
                        new LatLng(36.063803 ,-94.177542),
                        new LatLng(36.063826 ,-94.177542),
                        new LatLng(36.063832 ,-94.177541)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot32");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.059887 ,-94.176913),
                        new LatLng(36.060213 ,-94.176905),
                        new LatLng(36.060209 ,-94.176962),
                        new LatLng(36.059882 ,-94.176977),
                        new LatLng(36.059885 ,-94.176916),
                        new LatLng(36.059887 ,-94.176913)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot16");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.058018 ,-94.18272),
                        new LatLng(36.05798 ,-94.181179),
                        new LatLng(36.057908 ,-94.181177),
                        new LatLng(36.057941 ,-94.18272),
                        new LatLng(36.058018 ,-94.18272)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot13");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.070073 ,-94.173359),
                        new LatLng(36.069773 ,-94.173367),
                        new LatLng(36.069746 ,-94.173321),
                        new LatLng(36.070051 ,-94.17331),
                        new LatLng(36.070073 ,-94.173359)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot71");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.064493 ,-94.174836),
                        new LatLng(36.06443 ,-94.174977),
                        new LatLng(36.064387 ,-94.174949),
                        new LatLng(36.064328 ,-94.175037),
                        new LatLng(36.064381 ,-94.175047),
                        new LatLng(36.064372 ,-94.175174),
                        new LatLng(36.064174 ,-94.175165),
                        new LatLng(36.0643 ,-94.17487),
                        new LatLng(36.06434 ,-94.174899),
                        new LatLng(36.064401 ,-94.174781),
                        new LatLng(36.064493 ,-94.174836)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot44-1");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.064185 ,-94.175106),
                        new LatLng(36.064016 ,-94.175064),
                        new LatLng(36.064019 ,-94.175032),
                        new LatLng(36.064188 ,-94.175071),
                        new LatLng(36.064185 ,-94.175106)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot44-2");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.069377, -94.17662),
                        new LatLng(36.069282, -94.17658),
                        new LatLng(36.06921, -94.176582),
                        new LatLng(36.06921, -94.176483),
                        new LatLng(36.069299, -94.176515),
                        new LatLng(36.06939, -94.176561)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot75-2");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.063949 ,-94.174642),
                        new LatLng(36.064054 ,-94.174596),
                        new LatLng(36.064231 ,-94.174579),
                        new LatLng(36.064226 ,-94.174545),
                        new LatLng(36.064106 ,-94.174556),
                        new LatLng(36.064045 ,-94.174564),
                        new LatLng(36.06394 ,-94.174616),
                        new LatLng(36.063949 ,-94.174642)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot44-3");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.069995 ,-94.174253),
                        new LatLng(36.069848 ,-94.174264),
                        new LatLng(36.069845 ,-94.174198),
                        new LatLng(36.069814 ,-94.174197),
                        new LatLng(36.069817 ,-94.174266),
                        new LatLng(36.069692 ,-94.17427),
                        new LatLng(36.06969 ,-94.174202),
                        new LatLng(36.069993 ,-94.174182),
                        new LatLng(36.069995 ,-94.174253)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot73");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.065876 ,-94.171819),
                        new LatLng(36.065884 ,-94.172127),
                        new LatLng(36.065774 ,-94.172127),
                        new LatLng(36.06577 ,-94.172095),
                        new LatLng(36.06572 ,-94.172097),
                        new LatLng(36.065717 ,-94.172063),
                        new LatLng(36.065668 ,-94.172063),
                        new LatLng(36.065661 ,-94.171821),
                        new LatLng(36.065876 ,-94.171819)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot94");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.065598 ,-94.172753),
                        new LatLng(36.066012 ,-94.172739),
                        new LatLng(36.066012 ,-94.172694),
                        new LatLng(36.065598 ,-94.172702),
                        new LatLng(36.065598 ,-94.172753)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot95-1");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.066094 ,-94.172696),
                        new LatLng(36.066104 ,-94.172995),
                        new LatLng(36.066148 ,-94.172993),
                        new LatLng(36.066143 ,-94.1727),
                        new LatLng(36.066094 ,-94.172696)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot95-2");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.069625 ,-94.175895),
                        new LatLng(36.069702 ,-94.175904),
                        new LatLng(36.069702 ,-94.175959),
                        new LatLng(36.069624 ,-94.175955),
                        new LatLng(36.069625 ,-94.175895)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot74-3");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.071694 ,-94.176966),
                        new LatLng(36.071703 ,-94.177225),
                        new LatLng(36.071657 ,-94.177192),
                        new LatLng(36.071651 ,-94.176937),
                        new LatLng(36.071694 ,-94.176966)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot86");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.071121 ,-94.177322),
                        new LatLng(36.071123 ,-94.177383),
                        new LatLng(36.071072 ,-94.177385),
                        new LatLng(36.071069 ,-94.177324),
                        new LatLng(36.071121 ,-94.177322)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot84-2");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.07103 ,-94.176353),
                        new LatLng(36.071006 ,-94.176302),
                        new LatLng(36.070586 ,-94.176313),
                        new LatLng(36.070605 ,-94.176364),
                        new LatLng(36.07103 ,-94.176353)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot85-2");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.071149 ,-94.176344),
                        new LatLng(36.071398 ,-94.17634),
                        new LatLng(36.071379 ,-94.176289),
                        new LatLng(36.071128 ,-94.176294),
                        new LatLng(36.071149 ,-94.176344)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot85-3");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.071211 ,-94.176216),
                        new LatLng(36.071236 ,-94.17616),
                        new LatLng(36.071181 ,-94.176164),
                        new LatLng(36.071154 ,-94.176223),
                        new LatLng(36.071211 ,-94.176216)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot85-4");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.071402 ,-94.17616),
                        new LatLng(36.071289 ,-94.176163),
                        new LatLng(36.071272 ,-94.176219),
                        new LatLng(36.071385 ,-94.176216),
                        new LatLng(36.071402 ,-94.17616)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot85-5");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.064344 ,-94.171219),
                        new LatLng(36.064094 ,-94.171227),
                        new LatLng(36.064093 ,-94.171294),
                        new LatLng(36.064343 ,-94.171284),
                        new LatLng(36.064344 ,-94.171219)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot97-1");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.064419 ,-94.171232),
                        new LatLng(36.064419 ,-94.171352),
                        new LatLng(36.06446 ,-94.171397),
                        new LatLng(36.06446 ,-94.171279),
                        new LatLng(36.064419 ,-94.171232)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot97-2");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.064099 ,-94.171364),
                        new LatLng(36.064371 ,-94.171356),
                        new LatLng(36.064373 ,-94.171417),
                        new LatLng(36.0641 ,-94.171425),
                        new LatLng(36.064099 ,-94.171364)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot97-3");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.065598 ,-94.172753),
                        new LatLng(36.066012 ,-94.172739),
                        new LatLng(36.066012 ,-94.172694),
                        new LatLng(36.065598 ,-94.172702),
                        new LatLng(36.065598 ,-94.172753)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot95");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.071424 ,-94.174949),
                        new LatLng(36.071424 ,-94.175076),
                        new LatLng(36.071372 ,-94.175078),
                        new LatLng(36.071368 ,-94.174948),
                        new LatLng(36.071424 ,-94.174949)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot55");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.072329 ,-94.173148),
                        new LatLng(36.072268 ,-94.173151),
                        new LatLng(36.072268 ,-94.173049),
                        new LatLng(36.072331 ,-94.173049),
                        new LatLng(36.072329 ,-94.173148)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot57");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.072041 ,-94.179003),
                        new LatLng(36.072037 ,-94.178941),
                        new LatLng(36.072529 ,-94.178912),
                        new LatLng(36.072531 ,-94.178973),
                        new LatLng(36.072041 ,-94.179003)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot82");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.065854 ,-94.176383),
                        new LatLng(36.065951 ,-94.1764),
                        new LatLng(36.065944 ,-94.176465),
                        new LatLng(36.06585 ,-94.176445),
                        new LatLng(36.065854 ,-94.176383)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot39");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.067495 ,-94.174393),
                        new LatLng(36.067335 ,-94.174603),
                        new LatLng(36.067317 ,-94.174753),
                        new LatLng(36.067367 ,-94.174753),
                        new LatLng(36.067376 ,-94.174629),
                        new LatLng(36.06753 ,-94.174426),
                        new LatLng(36.067495 ,-94.174393)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot99-1");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.067425 ,-94.174815),
                        new LatLng(36.067402 ,-94.174871),
                        new LatLng(36.067593 ,-94.174874),
                        new LatLng(36.067616 ,-94.174815),
                        new LatLng(36.067425 ,-94.174815)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot99-2");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.067553 ,-94.174589),
                        new LatLng(36.067557 ,-94.174694),
                        new LatLng(36.067598 ,-94.17466),
                        new LatLng(36.067593 ,-94.17456),
                        new LatLng(36.067553 ,-94.174589)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot99-3");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.067553 ,-94.174589),
                        new LatLng(36.067557 ,-94.174694),
                        new LatLng(36.067598 ,-94.17466),
                        new LatLng(36.067593 ,-94.17456),
                        new LatLng(36.067553 ,-94.174589)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot99-4");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.067531 ,-94.174695),
                        new LatLng(36.067557 ,-94.174756),
                        new LatLng(36.067474 ,-94.174753),
                        new LatLng(36.067446 ,-94.174697),
                        new LatLng(36.067531 ,-94.174695)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot99-5");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.067522 ,-94.174526),
                        new LatLng(36.067533 ,-94.174573),
                        new LatLng(36.067454 ,-94.174668),
                        new LatLng(36.067442 ,-94.174619),
                        new LatLng(36.067522 ,-94.174526)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot99-6");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.065869 ,-94.17823),
                        new LatLng(36.065867 ,-94.178387),
                        new LatLng(36.065917 ,-94.178388),
                        new LatLng(36.065916 ,-94.178236),
                        new LatLng(36.065869 ,-94.17823)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot34");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.067305 ,-94.175509),
                        new LatLng(36.067298 ,-94.175089),
                        new LatLng(36.06725 ,-94.175089),
                        new LatLng(36.06726 ,-94.175477),
                        new LatLng(36.067305 ,-94.175509)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot42");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.067098 ,-94.175222),
                        new LatLng(36.067097 ,-94.175253),
                        new LatLng(36.06705 ,-94.175252),
                        new LatLng(36.067049 ,-94.175219),
                        new LatLng(36.067098 ,-94.175222)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot42");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.065598 ,-94.172753),
                        new LatLng(36.066012 ,-94.172739),
                        new LatLng(36.066012 ,-94.172694),
                        new LatLng(36.065598 ,-94.172702),
                        new LatLng(36.065598 ,-94.172753)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot95");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.061142 ,-94.177184),
                        new LatLng(36.061131 ,-94.177189),
                        new LatLng(36.060972 ,-94.177415),
                        new LatLng(36.061007 ,-94.177457),
                        new LatLng(36.061176 ,-94.177235),
                        new LatLng(36.061142 ,-94.177184),
                        new LatLng(36.061142 ,-94.177184)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot20");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.06972 ,-94.173243),
                        new LatLng(36.069747 ,-94.173194),
                        new LatLng(36.069898 ,-94.173174),
                        new LatLng(36.069872 ,-94.173237),
                        new LatLng(36.06972 ,-94.173243)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot71");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.069995 ,-94.173171),
                        new LatLng(36.069975 ,-94.173225),
                        new LatLng(36.070121 ,-94.173217),
                        new LatLng(36.070141 ,-94.173159),
                        new LatLng(36.069995 ,-94.173171)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot71");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.068986 ,-94.180486),
                        new LatLng(36.069025 ,-94.180488),
                        new LatLng(36.069017 ,-94.180291),
                        new LatLng(36.069638 ,-94.180272),
                        new LatLng(36.069637 ,-94.180446),
                        new LatLng(36.069708 ,-94.180453),
                        new LatLng(36.069709 ,-94.180588),
                        new LatLng(36.069695 ,-94.180588),
                        new LatLng(36.069025 ,-94.180606),
                        new LatLng(36.069018 ,-94.180571),
                        new LatLng(36.068988 ,-94.180545),
                        new LatLng(36.068986 ,-94.180486),
                        new LatLng(36.068986 ,-94.180486)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot110");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.064711 ,-94.177696),
                        new LatLng(36.064962 ,-94.177696),
                        new LatLng(36.064963 ,-94.177815),
                        new LatLng(36.064716 ,-94.177815),
                        new LatLng(36.064709 ,-94.177707),
                        new LatLng(36.064711 ,-94.177696)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot111");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.069997 ,-94.173084),
                        new LatLng(36.070006 ,-94.172825),
                        new LatLng(36.069949 ,-94.17282),
                        new LatLng(36.06994 ,-94.173111),
                        new LatLng(36.069992 ,-94.173091),
                        new LatLng(36.069997 ,-94.173084)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot71");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.058435 ,-94.1829),
                        new LatLng(36.058435 ,-94.182846),
                        new LatLng(36.058359 ,-94.182857),
                        new LatLng(36.058361 ,-94.182897),
                        new LatLng(36.058428 ,-94.182901),
                        new LatLng(36.058435 ,-94.1829)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot12");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.058267 ,-94.183264),
                        new LatLng(36.058262 ,-94.183273),
                        new LatLng(36.058151 ,-94.183272),
                        new LatLng(36.058151 ,-94.183381),
                        new LatLng(36.058267 ,-94.183379),
                        new LatLng(36.058268 ,-94.18328),
                        new LatLng(36.058267 ,-94.183264)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot12");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.072348 ,-94.174202),
                        new LatLng(36.072226 ,-94.174204),
                        new LatLng(36.072221 ,-94.173912),
                        new LatLng(36.072347 ,-94.173906),
                        new LatLng(36.072354 ,-94.1742),
                        new LatLng(36.072348 ,-94.174202)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot129");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.071734 ,-94.172679),
                        new LatLng(36.071726 ,-94.172407),
                        new LatLng(36.071613 ,-94.172412),
                        new LatLng(36.071588 ,-94.172354),
                        new LatLng(36.071492 ,-94.172353),
                        new LatLng(36.071469 ,-94.172416),
                        new LatLng(36.071477 ,-94.172696),
                        new LatLng(36.071528 ,-94.172741),
                        new LatLng(36.071686 ,-94.172731),
                        new LatLng(36.071727 ,-94.17268),
                        new LatLng(36.071734 ,-94.172679)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot131");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.069693 ,-94.171083),
                        new LatLng(36.069618 ,-94.171056),
                        new LatLng(36.069592 ,-94.171142),
                        new LatLng(36.069646 ,-94.171169),
                        new LatLng(36.06969 ,-94.171094),
                        new LatLng(36.069693 ,-94.171083)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot134");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.06975 ,-94.170877),
                        new LatLng(36.069703 ,-94.170865),
                        new LatLng(36.069702 ,-94.17078),
                        new LatLng(36.069754 ,-94.170785),
                        new LatLng(36.069759 ,-94.170879),
                        new LatLng(36.06975 ,-94.170877)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot134");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.066247 ,-94.178707),
                        new LatLng(36.06625 ,-94.178863),
                        new LatLng(36.066189 ,-94.178867),
                        new LatLng(36.066186 ,-94.17871),
                        new LatLng(36.066245 ,-94.178707),
                        new LatLng(36.066247 ,-94.178707)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot135");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.060496 ,-94.176783),
                        new LatLng(36.060482 ,-94.17603),
                        new LatLng(36.060418 ,-94.176025),
                        new LatLng(36.060403 ,-94.175959),
                        new LatLng(36.060243 ,-94.175988),
                        new LatLng(36.060244 ,-94.176049),
                        new LatLng(36.06039 ,-94.176029),
                        new LatLng(36.060427 ,-94.176056),
                        new LatLng(36.060444 ,-94.176779),
                        new LatLng(36.060489 ,-94.176785),
                        new LatLng(36.060496 ,-94.176783)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot16");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.064488 ,-94.171667),
                        new LatLng(36.064496 ,-94.171772),
                        new LatLng(36.064387 ,-94.171772),
                        new LatLng(36.064384 ,-94.171842),
                        new LatLng(36.064294 ,-94.171841),
                        new LatLng(36.064291 ,-94.171754),
                        new LatLng(36.064246 ,-94.171751),
                        new LatLng(36.064246 ,-94.171681),
                        new LatLng(36.064478 ,-94.171673),
                        new LatLng(36.064488 ,-94.171667)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot155");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.069565 ,-94.174095),
                        new LatLng(36.069564 ,-94.174017),
                        new LatLng(36.06952 ,-94.173985),
                        new LatLng(36.069517 ,-94.174068),
                        new LatLng(36.069557 ,-94.174093),
                        new LatLng(36.069565 ,-94.174095)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot73");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.069556 ,-94.173907),
                        new LatLng(36.069557 ,-94.173804),
                        new LatLng(36.069515 ,-94.173772),
                        new LatLng(36.069512 ,-94.173871),
                        new LatLng(36.069549 ,-94.173901),
                        new LatLng(36.069556 ,-94.173907)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot73");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.069557 ,-94.1737),
                        new LatLng(36.069557 ,-94.173619),
                        new LatLng(36.069516 ,-94.173591),
                        new LatLng(36.069517 ,-94.173677),
                        new LatLng(36.069546 ,-94.173692),
                        new LatLng(36.069557 ,-94.1737)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot73");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.070515 ,-94.168444),
                        new LatLng(36.070258 ,-94.168445),
                        new LatLng(36.070251 ,-94.168394),
                        new LatLng(36.070514 ,-94.168393),
                        new LatLng(36.070516 ,-94.168436),
                        new LatLng(36.070515 ,-94.168444)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot64");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.071081 ,-94.170949),
                        new LatLng(36.070897 ,-94.170956),
                        new LatLng(36.070896 ,-94.170212),
                        new LatLng(36.071077 ,-94.170194),
                        new LatLng(36.071081 ,-94.170947),
                        new LatLng(36.071081 ,-94.170949)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot60");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.070771 ,-94.170799),
                        new LatLng(36.070814 ,-94.170802),
                        new LatLng(36.070814 ,-94.170702),
                        new LatLng(36.070769 ,-94.170705),
                        new LatLng(36.070766 ,-94.170788),
                        new LatLng(36.070771 ,-94.170799)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot161");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.062719 ,-94.176041),
                        new LatLng(36.062711 ,-94.176069),
                        new LatLng(36.062544 ,-94.17597),
                        new LatLng(36.062549 ,-94.175944),
                        new LatLng(36.062711 ,-94.176034),
                        new LatLng(36.062719 ,-94.176041)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot140");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);

/////////////////////////////////////////////////////////////////////////////////////////////////////
        polygon = mMap.addPolygon(new PolygonOptions()
                .clickable(true).add(
                        new LatLng(36.070941 ,-94.17172),
                        new LatLng(36.071041 ,-94.171717),
                        new LatLng(36.071056 ,-94.172138),
                        new LatLng(36.070484 ,-94.172167),
                        new LatLng(36.070475 ,-94.171969),
                        new LatLng(36.070933 ,-94.171937),
                        new LatLng(36.070937 ,-94.171741),
                        new LatLng(36.070941 ,-94.17172)));
        // Store a data object with the polygon, used here to indicate an arbitrary type.
        polygon.setTag("Lot163");
        stylePolygon(polygon, "Reserved");
        ReservedLots.add(polygon);


    }
    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "ArrayList size is:\n" + ReservedLots.size(), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "ArrayList 0 tag is: " + ReservedLots.get(0).getTag(), Toast.LENGTH_LONG).show();
//        Toast.makeText(this, "ArrayList 1 tag is: " + ReservedLots.get(1).getTag(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    private void stylePolygon(Polygon polygon, String type) {

        int fillColor = COLOR_WHITE_ARGB;

        switch (type) {
            case "Reserved":
                fillColor = COLOR_BLUE_ARGB;
                break;
            case "Faculty":
                fillColor = COLOR_YELLOW_ARGB;
                break;
            case "Resident":
                fillColor = COLOR_RED_ARGB;
                break;
            case "Student":
                fillColor = COLOR_GREEN_ARGB;
                break;
        }
        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
        polygon.setFillColor(fillColor);
    }

    @Override
    public void onPolylineClick(Polyline polyline) {}

    @Override
    public void onPolygonClick(Polygon polygon) {
        // Flip the values of the red, green, and blue components of the polygon's color.
        int color = polygon.getStrokeColor() ^ 0x00ffffff;
        polygon.setStrokeColor(color);
        color = polygon.getFillColor() ^ 0x00ffffff;
        polygon.setFillColor(color);

        Toast.makeText(this, "Area type " + polygon.getTag().toString(), Toast.LENGTH_SHORT).show();
    }

}