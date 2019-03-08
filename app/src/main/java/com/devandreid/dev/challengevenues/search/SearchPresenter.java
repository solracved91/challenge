package com.devandreid.dev.challengevenues.search;

import com.devandreid.dev.challengevenues.models.Venue;

import java.util.List;

public class SearchPresenter implements SearchMVP.Presenter {

    SearchMVP.View v;
    SearchRepository searchRepository;


    public SearchPresenter(SearchMVP.View v) {
        this.v = v;
        searchRepository = new SearchRepository(this);
    }

    @Override
    public void getVenues(String text) {
        if (searchRepository != null) {
            searchRepository.getVenues(text);
        }
    }

    @Override
    public void getErrorVenues(String error) {
        if (v != null) {
            v.showMessage(error);
        }
    }

    @Override
    public void getSuccessVenues(List<Venue> venueList) {
        if (v != null) {
            v.showVenues(venueList);
            if(venueList.isEmpty()) {
                v.showMessage("No hay resultados de su consulta");
            }
        }
    }
}
