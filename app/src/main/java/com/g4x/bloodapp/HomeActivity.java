package com.g4x.bloodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        if (getIntent().hasExtra("message")){
            String messageIGot = getIntent().getStringExtra("message");
            String toSendDeviceToken = getIntent().getStringExtra("deviceToken");
            Log.e("homeactivity", messageIGot);
            Log.e("homeactivity", toSendDeviceToken);

            Intent intent = new Intent(HomeActivity.this,ReceiveNotificationActivity.class);
            intent.putExtra("message",messageIGot);
            intent.putExtra("deviceToken",toSendDeviceToken);
            startActivity(intent);
        }

        View bloodAvailability = findViewById(R.id.blood_availability);
        View searchForDonors = findViewById(R.id.search_for_donors);
        View aboutUs = findViewById(R.id.about_us);
        View bloodBankLogin = findViewById(R.id.blood_bank_login);
        View donorLogin = findViewById(R.id.donor_login);
        View whyDonateBlood = findViewById(R.id.why_donate_blood);

        bloodAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, BloodAvailabilityActivity.class);
                startActivity(intent);
            }
        });

        searchForDonors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchForDonorsActivity.class);
                startActivity(intent);
            }
        });

        bloodBankLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, BloodBankUpdateItemsActivity.class);
                startActivity(intent);
            }
        });

        donorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, BloodAvailabilityActivity.class);
                startActivity(intent);
            }
        });


    }
}