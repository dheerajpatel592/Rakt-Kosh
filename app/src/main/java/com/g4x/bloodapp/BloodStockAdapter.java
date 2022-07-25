package com.g4x.bloodapp;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BloodStockAdapter extends RecyclerView.Adapter<BloodStockAdapter.ItemViewHolder> {
    Context context ;
    static ArrayList<BloodStock> bloodStocks;
    String[] arr = new String[]{"O+Ve","O-Ve","A+Ve","A-Ve","B+Ve","B-Ve","AB+Ve","AB-Ve"};
    public BloodStockAdapter(Context context , ArrayList<BloodStock> bloodStocks){
        this.context = context ;
        this.bloodStocks = bloodStocks;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.something, parent , false) ;
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        BloodStock bloodStock = bloodStocks.get(position) ;

        holder.bloodGrpName.setText(arr[position]);
        holder.whole_blood.setText(bloodStock.getWhole_blood()+"");
        holder.cryoprecipitate.setText(bloodStock.getCryoprecipitate()+"");
        holder.packed_red_blood_cells.setText(bloodStock.getPacked_red_blood_cells()+"");
        holder.fresh_frozen_plasma.setText(bloodStock.getFresh_frozen_plasma()+"");
        holder.platelet_concentrate.setText(bloodStock.getPlatelet_concentrate()+"");
        holder.irradiated_rbc.setText(bloodStock.getIrradiated_rbc()+"");
        holder.platelet_poor_plasma.setText(bloodStock.getPlatelet_poor_plasma()+"");
        holder.platelet_rich_plasma.setText(bloodStock.getPlatelet_rich_plasma()+"");

        holder.whole_blood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if(text.isEmpty()) {
                    bloodStock.setWhole_blood(0);
                    holder.whole_blood.setText(0+"");
                }
                else
                    bloodStock.setWhole_blood(Integer.parseInt(text));
            }
        });

        holder.cryoprecipitate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if(text.isEmpty()) {
                    bloodStock.setCryoprecipitate(0);
                    holder.cryoprecipitate.setText(0+"");
                }
                else
                    bloodStock.setCryoprecipitate(Integer.parseInt(text));
            }
        });

        holder.packed_red_blood_cells.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if(text.isEmpty()) {
                    bloodStock.setPacked_red_blood_cells(0);
                    holder.packed_red_blood_cells.setText(0+"");
                }
                else
                    bloodStock.setPacked_red_blood_cells(Integer.parseInt(text));
            }
        });
        holder.fresh_frozen_plasma.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if(text.isEmpty()) {
                    bloodStock.setFresh_frozen_plasma(0);
                    holder.fresh_frozen_plasma.setText(0+"");
                }
                else
                    bloodStock.setFresh_frozen_plasma(Integer.parseInt(text));
            }
        });

        holder.platelet_concentrate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if(text.isEmpty()) {
                    bloodStock.setPlatelet_concentrate(0);
                    holder.platelet_concentrate.setText(0+"");
                }
                else
                    bloodStock.setPlatelet_concentrate(Integer.parseInt(text));
            }
        });
        holder.irradiated_rbc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if(text.isEmpty()) {
                    bloodStock.setIrradiated_rbc(0);
                    holder.irradiated_rbc.setText(0+"");
                }
                else
                    bloodStock.setIrradiated_rbc(Integer.parseInt(text));
            }
        });

        holder.platelet_poor_plasma.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if(text.isEmpty()) {
                    bloodStock.setPlatelet_poor_plasma(0);
                    holder.platelet_poor_plasma.setText(0+"");
                }
                else
                    bloodStock.setPlatelet_poor_plasma(Integer.parseInt(text));
            }
        });

        holder.platelet_rich_plasma.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if(text.isEmpty()) {
                    bloodStock.setPlatelet_rich_plasma(0);
                    holder.platelet_rich_plasma.setText(0+"");
                }
                else
                    bloodStock.setPlatelet_rich_plasma(Integer.parseInt(text));
            }
        });
    }

    @Override
    public int getItemCount() {
        return bloodStocks.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        EditText whole_blood , cryoprecipitate , packed_red_blood_cells
                , fresh_frozen_plasma ,platelet_concentrate ,irradiated_rbc ,
                platelet_poor_plasma , platelet_rich_plasma;
        TextView bloodGrpName ;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            bloodGrpName = itemView.findViewById(R.id.blood_group_name);
            whole_blood = itemView.findViewById(R.id.whole_blood);
            cryoprecipitate = itemView.findViewById(R.id.cryoprecipitate);
            packed_red_blood_cells = itemView.findViewById(R.id.packed_red_blood_cells);
            fresh_frozen_plasma = itemView.findViewById(R.id.fresh_frozen_plasma);
            platelet_concentrate = itemView.findViewById(R.id.platelet_concentrate);
            irradiated_rbc = itemView.findViewById(R.id.irradiated_rbc);
            platelet_poor_plasma = itemView.findViewById(R.id.platelet_poor_plasma);
            platelet_rich_plasma = itemView.findViewById(R.id.platelet_rich_plasma);
        }
    }
}
