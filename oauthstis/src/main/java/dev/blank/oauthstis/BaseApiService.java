package dev.blank.oauthstis;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BaseApiService {


    @FormUrlEncoded
    @POST("oauth/token")
    Call<ResponseBody> getAccessToken(@Field("grant_type") String grant_type,
                                      @Field("client_id") String client_id,
                                      @Field("client_secret") String client_secret,
                                      @Field("redirect_uri") String redirect_uri,
                                      @Field("code") String code

    );

}
