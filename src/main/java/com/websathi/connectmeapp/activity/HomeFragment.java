package com.websathi.connectmeapp.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.websathi.connectmeapp.R;
import com.websathi.connectmeapp.adapter.BusinessCardApater;
import com.websathi.connectmeapp.model.business.Business;
import com.websathi.connectmeapp.model.business.Location;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BusinessCardApater adapter;
    private TextView noResultsTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // fetch business recycle list view
        recyclerView = view.findViewById(R.id.business_list);

        noResultsTextView = view.findViewById(R.id.no_results_text_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BusinessCardApater(getBusinesses());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private ArrayList<Business> getBusinesses() {
        ArrayList<Business> businesses = new ArrayList<>();
        int j = 0;
        while(j < 100) {
            Location location = new Location();
            location.street="Charkhal Rd, Kathmandu 44605";
            location.coordinates= new double[2];
            businesses.add(new Business("1", "Leapfrog Technology, Inc.","Software company", location, 5));
            j++;
        }
        return businesses;
    }

//    private class BusinessAdapter extends RecyclerView.Adapter<BusinessViewHolder> {
//
//        private final List<Business> businesses;
//
//        public BusinessAdapter(List<Business> businesses) {
//            this.businesses = businesses;
//        }
//
//        @NonNull
//        @Override
//        public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home, parent, false);
//            LinearLayout businessListItem = view.findViewById(R.id.business_list_item);
//            return new BusinessViewHolder(businessListItem);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull BusinessViewHolder holder, int position) {
//            Business business = businesses.get(position);
//            holder.nameTextView.setText(business.name);
//            holder.addressTextView.setText(business.location.street);
//        }
//
//        @Override
//        public int getItemCount() {
//            return businesses.size();
//        }
//
//        @Override
//        public String toString() {
//            return "BusinessAdapter{" +
//                    "businesses=" + businesses +
//                    '}';
//        }
//    }

//    private class BusinessViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView nameTextView;
//        private TextView addressTextView;
//
//        public BusinessViewHolder(@NonNull View itemView) {
//            super(itemView);
//            nameTextView = itemView.findViewById(R.id.titleTextView);
//            addressTextView = itemView.findViewById(R.id.descriptionTextView);
//        }
//    }
}

