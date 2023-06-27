package com.websathi.connectmeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websathi.connectmeapp.R;
import com.websathi.connectmeapp.model.business.Business;

import java.util.ArrayList;

public class BusinessCardApater extends RecyclerView.Adapter<BusinessCardApater.CustomViewHolder> {

    private ArrayList<Business> businessArrayList;

    public BusinessCardApater(final ArrayList<Business> businessArrayList) {
        this.businessArrayList = businessArrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return  new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_card, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        Business business = businessArrayList.get(position);
        holder.titleTextView.setText(business.getName());
        holder.locationTextView.setText(business.location.street);
        holder.descriptionTextView.setText(business.description);
    }


    @Override
    public int getItemCount() {
        return businessArrayList.size();
    }

     public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView locationTextView;

        public CustomViewHolder(@NonNull final View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);

        }
    }
}
