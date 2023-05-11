package com.websathi.connectmeapp.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.websathi.connectmeapp.R;
import com.websathi.connectmeapp.model.Business;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BusinessAdapter adapter;
    private TextView noResultsTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.business_list);
        noResultsTextView = view.findViewById(R.id.no_results_text_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BusinessAdapter(getBusinesses());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<Business> getBusinesses() {
        List<Business> businesses = new ArrayList<>();
        businesses.add(new Business("Business 1", "123 Main St", 1));
        businesses.add(new Business("Business 2", "456 Maple Ave", 2));
        businesses.add(new Business("Business 3", "789 Oak Ln", 3));
        businesses.add(new Business("Business 4", "321 Elm St", 4));
        businesses.add(new Business("Business 5", "654 Pine Rd", 5));
        return businesses;
    }

    private class BusinessAdapter extends RecyclerView.Adapter<BusinessViewHolder> {

        private List<Business> businesses;

        public BusinessAdapter(List<Business> businesses) {
            this.businesses = businesses;
        }

        @NonNull
        @Override
        public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home, parent, false);
            LinearLayout businessListItem = view.findViewById(R.id.business_list_item);
            return new BusinessViewHolder(businessListItem);
        }

        @Override
        public void onBindViewHolder(@NonNull BusinessViewHolder holder, int position) {
            Business business = businesses.get(position);
            holder.nameTextView.setText(business.getName());
            holder.addressTextView.setText(business.getAddress());
        }

        @Override
        public int getItemCount() {
            return businesses.size();
        }
    }

    private class BusinessViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView addressTextView;

        public BusinessViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            addressTextView = itemView.findViewById(R.id.address_text_view);
        }
    }
}

