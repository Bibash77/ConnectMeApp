package com.websathi.connectmeapp.helper;

import com.websathi.connectmeapp.model.Business;
import com.websathi.connectmeapp.model.BusinessResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @POST("posts")
    Call<BusinessResponse> createBusiness(@Body Business business);


    @GET("business/paginated")
    Call<List<BusinessResponse>> getAllBusinessPaginated();
}