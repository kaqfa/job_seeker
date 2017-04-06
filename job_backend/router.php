<?php

$inputData = json_decode(file_get_contents('php://input'));
$cont = new Controller($db);

// print_r($inputData);

if ($path[0] == 'applogin' && $method == 'POST'){

	$cont->login($inputData);

} else if($path[0] == 'profile' && $method == 'GET'){

	$cont->getProfile();

} else if($path[0] == 'profile' && $method == 'PUT'){

	$cont->updateProfile($inputData);

} else if($path[0] == 'jobs' && $method == 'GET'){

	$cont->getJobs();

} else if($path[0] == 'jobs' && is_numeric($path[1]) && $method == 'POST'){

	$cont->applyJob($path[1]);

} else if($path[0] == 'jobs' && is_numeric($path[1]) && $method == 'DELETE'){

	$cont->cancelApply($path[1]);

} else {
	http_response_code(404);
	exit(json_encode(['message'=>'halaman tidak ditemukan']));
}