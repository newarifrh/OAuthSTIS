package dev.blank.sampleoauthstis;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import dev.blank.oauthstis.OAuthSTIS;
import dev.blank.oauthstis.model.dosen.Dosen;
import dev.blank.oauthstis.model.mahasiswa.Mahasiswa;
import dev.blank.oauthstis.utils.LoginListener;

import static dev.blank.sampleoauthstis.Constant.CLIENT_ID;
import static dev.blank.sampleoauthstis.Constant.CLIENT_SECRET;
import static dev.blank.sampleoauthstis.Constant.REDIRECT_URI;

public class MainActivity extends AppCompatActivity {
    OAuthSTIS oAuthSTIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        oAuthSTIS = findViewById(R.id.oauth);
        oAuthSTIS.setClientId(CLIENT_ID);
        oAuthSTIS.setClientSecret(CLIENT_SECRET);
        oAuthSTIS.setRedirectUri(REDIRECT_URI);

        oAuthSTIS.setLoginListener(new LoginListener() {
            @Override
            public void onError(String error) {
                print("Error", error);
            }

            @Override
            public void onFinish(Mahasiswa mahasiswa) {
                print("Nama", mahasiswa.getMahasiswaProfile().getNama());
                print("NIM", mahasiswa.getMahasiswaProfile().getNim());
                print("Kelas", mahasiswa.getMahasiswaProfile().getKelasList().get(0).getKode_kelas());
                print("Tahun", mahasiswa.getMahasiswaProfile().getKelasList().get(0).getTahun_akademik());
            }

            @Override
            public void onFinish(Dosen dosen) {
                print("Nama", dosen.getDosenProfile().getGelar_belakang() + " " + dosen.getDosenProfile().getNama() + " " + dosen.getDosenProfile().getGelar_belakang());
                print("Username", dosen.getDosenProfile().getUsername());
                print("Status", dosen.getDosenProfile().getRole());
            }
        });
    }

    public void print(String title, String value) {
        System.out.println(title + " : " + value);
    }
}
