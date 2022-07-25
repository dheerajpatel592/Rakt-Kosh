package com.g4x.bloodapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class BloodBankUpdateItemsActivity extends AppCompatActivity {
    private String saveCurrentDate, saveCurrentTime, lastUpdated;
    private Button updateButton;
    private DatabaseReference bloodBankRef ;
    private ProgressDialog loadingBar;
    private String bloodBankName = "Saroj Hospital";

    private BloodStockAdapter bloodStockAdapter ;
    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference bloodBankDatabaseReference ;
    private ChildEventListener childEventListener;
    private ArrayList<BloodStock> bloodStockList;
    private Item item ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_stock);


        //CategoryName = getIntent().getExtras().get("category").toString();
        bloodBankRef = FirebaseDatabase.getInstance().getReference().child("bloodBanks");

        loadingBar = new ProgressDialog(this);

        updateButton = findViewById(R.id.update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                UpdateStockInformation();
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerView) ;
        bloodStockList = new ArrayList<>() ;

        bloodStockAdapter = new BloodStockAdapter(this , bloodStockList) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) ;
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(bloodStockAdapter);

//        firebaseDatabase = FirebaseDatabase.getInstance();
        //bloodBankDatabaseReference = firebaseDatabase.getReference().child("bloodBanks").child(bloodBankName);
        //attachDatabaseReadListener();
        something();
