package com.marco.yourmdapp;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marco.yourmdapp.adapter.VenueAdapter;
import com.marco.yourmdapp.viewmodel.MainActivityViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.til_search)
    TextInputLayout searchLayout;
    @BindView(R.id.tiet_search)
    TextInputEditText et_search;
    @BindView(R.id.btn_search)
    MaterialButton searchButton;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.error_message)
    TextView errorTv;

    VenueAdapter adapter;
    private MainActivityViewModel model;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupRecyclerView();
        observeData();
    }

    void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VenueAdapter();
        recyclerView.setAdapter(adapter);
    }

    void observeData() {
        model = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        model.OnVenuesChanged().observe(this, venues -> {
            if (venues != null) {
                adapter.setData(venues);
                adapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.INVISIBLE);
            }
        });
        model.OnErrorOccured().observe(this, error -> {
            if (error != null) {
                    errorTv.setText(error);
                    errorTv.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
            } else {
                errorTv.setVisibility(View.INVISIBLE);
            }
        });
        model.OnLoadingStatus().observe(this, isLoading -> {
            if (isLoading != null) {
                loading.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
            }
        });

    }

    @OnTextChanged(R.id.tiet_search)
    void onQueryChanged(CharSequence text) {
        query = text.toString();
        if (query.isEmpty()) {
            searchButton.setEnabled(false);
        } else {
            searchButton.setEnabled(true);
        }
    }

    @OnClick(R.id.btn_search)
    void onSearchClicked() {
        recyclerView.setVisibility(View.INVISIBLE);
        model.searchVenues(query);
    }
}
