package dev.blank.oauthstis.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static dev.blank.oauthstis.utils.Constant.URL_WS;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(URL_WS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}