//        UpdateStockInformation();
    }


    private void UpdateStockInformation()
    {
        loadingBar.setTitle("Updating stock information");
        loadingBar.setMessage("Please wait while we are updating details...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        lastUpdated = "Last updated on :\n"+ saveCurrentDate + "  " + saveCurrentTime;
        SaveStockInfoToDatabase();

    }

    private void SaveStockInfoToDatabase()
    {
        HashMap<String,Integer> OPositiveMap = new HashMap<>();
        HashMap<String,Integer> ONegativeMap = new HashMap<>();
        HashMap<String,Integer> APositiveMap = new HashMap<>();
        HashMap<String,Integer> ANegativeMap = new HashMap<>();
        HashMap<String,Integer> BPositiveMap = new HashMap<>();
        HashMap<String,Integer> BNegativeMap = new HashMap<>();
        HashMap<String,Integer> ABPositiveMap = new HashMap<>();
        HashMap<String,Integer> ABNegativeMap = new HashMap<>();

        BloodStock bloodStock ;
        bloodStock = BloodStockAdapter.bloodStocks.get(0);
        OPositiveMap.put("whole_blood", bloodStock.getWhole_blood());
        OPositiveMap.put("cryoprecipitate" ,bloodStock.getCryoprecipitate());
        OPositiveMap.put("packed_red_blood_cells" ,bloodStock.getPacked_red_blood_cells());
        OPositiveMap.put("fresh_frozen_plasma", bloodStock.getFresh_frozen_plasma());
        OPositiveMap.put("platelet_concentrate", bloodStock.getPlatelet_concentrate());
        OPositiveMap.put("irradiated_rbc", bloodStock.getIrradiated_rbc());
        OPositiveMap.put("platelet_poor_plasma" , bloodStock.getPlatelet_poor_plasma());
        OPositiveMap.put("platelet_rich_plasma", bloodStock.getPlatelet_rich_plasma());

        bloodStock = BloodStockAdapter.bloodStocks.get(1);
        ONegativeMap.put("whole_blood", bloodStock.getWhole_blood());
        ONegativeMap.put("cryoprecipitate" ,bloodStock.getCryoprecipitate());
        ONegativeMap.put("packed_red_blood_cells" ,bloodStock.getPacked_red_blood_cells());
        ONegativeMap.put("fresh_frozen_plasma", bloodStock.getFresh_frozen_plasma());
        ONegativeMap.put("platelet_concentrate", bloodStock.getPlatelet_concentrate());
        ONegativeMap.put("irradiated_rbc", bloodStock.getIrradiated_rbc());
        ONegativeMap.put("platelet_poor_plasma" , bloodStock.getPlatelet_poor_plasma());
        ONegativeMap.put("platelet_rich_plasma", bloodStock.getPlatelet_rich_plasma());

        bloodStock = BloodStockAdapter.bloodStocks.get(2);
        APositiveMap.put("whole_blood", bloodStock.getWhole_blood());
        APositiveMap.put("cryoprecipitate" ,bloodStock.getCryoprecipitate());
        APositiveMap.put("packed_red_blood_cells" ,bloodStock.getPacked_red_blood_cells());
        APositiveMap.put("fresh_frozen_plasma", bloodStock.getFresh_frozen_plasma());
        APositiveMap.put("platelet_concentrate", bloodStock.getPlatelet_concentrate());
        APositiveMap.put("irradiated_rbc", bloodStock.getIrradiated_rbc());
        APositiveMap.put("platelet_poor_plasma" , bloodStock.getPlatelet_poor_plasma());
        APositiveMap.put("platelet_rich_plasma", bloodStock.getPlatelet_rich_plasma());

        bloodStock = BloodStockAdapter.bloodStocks.get(3);
        ANegativeMap.put("whole_blood", bloodStock.getWhole_blood());
        ANegativeMap.put("cryoprecipitate" ,bloodStock.getCryoprecipitate());
        ANegativeMap.put("packed_red_blood_cells" ,bloodStock.getPacked_red_blood_cells());
        ANegativeMap.put("fresh_frozen_plasma", bloodStock.getFresh_frozen_plasma());
        ANegativeMap.put("platelet_concentrate", bloodStock.getPlatelet_concentrate());
        ANegativeMap.put("irradiated_rbc", bloodStock.getIrradiated_rbc());
        ANegativeMap.put("platelet_poor_plasma" , bloodStock.getPlatelet_poor_plasma());
        ANegativeMap.put("platelet_rich_plasma", bloodStock.getPlatelet_rich_plasma());

        bloodStock = BloodStockAdapter.bloodStocks.get(4);
        BPositiveMap.put("whole_blood", bloodStock.getWhole_blood());
        BPositiveMap.put("cryoprecipitate" ,bloodStock.getCryoprecipitate());
        BPositiveMap.put("packed_red_blood_cells" ,bloodStock.getPacked_red_blood_cells());
        BPositiveMap.put("fresh_frozen_plasma", bloodStock.getFresh_frozen_plasma());
        BPositiveMap.put("platelet_concentrate", bloodStock.getPlatelet_concentrate());
        BPositiveMap.put("irradiated_rbc", bloodStock.getIrradiated_rbc());
        BPositiveMap.put("platelet_poor_plasma" , bloodStock.getPlatelet_poor_plasma());
        BPositiveMap.put("platelet_rich_plasma", bloodStock.getPlatelet_rich_plasma());

        bloodStock = BloodStockAdapter.bloodStocks.get(5);
        BNegativeMap.put("whole_blood", bloodStock.getWhole_blood());
        BNegativeMap.put("cryoprecipitate" ,bloodStock.getCryoprecipitate());
        BNegativeMap.put("packed_red_blood_cells" ,bloodStock.getPacked_red_blood_cells());
        BNegativeMap.put("fresh_frozen_plasma", bloodStock.getFresh_frozen_plasma());
        BNegativeMap.put("platelet_concentrate", bloodStock.getPlatelet_concentrate());
        BNegativeMap.put("irradiated_rbc", bloodStock.getIrradiated_rbc());
        BNegativeMap.put("platelet_poor_plasma" , bloodStock.getPlatelet_poor_plasma());
        BNegativeMap.put("platelet_rich_plasma", bloodStock.getPlatelet_rich_plasma());

        bloodStock = BloodStockAdapter.bloodStocks.get(6);
        ABPositiveMap.put("whole_blood", bloodStock.getWhole_blood());
        ABPositiveMap.put("cryoprecipitate" ,bloodStock.getCryoprecipitate());
        ABPositiveMap.put("packed_red_blood_cells" ,bloodStock.getPacked_red_blood_cells());
        ABPositiveMap.put("fresh_frozen_plasma", bloodStock.getFresh_frozen_plasma());
        ABPositiveMap.put("platelet_concentrate", bloodStock.getPlatelet_concentrate());
        ABPositiveMap.put("irradiated_rbc", bloodStock.getIrradiated_rbc());
        ABPositiveMap.put("platelet_poor_plasma" , bloodStock.getPlatelet_poor_plasma());
        ABPositiveMap.put("platelet_rich_plasma", bloodStock.getPlatelet_rich_plasma());

        bloodStock = BloodStockAdapter.bloodStocks.get(7);
        ABNegativeMap.put("whole_blood", bloodStock.getWhole_blood());
        ABNegativeMap.put("cryoprecipitate" ,bloodStock.getCryoprecipitate());
        ABNegativeMap.put("packed_red_blood_cells" ,bloodStock.getPacked_red_blood_cells());
        ABNegativeMap.put("fresh_frozen_plasma", bloodStock.getFresh_frozen_plasma());
        ABNegativeMap.put("platelet_concentrate", bloodStock.getPlatelet_concentrate());
        ABNegativeMap.put("irradiated_rbc", bloodStock.getIrradiated_rbc());
        ABNegativeMap.put("platelet_poor_plasma" , bloodStock.getPlatelet_poor_plasma());
        ABNegativeMap.put("platelet_rich_plasma", bloodStock.getPlatelet_rich_plasma());

        HashMap<String, Object> stockMap = new HashMap<>();
        stockMap.put("latitude", "65.35" );
        stockMap.put("longitude","-122.65");
        stockMap.put("name", "Saroj Hospital" );
        stockMap.put("phoneNo", "9315064232" );
        stockMap.put("emailId","dheerajpatel592@gmail.com" );
        stockMap.put("address", "Near Here");
        stockMap.put("lastUpdated",lastUpdated);
        stockMap.put("type","PRIVATE");
        stockMap.put("O+Ve", OPositiveMap);
        stockMap.put("O-Ve", ONegativeMap);
        stockMap.put("A+Ve", APositiveMap);
        stockMap.put("A-Ve", ANegativeMap);
        stockMap.put("B+Ve", BPositiveMap);
        stockMap.put("B-Ve", BNegativeMap);
        stockMap.put("AB+Ve", ABPositiveMap);
        stockMap.put("AB-Ve", ABNegativeMap);

        bloodBankRef.child(bloodBankName).updateChildren(stockMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            //Intent intent = new Intent(AdminAddNewProductActivity.this, AdminHomeActivity.class);
                            //startActivity(intent);

                            loadingBar.dismiss();
                            //Toast.makeText(AdminAddNewProductActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            //Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void something(){
        bloodBankRef.child(bloodBankName).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Item item = task.getResult().getValue(Item.class);
                    bloodStockAdapter.bloodStocks.add(task.getResult().child("O+Ve").getValue(BloodStock.class));
                    bloodStockAdapter.bloodStocks.add(task.getResult().child("O-Ve").getValue(BloodStock.class));
                    bloodStockAdapter.bloodStocks.add(task.getResult().child("A+Ve").getValue(BloodStock.class));
                    bloodStockAdapter.bloodStocks.add(task.getResult().child("A-Ve").getValue(BloodStock.class));
                    bloodStockAdapter.bloodStocks.add(task.getResult().child("B+Ve").getValue(BloodStock.class));
                    bloodStockAdapter.bloodStocks.add(task.getResult().child("B-Ve").getValue(BloodStock.class));
                    bloodStockAdapter.bloodStocks.add(task.getResult().child("AB+Ve").getValue(BloodStock.class));
                    bloodStockAdapter.bloodStocks.add(task.getResult().child("AB-Ve").getValue(BloodStock.class));
                    bloodStockAdapter.notifyDataSetChanged();
                    Log.e("MainActivity", task.getResult().child("O+Ve").getValue(BloodStock.class).getWhole_blood() + "");
                    Log.e("MainActivity", task.getResult().child("O+Ve").getValue(BloodStock.class).getCryoprecipitate() + "");
                    Log.e("MainActivity", task.getResult().child("O+Ve").getValue(BloodStock.class).getFresh_frozen_plasma() + "");
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
}
