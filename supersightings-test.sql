-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `supersightingstest`;
CREATE SCHEMA IF NOT EXISTS `supersightingstest` DEFAULT CHARACTER SET utf8 ;
USE `supersightingstest` ;

-- -----------------------------------------------------
-- Table `supersightingstest`.`Supers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightingstest`.`Supers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `descr` VARCHAR(145) NULL,
  `hero` TINYINT NULL DEFAULT 1,
  `photos` VARCHAR(65) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `supersightingstest`.`Powers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightingstest`.`Powers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `descr` VARCHAR(145) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `supersightingstest`.`Powers_has_Super`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightingstest`.`Powers_has_Super` (
  `Powers_id` INT NOT NULL,
  `Super_id` INT NOT NULL,
  PRIMARY KEY (`Powers_id`, `Super_id`),
  INDEX `fk_Powers_has_Super_Super1_idx` (`Super_id` ASC) VISIBLE,
  INDEX `fk_Powers_has_Super_Powers_idx` (`Powers_id` ASC) VISIBLE,
  CONSTRAINT `fk_Powers_has_Super_Powers`
    FOREIGN KEY (`Powers_id`)
    REFERENCES `supersightingstest`.`Powers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Powers_has_Super_Super1`
    FOREIGN KEY (`Super_id`)
    REFERENCES `supersightingstest`.`Supers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `supersightingstest`.`Organizations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightingstest`.`Organizations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `descr` VARCHAR(145) NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `supersightingstest`.`Super_has_Organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightingstest`.`Super_has_Organization` (
  `Super_id` INT NOT NULL,
  `Organization_id` INT NOT NULL,
  PRIMARY KEY (`Super_id`, `Organization_id`),
  INDEX `fk_Super_has_Organization_Organization1_idx` (`Organization_id` ASC) VISIBLE,
  INDEX `fk_Super_has_Organization_Super1_idx` (`Super_id` ASC) VISIBLE,
  CONSTRAINT `fk_Super_has_Organization_Super1`
    FOREIGN KEY (`Super_id`)
    REFERENCES `supersightingstest`.`Supers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Super_has_Organization_Organization1`
    FOREIGN KEY (`Organization_id`)
    REFERENCES `supersightingstest`.`Organizations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `supersightingstest`.`Locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightingstest`.`Locations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `descr` VARCHAR(145) NULL,
  `address` VARCHAR(45) NOT NULL,
  `latitude` DECIMAL(12,6) NOT NULL,
  `longitude` DECIMAL(12,6) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `supersightingstest`.`Sightings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightingstest`.`Sightings` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `location_id` INT,
  `super_id` INT,
  PRIMARY KEY (`id`),
  INDEX `location_id_idx` (`location_id` ASC) VISIBLE,
  INDEX `super_idx` (`super_id` ASC) VISIBLE,
  CONSTRAINT `location`
    FOREIGN KEY (`location_id`)
    REFERENCES `supersightingstest`.`Locations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `super`
    FOREIGN KEY (`super_id`)
    REFERENCES `supersightingstest`.`Supers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

