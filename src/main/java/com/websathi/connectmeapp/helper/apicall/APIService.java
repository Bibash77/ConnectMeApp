package com.websathi.connectmeapp.helper.apicall;

import com.websathi.connectmeapp.model.business.Business;
import com.websathi.connectmeapp.model.business.BusinessResponse;
import com.websathi.connectmeapp.model.business.PaginatedResponse;
import com.websathi.connectmeapp.model.business.search.SearchDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @POST("posts")
    Call<BusinessResponse> createBusiness(@Body Business business);


    @POST("business/paginated")
    Call<PaginatedResponse> getAllBusinessPaginated(@Body SearchDto searchDto);
}
