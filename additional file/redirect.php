<?php

$query = http_build_query([
	'client_id' => (CLIENT-ID),
	'redirect_uri' => '(URL Callback)',
	'response_type' => 'code',
	'scope' => ''
]);


header('Location: http://ws.stis.ac.id/oauth/authorize?' . $query);


?>