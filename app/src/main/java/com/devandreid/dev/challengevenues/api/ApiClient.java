package com.devandreid.dev.challengevenues.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://api.foursquare.com/v2/";
    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";
    public static final String NEAR = "Seattle,+WA";
    public static final String V = "20180401";
    public static final int LIMIT = 20;

    public static OkHttpClient provideHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    public static Retrofit provideRetorfit(String baseUrl, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService provideFoursquareAPI() {
        return provideRetorfit(BASE_URL, provideHttpClient()).create(ApiService.class);
    }
}
