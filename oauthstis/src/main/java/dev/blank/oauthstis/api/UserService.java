package dev.blank.oauthstis.api;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserService {
    @GET("api/user")
    Call<JsonElement> getUser(@Header("Authorization") String token);
}
