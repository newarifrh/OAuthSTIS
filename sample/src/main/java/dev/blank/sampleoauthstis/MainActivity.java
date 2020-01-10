package dev.blank.sampleoauthstis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import org.json.JSONObject;

import dev.blank.oauthstis.OAuthSTIS;

public class MainActivity extends AppCompatActivity {
    OAuthSTIS login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.oauth);


        login.setLoginListener(new OAuthSTIS.LoginListener() {
            @Override
            public void onError(String error) {
                System.out.println("Error : " + error);


            }

            @Override
            public void onFinish(JSONObject user) {
                System.out.println("Result : " + user.toString());

            }
        });

    }
}
