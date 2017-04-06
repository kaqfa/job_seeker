<?php
class Controller {

	function __construct($db_connect){
		$this->md = new Model($db_connect);
	}

	function login($jsonData){
		// print_r($jsonData);
		$resp = $this->md->authUser($jsonData->username, $jsonData->password);
		echo json_encode($resp);
	}

	function checkAuth(){
		$headers = apache_request_headers();
		if(!$this->md->checkAuth($headers['token'], $headers['uid'])){
			http_response_code(403);
			exit(json_encode(['message'=>'not authorized']));
		}
		return true;
	}

	function getProfile(){
		$this->checkAuth();
		$headers = apache_request_headers();
		http_response_code(200);
		echo json_encode($this->md->getProfile($headers['uid']));
	}

	function updateProfile($jsonData){
		$this->checkAuth();
		if($this->md->updateProfile($jsonData) > 0){
			http_response_code(200);
			echo json_encode(["message"=>'succesfully changed']);
		} else {
			http_response_code(500);
			echo json_encode(["message"=>'something is wrong']);
		}
	}

	function getJobs(){
		$this->checkAuth();
		$headers = apache_request_headers();
		$jobs = null;

		if(!isset($_GET['keywords'])){
			$jobs = $this->md->getJobs($headers['uid']);
		} else {
			$filter = ['keywords' => $_GET['keywords'], 
					   'location' => 0, 'category' => 0, 
					   'salary' => 0, 'sort' => 'post_date'];
			if(isset($_GET['location']))
				$filter['location'] = $_GET['location'];
			if(isset($_GET['category']))
				$filter['category'] = $_GET['category'];
			if(isset($_GET['salary']))
				$filter['salary'] = $_GET['salary'];
			if(isset($_GET['sort']))
				$filter['sort'] = $_GET['sort'];

			$jobs = $this->md->getJobs($headers['uid'], $filter['keywords'], 
								$filter['location'], $filter['category'],
								$filter['salary'], $filter['sort']);			
		}
		http_response_code(200);
		echo json_encode($jobs);
	}

	function applyJob($idJob){
		$this->checkAuth();
		$headers = apache_request_headers();
		$res = $this->md->applyJob($idJob, $headers['uid']);
		
		if($res > 0){
			http_response_code(200);
			echo json_encode(['message'=>'apply success']);
		} else {
			http_response_code(500);
			echo json_encode(['message'=>'something is wrong']);
		}
	}

	function cancelApply($idJob){
		$this->checkAuth();
		$headers = apache_request_headers();
		$res = $this->md->cancelApply($idJob, $headers['uid']);
		
		if($res > 0){
			http_response_code(200);
			echo json_encode(['message'=>'apply cancelled']);
		} else {
			http_response_code(500);
			echo json_encode(['message'=>'something is wrong']);
		}
	}
}