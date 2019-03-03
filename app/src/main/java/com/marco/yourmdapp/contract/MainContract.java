package com.marco.yourmdapp.contract;

import android.arch.lifecycle.MutableLiveData;

import com.marco.yourmdapp.model.primitives.Venue;

import java.util.List;

public interface MainContract {

    /**
     * interface useful to make the communication between the main activity and the repository
     * */
    interface Presenter{

        void onDestroy();

        void onSearchVenues(MutableLiveData<List<Venue>> liveData, String query);

        void attachView(MainView view);

    }

    /**
     * interface useful to make the MainActivity View reacting to changes
     **/
    interface MainView {

        void showProgress();

        void hideProgress();

        void onResponseFailure(Throwable throwable);

        void onResponseFailureError(String error);

        void onDataEmpty();
    }

    /**
     * Fetching data
     **/
    interface Interactor {

        interface OnFinishedListener {
            void onFinished();
            void onFailure(Throwable t);
            void onFinishedWithError(String errorBody);

            void onEmptyData();
        }

        void searchVenuesList(MutableLiveData<List<Venue>> liveData, String query, OnFinishedListener listener);
    }
}
