package com.websathi.connectmeapp.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APiHelper {

    private static Retrofit retrofit;
    private static final String BASE_URL = "localhost:4500/v1/";

    public static Retrofit getInstance() {
        if (APiHelper.retrofit == null) {
            System.out.println("Object is null");
            retrofit = new Retrofit.Builder()
                    .baseUrl(APiHelper.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            System.out.println("Object is not null");
        }
        return APiHelper.retrofit;
    }


}
