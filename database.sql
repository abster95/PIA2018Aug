-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 12, 2018 at 10:40 PM
-- Server version: 10.1.33-MariaDB
-- PHP Version: 7.2.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `piabus`
--

-- --------------------------------------------------------

--
-- Table structure for table `bus`
--

CREATE TABLE `bus` (
  `id` int(11) NOT NULL,
  `model` varchar(50) NOT NULL,
  `creator` varchar(50) NOT NULL,
  `capacity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `bus_companys`
--

CREATE TABLE `bus_companys` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `number` varchar(50) NOT NULL,
  `adrerss` varchar(50) NOT NULL,
  `logo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `bus_pictures`
--

CREATE TABLE `bus_pictures` (
  `id` int(11) NOT NULL,
  `bus_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `city_line`
--

CREATE TABLE `city_line` (
  `id` int(11) NOT NULL,
  `bus_id` int(11) NOT NULL,
  `first_station` varchar(50) NOT NULL,
  `other_stations` text NOT NULL COMMENT 'Same as for inter city lines, One Query to display them all, One Query to search them all, One Query to fetch them all and to variable bind them. \r\nIn the land of ETF where teaching assistants lie.',
  `last_station` int(11) NOT NULL,
  `departure_times` text NOT NULL COMMENT 'Contains departure times, in format hh:mm, hh:mm, hh:mm.'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `driver`
--

CREATE TABLE `driver` (
  `id` int(11) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `dob` date NOT NULL COMMENT 'Date of birth.',
  `started_driving` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `employment`
--

CREATE TABLE `employment` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `discount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employment`
--

INSERT INTO `employment` (`id`, `name`, `discount`) VALUES
(1, 'Penzos', 10);

-- --------------------------------------------------------

--
-- Table structure for table `inter_city_line`
--

CREATE TABLE `inter_city_line` (
  `id` int(11) NOT NULL,
  `bus_company_id` int(11) NOT NULL,
  `bus_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `departure` datetime NOT NULL,
  `first_station` varchar(50) NOT NULL,
  `arrival` datetime NOT NULL,
  `last_station` varchar(50) DEFAULT NULL,
  `other_stations` text COMMENT 'Text which contains all stations, so we can search it with one query. Since we won''t be doing advance features don''t need it to be too good.\r\nIt shold store something like station1,station2,station3 and we query it.',
  `is_active` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `monthly_cards`
--

CREATE TABLE `monthly_cards` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `line_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `number_of_cards` int(11) NOT NULL,
  `line_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `pass` varchar(255) NOT NULL COMMENT 'Dont need to hash it, only if you want to.',
  `adress` varchar(50) NOT NULL,
  `dob` date DEFAULT NULL COMMENT 'Date of birth',
  `phone` varchar(50) NOT NULL,
  `emplotment_cat_id` int(11) NOT NULL COMMENT 'Id which points to category employment category.',
  `email` varchar(255) DEFAULT NULL,
  `user_type` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bus`
--
ALTER TABLE `bus`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bus_companys`
--
ALTER TABLE `bus_companys`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bus_pictures`
--
ALTER TABLE `bus_pictures`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bus_pictures_bus_id_fk` (`bus_id`);

--
-- Indexes for table `city_line`
--
ALTER TABLE `city_line`
  ADD PRIMARY KEY (`id`),
  ADD KEY `city_line_bus_id_fk` (`bus_id`);

--
-- Indexes for table `driver`
--
ALTER TABLE `driver`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `employment`
--
ALTER TABLE `employment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `inter_city_line`
--
ALTER TABLE `inter_city_line`
  ADD PRIMARY KEY (`id`),
  ADD KEY `inter_city_line_bus_companys_id_fk` (`bus_company_id`),
  ADD KEY `inter_city_line_bus_id_fk` (`bus_id`),
  ADD KEY `inter_city_line_driver_id_fk` (`driver_id`);

--
-- Indexes for table `monthly_cards`
--
ALTER TABLE `monthly_cards`
  ADD PRIMARY KEY (`id`),
  ADD KEY `monthly_cards_city_line_id_fk` (`line_id`),
  ADD KEY `monthly_cards_user_id_fk` (`user_id`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `reservations_inter_city_line_id_fk` (`line_id`),
  ADD KEY `reservations_user_id_fk` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_username_uindex` (`username`),
  ADD KEY `user_employment_id_fk` (`emplotment_cat_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bus`
--
ALTER TABLE `bus`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `bus_companys`
--
ALTER TABLE `bus_companys`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `city_line`
--
ALTER TABLE `city_line`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `driver`
--
ALTER TABLE `driver`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employment`
--
ALTER TABLE `employment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `inter_city_line`
--
ALTER TABLE `inter_city_line`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `monthly_cards`
--
ALTER TABLE `monthly_cards`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bus_pictures`
--
ALTER TABLE `bus_pictures`
  ADD CONSTRAINT `bus_pictures_bus_id_fk` FOREIGN KEY (`bus_id`) REFERENCES `bus` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `city_line`
--
ALTER TABLE `city_line`
  ADD CONSTRAINT `city_line_bus_id_fk` FOREIGN KEY (`bus_id`) REFERENCES `bus` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `inter_city_line`
--
ALTER TABLE `inter_city_line`
  ADD CONSTRAINT `inter_city_line_bus_companys_id_fk` FOREIGN KEY (`bus_company_id`) REFERENCES `bus_companys` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `inter_city_line_bus_id_fk` FOREIGN KEY (`bus_id`) REFERENCES `bus` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `inter_city_line_driver_id_fk` FOREIGN KEY (`driver_id`) REFERENCES `driver` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `monthly_cards`
--
ALTER TABLE `monthly_cards`
  ADD CONSTRAINT `monthly_cards_city_line_id_fk` FOREIGN KEY (`line_id`) REFERENCES `city_line` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `monthly_cards_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_inter_city_line_id_fk` FOREIGN KEY (`line_id`) REFERENCES `inter_city_line` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservations_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_employment_id_fk` FOREIGN KEY (`emplotment_cat_id`) REFERENCES `employment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
