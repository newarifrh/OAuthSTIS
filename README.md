# OAuthSTIS
Sebuah sistem otentikasi untuk mengizinkan pengguna menggunakan berbagai layanan di Politeknik Statistika STIS dengan menggunakan satu akun pengguna saja yaitu Akun Sipadu STIS.

## Getting Started

Sebelum menggunakan *library* ini pastikan anda telah mendaftar di Sipadu Dev+ (https://ws.stis.ac.id), dan telah membuat sebuah aplikasi di Sipadu Dev+

### Gradle

```
implementation 'com.github.newarifrh:oauthstis:0.0.10-alpha'
```

## Usage

### Include following code in your layout:

```
 <dev.blank.oauthstis.OAuthSTIS
                        android:id="@+id/oauth"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_gravity="center"
                        android:layout_margin="20dp"
                        android:textSize="16sp"
                        app:clientId="CLIENT ID"
                        app:redirectUri="URL CALLBACK" />
```

### Include following code in your activity:

![carbon](https://user-images.githubusercontent.com/40921368/65258785-b51b7e80-db2d-11e9-97c6-be710f0b1d7c.png)

```

 OAuthSTIS  oAuthSTIS = findViewById(R.id.oauth);
        oAuthSTIS.setLoginListener(new OAuthSTIS.LoginListener() {
            @Override
            public void onError(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(String result) {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
        });

```



## Authors

* **Arif Rahman Hakim**

## Special Thanks

Terimakasih banyak atas bantuan dari Rahadi Jalu Yoga U, team SIMPus, dan lainnya yang tidak dapat disebutkan satu per satu.
