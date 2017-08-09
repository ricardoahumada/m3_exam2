-- phpMyAdmin SQL Dump
-- version 4.0.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 11, 2016 at 05:34 PM
-- Server version: 5.6.11-log
-- PHP Version: 5.4.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `dehumo`
--
CREATE DATABASE IF NOT EXISTS `dehumo` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `dehumo`;

-- --------------------------------------------------------

--
-- Table structure for table `action`
--

CREATE TABLE IF NOT EXISTS `action` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(128) NOT NULL,
  `detail` varchar(256) NOT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `canal`
--

CREATE TABLE IF NOT EXISTS `canal` (
  `chid` int(11) NOT NULL AUTO_INCREMENT,
  `tabaco` int(11) NOT NULL,
  `carga` int(11) NOT NULL,
  `stock_maximo` int(11) NOT NULL,  
  `modif_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`chid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `cliente`
--

CREATE TABLE IF NOT EXISTS `cliente` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `propietario` int(11) NOT NULL,
  `nombre` varchar(128) NOT NULL,
  `direccion` varchar(256) NOT NULL,
  `telefono` int(11) NOT NULL,
  `movil` int(11) NOT NULL,
  `email` varchar(256) NOT NULL,
  `creationdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `empresa` varchar(256) NOT NULL,
  `NIF` varchar(128) NOT NULL,
  `pais` varchar(256) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Table structure for table `machaction`
--

CREATE TABLE IF NOT EXISTS `machaction` (
  `mid` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `action` int(11) NOT NULL,
  `detail` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `maquina`
--

CREATE TABLE IF NOT EXISTS `maquina` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `cliente` int(11) NOT NULL,
  `propietario` int(11) NOT NULL,
  `marca` varchar(128) NOT NULL,
  `modelo` varchar(128) NOT NULL,
  `num_serie` varchar(256) NOT NULL,
  `canales` varchar(256) NOT NULL,
  `estado` int(11) NOT NULL,
  `modif_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`mid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Table structure for table `tabaco`
--

CREATE TABLE IF NOT EXISTS `tabaco` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `marca` varchar(128) NOT NULL,
  `modelo` varchar(128) NOT NULL,
  `num_serie` varchar(256) NOT NULL,
  `precio` float NOT NULL,
  `modif_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`tid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_action`
--

CREATE TABLE IF NOT EXISTS `user_action` (
  `uid` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `action` int(11) NOT NULL,
  `detail` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(128) NOT NULL,
  `apellido` varchar(128) NOT NULL,
  `email` varchar(256) NOT NULL,
  `pass` varchar(256) NOT NULL,
  `creationdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) NOT NULL DEFAULT '0',
  `Empresa` varchar(256) NOT NULL,
  `NIF` varchar(128) NOT NULL,
  `pais` varchar(128) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;
