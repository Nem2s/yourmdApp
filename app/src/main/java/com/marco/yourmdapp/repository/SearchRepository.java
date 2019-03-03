package com.marco.yourmdapp.repository;

import com.marco.yourmdapp.model.SearchVenueResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class SearchRepository {
    private static Retrofit instance;
    private static final String URL = "https://api.foursquare.com/";

    /**
     * Create an instance of Retrofit object
     * */
    public static Retrofit getRetrofitInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(createHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

    private static OkHttpClient createHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public interface myAPI {

        @GET("v2/venues/search?client_id=ZBO1UNSFXVGUL54VVDAXVLFXRZQQMAWRQXD44R22S1SZN1PK&client_secret=SR2MNLMS3HW05W12W2IPYXEMB2ZCXM5P0CHPBQ5PLW55KH0J&v=20190301")
        Call<SearchVenueResponse> searchVenue(@Query("near") String searchVenueRequest
        );
    }
}
