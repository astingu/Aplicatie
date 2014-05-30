create database if not exists Application;
use Application;

create table if not exists Movies (
	id INT PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(100),
	year INT,
	url  VARCHAR(100),
	poster varchar(200),
	genre varchar(100),
	actors varchar(200),
	plot varchar(200),
	country varchar(200),
	currentDate TIMESTAMP
);