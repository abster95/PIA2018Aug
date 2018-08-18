-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 18, 2018 at 12:35 PM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
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

--
-- Dumping data for table `bus`
--

INSERT INTO `bus` (`id`, `model`, `creator`, `capacity`) VALUES
(1, '2018S', 'Mercedes', 50),
(2, '2010Gradski', 'Ikarbus', 120),
(3, '2014GradskiDugi', 'Solaris', 130),
(4, '2005Medjugradski', 'Altina', 50);

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

--
-- Dumping data for table `bus_companys`
--

INSERT INTO `bus_companys` (`id`, `name`, `number`, `adrerss`, `logo`) VALUES
(1, 'Busko', '+38162123456', 'Autobuska bb', 'BuSkO'),
(2, 'Bule bus', '+38160111222', 'Levo desno desno levo', '________\r\n| |  | |\r\n-_----_-'),
(3, 'U Gasu Sam', '+38169696969', 'Fox 69', '-_(o.o)_-'),
(4, 'Majstore Zadnja', '+38165333222', 'Zadnja Vrata 77', '[]<---[]-----[]-()');

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

--
-- Dumping data for table `city_line`
--

INSERT INTO `city_line` (`id`, `bus_id`, `first_station`, `other_stations`, `last_station`, `departure_times`) VALUES
(3, 2, 'Pohorska', 'Pohorska#GO Novi Beograd#Blok 30#Brankov#Zelenjak#Vasingtona#Karaburma 2', 16, '08:00#08:30#09:00#09:30#10:00#10:30#11:00#11:30#12:00#12:30'),
(4, 3, 'Zvezdara 2', 'Zvezdara 2#Dalmatinska#Masinski#Palilulska#Zelenjak#Branko#Usce', 65, '13:00#13:30#14:00#14:30#15:00#15:30#16:00');

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

--
-- Dumping data for table `driver`
--

INSERT INTO `driver` (`id`, `firstname`, `lastname`, `dob`, `started_driving`) VALUES
(1, 'Misko', 'Miskovic', '1990-01-15', '2018-04-15'),
(2, 'Majstor', 'Miki', '1980-08-01', '1996-08-08'),
(3, 'Bucko', 'Buckovic', '1985-07-03', '2017-12-19'),
(4, 'Saban', 'Sapke', '1970-09-02', '2013-04-05');

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
(1, 'Penzos', 10),
(2, 'Nema', 0);

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

--
-- Dumping data for table `inter_city_line`
--

INSERT INTO `inter_city_line` (`id`, `bus_company_id`, `bus_id`, `driver_id`, `departure`, `first_station`, `arrival`, `last_station`, `other_stations`, `is_active`) VALUES
(1, 1, 1, 3, '2018-08-15 08:30:00', 'Beograd', '2018-08-15 15:00:00', 'Novi Pazar', 'Beograd Kragujevac Kraljevo Novi Pazar', 1),
(3, 2, 4, 2, '2018-08-15 12:00:00', 'Nis', '2018-08-15 17:00:00', 'Subotica', 'Nis Beograd Novi Sad Subotica', 1);

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
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `firstname`, `lastname`, `username`, `pass`, `adress`, `dob`, `phone`, `emplotment_cat_id`, `email`, `user_type`) VALUES
(1, 'Ado', 'Adminson', 'admin', 'admin', 'Zasto je ovo obavezno 32', '1990-08-08', '+3816543210', 1, 'admin@administrator.com', 2),
(2, 'Obican', 'User', 'obican', 'obican', 'Adresica 123', '1992-08-14', '+38164656768', 1, 'obican@lik.com', 1),
(3, 'Not', 'Authorized', 'neodobren', 'neodobren', 'Zasto brt', '2018-08-06', '+38165233223', 1, 'neodobren@korisnik.com', 0),
(4, 'Abi', 'Babi', 'abibabi', 'abibabi', 'Ne dam', '2018-08-13', '+3816523432', 2, 'abi@babi.com', 0),
(5, 'Ajmo', 'Opet', 'ajmo', 'ajmo', 'Neka', '2018-06-11', '+3816523432', 2, 'ajmo@opet.com', 0),
(6, 'Succ', 'Essful', 'succ', 'succ', 'Success', '2018-08-14', '+3816523734', 2, 'succ@ess.com', 0);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `bus_companys`
--
ALTER TABLE `bus_companys`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `city_line`
--
ALTER TABLE `city_line`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `driver`
--
ALTER TABLE `driver`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `employment`
--
ALTER TABLE `employment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `inter_city_line`
--
ALTER TABLE `inter_city_line`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
