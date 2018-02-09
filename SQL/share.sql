-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- 主機: 127.0.0.1
-- 產生時間： 2018-02-09 03:46:31
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
-- 資料表結構 `share`
--

CREATE TABLE `share` (
  `id` int(5) NOT NULL,
  `encloseid` varchar(20) NOT NULL,
  `enclosename` varchar(20) NOT NULL,
  `startPosition` varchar(100) NOT NULL,
  `transoprt` varchar(100) NOT NULL,
  `viewpoint` varchar(3000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 資料表的匯出資料 `share`
--

INSERT INTO `share` (`id`, `encloseid`, `enclosename`, `startPosition`, `transoprt`, `viewpoint`) VALUES
(1, 'LEFBKGI83G', '', '25.1487716,121.77910589999999', 'images/car.png', '[{"departureTime":"2017-10-24 15:14","arrivalTime":"16:22","playTime":"1","spotName":"?鶯歌陶瓷? ","weatherIcon":"images/02.png","spotID":"116","spotPicture":"http://ed.arte.gov.tw/uploadfile/link/%E6%96%B0%E5%8C%97%E5%B8%82%E7%AB%8B%E9%B6%AF%E6%AD%8C%E9%99%B6%E7%93%B7%E5%8D%9A%E7%89%A9%E9%A4%A8.jpg"}]'),
(2, '56EA2RZ0FO', '123', '25.1486749,121.7792024', 'images/car.png', '[{"departureTime":"2017-10-24 15:17","arrivalTime":"16:25","playTime":"1","spotName":"?鶯歌陶瓷? ","weatherIcon":"images/02.png","spotID":"116","spotPicture":"http://ed.arte.gov.tw/uploadfile/link/%E6%96%B0%E5%8C%97%E5%B8%82%E7%AB%8B%E9%B6%AF%E6%AD%8C%E9%99%B6%E7%93%B7%E5%8D%9A%E7%89%A9%E9%A4%A8.jpg"},{"departureTime":"2017-10-24 17:25","arrivalTime":"16:16","playTime":"1","spotName":"?板橋435特區? ","weatherIcon":"images/03.png","spotID":"101","spotPicture":"http://diyuan.hotel.taiwandao.tw/Public/Ueditor/php/upload/15351380530343.jpg"}]'),
(3, 'LAKBVQLPP4', 'test', '25.1510461,121.7801471', 'images/car.png', '[{"departureTime":"2017-10-26 15:20","arrivalTime":"16:17","playTime":"1","spotName":"?野柳地質公園? ","weatherIcon":"images/02.png","spotID":"85","spotPicture":"http://mays2.weebly.com/uploads/5/6/7/6/5676424/762752_orig.jpg"},{"departureTime":"2017-10-26 17:17","arrivalTime":"16:10","playTime":"1","spotName":"?台北101觀景台? ","weatherIcon":"images/03.png","spotID":"61","spotPicture":"http://f.share.photo.xuite.net/jesse.yy/1fb5524/5905004/235190200_x.jpg"}]'),
(4, 'PCPU5Z39Z9', '123', '25.1503581,121.7808893', 'images/car.png', '[{"departureTime":"2017-10-26 16:13","arrivalTime":"16:15","playTime":"1","spotName":"?八斗子漁港? ","weatherIcon":"images/03.png","spotID":"1","spotPicture":"https://cdn0-digiphoto-techbang.pixfs.net/system/images/118988/medium/ebff5105406b38286275cd75683ead03.jpg?1492502178"}]'),
(5, '3UBF266TLT', '123', '25.0280356,121.4714112', 'images/car.png', '[{"departureTime":"2017-11-14 17:39","arrivalTime":"17:48","playTime":"1","spotName":"板橋大遠百","weatherIcon":"images/03.png","spotID":"96","spotPicture":"http://www.vrwalker.net/public/sceneryfiles/221/1184.jpg"}]'),
(6, 'TAZBKRQ9GT', '123', '25.0447071,121.525245', 'images/car.png', '[{"departureTime":"2017-11-20 15:35","arrivalTime":"15:56","playTime":"1","spotName":"?松山文創園區? ","weatherIcon":"images/04.png","spotID":"72","spotPicture":"http://7.share.photo.xuite.net/chaoling0519/172e5db/7339215/281261836_m.jpg"}]'),
(7, 'JPXLWYL6NM', '一日行程', '25.0588896,121.55472449999999', 'images/car.png', '[{"departureTime":"2017-12-1 14:40","arrivalTime":"18:55","playTime":"1","spotName":"?林後四林平地? ","weatherIcon":"images/02.png","spotID":"567","spotPicture":"http://pic.pimg.tw/nellydyu/1405233017-3620806749.jpg"}]'),
(8, 'JGA5TOBBSV', '1231', '25.0100515,121.46074080000001', 'images/car.png', '[{"departureTime":"2017-12-02 22:37","arrivalTime":"22:40","playTime":"1","spotName":"板橋大遠百","weatherIcon":"images/03.png","spotID":"96","spotPicture":"http://www.vrwalker.net/public/sceneryfiles/221/1184.jpg"}]'),
(9, 'ZNETM740PI', '123', '24.9948482,121.3173342', 'images/car.png', '[{"departureTime":"2017-12-02 22:17","arrivalTime":"22:23","playTime":"1","spotName":"南僑桃園工廠","weatherIcon":"images/03.png","spotID":"115","spotPicture":"http://img3.okgo.tw/titlepic/b3045_2.jpg"},{"departureTime":"2017-12-02 23:23","arrivalTime":"23:43","playTime":"1","spotName":"?巧克力共和國? ","weatherIcon":"images/02.png","spotID":"118","spotPicture":"http://pic.pimg.tw/kate7549/1334495897-3333444886_m.jpg"}]'),
(10, 'PUBLNHI31H', 'eqwe', '25.010049,121.4607412', 'images/car.png', '[{"departureTime":"2017-12-02 23:14","arrivalTime":"23:17","playTime":"1","spotName":"板橋大遠百?","weatherIcon":"images/03.png","spotID":"96","spotPicture":"http://www.vrwalker.net/public/sceneryfiles/221/1184.jpg"}]'),
(11, '8KNP5PCLUQ', 'qweq', '25.0100525,121.46074309999999', 'images/car.png', '[{"departureTime":"2017-12-02 23:15","arrivalTime":"23:18","playTime":"1","spotName":"板橋大遠百","weatherIcon":"images/03.png","spotID":"96","spotPicture":"http://www.vrwalker.net/public/sceneryfiles/221/1184.jpg"}]'),
(12, 'WN3Y56R4M8', '132', '25.0100472,121.46073469999999', 'images/car.png', '[{"departureTime":"2017-12-02 23:25","arrivalTime":"23:29","playTime":"1","spotName":"本源","weatherIcon":"images/03.png","spotID":"100","spotPicture":"http://pic.pimg.tw/zxa945975/1380715146-2282349080.jpg?v=1380715147"}]'),
(13, 'EZ92B400N5', 'demo', '22.7346763,120.285457', 'images/car.png', '[{"departureTime":"2017-12-06 10:02","arrivalTime":"11:07","playTime":"1","spotName":"林百貨","weatherIcon":"images/01.png","spotID":"462","spotPicture":"https://farm8.staticflickr.com/7536/15409955404_b802b7028b_b.jpg"}]'),
(14, '5TMDHAL454', 'demo', '22.6500073,120.327765', 'images/car.png', '[{"departureTime":"2017-12-08 13:22","arrivalTime":"13:30","playTime":"3","spotName":"科學工藝博物館","weatherIcon":"images/02.png","spotID":"529","spotPicture":"http://www.1111.com.tw/Zones/images/Art/Art20052.jpg"}]');

--
-- 已匯出資料表的索引
--

--
-- 資料表索引 `share`
--
ALTER TABLE `share`
  ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
