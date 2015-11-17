
CREATE DATABASE IF NOT EXISTS oncourse;

use oncourse;

CREATE TABLE IF NOT EXISTS course_package
(
id int NOT NULL,
cpid int NOT NULL,
media_type varchar(20) NOT NULL,
page int NOT NULL,
source varchar(255) NOT NULL,
location varchar(10)
);

CREATE TABLE IF NOT EXISTS users
(
id int NOT NULL,
user_name varchar(255) NOT NULL,
email varchar(255) NOT NULL,
type varchar(10) DEFAULT 'student'
);

CREATE TABLE IF NOT EXISTS course_package_name
(
id int NOT NULL,
name varchar(255) NOT NULL,
course_code varchar(255)
);

CREATE TABLE IF NOT EXISTS user_save
(
id int NOT NULL,
uid int NOT NULL,
cpid int NOT NULL,
page int NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS user_permission
(
id int NOT NULL,
uid int NOT NULL,
cpid int NOT NULL,
permission_level varchar(255) NOT NULL DEFAULT 'read'
);

GRANT ALL PRIVILEGES ON oncourse.* TO 'oc'@'localhost';

