<?php

require "config.php";
require "model.php";
require "controller.php";

$method = $_SERVER['REQUEST_METHOD'];
$path = explode('/', trim($_SERVER['PATH_INFO'], '/')) ;

require "router.php";