
show databases;
create database artonline;
use artonline;
show tables;
use artonline;
CREATE TABLE address (
  plot_no varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  street varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  address_id int(11) NOT NULL AUTO_INCREMENT,
  city varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  pin varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  state varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (address_id)
)

use artonline;
CREATE TABLE Artist(
    artist_id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(16) NOT NULL,
    last_name VARCHAR(32),
    email_id VARCHAR(32) NOT NULL,
    painting_style VARCHAR(10) NOT NULL,
    experience INT,
    license VARCHAR(20),
    PRIMARY KEY (artist_id)
);
use artonline;
insert into artist values(10,'kaosh','smit', 'kaoshsmith@gmail.com', 'watercolor', 5,'qwe1102');
use artonline;
CREATE TABLE `user` (
  `first_name` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `password` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`)
) 

use artonline;
CREATE TABLE `addressOfUser` (
  `address_type` varchar(122) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  `address_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`address_id`),
  KEY `address_id` (`address_id`),
  CONSTRAINT `addressOfUser_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `addressOfUser_ibfk_2` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`)
)

use artonline;
CREATE TABLE `paintings` (
  `painting_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(48) COLLATE utf8mb4_unicode_ci NOT NULL,
  `artist_name` varchar(48) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_framed` int(11) NOT NULL,
  `length` int(11) NOT NULL,
  `breadth` int (11) NOT NULL,
  `artist_id` int(11),
  `price` decimal(8,2) NOT NULL,
   CONSTRAINT `paitings_ibfk_1` FOREIGN KEY (`artist_id`) REFERENCES `artist` (`artist_id`),
  PRIMARY KEY (`painting_id`)
);



use artonline;
select * from paintings;


use artonline;
CREATE TABLE `gallery` (
  `gallery_id` int(11) NOT NULL AUTO_INCREMENT,
  `gallery_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `license` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address_id` int(11) NOT NULL,
  `email_id` varchar(48) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`gallery_id`),
  KEY `address_id` (`address_id`),
  CONSTRAINT `gallery_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`)
) 

use artonline;
select * from orders ;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_status` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `order_date` date NOT NULL,
  `order_time` time NOT NULL,
  `bill_amount` decimal(8,2) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `gallery_id` (`gallery_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
)



use artonline;
CREATE TABLE `galleryInventory` (
  `painting_id` int(11) NOT NULL AUTO_INCREMENT,
  `gallery_id` int(11) NOT NULL,
  `batch_id` int(11) ,
  PRIMARY KEY (`painting_id`),
  KEY `gallery_id` (`gallery_id`),
  KEY `batch_id` (`batch_id`),
  CONSTRAINT `galleryInventory_ibfk_1` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`),
  CONSTRAINT `galleryInventory_ibfk_2` FOREIGN KEY (`batch_id`) REFERENCES `paintingBatch` (`batch_id`)
)

use artonline;
CREATE TABLE `paintingsOrdered` (
  `price` decimal(7,2) NOT NULL,
  `user_id` int(11) NOT NULL,
  `painting_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`painting_id`),
  KEY `painting_id` (`painting_id`),
  CONSTRAINT `paintingsOrdered_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) 



use artonline;
select * from paintingsordered;

use artonline;
CREATE TABLE `owner` (
  `user_id` int(11) NOT NULL,
  `gallery_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`gallery_id`),
  KEY `gallery_id` (`gallery_id`),
  CONSTRAINT `owner_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `owner_ibfk_2` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`) ON DELETE CASCADE
)

use artonline;
CREATE TABLE `reviews` (
  `comment` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL,
  `rating` decimal(3,2) NOT NULL,
  `review_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  PRIMARY KEY (`review_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE
) 
use artonline;
describe reviews;

use artonline;
CREATE TABLE `galleryPhone` (
  `phone_no` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gallery_id` int(11) NOT NULL,
  PRIMARY KEY (`phone_no`,`gallery_id`),
  KEY `gallery_id` (`gallery_id`),
  CONSTRAINT `galleryPhone_ibfk_1` FOREIGN KEY (`gallery_id`) REFERENCES `gallery` (`gallery_id`) ON DELETE CASCADE
) 

use artonline;
CREATE TABLE `userPhone` (
  `phone_no` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`phone_no`,`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `userPhone_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
)

use artonline;
show tables;

use artonline;
desc address;

use artonline;
desc addressofuser;

use artonline;
desc artist;

use artonline;
desc gallery;

use artonline;
desc galleryinventory;

use artonline;
desc galleryphone;

use artonline;
desc orders;

use artonline;
desc owner;

use artonline;
desc paintings;

use artonline;
desc paintingsordered;

use artonline;
desc reviews;

use artonline;
desc user;

use artonline;
desc userphone;

