package dev.blank.oauthstis;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccessToken {
    private String clientSecret;
    private String access_token;
    private String clientId;
    private String redirectUri;
    private String code;
    public String token;


    public AccessToken(String clientId, String clientSecret, String redirectUri, String code) {
        this.clientSecret = clientSecret;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public String getAccess_token() {

        UtilsApi.getAPIService().getAccessToken("authorization_code", clientId, clientSecret, redirectUri, code).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    try {
                        System.out.println(result);
                        JSONObject jo = new JSONObject(result);
                        token = jo.getString("access_token");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


            }
        });

        return token;


    }
}
