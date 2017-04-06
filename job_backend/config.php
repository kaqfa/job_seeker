<?php

$db = null;

try {
	$db = new PDO("mysql:host=localhost;dbname=job_seeker;", 'root', 'root');
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);	
} catch (PDOException $e) {
	echo $e->getMessage();
}
