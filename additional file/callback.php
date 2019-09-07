<?php


if (isset($_REQUEST['code']) && $_REQUEST['code']) {


	$curl_status = curl_init();

	curl_setopt_array($curl_status, [
		CURLOPT_RETURNTRANSFER => 1,
		CURLOPT_URL => 'https://ws.stis.ac.id/oauth/token',
		CURLOPT_POST => 1,

		CURLOPT_POSTFIELDS => [
			'grant_type' => 'authorization_code',
			'client_id' => '(CLIENT ID)',
			'client_secret' => '(CLIENT SECRET)',
			'redirect_uri' => '(URL CALLBACK)',
			'code' => $_REQUEST['code']
		]
	]);
	curl_setopt($curl_status, CURLOPT_FRESH_CONNECT, TRUE);
	$result = curl_exec($curl_status);
	curl_close($curl_status);
	$hasil = json_decode($result);
	$token_user = $hasil->access_token;


	$authorization = "Authorization: Bearer " . $token_user;

	$curl_status = curl_init();

	curl_setopt_array($curl_status, [
		CURLOPT_RETURNTRANSFER => 1,
		CURLOPT_URL => 'https://ws.stis.ac.id/api/user',
		CURLOPT_HTTPHEADER => array($authorization)
	]);

	curl_setopt($curl_status, CURLOPT_FRESH_CONNECT, TRUE);

	$result_data_user = curl_exec($curl_status);
	curl_close($curl_status);

	if (isset($_SERVER['HTTP_X_REQUESTED_WITH'])) {
		if ($_SERVER['HTTP_X_REQUESTED_WITH'] == "(packge name app android)") {
			echo $result_data_user;
		} 
	} else {
		echo json_decode($result_data_user);
	}
}


?>