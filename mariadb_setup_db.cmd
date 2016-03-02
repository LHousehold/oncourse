
CREATE DATABASE IF NOT EXISTS oncourse;

use oncourse;

CREATE TABLE IF NOT EXISTS course_package
(
id int AUTO_INCREMENT,
cpid int NOT NULL,
media_type varchar(20) NOT NULL,
page int NOT NULL,
source varchar(255) NOT NULL,
location varchar(10),
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS course_package_section
(
id int AUTO_INCREMENT,
cpid int NOT NULL,
section_index decimal(6,2) NOT NULL,
section_name varchar(255) NOT NULL,
section_type varchar(100) NOT NULL,
page_number int NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
(
id int AUTO_INCREMENT,
user_name varchar(255) NOT NULL,
email varchar(255) NOT NULL,
type varchar(10) DEFAULT 'student',
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS course_package_name
(
id int AUTO_INCREMENT,
name varchar(255) NOT NULL,
course_code varchar(255) NOT NULL,
cpid int NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_save
(
id int AUTO_INCREMENT,
uid int NOT NULL,
cpid int NOT NULL,
page int NOT NULL DEFAULT 1,
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_permission
(
id int AUTO_INCREMENT,
uid int NOT NULL,
cpid int NOT NULL,
permission_level varchar(255) NOT NULL DEFAULT 'read',
PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS files
(
id int AUTO_INCREMENT,
cpid int NOT NULL,
name varchar(255) NOT NULL,
source varchar(255) NOT NULL,
media_type varchar(20) NOT NULL,
PRIMARY KEY (id)
);

GRANT ALL PRIVILEGES ON oncourse.* TO 'oc'@'localhost';
