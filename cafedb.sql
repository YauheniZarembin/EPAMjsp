-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: cafedb
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bank_info`
--

DROP TABLE IF EXISTS `bank_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bank_info` (
  `card_number` varchar(4) NOT NULL COMMENT 'номер карты',
  `password` varchar(45) DEFAULT NULL COMMENT 'пароль',
  `amount` decimal(10,0) unsigned NOT NULL COMMENT 'количество денег на карте',
  PRIMARY KEY (`card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='это так называемый банк.  сдесь храниться номера карточки с паролем и количество денег на нём. Чтобы проверять при регистрации существует ли такая карта. И для перевода денег  с карты на счёт на сайте.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank_info`
--

LOCK TABLES `bank_info` WRITE;
/*!40000 ALTER TABLE `bank_info` DISABLE KEYS */;
INSERT INTO `bank_info` VALUES ('1010','1010',6435),('1111','1111',3422),('1212','1212',5647),('1313','1313',32),('1414','1414',55),('1515','1515',43),('1616','1616',535),('1717','1717',43),('1818','1818',55),('1919','1919',435),('2020','2020',32253),('2121','2121',542),('2222','2222',3635),('2323','2323',563),('2424','2424',5464),('2525','2525',546),('2626','2626',43265),('2727','2727',7465),('2828','2828',8756),('2929','2929',567),('3030','3030',34),('3333','3333',3),('4444','4444',545),('5555','5555',3),('6666','6666',543),('7777','7777',233),('8888','8888',86),('9999','9999',32);
/*!40000 ALTER TABLE `bank_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menu` (
  `dish_name` varchar(45) NOT NULL COMMENT 'имя блюда (оно уникальное)',
  `type_of_dish` enum('dessert','soup','drink','side_dish','basic') NOT NULL COMMENT 'тип блюда(десерт , горячее , суп, и так далее)',
  `price` decimal(10,0) unsigned NOT NULL COMMENT 'цена за 1 порцию',
  `cooking_time` time NOT NULL COMMENT 'время приготовления 1 порции.',
  `max_number_of_servings` int(10) unsigned NOT NULL COMMENT 'максимальное количество порций блюда, которое можно заказать',
  `image_path` varchar(150) NOT NULL,
  PRIMARY KEY (`dish_name`),
  UNIQUE KEY `DishName_UNIQUE` (`dish_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Здесь информации о всех блюдах, которые можно заказать.\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES ('biscuit','dessert',3,'00:20:33',75,'\\resource\\image\\biscuit.jpg'),('black forest','dessert',9,'00:25:00',43,'\\resource\\image\\blackForest.jpg'),('borscht','soup',2,'00:15:00',100,'\\resource\\image\\borsch.jpg'),('buckwheat','side_dish',3,'00:25:00',322,'\\resource\\image\\buckwheat.jpg'),('cabbage','side_dish',3,'00:15:00',344,'\\resource\\image\\cabbage.jpg'),('coca-cola','drink',1,'00:03:00',1000,'\\resource\\image\\coca-cola.jpg'),('cutlet','basic',4,'00:20:00',72,'\\resource\\image\\cutlet.jpg'),('eclairs','dessert',3,'00:30:00',65,'\\resource\\image\\eclair.jpg'),('fanta','drink',1,'00:03:00',999,'\\resource\\image\\fanta.jpg'),('fillet','basic',9,'00:20:00',102,'\\resource\\image\\fillet.jpg'),('ice cream','dessert',4,'00:15:00',43,'\\resource\\image\\icecream.jpg'),('jelly','dessert',5,'00:23:00',76,'\\resource\\image\\jelly.jpg'),('meatballs','basic',7,'00:23:30',34,'\\resource\\image\\meatballs.jpg'),('minestrone','soup',1,'00:10:00',100,'\\resource\\image\\minestore.jpg'),('napoleon','dessert',5,'00:30:00',34,'\\resource\\image\\napoleon.jpg'),('pasta','side_dish',4,'00:10:00',233,'\\resource\\image\\pasta.jpg'),('potatoes','side_dish',3,'00:12:00',100,'\\resource\\image\\potatoes.jpg'),('rassolnik','soup',2,'00:10:00',150,'\\resource\\image\\rassolnik.jpg'),('rice','side_dish',2,'00:25:00',553,'\\resource\\image\\rice.jpg'),('sausage','basic',3,'00:05:00',328,'\\resource\\image\\sausage.jpg'),('smoothies','drink',3,'00:10:00',100,'\\resource\\image\\smoothies.jpg'),('sprite','drink',1,'00:03:00',888,'\\resource\\image\\sprite.jpg'),('water','drink',1,'00:01:00',2323,'\\resource\\image\\water.jpg');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id_order` int(10) unsigned NOT NULL,
  `user_name` varchar(45) NOT NULL COMMENT 'имя пользователя , который делает заказ',
  `date_of_receiving` datetime DEFAULT NULL,
  `is_cash_payment` tinyint(1) NOT NULL COMMENT 'наличный или безналичный расчёт',
  PRIMARY KEY (`id_order`),
  KEY `user_name_idx` (`user_name`),
  CONSTRAINT `user_name1` FOREIGN KEY (`user_name`) REFERENCES `personal_info` (`user_name`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Информация о всех заказах';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (2,'hurinovich','2027-12-06 23:00:00',1),(3,'hurunovich','2027-12-05 18:00:00',0),(4,'ivanov','2027-12-06 07:30:00',1),(5,'kaminskaya','2027-12-06 00:00:00',0),(6,'nevar','2027-12-06 23:45:00',0),(7,'omelusik','2027-12-06 00:00:00',0),(10,'borisenko','2027-12-06 00:00:00',1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_info`
--

DROP TABLE IF EXISTS `personal_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personal_info` (
  `user_name` varchar(45) NOT NULL COMMENT 'уникальное имя пользователя',
  `password` varchar(45) NOT NULL,
  `is_admin` tinyint(1) NOT NULL,
  `is_ban` tinyint(1) DEFAULT NULL,
  `name` varchar(45) NOT NULL COMMENT 'имя (обязательно)',
  `last_name` varchar(45) NOT NULL COMMENT 'фамилия (обязательно)',
  `loyalty_points` int(10) unsigned DEFAULT NULL COMMENT 'количество очков лояльность ( их добавляет и убавляет администратор)\nесли их очень мало , то пользователь может быть забанен\n( для администратора NULL)',
  `money` decimal(10,0) unsigned DEFAULT NULL COMMENT 'количество денег на счёте на сайте.( для администратора NULL)',
  `e-mail` varchar(45) NOT NULL COMMENT 'электронная почта (обязательно)',
  `number_of_orders` int(11) DEFAULT NULL COMMENT 'количество заказов сделанных на сайте (например за каждые 10 очков +1 балл лояльности)',
  `card_number` varchar(4) DEFAULT NULL COMMENT 'номер карты пользователя',
  PRIMARY KEY (`user_name`),
  UNIQUE KEY `e-mail_UNIQUE` (`e-mail`),
  UNIQUE KEY `card_number_UNIQUE` (`card_number`),
  CONSTRAINT `card_number` FOREIGN KEY (`card_number`) REFERENCES `bank_info` (`card_number`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='личная информация о каждом пользователе';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_info`
--

LOCK TABLES `personal_info` WRITE;
/*!40000 ALTER TABLE `personal_info` DISABLE KEYS */;
INSERT INTO `personal_info` VALUES ('basia','basia',0,0,'B','Kuskova',0,0,'aaa@aa.com',0,'2929'),('bazanov','qwe1',1,0,'Ivan','Bazanov',NULL,NULL,'bazanov@gmail.com',NULL,NULL),('borisenko','qwe2',0,0,'Boris','Borisenko',4,323,'borisenko@gmail.com',34,'9999'),('eylashevich','qwe3',0,0,'Maxim','Eylashevich',22,992,'eylashevich@gmail.com',3,'3333'),('german','qwe4',0,0,'Polina','German',0,43,'german@gmail.com',53,'7777'),('gtr4gt','4gt4',0,0,'etr4t','g5454g',0,0,'rgtn@mail.com',0,'2828'),('hurinovich','qwe5',0,1,'Maxim','Hurinovich',10,100,'hurinovich@gmail.com',10,'1111'),('ivanov','qwe6',0,0,'Ivan','Ivanov',14,242,'ivanov@gmail.com',23,'1313'),('kaminskaya','qwe7',0,0,'Tanya','Kaminskaya',32,23,'kaminskaya@gmail.com',2,'8888'),('merkis','qwe8',0,1,'Pavel','Merkis',0,32,'merkis@gmail.com',43,'4444'),('nevar','qwe9',0,0,'Marina','Nevar',10,232,'nevar@gmail.com',54,'1212'),('omelusik','qwe10',0,0,'Dmitrey','Omelusik',12,23,'omelusik@gmail.com',9,'2222'),('qqq','qqqblinov',0,0,'qqq','qqq',0,0,'qqq@mail.ru',0,'2626'),('rak','qwe11',0,1,'Aleksei','Rak',1,5343,'rak@gmail.com',34,'6666'),('sechko','qwe12',0,0,'Marina','Sechko',15,432,'sechko@gmail.com',23,'1010'),('tkachov','qwe13',0,0,'Ivan','Tkachov',9,233,'tkachov@gmail.com',22,'5555'),('user','qwe1111',0,0,'user','user',0,0,'user@gmail.com',0,'2727'),('zarembin','qwe14',1,0,'Eugeni','Zarembin',NULL,NULL,'zarembin@gmail.com',NULL,NULL);
/*!40000 ALTER TABLE `personal_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `preparing_dishes`
--

DROP TABLE IF EXISTS `preparing_dishes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `preparing_dishes` (
  `id_order` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_preparing_dish` int(11) NOT NULL COMMENT 'id для первичного ключа',
  `dish_name` varchar(30) NOT NULL COMMENT 'имя блюда',
  `number_of_servings` tinyint(4) NOT NULL COMMENT 'количество порций',
  PRIMARY KEY (`id_order`,`id_preparing_dish`),
  KEY `dish_name_idx` (`dish_name`),
  KEY `id_order_idx` (`id_order`),
  CONSTRAINT `dish_name` FOREIGN KEY (`dish_name`) REFERENCES `menu` (`dish_name`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `order` FOREIGN KEY (`id_order`) REFERENCES `orders` (`id_order`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='здесь находиться ифнормация о всех блюдах , которые находятся на стадии приготовления';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `preparing_dishes`
--

LOCK TABLES `preparing_dishes` WRITE;
/*!40000 ALTER TABLE `preparing_dishes` DISABLE KEYS */;
INSERT INTO `preparing_dishes` VALUES (2,1,'biscuit',1),(2,2,'black forest',2),(3,3,'borsht',1),(3,4,'cabbage',5),(4,5,'coca-cola',1),(4,6,'pasta',1),(5,7,'jelly',9),(5,8,'pasta',1),(6,9,'minestrone',1),(6,10,'sprite',1),(7,11,'meatballs',3),(7,12,'jelly',2),(10,13,'cutlet',2),(10,14,'eclairs',3);
/*!40000 ALTER TABLE `preparing_dishes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reviews` (
  `id_review` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id отзыва',
  `user_name` varchar(45) NOT NULL COMMENT 'имя пользователя, который оставил отзыв',
  `mark` tinyint(4) DEFAULT NULL COMMENT 'оценка заказа ( можно и не ставить)',
  `text_review` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_review`,`user_name`),
  UNIQUE KEY `id_review_UNIQUE` (`id_review`),
  KEY `id_review_idx` (`user_name`),
  CONSTRAINT `id_review` FOREIGN KEY (`user_name`) REFERENCES `personal_info` (`user_name`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8 COMMENT='таблица отзывов  ( связа с человеком , то есть человек может найти отзывы которые он оставлял)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (106,'hurinovich',10,'Хорошее кафе'),(107,'rak',7,'Не очень');
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-22 23:20:11
