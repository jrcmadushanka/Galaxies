package com.civ.galaxies.network;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpBasicNetworkClient {

    public OkHttpClient openConnection() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(180, TimeUnit.SECONDS);
        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(1);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });

        httpClient.dispatcher(dispatcher);
        return httpClient.build();
    }
}
