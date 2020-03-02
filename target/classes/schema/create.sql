CREATE DATABASE IF NOT EXISTS KthModbusSecurityDb;

USE KthModbusSecurityDb;

CREATE TABLE IF NOT EXISTS `Users` (
    `userId` bigint(20) NOT NULL AUTO_INCREMENT,
    `email` varchar(255) UNIQUE NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`userId`)
);