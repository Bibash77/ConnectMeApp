package com.websathi.connectmeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.websathi.connectmeapp.model.business.PaginatedResponse;
import com.websathi.connectmeapp.model.business.search.SearchDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BusinessCardApater adapter;
    private TextView noResultsTextView;

    private ImageView imageView;

    private APIService apiService;

// ...

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.business_list);
        noResultsTextView = view.findViewById(R.id.no_results_text_view);
        apiService = APiHelper.getInstance().create(APIService.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Call getBusinesses() asynchronously

        SearchDto searchDto = new SearchDto();
        searchDto.setLimit(20);
        searchDto.setPage(1);
        getBusinesses(searchDto);

        return view;
    }

    private void getBusinesses(final SearchDto searchDto) {
        final Call<PaginatedResponse> call = apiService.getAllBusinessPaginated(searchDto);
       try {
           call.enqueue(new Callback<PaginatedResponse>() {
               @Override
               public void onResponse(Call<PaginatedResponse> call, Response<PaginatedResponse> response) {
                   if (response.isSuccessful()) {
                       PaginatedResponse paginatedResponse = response.body();

                       List<Business> businesses = paginatedResponse.getResults();
                       if (businesses != null && !businesses.isEmpty()) {
                           adapter = new BusinessCardApater(businesses);
                           recyclerView.setAdapter(adapter);
                           System.out.println(businesses);
                       } else {
                           // Return a default list if the server doesn't provide any data
                           adapter = new BusinessCardApater(getDefaultBusinessList());
                           recyclerView.setAdapter(adapter);
                       }
                   } else {
                       // Handle the error response here
                       Toast.makeText(getContext(), "Unable to Retrieve Data From Server", Toast.LENGTH_SHORT).show();
                   }
               }

               @Override
               public void onFailure(Call<PaginatedResponse> call, Throwable t) {
//                   Toast.makeText(getContext(), "Unable to Retrieve Data From Server", Toast.LENGTH_LONG).show();
//                   System.out.println("fail to get data");
//                   t.printStackTrace();
//                   // Return a default list in case of failure
//                   adapter = new BusinessCardApater(getDefaultBusinessList());
//                   recyclerView.setAdapter(adapter);

                   Log.e("NetworkError", "Error during network request: " + t.getMessage());

                   // Log raw response body
                   if (call != null && call.isExecuted() && call.isCanceled()) {
                       ResponseBody errorBody = ((HttpException) t).response().errorBody();
                       if (errorBody != null) {
                           try {
                               Log.e("RawResponse", "Raw response body: " + errorBody.string());
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }
                   }

                   t.printStackTrace();
               }
           });
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

// ...

    private static List<Business> getDefaultBusinessList() {
        // Return your default list of businesses here
        List<Business> defaultBusinesses = new ArrayList<>();
        // Add your default business data to the list
        return defaultBusinesses;
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

