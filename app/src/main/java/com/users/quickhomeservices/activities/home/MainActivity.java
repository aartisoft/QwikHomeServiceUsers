package com.users.quickhomeservices.activities.home;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.users.quickhomeservices.R;
import com.users.quickhomeservices.activities.home.about.AboutActivity;
import com.users.quickhomeservices.activities.home.about.SettingsActivity;
import com.users.quickhomeservices.activities.welcome.SplashScreenActivity;
import com.users.quickhomeservices.databinding.ActivityMainBinding;
import com.users.quickhomeservices.utils.DisplayViewUI;
import com.users.quickhomeservices.utils.MyConstants;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    public static String serviceType, name, imageUrl, about, uid;
    private static FirebaseUser firebaseUser;
    private ActivityMainBinding activityMainBinding;
    public static DatabaseReference serviceTypeDbRef, serviceAccountDbRef;
    private static FirebaseAuth mAuth;
    private static Object mContext;
    //adds
    private InterstitialAd interstitialAd;

    private double latitude, longitude;

    //Step 5
    private FusedLocationProviderClient mFusedLocationProviderClient;
    //step 8
    private LocationCallback mLocationCallBack = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            Location lastLocation = locationResult.getLastLocation();
            //TODO update database with location results

            Log.i("Locationx: ", "Latitude " + lastLocation.getLatitude());
            Log.i("Locationx: ", "longitude " + lastLocation.getLongitude());



        }
    };

    public static Context getAppContext() {
        return (Context) mContext;
    }

    public static void retrieveServiceType() {

        serviceTypeDbRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("Services")
                .child("ServiceType")
                .child(uid);
        serviceTypeDbRef.keepSynced(true);

        serviceTypeDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceType = (String) dataSnapshot.child("accountType").getValue();
                name = (String) dataSnapshot.child("name").getValue();
                imageUrl = (String) dataSnapshot.child("image").getValue();

                Log.i(TAG, "onDataChange: " + serviceType + " " + name + " " + imageUrl);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DisplayViewUI.displayToast(MainActivity.this, databaseError.getMessage());
            }
        });


    }

    public static void retrieveSingleUserDetails(TextView txtName, TextView txtAbout, CircleImageView photo) {
        serviceAccountDbRef = FirebaseDatabase.getInstance()
                .getReference().child("Services")
                .child(serviceType)
                .child(uid);
        serviceAccountDbRef.keepSynced(true);

        serviceAccountDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name = (String) dataSnapshot.child("name").getValue();
                about = (String) dataSnapshot.child("about").getValue();
                imageUrl = (String) dataSnapshot.child("image").getValue();

                txtName.setText(name);
                txtAbout.setText(about);
                Glide.with(getAppContext())
                        .load(MainActivity.imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(photo);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static void retrieveSingleUserDetails(AppCompatImageView photo) {
        serviceAccountDbRef = FirebaseDatabase.getInstance()
                .getReference().child("Services")
                .child(serviceType)
                .child(uid);
        serviceAccountDbRef.keepSynced(true);

        serviceAccountDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                imageUrl = (String) dataSnapshot.child("image").getValue();

                Glide.with(getAppContext())
                        .load(MainActivity.imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(photo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public static void retrieveSingleUserDetails(String position, TextView txtName, TextView txtAbout, ImageView photo) {

        serviceAccountDbRef = FirebaseDatabase.getInstance()
                .getReference().child("Services")
                .child(serviceType)
                .child(position);
        serviceAccountDbRef.keepSynced(true);

        serviceAccountDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name = (String) dataSnapshot.child("name").getValue();
                about = (String) dataSnapshot.child("about").getValue();
                imageUrl = (String) dataSnapshot.child("image").getValue();

                txtName.setText(name);
                txtAbout.setText(about);
                Glide.with(getAppContext())
                        .load(MainActivity.imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(photo);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public static void retrieveSingleUserDetails() {

        serviceAccountDbRef = FirebaseDatabase.getInstance()
                .getReference().child("Services")
                .child(serviceType)
                .child(uid);
        serviceAccountDbRef.keepSynced(true);

        serviceAccountDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name = (String) dataSnapshot.child("name").getValue();
                about = (String) dataSnapshot.child("about").getValue();
                imageUrl = (String) dataSnapshot.child("image").getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mContext = getApplicationContext();
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        assert firebaseUser != null;
        if (mAuth.getCurrentUser() == null) {
            SendUserToLoginActivity();
            return;
        }

        //initialize step 5
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        setUpAppBarConfig();
        //step 9
        // getLastLocation();

        //load add
        loadAdds();


    }

    private void loadAdds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd = new InterstitialAd(MainActivity.this);
        interstitialAd.setAdUnitId(getString(R.string.adUnit));
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                displayInterstitial();
            }
        });
    }

    private void displayInterstitial() {

        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    //Step 1 CHECK PERMISSION
    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    //Step 2 REQUEST PERMISSION IF NOT GRANTED
    private void requestPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, MyConstants.REQUEST_CODE);
        }
    }

    //Step 4 check if the location is turned on from the settings
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //STEP 6 check if permission is granted , next check if gps is enabled.
    private void getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                //get the last known location
                mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {

                    Location location = task.getResult();
                    if (location == null) {
                        //request new location data from step 7 method
                        requestNewLocationData();

                    } else {
                        //TODO update user account with location ON UI THREAD

                        Log.i("Location: ", "Latitude " + location.getLatitude());
                        Log.i("Location: ", "Longitude " + location.getLongitude());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                                try {
                                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                                    if (addressList.size() > 0) {
                                        Log.i(TAG, "getLastLocation: " + addressList.get(0).getLocality());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                    }
                });
            } else {

                DisplayViewUI.displayAlertDialog(MainActivity.this,
                        "Turn on location",
                        "Locations are used to allow best search results.Please turn on location to allow best search results",
                        "ok",
                        (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
            }
        } else {
            requestPermission();
        }
    }

    //STEP 7 REQUEST NEW LOCATION DATA IF THE LOCATION IS NULL
    private void requestNewLocationData() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(0);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallBack, Looper.myLooper());

    }

    private void checkDisplayAlertDialog() {
        SharedPreferences pref = getSharedPreferences(MyConstants.PREFS, 0);
        boolean alertShown = pref.getBoolean(MyConstants.IS_DIALOG_SHOWN, false);

        if (!alertShown) {
            new Handler().postDelayed(() -> DisplayViewUI.displayAlertDialogMsg(this,
                    "Want to be seen by more users?\nPlease edit profile and add more skills",
                    "OK", (dialog, which) -> {
                        if (which == -1) {

                            dialog.dismiss();
                            Intent gotoAbout = new Intent(MainActivity.this, AboutActivity.class);
                            gotoAbout.putExtra(MyConstants.ACCOUNT_TYPE, serviceType);
                            startActivity(gotoAbout);
                        }
                    }), 15000);

            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean(MyConstants.IS_DIALOG_SHOWN, true);
            edit.apply();
        }
    }

    private void SendUserToLoginActivity() {
        Intent Login = new Intent(MainActivity.this, SplashScreenActivity.class);
        startActivity(Login);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.main_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent gotoSettingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(gotoSettingsIntent);

                return true;

            case R.id.action_logout:
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, SplashScreenActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

                break;



            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    private void setUpAppBarConfig() {
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_activities, R.id.navigation_home, R.id.navigation_request)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(activityMainBinding.bottomNavigationView, navController);

        activityMainBinding.bottomNavigationView.setOnNavigationItemReselectedListener(menuItem -> {
            //do nothing
        });
    }


    //Step 3
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MyConstants.REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted, get location update , called from step 6
                getLastLocation();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            assert firebaseUser != null;

            if (mAuth.getCurrentUser() == null || !firebaseUser.isEmailVerified()) {
                SendUserToLoginActivity();
            } else {
                uid = firebaseUser.getUid();
                checkDisplayAlertDialog();
                retrieveServiceType();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* if (checkPermission()){
            getLastLocation();
        }*/
    }
}
