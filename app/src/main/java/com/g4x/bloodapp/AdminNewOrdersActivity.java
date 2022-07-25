package com.g4x.bloodapp;

//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//
//public class AdminNewOrdersActivity extends AppCompatActivity {
//    private RecyclerView ordersList;
//    private Query ordersQuery;
//    private DatabaseReference ordersRef ;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.activity_admin_orders);
//        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
//        ordersQuery = FirebaseDatabase.getInstance().getReference().child("Orders").orderByChild("state").equalTo("Not Shipped");
//       // ordersList = findViewById(R.id.orders_list);
//        ordersList.setLayoutManager(new LinearLayoutManager(this));
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();

//        FirebaseRecyclerOptions<AdminOrders> options=
//                new FirebaseRecyclerOptions.Builder<AdminOrders>()
//                        .setQuery(ordersQuery, AdminOrders.class)
//                        .build();
//        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter =
//                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model) {
//
//                        holder.userName.setText("Name: "+model.getName());
//                        holder.userPhoneNumber.setText("Name: "+model.getPhone());
//                        holder.userTotalPrice.setText("Total Ammount = Rs."+model.getTotalAmount());
//                        holder.userDateTime.setText("Order at: "+model.getDate()+" "+ model.getTime());
//                        holder.userShippingAddress.setText("Shipping Address: "+model.getAddress()+", "+model.getCity());
//                        holder.showOrdersBtn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                String uID = getRef(position).getKey();
//                                Intent intent = new Intent(AdminNewOrdersActivity.this,AdminUserProductsActivity.class);
//                                intent.putExtra("uid",uID);
//                                startActivity(intent);
//                            }
//                        });
//
//                        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                CharSequence options[] =new CharSequence[]{
//                                        "Yes",
//                                        "No",
//                                        "Cancel this Order"
//
//                                };
//
//                                AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrdersActivity.this);
//                                builder.setTitle("Have you shipped this order products?");
//                                builder.setItems(options, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        if (i==0){
//                                            String uID = getRef(position).getKey();
//                                            OrderShipped(uID);
//
//                                        }
//                                        else if(i==2){
//                                            String uID = getRef(position).getKey();
//                                            CancelOrder(uID);
//                                        }
//                                        else {
//                                            finish();
//                                        }
//
//                                    }
//                                });
//                                builder.show();
//                            }
//                        });
//
//                    }
//
//                    @NonNull
//                    @Override
//                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
//                        return new AdminOrdersViewHolder(view);
//                    }
//                };
//        ordersList.setAdapter(adapter);
//        adapter.startListening();
//
//    }



//    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder{
//
//        public TextView userName, userPhoneNumber,userTotalPrice,userDateTime,userShippingAddress;
//        public Button showOrdersBtn;
//        public AdminOrdersViewHolder(View itemView) {
//            super(itemView);
//            userName = itemView.findViewById(R.id.order_user_name);
//            userPhoneNumber = itemView.findViewById(R.id.order_phone_number);
//            userTotalPrice = itemView.findViewById(R.id.order_total_price);
//            userDateTime = itemView.findViewById(R.id.order_date_time);
//            userShippingAddress = itemView.findViewById(R.id.order_address_city);
//            showOrdersBtn = itemView.findViewById(R.id.show_all_product_btn);
//        }
//    }
//    private void OrderShipped(String uID) {
//        ordersRef.child(uID).child("state").setValue("Shipped").addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(AdminNewOrdersActivity.this,"Order status changed to shipped.",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AdminNewOrdersActivity.this, AdminHomeActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });;
//    }
//    private void CancelOrder(String uID) {
//        ordersRef.child(uID).child("state").setValue("Cancelled").addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(AdminNewOrdersActivity.this,"Order Cancelled",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AdminNewOrdersActivity.this,AdminCancelledOrdersActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });;
//    }
//}


//@Override
//public void onTokenRefresh() {
//        // Get updated InstanceID token.
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "Refreshed token: " + refreshedToken);
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);
//        }