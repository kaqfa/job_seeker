-- phpMyAdmin SQL Dump
-- version 4.6.5.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 06, 2017 at 10:28 AM
-- Server version: 5.6.34
-- PHP Version: 7.1.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `job_seeker`
--

-- --------------------------------------------------------

--
-- Table structure for table `jobs_category`
--

CREATE TABLE `jobs_category` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jobs_category`
--

INSERT INTO `jobs_category` (`id`, `title`, `description`) VALUES
(1, 'web development', ''),
(2, 'mobile application', ''),
(3, 'desain grafis', ''),
(4, 'marketing', '');

-- --------------------------------------------------------

--
-- Table structure for table `jobs_job`
--

CREATE TABLE `jobs_job` (
  `id` int(11) NOT NULL,
  `title` varchar(120) NOT NULL,
  `description` longtext NOT NULL,
  `salary` int(11) NOT NULL,
  `post_date` datetime(6) NOT NULL,
  `update_date` datetime(6) NOT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  `location_id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jobs_job`
--

INSERT INTO `jobs_job` (`id`, `title`, `description`, `salary`, `post_date`, `update_date`, `end_date`, `category_id`, `location_id`, `provider_id`) VALUES
(1, 'web development', 'web development di PSI UDINUS, syaratnya harus menguasai beberapa hal berikut:\\r\\n\\r\\n- PHP\\r\\n- MySQL\\r\\n- Angular\\r\\n- Ruby on Rails\\r\\n- Server Settings', 1000000, '2017-04-04 00:00:00.000000', '2017-04-04 00:00:00.000000', '2017-04-30 00:00:00.000000', 1, 1, 1),
(2, 'aplikasi mobile', 'Membuat aplikasi mobile untuk perusahaan dengan Android:\\r\\n\\r\\n- Android\\r\\n- MySQL\\r\\n- PHP\\r\\n- Firebase', 5000000, '2017-04-03 18:07:09.101975', '2017-04-04 01:04:18.338694', '2017-04-28 18:07:02.000000', 2, 2, 1),
(3, 'Pembuatan Iklan Baliho', 'Dicari desainer grafis untuk membuat iklan pada baliho-baliho top, dengan syarat:\\r\\n\\r\\n- Menguasai Photoshop, CorelDraw, Flash\\r\\n- Mampu berkomunikasi dengan baik\\r\\n- Memiliki pengalaman 10 tahun di bidang desain grafis', 6000000, '2017-04-03 18:09:09.925158', '2017-04-04 01:04:33.884076', '2017-04-28 18:09:05.000000', 3, 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `jobs_job_applicants`
--

CREATE TABLE `jobs_job_applicants` (
  `id` int(11) NOT NULL,
  `job_id` int(11) NOT NULL,
  `member_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jobs_job_applicants`
--

INSERT INTO `jobs_job_applicants` (`id`, `job_id`, `member_id`) VALUES
(6, 1, 2),
(7, 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `jobs_location`
--

CREATE TABLE `jobs_location` (
  `id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `parent_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jobs_location`
--

