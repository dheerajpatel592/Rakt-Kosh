package com.g4x.bloodapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReceiveNotificationActivity extends AppCompatActivity {

    private RequestQueue mRequestQue;
    private String URL = "https://fcm.googleapis.com/fcm/send";
    String toSendDeviceToken ;
    String myDeviceToken ;
    EditText reply ;
    TextView message ;
    View sendReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_notification);

        mRequestQue = Volley.newRequestQueue(this);
        message = findViewById(R.id.message);
        sendReply = findViewById(R.id.send_reply);
        reply = findViewById(R.id.reply);

        if (getIntent().hasExtra("message")){
            String messageIGot = getIntent().getStringExtra("message");
            toSendDeviceToken = getIntent().getStringExtra("deviceToken");
            Log.e("TAG",messageIGot);
            Log.e("device token got", toSendDeviceToken);
            message.setText(messageIGot);

        }
        sendReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    }

    private void sendNotification() {

        JSONObject json = new JSONObject();
        try {
            json.put("to",toSendDeviceToken);
            Log.e("trying to send", toSendDeviceToken);
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title","Got a reply back");
            notificationObj.put("body",reply.getText());

            JSONObject extraData = new JSONObject();
            extraData.put("brandId","puma");
            extraData.put("category","Shoes");
            notificationObj.put("message", reply.getText());
            extraData.put("deviceToken", myDeviceToken);


            json.put("notification",notificationObj);
            json.put("data",extraData);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("MUR", "onResponse: ");
                            finish();
                            Toast.makeText(ReceiveNotificationActivity.this, "Reply sent successfully", Toast.LENGTH_SHORT).show();
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
}
