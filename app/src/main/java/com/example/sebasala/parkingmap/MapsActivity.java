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

        //This is Suhail branch
    private GoogleMap mMap;
    private List<Polygon> ReservedLots;
    private String TAG = "MapsActivity";

    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_GREEN_ARGB = 0xff388E3C;
    private static final int COLOR_PURPLE_ARGB = 0xff81C784;
    private static final int COLOR_ORANGE_ARGB = 0xffF57F17;
    private static final int COLOR_BLUE_ARGB = 0xff0a9eee;

    private static final int POLYLINE_STROKE_WIDTH_PX = 12;
    private static final int POLYGON_STROKE_WIDTH_PX = 0;
    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dot.
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);

    // Create a stroke pattern of a gap followed by a dash.
    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private static final List<PatternItem> PATTERN_POLYGON_BETA =
            Arrays.asList(DOT, GAP, DASH, GAP);

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
        // Add polylines and polygons to the map. This section shows just
        // a single polyline. Read the rest of the tutorial to learn more.
        addReservedLotsPolygons();
        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-23.684, 133.903), 4));

        // Set listeners for click events.




            // Add a marker in Sydney and move the camera
            LatLng UARK = new LatLng(36.0685126, -94.1729845);
           // mMap.addMarker(new MarkerOptions().position(UARK).title("Univeristy of Arkansas"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UARK, 15));
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);
            mMap.setOnPolygonClickListener(this);


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
    /**
     * Styles the polyline, based on type.
     *
     * @param polyline The polyline object that needs styling.
     */
    /**
     * Styles the polygon, based on type.
     *
     * @param polygon The polygon object that needs styling.
     */
    private void stylePolygon(Polygon polygon, String type) {

        int fillColor = COLOR_WHITE_ARGB;

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "Reserved":
                fillColor = COLOR_BLUE_ARGB;
                break;
            case "Student":
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                fillColor = COLOR_BLUE_ARGB;
                break;
        }
        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
        polygon.setFillColor(fillColor);
    }

    /**
     * Listens for clicks on a polyline.
     *
     * @param polyline The polyline object that the user has clicked.
     */
    @Override
    public void onPolylineClick(Polyline polyline) {
        // Flip from solid stroke to dotted stroke pattern.
        if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            // The default pattern is a solid stroke.
            polyline.setPattern(null);
        }

        Toast.makeText(this, "Route type " + polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Listens for clicks on a polygon.
     *
     * @param polygon The polygon object that the user has clicked.
     */
    @Override
    public void onPolygonClick(Polygon polygon) {
        // Flip the values of the red, green, and blue components of the polygon's color.
        int color = polygon.getStrokeColor() ^ 0x00ffffff;
        polygon.setStrokeColor(color);
        color = polygon.getFillColor() ^ 0x00ffffff;
        polygon.setFillColor(color);

        Toast.makeText(this, "Area type " + polygon.getTag().toString(), Toast.LENGTH_SHORT).show();
    }

//   private void getLocationPermissions()
//   {
//       String[] permissions = {FINE_LOCATION, COARSE_LOCATION};
//       if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//       {
//           if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//           {
//               LocationPermissionsGranted = true;
//           }
//       }
//       else
//       {
//           ActivityCompat.requestPermissions(this,permissions, LOCATION_PERMISSION_REQUEST_CODE);
//       }
//   }
//
//   @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
//   {
//        LocationPermissionsGranted = false;
//        switch(requestCode)
//        {
//            case LOCATION_PERMISSION_REQUEST_CODE:
//            {
//                if(grantResults.length > 0)
//                {
//                    for(int i=0; i<grantResults.length; i++)
//                    {
//                        if(grantResults[i]!=PackageManager.PERMISSION_GRANTED)
//                        {
//                            LocationPermissionsGranted = false;
//                            return;
//                        }
//                    }
//                    LocationPermissionsGranted = true;
//                }
//            }
//        }
//   }
}