INSERT INTO `jobs_location` (`id`, `title`, `parent_id`) VALUES
(1, 'Kota Semarang', NULL),
(2, 'Pati', NULL),
(3, 'Kudus', NULL),
(4, 'Demak', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `jobs_member`
--

CREATE TABLE `jobs_member` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `fullname` varchar(100) DEFAULT NULL,
  `email` varchar(150) NOT NULL,
  `member_type` varchar(2) NOT NULL,
  `join_since` datetime(6) NOT NULL,
  `token` varchar(30) DEFAULT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `expertise_id` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jobs_member`
--

INSERT INTO `jobs_member` (`id`, `username`, `password`, `fullname`, `email`, `member_type`, `join_since`, `token`, `expiry_date`, `expertise_id`, `location_id`) VALUES
(1, 'fahri', '123', 'fahri aja', 'fahri@dsn.dinus.ac.id', 'p', '2017-04-03 02:09:39.886266', 'be12VqY7OtCQUhUR8Cqzs4CVROGOwl', '2017-04-05 00:00:00', 1, 1),
(2, 'A12345', '123', 'Anto Wijoyo', 'antonwijoyo@email.com', 'w', '2017-04-03 18:18:40.727262', 'U8XfhRuTdNtZ86R67uJpIya6FsRvly', '2017-04-07 00:00:00', 2, 2),
(3, 'B12345', '123', 'Lina Marcelina', 'lina@email.com', 'w', '2017-04-03 18:19:23.696506', 'LzI9p2gKTAHOVqEEWN04l8bn8lMZRX', '2017-04-07 00:00:00', 3, 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `jobs_category`
--
ALTER TABLE `jobs_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `jobs_job`
--
ALTER TABLE `jobs_job`
  ADD PRIMARY KEY (`id`),
  ADD KEY `jobs_job_category_id_fd8331ed_fk_jobs_category_id` (`category_id`),
  ADD KEY `jobs_job_location_id_79292eb5_fk_jobs_location_id` (`location_id`),
  ADD KEY `jobs_job_provider_id_f3009540_fk_jobs_member_id` (`provider_id`);

--
-- Indexes for table `jobs_job_applicants`
--
ALTER TABLE `jobs_job_applicants`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `jobs_job_applicants_job_id_53bf3e97_uniq` (`job_id`,`member_id`),
  ADD KEY `jobs_job_applicants_member_id_5d197596_fk_jobs_member_id` (`member_id`);

--
-- Indexes for table `jobs_location`
--
ALTER TABLE `jobs_location`
  ADD PRIMARY KEY (`id`),
  ADD KEY `jobs_location_parent_id_28558bf6_fk_jobs_location_id` (`parent_id`);

--
-- Indexes for table `jobs_member`
--
ALTER TABLE `jobs_member`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `jobs_member_expertise_id_9055de73_fk_jobs_category_id` (`expertise_id`),
  ADD KEY `jobs_member_location_id_56ad03bb_fk_jobs_location_id` (`location_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `jobs_category`
--
ALTER TABLE `jobs_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `jobs_job`
--
ALTER TABLE `jobs_job`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `jobs_job_applicants`
--
ALTER TABLE `jobs_job_applicants`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `jobs_location`
--
ALTER TABLE `jobs_location`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `jobs_member`
--
ALTER TABLE `jobs_member`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `jobs_job`
--
ALTER TABLE `jobs_job`
  ADD CONSTRAINT `jobs_job_category_id_fd8331ed_fk_jobs_category_id` FOREIGN KEY (`category_id`) REFERENCES `jobs_category` (`id`),
  ADD CONSTRAINT `jobs_job_location_id_79292eb5_fk_jobs_location_id` FOREIGN KEY (`location_id`) REFERENCES `jobs_location` (`id`),
  ADD CONSTRAINT `jobs_job_provider_id_f3009540_fk_jobs_member_id` FOREIGN KEY (`provider_id`) REFERENCES `jobs_member` (`id`);

--
-- Constraints for table `jobs_job_applicants`
--
ALTER TABLE `jobs_job_applicants`
  ADD CONSTRAINT `jobs_job_applicants_job_id_791f920a_fk_jobs_job_id` FOREIGN KEY (`job_id`) REFERENCES `jobs_job` (`id`),
  ADD CONSTRAINT `jobs_job_applicants_member_id_5d197596_fk_jobs_member_id` FOREIGN KEY (`member_id`) REFERENCES `jobs_member` (`id`);

--
-- Constraints for table `jobs_location`
--
ALTER TABLE `jobs_location`
  ADD CONSTRAINT `jobs_location_parent_id_28558bf6_fk_jobs_location_id` FOREIGN KEY (`parent_id`) REFERENCES `jobs_location` (`id`);

--
-- Constraints for table `jobs_member`
--
ALTER TABLE `jobs_member`
  ADD CONSTRAINT `jobs_member_expertise_id_9055de73_fk_jobs_category_id` FOREIGN KEY (`expertise_id`) REFERENCES `jobs_category` (`id`),
  ADD CONSTRAINT `jobs_member_location_id_56ad03bb_fk_jobs_location_id` FOREIGN KEY (`location_id`) REFERENCES `jobs_location` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
