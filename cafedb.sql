-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema cafedb
-- -----------------------------------------------------
-- Эта база  данных иллюстрирует работу сайта кафе. Она хранит информацию о пользователе(его личный данные, информацию о банковской карте, роль). Также база данных хранит меню всех блюд с ценой , временем приготовления и типом блюда.  Хранится информация о всех заказах ( куда , когда , наличный/безналичный расчёт) и отзывах, которые может оставлять пользователь.

-- -----------------------------------------------------
-- Schema cafedb
--
-- Эта база  данных иллюстрирует работу сайта кафе. Она хранит информацию о пользователе(его личный данные, информацию о банковской карте, роль). Также база данных хранит меню всех блюд с ценой , временем приготовления и типом блюда.  Хранится информация о всех заказах ( куда , когда , наличный/безналичный расчёт) и отзывах, которые может оставлять пользователь.
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cafedb` DEFAULT CHARACTER SET utf8 ;
USE `cafedb` ;

-- -----------------------------------------------------
-- Table `cafedb`.`bank_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cafedb`.`bank_info` (
  `card_number` VARCHAR(4) NOT NULL COMMENT 'номер карты',
  `password` VARCHAR(45) NULL COMMENT 'пароль',
  `amount` DECIMAL UNSIGNED NOT NULL COMMENT 'количество денег на карте',
  PRIMARY KEY (`card_number`))
ENGINE = InnoDB
COMMENT = 'это так называемый банк.  сдесь храниться номера карточки с паролем и количество денег на нём. Чтобы проверять при регистрации существует ли такая карта. И для перевода денег  с карты на счёт на сайте.';


-- -----------------------------------------------------
-- Table `cafedb`.`personal_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cafedb`.`personal_info` (
  `user_name` VARCHAR(45) NOT NULL COMMENT 'уникальное имя пользователя',
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL COMMENT 'имя (обязательно)',
  `last_name` VARCHAR(45) NOT NULL COMMENT 'фамилия (обязательно)',
  `id_admin` TINYINT(1) NOT NULL,
  `id_ban` TINYINT(1) NOT NULL,
  `loyalty_points` INT UNSIGNED NULL COMMENT 'количество очков лояльность ( их добавляет и убавляет администратор)\nесли их очень мало , то пользователь может быть забанен\n( для администратора NULL)',
  `money` DECIMAL UNSIGNED NULL COMMENT 'количество денег на счёте на сайте.( для администратора NULL)',
  `e-mail` VARCHAR(45) NOT NULL COMMENT 'электронная почта (обязательно)',
  `number_of_orders` INT NULL COMMENT 'количество заказов сделанных на сайте (например за каждые 10 очков +1 балл лояльности)',
  `card_number` VARCHAR(4) NULL COMMENT 'номер карты пользователя',
  PRIMARY KEY (`user_name`),
  UNIQUE INDEX `e-mail_UNIQUE` (`e-mail` ASC),
  UNIQUE INDEX `card_number_UNIQUE` (`card_number` ASC),
  CONSTRAINT `card_number`
    FOREIGN KEY (`card_number`)
    REFERENCES `cafedb`.`bank_info` (`card_number`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'личная информация о каждом пользователе';


-- -----------------------------------------------------
-- Table `cafedb`.`menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cafedb`.`menu` (
  `dish_name` VARCHAR(45) NOT NULL COMMENT 'имя блюда (оно уникальное)',
  `type_of_dish` VARCHAR(45) NOT NULL COMMENT 'тип блюда(десерт , горячее , суп, и так далее)',
  `price` DECIMAL UNSIGNED NOT NULL COMMENT 'цена за 1 порцию',
  `cooking_time` TIME NOT NULL COMMENT 'время приготовления 1 порции.',
  `max_number_of_servings` INT UNSIGNED NOT NULL COMMENT 'максимальное количество порций блюда, которое можно заказать',
  PRIMARY KEY (`dish_name`),
  UNIQUE INDEX `DishName_UNIQUE` (`dish_name` ASC))
ENGINE = InnoDB
COMMENT = 'Здесь информации о всех блюдах, которые можно заказать.\n';


-- -----------------------------------------------------
-- Table `cafedb`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cafedb`.`orders` (
  `id_order` INT UNSIGNED NOT NULL,
  `date_of_receiving` DATETIME NOT NULL COMMENT 'дата получения заказа ( её устанавливает пользователь)',
  `user_name` VARCHAR(45) NOT NULL COMMENT 'имя пользователя , который делает заказ',
  `is_cash_payment` TINYINT(1) NOT NULL COMMENT 'наличный или безналичный расчёт',
  PRIMARY KEY (`id_order`),
  INDEX `user_name_idx` (`user_name` ASC),
  CONSTRAINT `user_name`
    FOREIGN KEY (`user_name`)
    REFERENCES `cafedb`.`personal_info` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Информация о всех заказах';


-- -----------------------------------------------------
-- Table `cafedb`.`reviews`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cafedb`.`reviews` (
  `id_review` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id отзыва',
  `user_name` VARCHAR(45) NOT NULL COMMENT 'имя пользователя, который оставил отзыв',
  `text_review` VARCHAR(45) NULL,
  `mark` TINYINT(4) NULL COMMENT 'оценка заказа ( можно и не ставить)',
  PRIMARY KEY (`id_review`, `user_name`),
  UNIQUE INDEX `id_review_UNIQUE` (`id_review` ASC),
  INDEX `id_review_idx` (`user_name` ASC),
  CONSTRAINT `id_review`
    FOREIGN KEY (`user_name`)
    REFERENCES `cafedb`.`personal_info` (`user_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'таблица отзывов о заказа ( связа с человеком , то есть человек может найти отзывы которые он оставлял)';


-- -----------------------------------------------------
-- Table `cafedb`.`preparing_dishes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cafedb`.`preparing_dishes` (
  `id_order` INT UNSIGNED NOT NULL COMMENT 'какому заказу принадлежит блюдо',
  `id_preparing_dish` INT NOT NULL COMMENT 'id для первичного ключа',
  `dish_name` VARCHAR(30) NOT NULL COMMENT 'имя блюда',
  `number_of_servings` TINYINT(4) NOT NULL COMMENT 'количество порций',
  PRIMARY KEY (`id_order`, `id_preparing_dish`),
  INDEX `dish_name_idx` (`dish_name` ASC),
  INDEX `id_order_idx` (`id_order` ASC),
  CONSTRAINT `dish_name`
    FOREIGN KEY (`dish_name`)
    REFERENCES `cafedb`.`menu` (`dish_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_order`
    FOREIGN KEY (`id_order`)
    REFERENCES `cafedb`.`orders` (`id_order`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'здесь находиться ифнормация о всех блюдах , которые находятся на стадии приготовления';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
