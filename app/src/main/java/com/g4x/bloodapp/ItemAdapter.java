package com.g4x.bloodapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    Context context ;
    ArrayList<Item> items;

    public ItemAdapter(Context context , ArrayList<Item> items){
        this.context = context ;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent , false) ;
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position) ;

        holder.name.setText(item.getName());
        holder.phoneNo.setText(item.getPhoneNo());
        holder.address.setText(item.getAddress());
        holder.emailId.setText(item.getEmailId());
        holder.type.setText(item.getType());
        holder.lastUpdated.setText(item.getLastUpdated());

        holder.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUri = "http://maps.google.com/maps?q=loc:" + item.getLatitude() + "," + item.getLongitude() + " (" + item.getName() + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                context.startActivity(intent);
            }
        });

        holder.phoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = item.getPhoneNo();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                context.startActivity(intent);
            }
        });

        String distance = (int) getDistanceInKm(BloodAvailabilityActivity.latitude, BloodAvailabilityActivity.longitude, Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude())) +"";
        holder.distance.setText(distance+" Km");
        String bloodGrp  = BloodAvailabilityActivity.bloodGrp , bloodComponent = BloodAvailabilityActivity.bloodComponent ;
        int units = 0 ;
        if(bloodGrp.equals("O+Ve")){
            if(bloodComponent.equals("Whole Blood"))
                units = item.getOPositiveStock().getWhole_blood() ;
            else if(bloodComponent.equals("Cryoprecipitate"))
                units = item.getOPositiveStock().getCryoprecipitate() ;
            else if(bloodComponent.equals("Packed Red Blood Cells"))
                units = item.getOPositiveStock().getPacked_red_blood_cells() ;
            else if(bloodComponent.equals("Fresh Frozen Plasma"))
                units = item.getOPositiveStock().getFresh_frozen_plasma() ;
            else if(bloodComponent.equals("Platelet Concentrate"))
                units = item.getOPositiveStock().getPlatelet_concentrate() ;
            else if(bloodComponent.equals("Irradiated RBC"))
                units = item.getOPositiveStock().getIrradiated_rbc() ;
            else if(bloodComponent.equals("Platelet Poor Plasma"))
                units = item.getOPositiveStock().getPlatelet_poor_plasma() ;
            else if(bloodComponent.equals("Platelet Rich Plasma"))
                units = item.getOPositiveStock().getPlatelet_rich_plasma() ;
        }

        else if(bloodGrp.equals("O-Ve")){
            if(bloodComponent.equals("Whole Blood"))
                units = item.getONegativeStock().getWhole_blood() ;
            else if(bloodComponent.equals("Cryoprecipitate"))
                units = item.getONegativeStock().getCryoprecipitate() ;
            else if(bloodComponent.equals("Packed Red Blood Cells"))
                units = item.getONegativeStock().getPacked_red_blood_cells() ;
            else if(bloodComponent.equals("Fresh Frozen Plasma"))
                units = item.getONegativeStock().getFresh_frozen_plasma() ;
            else if(bloodComponent.equals("Platelet Concentrate"))
                units = item.getONegativeStock().getPlatelet_concentrate() ;
            else if(bloodComponent.equals("Irradiated RBC"))
                units = item.getONegativeStock().getIrradiated_rbc() ;
            else if(bloodComponent.equals("Platelet Poor Plasma"))
                units = item.getONegativeStock().getPlatelet_poor_plasma() ;
            else if(bloodComponent.equals("Platelet Rich Plasma"))
                units = item.getONegativeStock().getPlatelet_rich_plasma() ;
        }

        else if(bloodGrp.equals("A+Ve")){
            if(bloodComponent.equals("Whole Blood"))
                units = item.getAPositiveStock().getWhole_blood() ;
            else if(bloodComponent.equals("Cryoprecipitate"))
                units = item.getAPositiveStock().getCryoprecipitate() ;
            else if(bloodComponent.equals("Packed Red Blood Cells"))
                units = item.getAPositiveStock().getPacked_red_blood_cells() ;
            else if(bloodComponent.equals("Fresh Frozen Plasma"))
                units = item.getAPositiveStock().getFresh_frozen_plasma() ;
            else if(bloodComponent.equals("Platelet Concentrate"))
                units = item.getAPositiveStock().getPlatelet_concentrate() ;
            else if(bloodComponent.equals("Irradiated RBC"))
                units = item.getAPositiveStock().getIrradiated_rbc() ;
            else if(bloodComponent.equals("Platelet Poor Plasma"))
                units = item.getAPositiveStock().getPlatelet_poor_plasma() ;
            else if(bloodComponent.equals("Platelet Rich Plasma"))
                units = item.getAPositiveStock().getPlatelet_rich_plasma() ;
        }

        else if(bloodGrp.equals("A-Ve")){
            if(bloodComponent.equals("Whole Blood"))
                units = item.getANegativeStock().getWhole_blood() ;
            else if(bloodComponent.equals("Cryoprecipitate"))
                units = item.getANegativeStock().getCryoprecipitate() ;
            else if(bloodComponent.equals("Packed Red Blood Cells"))
                units = item.getANegativeStock().getPacked_red_blood_cells() ;
            else if(bloodComponent.equals("Fresh Frozen Plasma"))
                units = item.getANegativeStock().getFresh_frozen_plasma() ;
            else if(bloodComponent.equals("Platelet Concentrate"))
                units = item.getANegativeStock().getPlatelet_concentrate() ;
            else if(bloodComponent.equals("Irradiated RBC"))
                units = item.getANegativeStock().getIrradiated_rbc() ;
            else if(bloodComponent.equals("Platelet Poor Plasma"))
                units = item.getANegativeStock().getPlatelet_poor_plasma() ;
            else if(bloodComponent.equals("Platelet Rich Plasma"))
                units = item.getONegativeStock().getPlatelet_rich_plasma() ;
        }

        else if(bloodGrp.equals("B+Ve")){
            if(bloodComponent.equals("Whole Blood"))
                units = item.getBPositiveStock().getWhole_blood() ;
            else if(bloodComponent.equals("Cryoprecipitate"))
                units = item.getBPositiveStock().getCryoprecipitate() ;
            else if(bloodComponent.equals("Packed Red Blood Cells"))
                units = item.getBPositiveStock().getPacked_red_blood_cells() ;
            else if(bloodComponent.equals("Fresh Frozen Plasma"))
                units = item.getBPositiveStock().getFresh_frozen_plasma() ;
            else if(bloodComponent.equals("Platelet Concentrate"))
                units = item.getBPositiveStock().getPlatelet_concentrate() ;
            else if(bloodComponent.equals("Irradiated RBC"))
                units = item.getBPositiveStock().getIrradiated_rbc() ;
            else if(bloodComponent.equals("Platelet Poor Plasma"))
                units = item.getBPositiveStock().getPlatelet_poor_plasma() ;
            else if(bloodComponent.equals("Platelet Rich Plasma"))
                units = item.getBPositiveStock().getPlatelet_rich_plasma() ;
        }

        else if(bloodGrp.equals("B-Ve")){
            if(bloodComponent.equals("Whole Blood"))
                units = item.getBNegativeStock().getWhole_blood() ;
            else if(bloodComponent.equals("Cryoprecipitate"))
                units = item.getBNegativeStock().getCryoprecipitate() ;
            else if(bloodComponent.equals("Packed Red Blood Cells"))
                units = item.getBNegativeStock().getPacked_red_blood_cells() ;
            else if(bloodComponent.equals("Fresh Frozen Plasma"))
                units = item.getBNegativeStock().getFresh_frozen_plasma() ;
            else if(bloodComponent.equals("Platelet Concentrate"))
                units = item.getBNegativeStock().getPlatelet_concentrate() ;
            else if(bloodComponent.equals("Irradiated RBC"))
                units = item.getBNegativeStock().getIrradiated_rbc() ;
            else if(bloodComponent.equals("Platelet Poor Plasma"))
                units = item.getBNegativeStock().getPlatelet_poor_plasma() ;
            else if(bloodComponent.equals("Platelet Rich Plasma"))
                units = item.getBNegativeStock().getPlatelet_rich_plasma() ;
        }

        else if(bloodGrp.equals("AB+Ve")){
            if(bloodComponent.equals("Whole Blood"))
                units = item.getABPositiveStock().getWhole_blood() ;
            else if(bloodComponent.equals("Cryoprecipitate"))
                units = item.getABPositiveStock().getCryoprecipitate() ;
            else if(bloodComponent.equals("Packed Red Blood Cells"))
                units = item.getABPositiveStock().getPacked_red_blood_cells() ;
            else if(bloodComponent.equals("Fresh Frozen Plasma"))
                units = item.getABPositiveStock().getFresh_frozen_plasma() ;
            else if(bloodComponent.equals("Platelet Concentrate"))
                units = item.getABPositiveStock().getPlatelet_concentrate() ;
            else if(bloodComponent.equals("Irradiated RBC"))
                units = item.getABPositiveStock().getIrradiated_rbc() ;
            else if(bloodComponent.equals("Platelet Poor Plasma"))
                units = item.getABPositiveStock().getPlatelet_poor_plasma() ;
            else if(bloodComponent.equals("Platelet Rich Plasma"))
                units = item.getABPositiveStock().getPlatelet_rich_plasma() ;
        }

        else if(bloodGrp.equals("AB-Ve")){
            if(bloodComponent.equals("Whole Blood"))
                units = item.getABNegativeStock().getWhole_blood() ;
            else if(bloodComponent.equals("Cryoprecipitate"))
                units = item.getABNegativeStock().getCryoprecipitate() ;
            else if(bloodComponent.equals("Packed Red Blood Cells"))
                units = item.getABNegativeStock().getPacked_red_blood_cells() ;
            else if(bloodComponent.equals("Fresh Frozen Plasma"))
                units = item.getABNegativeStock().getFresh_frozen_plasma() ;
            else if(bloodComponent.equals("Platelet Concentrate"))
                units = item.getABNegativeStock().getPlatelet_concentrate() ;
            else if(bloodComponent.equals("Irradiated RBC"))
                units = item.getABNegativeStock().getIrradiated_rbc() ;
            else if(bloodComponent.equals("Platelet Poor Plasma"))
                units = item.getABNegativeStock().getPlatelet_poor_plasma() ;
            else if(bloodComponent.equals("Platelet Rich Plasma"))
                units = item.getABNegativeStock().getPlatelet_rich_plasma() ;
        }

        holder.available.setText("Available : "+units);

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

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView name ;
        TextView phoneNo ;
        TextView address ;
        TextView emailId ;
        TextView distance ;
        TextView type ;
        TextView lastUpdated ;
        TextView available ;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name) ;
            phoneNo = itemView.findViewById(R.id.phoneNo) ;
            address = itemView.findViewById(R.id.address) ;
            emailId = itemView.findViewById(R.id.emailId) ;
            distance = itemView.findViewById(R.id.distance) ;
            type = itemView.findViewById(R.id.type) ;
            lastUpdated = itemView.findViewById(R.id.lastUpdated);
            available = itemView.findViewById(R.id.available);
        }
    }
}
