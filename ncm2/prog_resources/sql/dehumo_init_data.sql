-- phpMyAdmin SQL Dump
-- version 4.0.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 11, 2016 at 06:59 AM
-- Server version: 5.6.11-log
-- PHP Version: 5.4.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Dumping data for table `canal`
--

INSERT INTO `canal` (`chid`, `tabaco`, `carga`, `stock_maximo`, `modif_date`) VALUES
(1, 1, 20, 20, '2016-08-11 06:55:23'),
(2, 2, 20, 20, '2016-08-11 06:55:23');

--
-- Dumping data for table `cliente`
--

INSERT INTO `cliente` (`cid`, `propietario`, `nombre`, `direccion`, `telefono`, `movil`, `email`, `creationdate`, `status`, `empresa`, `NIF`, `pais`) VALUES
(1, 1, 'Bar Mi Bar', 'Avenida del bar', 943943943, 666777888, 'mibar@dehumo.com', '2016-08-11 06:54:11', 1, 'Bar Mi Bar SL', '12345678B', 'Catalunya');

--
-- Dumping data for table `maquina`
--

INSERT INTO `maquina` (`mid`, `cliente`, `propietario`, `marca`, `modelo`, `num_serie`, `canales`, `estado`, `modif_date`) VALUES
(1, 1, 1, 'ARGOS', ' 15/20', 'qrty346', '2', 1, '2016-08-11 06:56:39');

--
-- Dumping data for table `tabaco`
--

INSERT INTO `tabaco` (`tid`, `marca`, `modelo`, `num_serie`, `precio`, `modif_date`) VALUES
(1, 'Winston', 'CLASSIC RED 100', '1313', 4.35, '2016-08-11 06:55:58'),
(2, 'Winston', 'SUBTLE SILVER', '2545', 4.45, '2016-08-11 06:55:58'),
(3, 'Winston', 'XSTYLE RED', '24977', 4.30, '2016-08-11 06:55:58'),
(4, 'Winston', 'EVOLUTION 100 DURO', '25915', 4.10, '2016-08-11 06:55:58'),
(5, 'L&M', 'RED LABEL 100´S', '26586', 4.10, '2016-08-11 06:55:58'),
(6, 'Chesterfield', 'CLASSIC BLUE DURO', '1051', 4.55, '2016-08-11 06:55:58'),
(7, 'Philip Morris', 'CLASSIC', '2548', 4.30, '2016-08-11 06:55:58'),
(8, 'Malboro', 'TOUCH ROJO', '22196', 4.45, '2016-08-11 06:55:58'),
(9, 'Malboro', 'RED DURO', '2884', 4.85, '2016-08-11 06:55:58');

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`uid`, `nombre`, `apellido`, `email`, `pass`, `creationdate`, `status`, `Empresa`, `NIF`, `pais`) VALUES
(1, 'Amador', 'Lopez', 'amador.lopez.1963@gmail.com', 'md5_coded', '2016-08-11 06:52:58', 1, 'De Humo', '12345678A', 'Catalunya');
