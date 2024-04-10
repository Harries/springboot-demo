CREATE DATABASE IF NOT EXISTS `BOOK_API_DATA`;
USE `BOOK_API_DATA`;

CREATE TABLE IF NOT EXISTS `Book` (
    `id` int(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `pageCount` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `Index_name` (`name`)
    ) ENGINE=InnoDB AUTO_INCREMENT=235 DEFAULT CHARSET=utf8;

CREATE TABLE `Author` (
     `id` INT(20) NOT NULL AUTO_INCREMENT,
     `firstName` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
     `lastName` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
     `bookId` INT(20) NULL DEFAULT NULL,
     PRIMARY KEY (`id`) USING BTREE,
     UNIQUE INDEX `Index_name` (`firstName`) USING BTREE,
     INDEX `FK_Author_Book` (`bookId`) USING BTREE,
     CONSTRAINT `FK_Author_Book` FOREIGN KEY (`bookId`) REFERENCES `BOOK_API_DATA`.`Book` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
    COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=6
;



INSERT INTO `Book` (`id`, `name`, `pageCount`) VALUES (1, 'the golden ticket', '255');
INSERT INTO `Book` (`id`, `name`, `pageCount`) VALUES (2, 'coding game', '300');

INSERT INTO `Author` (`id`, `firstName`, `LastName`, `bookId`) VALUES (4, 'Brendon', 'Bouchard', 1);
INSERT INTO `Author` (`id`, `firstName`, `LastName`, `bookId`) VALUES (5, 'John', 'Doe', 2);
