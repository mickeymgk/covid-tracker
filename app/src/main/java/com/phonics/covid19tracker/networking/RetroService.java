package com.phonics.covid19tracker.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroService {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.thevirustracker.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
