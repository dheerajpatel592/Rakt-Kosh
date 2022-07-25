package com.g4x.bloodapp;

import static com.google.android.gms.location.LocationRequest.PRIORITY_LOW_POWER;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SearchForDonorsActivity extends AppCompatActivity {

    String bloodGroup = "All Groups";
    int radiusToSearch = 5 ;
    int donorsFound = 0 ;
    private RequestQueue mRequestQue;
    private String URL = "https://fcm.googleapis.com/fcm/send";
    String myDeviceToken = "";
    DatabaseReference donorsDatabaseReference ;
    JSONArray registrationIds ;
    static double latitude = 0.0 ,longitude = 0.0;
    EditText message;
    private View viewToHide , viewToShow;
    private Button turnOnLocation ;

    private FusedLocationProviderClient fusedLocationClient;
    ActivityResultLauncher<String[]> locationPermissionRequest ;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_donors);

        View selectBloodGrp = findViewById(R.id.select_blood_group);
        TextView bloodGrpName = findViewById(R.id.blood_group_name);
        View selectSearchDistance = findViewById(R.id.select_search_distance);
        TextView searchDistance = findViewById(R.id.search_distance);
        View search = findViewById(R.id.search) ;
        TextView found = findViewById(R.id.found);
        message = findViewById(R.id.message);
        View send = findViewById(R.id.send_notification);
        viewToHide = findViewById(R.id.view_to_hide);
        viewToShow = findViewById(R.id.view_to_show);
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

        selectBloodGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                found.setText("Found : 0");
                CharSequence options[] = new CharSequence[]{
                        "All Groups","O+Ve", "O-Ve", "A+Ve", "A-Ve", "B+Ve", "B-Ve", "AB+Ve", "AB-Ve"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(SearchForDonorsActivity.this);
                builder.setTitle("Select Blood Group");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bloodGrpName.setText(options[i]);
                        bloodGroup = options[i].toString();
                        Log.e("MainActivity", bloodGroup);
                    }
                });
                builder.show();
            }
        });

        selectSearchDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                found.setText("Found : 0");
                CharSequence options[] = new CharSequence[]{
                        "5","10", "15", "20", "25"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(SearchForDonorsActivity.this);
                builder.setTitle("Select Search Radius");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        searchDistance.setText(options[i]+" Km");
                        radiusToSearch = Integer.parseInt(options[i].toString());
                        Log.e("MainActivity", radiusToSearch+"");
                    }
                });
                builder.show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrationIds = new JSONArray();
                donorsFound = 0 ;
                found.setText("Found : 0");
                if(bloodGroup.equals("All Groups")){
                    donorsDatabaseReference.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Donor donor = snapshot.getValue(Donor.class);
                            int distance = (int) getDistanceInKm(latitude, longitude, Double.parseDouble(donor.getLatitude()), Double.parseDouble(donor.getLongitude()));
                            if(distance <= radiusToSearch) {
                                registrationIds.put(donor.getToken());
                                donorsFound = registrationIds.length();
                                found.setText("Found : "+donorsFound);
                            }
                                //registrationIds.put("fFIvXh1KSwSZ4n1-7NJ4eT:APA91bFVOYCN7CGd5QbBbz-usdclf1LMXpl1SrZ99C0y5R5-xNQqFma4szTjnEUWH4nW2Loq8QCwp-uAmo0Ebx-h_OCo4YFWse5ATWhReAIoEmz_XjYBEZy8nAsbt-91cYpSVGncRC0Z");
                            Log.e("donor", donor.getBloodGrp());
                            Log.e("donor", "distance="+distance);
                            Log.e("donor", donor.getLongitude());
                            Log.e("donor", donor.getToken());
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
                    });
                }
                else{
                    donorsDatabaseReference.orderByChild("bloodGrp").equalTo("O-Ve").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Donor donor = snapshot.getValue(Donor.class);
                            int distance = (int) getDistanceInKm(latitude, longitude, Double.parseDouble(donor.getLatitude()), Double.parseDouble(donor.getLongitude()));
                            if(distance <= radiusToSearch) {
                                registrationIds.put(donor.getToken());
                                donorsFound = registrationIds.length();
                                found.setText("Found : "+donorsFound);
                            }
                                //registrationIds.put("fFIvXh1KSwSZ4n1-7NJ4eT:APA91bFVOYCN7CGd5QbBbz-usdclf1LMXpl1SrZ99C0y5R5-xNQqFma4szTjnEUWH4nW2Loq8QCwp-uAmo0Ebx-h_OCo4YFWse5ATWhReAIoEmz_XjYBEZy8nAsbt-91cYpSVGncRC0Z");
                            Log.e("donor", donor.getBloodGrp());
                            Log.e("donor", "distance="+distance);
                            Log.e("donor", donor.getLongitude());
                            Log.e("donor", donor.getToken());
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
                    });
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(found.getText().equals("Found : 0"))
                    Toast.makeText(SearchForDonorsActivity.this, "Please click the search button before proceeding", Toast.LENGTH_SHORT).show();
                else
                    sendNotification();
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        myDeviceToken = task.getResult();
                        Log.e("Found token", myDeviceToken);
//                        fFIvXh1KSwSZ4n1-7NJ4eT:APA91bFVOYCN7CGd5QbBbz-usdclf1LMXpl1SrZ99C0y5R5-xNQqFma4szTjnEUWH4nW2Loq8QCwp-uAmo0Ebx-h_OCo4YFWse5ATWhReAIoEmz_XjYBEZy8nAsbt-91cYpSVGncRC0Z
                    }
                });
        mRequestQue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        donorsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("donors");

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

    }

    private void sendNotification() {

        JSONObject json = new JSONObject();
        try {
//            json.put("to","/topics/"+"news");
            Log.e("trying to send", registrationIds.get(0)+"");
            json.put("registration_ids", registrationIds);
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title","Blood Required Urgently");
            notificationObj.put("body",message.getText());

            JSONObject extraData = new JSONObject();
            extraData.put("deviceToken", myDeviceToken);
            extraData.put("message", message.getText());

            json.put("notification",notificationObj);
            json.put("data",extraData);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("MUR", "onResponse: ");
                            Toast.makeText(SearchForDonorsActivity.this, "Request sent successfully", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("MUR", "onError: "+error.networkResponse);
                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAAUXFiidQ:APA91bEOxtsF_5n9xjsHD3sXkW6xzROKBPycbiuperTMS0nqjqL4ia8w0n5UYP_XoZEha2RVfnZfw8_MTODEr3niJZQ3wKzGawsnUSXuR4uRLIdO6cOoOzPfWlEuBmPO8ZoxaTFzbEZc");
                    // old key header.put("authorization","key=AAAA77n5guc:APA91bH_cOytef3qWAU_P9reJx2bk7zdr6IekCVHkXTM72TmwLslBWuG-_R2QT0BwGl7D5HUwAxLmpInkAN6zmTwAsMFokwqyvudyA1xOgEqVg0yfoXxK3kOhbTtpD_O6QdpSkE-w72s");
                    return header;
                }
            };
            mRequestQue.add(request);
        }
        catch (JSONException e)

        {
            e.printStackTrace();
        }
    }

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
                        viewToHide.setVisibility(View.GONE);
                        viewToShow.setVisibility(View.VISIBLE);
                        Log.e("MainActivity", location.getLatitude() + " " + location.getLongitude());
                    }
                    else
                        Log.e("main", "cunt");
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
}