package com.websathi.connectmeapp.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.websathi.connectmeapp.R;
import com.websathi.connectmeapp.adapter.MyListAdapter;
import com.websathi.connectmeapp.helper.apicall.APIService;
import com.websathi.connectmeapp.helper.apicall.APiHelper;
import com.websathi.connectmeapp.model.business.MyListData;
import com.websathi.connectmeapp.model.business.PaginatedResponse;
import com.websathi.connectmeapp.model.business.search.DashboardSearchDTO;
import com.websathi.connectmeapp.model.business.search.DashboardSearchResultDTO;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private APIService apiService;

    private DashboardSearchResultDTO dashboardSearchResultDTO = new DashboardSearchResultDTO();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        apiService = APiHelper.getInstance().create(APIService.class);


//        MyListData[] myListData = new MyListData[]{
//                new MyListData("COLLEGE", getContext().getResources().getIdentifier("ic_setting", "drawable", getContext().getPackageName())),
//                new MyListData("RESTAURANT", android.R.drawable.ic_menu_gallery),
//                new MyListData("HOSPITAL", android.R.drawable.ic_menu_mapmode),
//                new MyListData("GOVERNMENT OFFICE", android.R.drawable.ic_lock_power_off),
//                new MyListData("IT COMPANY", android.R.drawable.btn_star),
//        };
        getDashboardData(view);
        return view;
    }

    private DashboardSearchResultDTO getDashboardData(View view) {
        DashboardSearchDTO dashboardSearchDTO = new DashboardSearchDTO();
        final Call<DashboardSearchResultDTO> call = apiService.getDashboardData(dashboardSearchDTO);
        try {
            call.enqueue(new Callback<DashboardSearchResultDTO>() {

                @Override
                public void onResponse(Call<DashboardSearchResultDTO> call, Response<DashboardSearchResultDTO> response) {
                    updateCategoriesData(null , view);
                }

                @Override
                public void onFailure(Call<DashboardSearchResultDTO> call, Throwable t) {
                    Toast.makeText(getContext(), "Error !! Please Check your Internet Setting !!!", Toast.LENGTH_SHORT).show();
                }
            });
            return dashboardSearchResultDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


        private void updateCategoriesData(MyListData[] listdata, View view) {
             listdata = new MyListData[]{
                    new MyListData("COLLEGE", getContext().getResources().getIdentifier("ic_school", "drawable", getContext().getPackageName())),
            new MyListData("RESTAURANT",getContext().getResources().getIdentifier("ic_restaurant", "drawable", getContext().getPackageName())),
                    new MyListData("HOSPITAL", getContext().getResources().getIdentifier("ic_hospital", "drawable", getContext().getPackageName())),
                    new MyListData("GOVERNMENT OFFICE", getContext().getResources().getIdentifier("ic_government_office", "drawable", getContext().getPackageName())),
                    new MyListData("IT COMPANY",getContext().getResources().getIdentifier("ic_it_company", "drawable", getContext().getPackageName())),
        };

            RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
            MyListAdapter adapter = new MyListAdapter(listdata);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            recyclerView.setAdapter(adapter);
        }
    }
