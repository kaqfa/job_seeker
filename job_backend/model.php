<?php

function generateRandomString($length = 10) {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}

class Model{

	function __construct($db_connect){
		$this->db = $db_connect;
	}

	function authUser($username, $password){
		$stmt = $this->db->prepare('select * from jobs_member where username = ?');
		$stmt->execute([$username]);
		$uCount = $stmt->rowCount();
		$user = $stmt->fetch(PDO::FETCH_ASSOC);
		if($uCount > 0 && $user['password'] == $password){
			$sql = 'update jobs_member set token = ?, 
					expiry_date = DATE_ADD(CURDATE(), INTERVAL 2 DAY)
					where username = ?';
			$stmt = $this->db->prepare($sql);
			$token = generateRandomString(30);
			$stmt->execute(array($token, $username));
			
			return ['userid'=>$user['id'], 'token'=>$token];
		}
		return ['userid'=>0, 'token'=>0];
	}

	function checkAuth($token, $userid){
		$stmt = $this->db->prepare('select * from jobs_member where token = ? 
									and id = ? and expiry_date > now()');
		// echo $token."-".$userid;
		$stmt->execute([$token, $userid]);
		return ($stmt->rowCount() > 0);
	}

	function getProfile($idUser){
		$stmt = $this->db->prepare('select * from jobs_member where id = ?');
		$stmt->execute([$idUser]);
		$user = $stmt->fetch(PDO::FETCH_ASSOC);
		return $user;
	}

	function updateProfile($jsonData){
		$sql = 'update jobs_member set fullname = ?, email = ?,
						expertise_id = ?, location_id = ? ';
		$data = [$jsonData->fullname, $jsonData->email, 
				$jsonData->expertise_id, $jsonData->location_id];
		if($jsonData->password != ''){
			$sql .= ', password = ? ';
			$data[] = $jsonData->password;
		}
		$sql .= ' where id = ? ';
		$data[] = $jsonData->id;

		$stmt = $this->db->prepare($sql);
		$stmt->execute($data);
		return $stmt->rowCount();
	}

	function getJobs($userId, $keywords = '', $location = 0,
					 $category = 0, $minsalary = 0,
					 $sort = 'post_date'){
		$sql = 'select jobs_job.id as jobid, jobs_job.title as title, 
					 jobs_job.description as description,
					 jobs_location.title as location, 
					 jobs_category.title as category, salary, post_date, end_date, 
					 jobs_member.fullname as provider,
			         (select count(*) from jobs_job_applicants where 
			         	jobs_job_applicants.job_id = jobid 
			         	and jobs_job_applicants.member_id = ?) as registered
			         from jobs_job 
					 join jobs_category on (category_id = jobs_category.id) 
			         join jobs_location on (location_id = jobs_location.id)
			         join jobs_member on (provider_id = jobs_member.id)
			         where salary > ? ';
		$data = [$userId, $minsalary];
		if($keywords != ''){
			$sql .= ' and (title like ? or description like ?) ';
			$data[] = '%'.$keywords.'%';
			$data[] = '%'.$keywords.'%';
		}
		if($location != 0){
			$sql .= ' and location_id = ? ';
			$data[] = $location;
		}
		if($category != 0){
			$sql .= ' and category_id = ? ';
			$data[] = $category;
		}
		$sql .= ' and end_date > now() order by '.$sort;

		$stmt = $this->db->prepare($sql);
		$stmt->execute($data);
		return $stmt->fetchAll(PDO::FETCH_ASSOC);
	}

	function applyJob($jobId, $userId){
		$sql = 'insert into jobs_job_applicants values (null, ?, ?)';
		$stmt = $this->db->prepare($sql);
		$stmt->execute([$jobId, $userId]);
		return $stmt->rowCount();
	}

	function cancelApply($jobId, $userId){
		$sql = 'delete from jobs_job_applicants where job_id = ? and member_id = ?';
		$stmt = $this->db->prepare($sql);
		$stmt->execute([$jobId, $userId]);
		return $stmt->rowCount();
	}

}