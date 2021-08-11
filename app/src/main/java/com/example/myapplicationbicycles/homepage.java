package com.example.myapplicationbicycles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class homepage extends FragmentActivity implements OnMapReadyCallback{

    //initialize variables
    //private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawerLayout;
    //initialize variable for map

    FusedLocationProviderClient fusedLocationProviderClient;
    SupportMapFragment mapFrag;
    // SearchView searchview;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean permissionDenied = false;
    GoogleMap map;
    ArrayList<LatLng> arrayList=new ArrayList<LatLng>();
    LatLng nai = new LatLng(-0.412284,36.950513);
    LatLng muranga = new LatLng(-0.7839, 37.04);
    LatLng karatina = new LatLng(-0.4832, 37.1274);
    LatLng nyeri = new LatLng(-0.412284,36.950513);
    LatLng nyeri1 = new LatLng(-0.413490,36.955249);
    LatLng nyeri2 = new LatLng(-0.412284,36.945765);
    LatLng nyeri3 = new LatLng(-0.419200,36.948663);
    LatLng nyeri4 = new LatLng(-0.421277,36.950132);
    ArrayList<String> title= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //assign variables
        //drawerLayout=findViewById(R.id.drawer_layout);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        drawerLayout = findViewById(R.id.drawer_layout);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // checkMyPermissions();
        initMap();
    }
    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    private void initMap() {
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        arrayList.add(muranga);
        arrayList.add(karatina);
        arrayList.add(nai);
        arrayList.add(nyeri);
        arrayList.add(nyeri1);
        arrayList.add(nyeri2);
        arrayList.add(nyeri3);
        arrayList.add(nyeri4);


        //adding titles
        title.add("muranga");
        title.add("karatina");
        title.add("nai");
        title.add("nyeri");
        title.add("nyeri1");
        title.add("nyeri2");
        title.add("nyeri3");
        title.add("nyeri4");


    }
    //onMapReady

    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;

        enableLocation();
        for (int i=0; i<arrayList.size();i++){
            for (int j=0;j<title.size();j++){
                //add title
                map.addMarker(new MarkerOptions().position(arrayList.get(i)).title(String.valueOf(title.get(j))));

            }
           // map.addMarker(new MarkerOptions().position(arrayList.get(i)).title("marker"));

            // Add a marker in nyeri

            // googleMap.addMarker(new MarkerOptions() .position(nyeri) .title("Kingongo") .snippet("23 max biking stations") .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)) .alpha(0.7f));
            map.animateCamera(CameraUpdateFactory.zoomTo(15));
            map.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }
        //onclick listener for markers
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                String markerTitle = marker.getTitle();
                Intent i = new Intent(homepage.this, Station_info.class);
                //pass title
                i.putExtra("title",markerTitle);
                startActivity(i);
                return false;
            }
        });

    }
    private void enableLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (map!=null){
                map.setMyLocationEnabled(true);
            }
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            PermissionUtils.requestPermission(homepage.this, LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableLocation();
        } else {
            // Permission was denied. Display an error message // [START_EXCLUDE] // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
            // [END_EXCLUDE]
        }
    }
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    } /** * Displays a dialog with error message explaining that the location permission is missing. */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {

    }


    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }


    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        // close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickHome(View view){
        recreate();
    }
    public void ClickDashboard(View view){
        redirectActivity(this,stationsDisplay.class);
    }
    public void ClickAboutus(View view){
        redirectActivity(this, aboutus.class);
    }
    public void ClickLogout(View view){
        logout(this);
    }

    public void ClickMore(View view){
        redirectActivity(this, stationsDisplay.class);
    }
    public static void logout(final Activity activity) {
        //initialize alert dialogs
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        //set title
        builder.setTitle("Logout");
        //set message
        builder.setMessage("Are you sure you wonna logout?");
        //positive
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish activity
                activity.finishAffinity();
                //exit
                System.exit(0);
            }
        });
        //negative
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //show dialog
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause(){
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }



}

