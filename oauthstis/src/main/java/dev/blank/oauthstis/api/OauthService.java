package dev.blank.oauthstis.api;

import dev.blank.oauthstis.model.Token;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OauthService {
    @FormUrlEncoded
    @POST("oauth/token")
    Call<Token> getToken(@Field("grant_type") String grant_type,
                         @Field("client_id") String client_id,
                         @Field("client_secret") String client_secret,
                         @Field("redirect_uri") String redirect_uri,
                         @Field("code") String code
    );
}
