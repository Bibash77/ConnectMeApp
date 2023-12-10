package com.websathi.connectmeapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.websathi.connectmeapp.BL.SearchConfig;
import com.websathi.connectmeapp.R;
import com.websathi.connectmeapp.adapter.BusinessCardApater;
import com.websathi.connectmeapp.adapter.MyListAdapter;
import com.websathi.connectmeapp.helper.apicall.APIService;
import com.websathi.connectmeapp.helper.apicall.APiHelper;
import com.websathi.connectmeapp.helper.db.SearchHistoryDBHelper;
import com.websathi.connectmeapp.helper.db.SearchSettingDBHelper;
import com.websathi.connectmeapp.model.business.Business;
import com.websathi.connectmeapp.model.business.CategoryResponseDto;
import com.websathi.connectmeapp.model.business.search.CoordinatesDTO;
import com.websathi.connectmeapp.model.business.search.DashboardSearchDTO;
import com.websathi.connectmeapp.model.business.search.DashboardSearchResultDTO;
import com.websathi.connectmeapp.model.business.search.RecommendedBusinessesDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private APIService apiService;

    private DashboardSearchResultDTO dashboardSearchResultDTO = new DashboardSearchResultDTO();
    private SearchHistoryDBHelper searchHistoryDBHelper;

    private SearchSettingDBHelper searchSettingDBHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        searchHistoryDBHelper = new SearchHistoryDBHelper(getContext());
        searchSettingDBHelper = new SearchSettingDBHelper(getContext());
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
        dashboardSearchDTO.setName(searchHistoryDBHelper.getSearchHistory());
        SearchConfig searchConfig = searchSettingDBHelper.getDefaultValues();
        dashboardSearchDTO.setCoordinates(new CoordinatesDTO(searchConfig.getLatitude(), searchConfig.getLongitude()));
        final Call<DashboardSearchResultDTO> call = apiService.getDashboardData(dashboardSearchDTO);
        try {
            call.enqueue(new Callback<DashboardSearchResultDTO>() {

                @Override
                public void onResponse(Call<DashboardSearchResultDTO> call, Response<DashboardSearchResultDTO> response) {
                    updateCategoriesData(null, view);

                    RecommendedBusinessesDTO recommendedBusinessesDTO = response.body().getResults();
                    updateCategoriesData(recommendedBusinessesDTO.getRecommendedCategories(), view);

                    updateRecomendedBusinessData(recommendedBusinessesDTO.getRecommendedBusinesses(), view);
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


    private void updateCategoriesData(CategoryResponseDto[] categoryResponseDtos, View view) {
        Context context = getContext();
        if (categoryResponseDtos != null) {
            for (CategoryResponseDto myListData : categoryResponseDtos) {
                int imgResourceId = 0;
                if(myListData.getIcon() != null) {
                   imgResourceId  = context.getResources().getIdentifier(myListData.getIcon(), "drawable", context.getPackageName());
                }
                myListData.setImgId(imgResourceId);
            }
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(categoryResponseDtos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setAdapter(adapter);
    }

    private void updateRecomendedBusinessData(List<Business> recommendedBusinesses, View view) {
        RecyclerView recyclerView = view.findViewById(R.id.business_list_dashboard);
        if (recommendedBusinesses != null && !recommendedBusinesses.isEmpty()) {
            BusinessCardApater adapter = new BusinessCardApater(recommendedBusinesses, "DASHBOARD");
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            TextView textView = view.findViewById(R.id.noBusinessFound);
            textView.setVisibility(View.INVISIBLE);
        }
    }
}
