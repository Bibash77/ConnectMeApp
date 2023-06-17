package com.websathi.connectmeapp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.websathi.connectmeapp.R;
import com.websathi.connectmeapp.helper.APIService;
import com.websathi.connectmeapp.helper.APiHelper;
import com.websathi.connectmeapp.model.Business;
import com.websathi.connectmeapp.model.BusinessResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddBusinessFragment extends Fragment {

    private EditText businessName;
    private EditText businessAddress;

    private Retrofit retrofit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_business, container, false);
        this.businessAddress = view.findViewById(R.id.address);
        this.businessName = view.findViewById(R.id.name);

        final String name = this.businessName.getText().toString();
        final String address = this.businessAddress.getText().toString();
        if(!name.isEmpty() && !address.isEmpty()) {
            Business business = new Business(name, address, 1);
            retrofit = APiHelper.getInstance();
            APIService apiService = retrofit.create(APIService.class);

            Call<BusinessResponse> call = apiService.createBusiness(business);

            call.enqueue(new Callback<BusinessResponse>() {
                @Override
                public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> response) {
                    if(response.code() == 201) {
//                        Toast.makeText(AddBusinessFragment.class, "SuccessFully Updated", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(AddBusinessFragment.class, "Unable to create", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BusinessResponse> call, Throwable t) {

                }
            });
        }
        return view;
    }
}
