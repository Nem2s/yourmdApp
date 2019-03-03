package com.marco.yourmdapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.marco.yourmdapp.model.SearchVenueResponse;
import com.marco.yourmdapp.model.primitives.Venue;
import com.marco.yourmdapp.repository.SearchRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel{
    private MutableLiveData<List<Venue>> venues = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private SearchRepository.myAPI api = SearchRepository.getRetrofitInstance().create(SearchRepository.myAPI.class);


    public void searchVenues(String query) {
        isLoading.postValue(true);
        error.postValue(null);

        api.searchVenue(query).enqueue(new Callback<SearchVenueResponse>() {
            @Override
            public void onResponse(Call<SearchVenueResponse> call, Response<SearchVenueResponse> response) {
                isLoading.postValue(false);
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getResponse() != null) {
                        List<Venue> list = response.body().getResponse().getVenues();
                        if (list != null) {
                            if (list.isEmpty()) {
                                error.postValue("No results");
                            }
                            venues.postValue(response.body().getResponse().getVenues());
                        }
                    } else {
                            error.postValue("Error in response");
                    }
                } else {
                    error.postValue("No venues found");
                }
            }

            @Override
            public void onFailure(Call<SearchVenueResponse> call, Throwable t) {
                error.postValue("Network Failure!");
                isLoading.postValue(false);
            }
        });
    }

    public LiveData<List<Venue>> OnVenuesChanged() {
        return venues;
    }

    public LiveData<String> OnErrorOccured() {
        return error;
    }

    public LiveData<Boolean> OnLoadingStatus() {
        return isLoading;
    }
}
