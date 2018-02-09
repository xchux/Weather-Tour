-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- 主機: 127.0.0.1
-- 產生時間： 2018-02-09 03:46:26
-- 伺服器版本: 10.1.19-MariaDB
-- PHP 版本： 5.5.38

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `weather_tour`
--

-- --------------------------------------------------------

--
-- 資料表結構 `seven_days`
--

CREATE TABLE `seven_days` (
  `ID` int(11) NOT NULL,
  `location` varchar(10) NOT NULL,
  `postal_code` int(5) NOT NULL,
  `day` varchar(10) NOT NULL,
  `time` varchar(10) NOT NULL,
  `status` varchar(10) NOT NULL,
  `high_temp` varchar(5) NOT NULL,
  `low_temp` varchar(5) NOT NULL,
  `rainfall_probability` varchar(5) NOT NULL,
  `ultraviolet` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 已匯出資料表的索引
--

--
-- 資料表索引 `seven_days`
--
ALTER TABLE `seven_days`
  ADD PRIMARY KEY (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
