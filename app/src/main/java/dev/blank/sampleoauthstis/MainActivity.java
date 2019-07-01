package dev.blank.sampleoauthstis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dev.blank.oauthstis.AccessToken;
import dev.blank.oauthstis.OAuthSTIS;

public class MainActivity extends AppCompatActivity {
    OAuthSTIS login;
    AccessToken accessToken;
    String clientId = "";
    String clientSecret = "";
    String redirectUri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.oauth);


        login.setLoginListener(new OAuthSTIS.LoginListener() {
            @Override
            public void onError(String error) {

            }

            @Override
            public void onFinish(String token) {


            }
        });

    }
}
