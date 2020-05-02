# OAuthSTIS
[![CircleCI](https://circleci.com/gh/newarifrh/OAuthSTIS.svg?style=svg)](https://circleci.com/gh/newarifrh/OAuthSTIS)
[![Actions Status](https://github.com/newarifrh/OAuthSTIS/workflows/Android%20CI/badge.svg)](https://github.com/newarifrh/OAuthSTIS/actions)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/newarifrh/OAuthSTIS)
![APM](https://img.shields.io/apm/l/vim-mode)
![GitHub stars](https://img.shields.io/github/stars/newarifrh/OAuthSTIS?style=social)

Sebuah sistem otentikasi untuk mengizinkan pengguna menggunakan berbagai layanan di Politeknik Statistika STIS dengan menggunakan satu akun pengguna saja yaitu Akun Sipadu STIS.

## Getting Started

Sebelum menggunakan *library* ini pastikan anda telah mendaftar di Sipadu Dev+ (https://ws.stis.ac.id), dan telah membuat sebuah aplikasi di Sipadu Dev+

### Gradle

```
implementation 'com.github.newarifrh:oauthstis:1.0'
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
                        android:textSize="16sp" />
```

### Include following code in your activity:

```

OAuthSTIS oAuthSTIS = findViewById(R.id.oauth);
oAuthSTIS.setClientId(CLIENT_ID);
oAuthSTIS.setClientSecret(CLIENT_SECRET);
oAuthSTIS.setRedirectUri(REDIRECT_URI);

oAuthSTIS.setLoginListener(new LoginListener() {
    @Override
    public void onError(String error) {
	
    }

    @Override
    public void onFinish(Mahasiswa mahasiswa) {
	
    }

    @Override
    public void onFinish(Dosen dosen) {
	
    }
});

```

## Authors

* **Arif Rahman Hakim**

## Special Thanks

Terimakasih banyak atas bantuan dari Rahadi Jalu Yoga U, team SIMPus, dan lainnya yang tidak dapat disebutkan satu per satu.
