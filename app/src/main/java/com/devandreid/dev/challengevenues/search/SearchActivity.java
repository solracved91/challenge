package com.devandreid.dev.challengevenues.search;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.devandreid.dev.challengevenues.R;
import com.devandreid.dev.challengevenues.details.DetailsActivity;
import com.devandreid.dev.challengevenues.map.MapsActivity;
import com.devandreid.dev.challengevenues.models.Venue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class SearchActivity extends AppCompatActivity implements SearchMVP.View {

    @BindView(R.id.search_activity)
    ViewGroup searchActivity;

    @BindView(R.id.sv_venues)
    SearchView svVenues;

    @BindView(R.id.rv_venues)
    RecyclerView rvVenues;

    @BindView(R.id.fab_venues)
    FloatingActionButton fabVenues;

    SearchPresenter searchPresenter;

    MyListAdapter listAdapter;

    List<Venue> venueList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        setInit();
    }

    private void setInit() {
        searchPresenter = new SearchPresenter(this);

        svVenues.setIconifiedByDefault(true);
        svVenues.setFocusable(true);
        svVenues.setIconified(false);
        svVenues.requestFocusFromTouch();

        listAdapter = new MyListAdapter(venueList, this, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent i = new Intent(getApplication(), DetailsActivity.class);
                i.putExtra("venue", venueList.get(position));
                startActivity(i);
            }
        });

        rvVenues.setAdapter(listAdapter);
        rvVenues.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        rvVenues.setItemAnimator(new DefaultItemAnimator());
        rvVenues.setHasFixedSize(false);
        rvVenues.setLayoutManager(new LinearLayoutManager(this));

        svVenues.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchPresenter.getVenues(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


    }

    @OnClick(R.id.fab_venues)
    public void goToMap() {
        if (!venueList.isEmpty()) {
            Intent i = new Intent(this, MapsActivity.class);
            i.putExtra("venueList", (Serializable) venueList);
            startActivity(i);
        }
    }

    @Override
    public void showVenues(List<Venue> venueList) {
        this.venueList.clear();
        listAdapter.notifyDataSetChanged();
        this.venueList.addAll(venueList);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(searchActivity, message, Snackbar.LENGTH_LONG).show();
    }
}
