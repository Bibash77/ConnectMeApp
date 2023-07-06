package com.websathi.connectmeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.websathi.connectmeapp.R;
import com.websathi.connectmeapp.adapter.BusinessCardApater;
import com.websathi.connectmeapp.helper.apicall.APIService;
import com.websathi.connectmeapp.helper.apicall.APiHelper;
import com.websathi.connectmeapp.model.business.Business;
import com.websathi.connectmeapp.model.business.Location;
import com.websathi.connectmeapp.model.business.search.SearchDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BusinessCardApater adapter;
    private TextView noResultsTextView;

    private ImageView imageView;

    private APIService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // fetch business recycle list view
        recyclerView = view.findViewById(R.id.business_list);

        noResultsTextView = view.findViewById(R.id.no_results_text_view);
        apiService = APiHelper.getInstance().create(APIService.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new BusinessCardApater(getBusinesses(new SearchDto()));
        recyclerView.setAdapter(adapter);
//        recyclerView.findViewById(R.id.delButton).setVisibility(4);
        return view;
    }

    private ArrayList<Business> getBusinesses() {
        ArrayList<Business> businesses = new ArrayList<>();
            Location location = new Location();
            location.street = "Data Not Found";
            location.coordinates = new double[2];
            businesses.add(new Business(1, "Default Data Inc.", "Default Data", location, 5));
        return businesses;
    }

    private List<Business> getBusinesses(final SearchDto searchDto) {
        final Call<List<Business>> call = apiService.getAllBusinessPaginated(searchDto);
        try {
            final Response<List<Business>> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                // Handle the error response here
                Toast.makeText(this.getContext(), "Unable to Retrieve Data From Server", Toast.LENGTH_SHORT).show();
            }
        } catch (final Exception e) {
            Toast.makeText(getContext(), "Unable to Retrieve Data From Server", Toast.LENGTH_LONG).show();
            System.out.println("unable to connect to internet");
            System.out.println(e.getCause());;
            return getBusinesses();
            // Handle the exception here
        }

        return new ArrayList<>();
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

