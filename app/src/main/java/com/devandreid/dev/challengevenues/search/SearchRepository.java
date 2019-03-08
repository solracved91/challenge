package com.devandreid.dev.challengevenues.search;

import com.devandreid.dev.challengevenues.api.ApiClient;
import com.devandreid.dev.challengevenues.api.ApiService;
import com.devandreid.dev.challengevenues.models.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository implements SearchMVP.Repository {

    SearchMVP.Presenter presenter;

    public SearchRepository(SearchMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getVenues(String text) {
        ApiService apiService = ApiClient.provideFoursquareAPI();

        Call<Result> call = apiService.getResult(ApiClient.CLIENT_ID, ApiClient.CLIENT_SECRET,
                ApiClient.NEAR, text, ApiClient.V, ApiClient.LIMIT);

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                presenter.getSuccessVenues(response.body().getResponse().getVenues());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                presenter.getErrorVenues("Ocurrió un error, intente nuevamente");
            }
        });
    }
}
