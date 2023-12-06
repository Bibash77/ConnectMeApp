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
import com.websathi.connectmeapp.adapter.MyListAdapter;
import com.websathi.connectmeapp.model.business.MyListData;

public class DashboardFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        MyListData[] myListData = new MyListData[]{
                new MyListData("COLLEGE", android.R.drawable.btn_radio),
                new MyListData("RESTAURANT", android.R.drawable.ic_menu_gallery),
                new MyListData("HOSPITAL", android.R.drawable.ic_menu_mapmode),
                new MyListData("GOVERNMENT OFFICE", android.R.drawable.ic_lock_power_off),
                new MyListData("IT COMPANY", android.R.drawable.btn_star),
        };

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void configureCategoriesSection() {

    }

}
