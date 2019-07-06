package dev.blank.sampleoauthstis;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import dev.blank.oauthstis.OAuthSTIS;

public class MainActivity extends AppCompatActivity {
    OAuthSTIS login;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.oauth);
        editText = findViewById(R.id.edit);


        login.setLoginListener(new OAuthSTIS.LoginListener() {
            @Override
            public void onError(String error) {


            }

            @Override
            public void onFinish(String result) {

            }
        });

    }
}
