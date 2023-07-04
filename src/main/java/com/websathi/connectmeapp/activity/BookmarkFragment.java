package com.websathi.connectmeapp.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.websathi.connectmeapp.R;
import com.websathi.connectmeapp.adapter.BusinessCardApater;
import com.websathi.connectmeapp.helper.BusinessDBHelper;
import com.websathi.connectmeapp.model.business.Business;
import com.websathi.connectmeapp.model.business.Location;

import java.util.ArrayList;

public class BookmarkFragment extends Fragment {
    private BusinessCardApater adapter;
    private RecyclerView recyclerView;
    private BusinessDBHelper businessDBHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        businessDBHelper = new BusinessDBHelper(getContext());
        recyclerView = view.findViewById(R.id.bookmarked_business_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BusinessCardApater(businessDBHelper.getAllBookMarkedItems());
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
            businesses.add(new Business(1, "Leapfrog Technology, Inc.","Software company", location, 5));
            j++;
        }
        return businesses;
    }

}
