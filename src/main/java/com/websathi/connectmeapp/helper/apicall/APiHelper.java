package com.websathi.connectmeapp.helper.apicall;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APiHelper {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://localhost:4500/v1/";

    public static Retrofit getInstance() {
        if (retrofit == null) {
            System.out.println("Object is null");
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            System.out.println("Object is not null");
        }
        return APiHelper.retrofit;
    }


}
