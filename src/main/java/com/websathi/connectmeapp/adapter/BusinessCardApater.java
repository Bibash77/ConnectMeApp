package com.websathi.connectmeapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.websathi.connectmeapp.R;
import com.websathi.connectmeapp.activity.DetailPageActivity;
import com.websathi.connectmeapp.helper.db.BusinessBookMarkDBHelper;
import com.websathi.connectmeapp.model.business.Business;
import com.websathi.connectmeapp.model.business.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessCardApater extends RecyclerView.Adapter<BusinessCardApater.CustomViewHolder> {

    private final ArrayList<Business> businessArrayList;

    private BusinessBookMarkDBHelper businessBookMarkDBHelper;

    private String viewName;

    public BusinessCardApater(final List<Business> businessArrayList) {
        this.businessArrayList = (ArrayList<Business>) businessArrayList;
    }
    public BusinessCardApater(final List<Business> businessArrayList, final String viewName) {
        this.businessArrayList = (ArrayList<Business>) businessArrayList;
        this.viewName=viewName;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_card, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        Business business = businessArrayList.get(position);
        businessBookMarkDBHelper = new BusinessBookMarkDBHelper(holder.itemView.getContext());
        holder.titleTextView.setText(business.getName());
        holder.locationTextView.setText(business.location.formattedAddress);
        holder.category.setText(business.category);
        List<Service> servicesResponse = business.services;
        String serviceTotal = "N/A";
        if(servicesResponse != null) {
         serviceTotal =  servicesResponse.stream()
                    .map(service -> service.getName())
                    .collect(Collectors.joining(", "));
            holder.services.setText(serviceTotal);
        }
        holder.distance.setText(business.distance + " KM");

        holder.bookMarkButton.setOnClickListener(view -> {
            System.out.println("book mark button clicked");
                // Use the business object as needed
                try {
                    long rowId = businessBookMarkDBHelper.insert(business);
                    if(rowId != -1) {
                        Toast.makeText(holder.itemView.getContext(), "Item Added to BookMark!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "Already In Bookmark!!!", Toast.LENGTH_SHORT).show();
                    }
                    System.out.println(rowId);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    System.out.println("issue occured");
                   // do nothing
                }
        });



        holder.deleteButton.setOnClickListener(view -> {
            System.out.println("delete button clicked");
            businessBookMarkDBHelper.deleteData(business.id);
            businessArrayList.remove(position);
            Toast.makeText(holder.itemView.getContext(), "Item Removed from BookMark!!!", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
        });
        if(viewName.equals("HOME")) {
            holder.deleteButton.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return businessArrayList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView category;
        private final TextView locationTextView;
        private final TextView services;
        private final Button bookMarkButton;
        private final Button deleteButton;

        private final ImageView imageView;

        private final TextView distance;
        BusinessBookMarkDBHelper businessBookMarkDBHelper;

        public CustomViewHolder(@NonNull final View itemView) {
            super(itemView);
            businessBookMarkDBHelper = new BusinessBookMarkDBHelper(itemView.getContext());
            titleTextView = itemView.findViewById(R.id.titleTextView);
            category = itemView.findViewById(R.id.category);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            bookMarkButton = itemView.findViewById(R.id.bookmarkButton);
            deleteButton = itemView.findViewById(R.id.delButton);
            imageView = itemView.findViewById(R.id.businessImage);
            services= itemView.findViewById(R.id.services);
            distance = itemView.findViewById(R.id.distance);
            imageView.setOnClickListener(view1 -> {
                Activity activity = (Activity) view1.getContext();
                Intent intent = new Intent(activity, DetailPageActivity.class);
                activity.startActivity(intent);
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

        public static <T> T getTFromView(Class<T> targetType, View view, int id) {
            CharSequence text = ((TextView) view.findViewById(id)).getText();
            if (targetType == Integer.class) {
                return targetType.cast(Integer.valueOf(text.toString()));
            } else if (targetType == String.class) {
                return targetType.cast(text.toString());
            } else {
                // Add other type conversions here as needed
                throw new IllegalArgumentException("Unsupported target type: " + targetType.getName());
            }
        }

//        private Business getDataFromView(View view) {
//            String id = getTFromView(String.class, view, R.id.businessCardId);
//            String street = getTFromView(String.class, view, R.id.locationTextView);
//            String category = getTFromView(String.class, view, R.id.category);
//            String title = getTFromView(String.class, view, R.id.titleTextView);
//
//            Location location = new Location();
//            location.street = street;
////            location.coordinates= new double[2];
//            return new Business(id, title, description, location, 5);
//        }

    }
}
