package com.websathi.connectmeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websathi.connectmeapp.R;
import com.websathi.connectmeapp.activity.HomeFragment;
import com.websathi.connectmeapp.helper.BusinessDBHelper;
import com.websathi.connectmeapp.model.business.Business;
import com.websathi.connectmeapp.model.business.Location;

import java.util.ArrayList;
import java.util.Random;

public class BusinessCardApater extends RecyclerView.Adapter<BusinessCardApater.CustomViewHolder> {

    private ArrayList<Business> businessArrayList;

    public BusinessCardApater(final ArrayList<Business> businessArrayList) {
        this.businessArrayList = businessArrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_card, parent, false));
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

     public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView locationTextView;

        private Button bookMarkButton;
        private Button deleteButton;

        BusinessDBHelper businessDBHelper;

        public CustomViewHolder(@NonNull final View itemView) {
            super(itemView);
            businessDBHelper = new BusinessDBHelper(itemView.getContext());
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            bookMarkButton = itemView.findViewById(R.id.bookmarkButton);
            deleteButton = itemView.findViewById(R.id.delButton);
            bookMarkButton.setOnClickListener(view -> {
                Location location = new Location();
                location.street="Charkhal Rd, Kathmandu 44605";
                location.coordinates= new double[2];
                Context context = itemView.getContext();
                Business business = new Business(new Random().nextInt(), "Leapfrog Technology, Inc.","Software company", location, 5);


                long rowId = businessDBHelper.insert(business);
                Toast.makeText(context, "Item Added to BookMark !!!", Toast.LENGTH_SHORT).show();
            });

            deleteButton.setOnClickListener(view -> {

            });


//            .setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                    Toast.makeText(view.getContext(), "clicked ",
//                            Toast.LENGTH_SHORT).show();
//
//                }
//            });
        }
    }
}
