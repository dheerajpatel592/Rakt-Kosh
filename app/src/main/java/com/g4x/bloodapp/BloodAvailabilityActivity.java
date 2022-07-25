package com.g4x.bloodapp;

import static com.google.android.gms.location.LocationRequest.PRIORITY_LOW_POWER;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BloodAvailabilityActivity extends AppCompatActivity {

    private ItemAdapter itemAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference itemsDatabaseReference;
    private ChildEventListener childEventListener;
    private ArrayList<Item> itemList;
    private View selectBloodGrp, selectBloodComponent;
    private TextView bloodGrpName, bloodComponentName;
    static String bloodGrp = "O+Ve", bloodComponent = "Whole Blood";
    RecyclerView recyclerView ;
    private View viewToHide ;
    private Button turnOnLocation ;

    LocationManager locationManager;
    static double latitude = 0.0 ,longitude = 0.0 ;
    private FusedLocationProviderClient fusedLocationClient;
    ActivityResultLauncher<String[]> locationPermissionRequest ;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.INVISIBLE);
        itemList = new ArrayList<>();

        itemAdapter = new ItemAdapter(BloodAvailabilityActivity.this, itemList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BloodAvailabilityActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemAdapter);
        viewToHide = findViewById(R.id.view_to_hide);
        turnOnLocation = findViewById(R.id.turn_on_location);
        turnOnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    onGPS();
                else
                    getLocation();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        itemsDatabaseReference = firebaseDatabase.getReference().child("bloodBanks");

        selectBloodGrp = findViewById(R.id.select_blood_grp);
        selectBloodComponent = findViewById(R.id.select_blood_component);
        bloodGrpName = findViewById(R.id.blood_group_name);
        bloodComponentName = findViewById(R.id.blood_component_name);
        selectBloodGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence options[] = new CharSequence[]{
                        "O+Ve", "O-Ve", "A+Ve", "A-Ve", "B+Ve", "B-Ve", "AB+Ve", "AB-Ve"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BloodAvailabilityActivity.this);
                builder.setTitle("Select Blood Group");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bloodGrpName.setText(options[i]);
                        bloodGrp = options[i].toString();
                        Log.e("MainActivity", bloodGrp);
                        itemAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });

        selectBloodComponent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence options[] = new CharSequence[]{
                        "Whole Blood", "Cryoprecipitate", "Packed Red Blood Cells"
                        , "Fresh Frozen Plasma", "Platelet Concentrate", "Irradiated RBC",
                        "Platelet Poor Plasma", "Platelet Rich Plasma"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BloodAvailabilityActivity.this);
                builder.setTitle("Select Blood Component");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bloodComponentName.setText(options[i]);
                        bloodComponent = options[i].toString();
                        Log.e("MainActivity", bloodComponent);
                        itemAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationPermissionRequest = registerForActivityResult(new ActivityResultContracts
                        .RequestMultiplePermissions(), result -> {
                    Boolean fineLocationGranted = result.getOrDefault(
                            Manifest.permission.ACCESS_FINE_LOCATION, false);
                    Boolean coarseLocationGranted = result.getOrDefault(
                            Manifest.permission.ACCESS_COARSE_LOCATION, false);
                    if (fineLocationGranted != null && fineLocationGranted) {
                        // Precise location access granted.
                        Log.e("MainActivity", "Precise location access granted");
                        getLocation();
                    } else if (coarseLocationGranted != null && coarseLocationGranted) {
                        getLocation();
                        Log.e("MainActivity", "Only approximate location access granted.");
                        // Only approximate location access granted.
                    } else {
                        Log.e("MainActivity", "No location access granted");
                        // No location access granted.
                    }
                }
        );
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            onGPS();
        else
            getLocation();
        attachDatabaseReadListener();

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
            Log.e("main", "Please provide location access");
            return;
        }
        else {
            fusedLocationClient.getCurrentLocation(PRIORITY_LOW_POWER, new CancellationToken() {
                @NonNull
                @Override
                public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                    return null;
                }

                @Override
                public boolean isCancellationRequested() {
                    return false;
                }
            }).addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latitude = location.getLatitude() ;
                        longitude = location.getLongitude();
                        Collections.sort(itemAdapter.items,comparator);
                        itemAdapter.notifyDataSetChanged();
                        recyclerView.setVisibility(View.VISIBLE);
                        viewToHide.setVisibility(View.GONE);
                        Log.e("MainActivity", location.getLatitude() + " " + location.getLongitude());
                    }
                    else
                        Log.e("main", "cunt");
//                    28.7102179 77.0952893
//                    28.7102166 77.0952935
                }
            });
        }
    }

    private void onGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable location?").setCancelable(false).setPositiveButton("Enable", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                getLocation();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void attachDatabaseReadListener(){
        if(childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Item item = snapshot.getValue(Item.class);
                    item.setOPositiveStock(snapshot.child("O+Ve").getValue(BloodStock.class));
                    item.setONegativeStock(snapshot.child("O-Ve").getValue(BloodStock.class));
                    item.setAPositiveStock(snapshot.child("A+Ve").getValue(BloodStock.class));
                    item.setANegativeStock(snapshot.child("A-Ve").getValue(BloodStock.class));
                    item.setBPositiveStock(snapshot.child("B+Ve").getValue(BloodStock.class));
                    item.setBNegativeStock(snapshot.child("B-Ve").getValue(BloodStock.class));
                    item.setABPositiveStock(snapshot.child("AB+Ve").getValue(BloodStock.class));
                    item.setABNegativeStock(snapshot.child("AB-Ve").getValue(BloodStock.class));
                    itemAdapter.items.add(item);

                    Collections.sort(itemAdapter.items,comparator);
                    itemAdapter.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            itemsDatabaseReference.addChildEventListener(childEventListener);
        }
    }

    public Comparator<Item> comparator = new Comparator<Item>(){
        @Override
        public int compare(Item a , Item b){
            return (int) (getDistanceInKm(latitude, longitude, Double.parseDouble(a.getLatitude()), Double.parseDouble(a.getLongitude()))
                    - getDistanceInKm(latitude, longitude, Double.parseDouble(b.getLatitude()), Double.parseDouble(b.getLongitude())) ) ;
        }
    };

    public double getDistanceInKm (double lat1,double lon1,double lat2,double lon2) {
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2) ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km
        return d;
    }

    public double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

}