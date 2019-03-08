package com.devandreid.dev.challengevenues.search;

import com.devandreid.dev.challengevenues.models.Venue;

import java.util.List;

public interface SearchMVP {

    interface View {
        void showVenues(List<Venue> venueList);
        void showMessage(String message);
    }

    interface Presenter {
        void getVenues(String text);
        void getErrorVenues(String error);
        void getSuccessVenues(List<Venue> venueList);
    }

    interface Repository {
        void getVenues(String text);
    }
}
