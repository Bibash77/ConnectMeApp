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
import com.websathi.connectmeapp.helper.db.BusinessBookMarkDBHelper;

public class BookmarkFragment extends Fragment {
    private BusinessCardApater adapter;
    private RecyclerView recyclerView;
    private BusinessBookMarkDBHelper businessBookMarkDBHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        businessBookMarkDBHelper = new BusinessBookMarkDBHelper(getContext());
        recyclerView = view.findViewById(R.id.bookmarked_business_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BusinessCardApater(businessBookMarkDBHelper.getAllBookMarkedItems());
        recyclerView.setAdapter(adapter);
        return view;
    }

}
