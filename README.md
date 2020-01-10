# OAuthSTIS
[![CircleCI](https://circleci.com/gh/newarifrh/OAuthSTIS.svg?style=svg)](https://circleci.com/gh/newarifrh/OAuthSTIS)

[![Actions Status](https://github.com/newarifrh/OAuthSTIS/workflows/Android CI/badge.svg)](https://github.com/newarifrh/OAuthSTIS/actions)

Sebuah sistem otentikasi untuk mengizinkan pengguna menggunakan berbagai layanan di Politeknik Statistika STIS dengan menggunakan satu akun pengguna saja yaitu Akun Sipadu STIS.

## Getting Started

Sebelum menggunakan *library* ini pastikan anda telah mendaftar di Sipadu Dev+ (https://ws.stis.ac.id), dan telah membuat sebuah aplikasi di Sipadu Dev+

### Gradle

```
implementation 'com.github.newarifrh:oauthstis:0.1.0'
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
                        app:clientId="#CLIENT-ID"
                        app:redirectUri="#URL-CALLBACK" />
```

### Include following code in your activity:

```

 OAuthSTIS  oAuthSTIS = findViewById(R.id.oauth);
        oAuthSTIS.setLoginListener(new OAuthSTIS.LoginListener() {
            @Override
            public void onError(String error) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish(JSONObject user) {
	    	Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
            }
        });

```

## Additional

### Code for the redirector:

```
$query = http_build_query([
            'client_id' => '#CLIENT-ID',
            'redirect_uri' => '#URL-CALLBACK',
            'response_type' => 'code',
            'scope' => ''
        ]);

        header('Location: http://ws.stis.ac.id/oauth/authorize?' . $query);

```

### Code for the callback:

```
if (isset($_REQUEST['code']) && $_REQUEST['code']) {
			$curl_status = curl_init();

			curl_setopt_array($curl_status, [
				CURLOPT_RETURNTRANSFER => 1,
				CURLOPT_URL => 'https://ws.stis.ac.id/oauth/token',
				CURLOPT_POST => 1,

				CURLOPT_POSTFIELDS => [
					'grant_type' => 'authorization_code',
					'client_id' => '#CLIENT-ID',
					'client_secret' => '#CLIENT-SECRET',
					'redirect_uri' => '#URL-CALLBACK',
					'code' => $_REQUEST['code']
				]
			]);

			$result = curl_exec($curl_status);
			curl_close($curl_status);
			$hasil = json_decode($result);
			$token = $hasil->access_token; //Token User


			$authorization = "Authorization: Bearer " . $token;

			$curl_status = curl_init();

			curl_setopt_array($curl_status, [
				CURLOPT_RETURNTRANSFER => 1,
				CURLOPT_URL => 'https://ws.stis.ac.id/api/user',
				CURLOPT_HTTPHEADER => array($authorization)
			]);

			$user = curl_exec($curl_status);
			curl_close($curl_status);

			echo $user;
		}

```

## Authors

* **Arif Rahman Hakim**

## Special Thanks

Terimakasih banyak atas bantuan dari Rahadi Jalu Yoga U, team SIMPus, dan lainnya yang tidak dapat disebutkan satu per satu.
