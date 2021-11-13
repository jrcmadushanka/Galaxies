package com.civ.galaxies.api.client;

import com.civ.galaxies.network.OkHttpBasicNetworkClient;
import com.civ.galaxies.utils.ApplicationConstants;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;

    public static Retrofit getBasicClient() {
        OkHttpBasicNetworkClient coverageNetworkClient = new OkHttpBasicNetworkClient();
        OkHttpClient okHttpClient = coverageNetworkClient.openConnection();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApplicationConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
