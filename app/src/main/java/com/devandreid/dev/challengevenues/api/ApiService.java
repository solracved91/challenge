package com.devandreid.dev.challengevenues.api;

import com.devandreid.dev.challengevenues.models.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("venues/search/")
    Call<Result> getResult(@Query("client_id") String clientId,
                           @Query("client_secret") String clientSecret,
                           @Query("near") String near,
                           @Query("query") String query,
                           @Query("v") String version,
                           @Query("limit") int limit);
}
