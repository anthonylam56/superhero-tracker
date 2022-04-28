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
DROP DATABASE IF EXISTS `supersightings`;
CREATE SCHEMA IF NOT EXISTS `supersightings` DEFAULT CHARACTER SET utf8 ;
USE `supersightings` ;

-- -----------------------------------------------------
-- Table `supersightings`.`Supers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightings`.`Supers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `descr` VARCHAR(145) NULL,
  `hero` TINYINT NULL DEFAULT 1,
  `photos` VARCHAR(65) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `supersightings`.`Powers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightings`.`Powers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `descr` VARCHAR(145) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `supersightings`.`Powers_has_Super`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightings`.`Powers_has_Super` (
  `Powers_id` INT NOT NULL,
  `Super_id` INT NOT NULL,
  PRIMARY KEY (`Powers_id`, `Super_id`),
  INDEX `fk_Powers_has_Super_Super1_idx` (`Super_id` ASC) VISIBLE,
  INDEX `fk_Powers_has_Super_Powers_idx` (`Powers_id` ASC) VISIBLE,
  CONSTRAINT `fk_Powers_has_Super_Powers`
    FOREIGN KEY (`Powers_id`)
    REFERENCES `supersightings`.`Powers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Powers_has_Super_Super1`
    FOREIGN KEY (`Super_id`)
    REFERENCES `supersightings`.`Supers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `supersightings`.`Organizations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightings`.`Organizations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `descr` VARCHAR(145) NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `supersightings`.`Super_has_Organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightings`.`Super_has_Organization` (
  `Super_id` INT NOT NULL,
  `Organization_id` INT NOT NULL,
  PRIMARY KEY (`Super_id`, `Organization_id`),
  INDEX `fk_Super_has_Organization_Organization1_idx` (`Organization_id` ASC) VISIBLE,
  INDEX `fk_Super_has_Organization_Super1_idx` (`Super_id` ASC) VISIBLE,
  CONSTRAINT `fk_Super_has_Organization_Super1`
    FOREIGN KEY (`Super_id`)
    REFERENCES `supersightings`.`Supers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Super_has_Organization_Organization1`
    FOREIGN KEY (`Organization_id`)
    REFERENCES `supersightings`.`Organizations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `supersightings`.`Locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightings`.`Locations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `descr` VARCHAR(145) NULL,
  `address` VARCHAR(45) NOT NULL,
  `latitude` DECIMAL(12,6) NOT NULL,
  `longitude` DECIMAL(12,6) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `supersightings`.`Sightings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `supersightings`.`Sightings` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `location_id` INT,
  `super_id` INT,
  PRIMARY KEY (`id`),
  INDEX `location_id_idx` (`location_id` ASC) VISIBLE,
  INDEX `super_idx` (`super_id` ASC) VISIBLE,
  CONSTRAINT `location`
    FOREIGN KEY (`location_id`)
    REFERENCES `supersightings`.`Locations` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `super`
    FOREIGN KEY (`super_id`)
    REFERENCES `supersightings`.`Supers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO supers (name,descr) VALUES("Superman","Man of steel");
INSERT INTO supers (name,descr) VALUES("Spiderman","Friendly neighborhood");
INSERT INTO supers (name,descr) VALUES("Batman","Dark knight");
INSERT INTO supers (name,descr) VALUES("Wonder Women","Princess of Themscira");
INSERT INTO supers (name,descr) VALUES("Wasp","Stings worse than a bee");
INSERT INTO supers (name,descr) VALUES("Aquaman","All wet");

INSERT INTO organizations (name,descr,address) VALUES ("The Avengers","It sounds dramatic","Avengers Tower");
INSERT INTO organizations (name,descr,address) VALUES ("Justice League","Gathering of Heroes","Secret Sanctuary");
INSERT INTO organizations (name,descr,address) VALUES ("The Defenders","The non-team","Sanctum Sanctorum");
INSERT INTO organizations (name,descr,address) VALUES ("Fantastic Four","Four against Doom","Baxter Building");

INSERT INTO powers (name,descr) VALUES ("Immeasurable Speed","Too fast to see");
INSERT INTO powers (name,descr) VALUES ("Heat Vision","Melt you with a look");
INSERT INTO powers (name,descr) VALUES ("Flight","Even in space");
INSERT INTO powers (name,descr) VALUES ("Spider-Sense","He'll feel you coming");
INSERT INTO powers (name,descr) VALUES ("Web-shooters","A sticky situation");
INSERT INTO powers (name,descr) VALUES ("Superhuman strength","Need a lift");
INSERT INTO powers (name,descr) VALUES ("Genius level Intelligence","Sharp as a tack");
INSERT INTO powers (name,descr) VALUES ("Master Martial Artist","Like Chuck Norris");
INSERT INTO powers (name,descr) VALUES ("Vast Wealth","Money buys almost anything");
INSERT INTO powers (name,descr) VALUES ("Telepathy","Won't see my lips move");
INSERT INTO powers (name,descr) VALUES ("Size Control","Big or small");
INSERT INTO powers (name,descr) VALUES ("Insect Control","New meaning to bugging you");
INSERT INTO powers (name,descr) VALUES ("Hydrokinesis","Water can hurt");
INSERT INTO powers (name,descr) VALUES ("Marine Telepathy","Whale of a good time");


INSERT INTO locations (name,descr,address,latitude,longitude) VALUES ("The Groves","Nice area","Mesa, AZ",33.432102,-111.737565);
INSERT INTO locations (name,descr,address,latitude,longitude) VALUES ("LA County","Most populated","LA, Ca",34.016972,-117.993939);
INSERT INTO locations (name,descr,address,latitude,longitude) VALUES ("Silver Hill","Old town","Albuquerque, NM",35.078339,-106.611615);
INSERT INTO locations (name,descr,address,latitude,longitude) VALUES ("The Strip","Sin City","Las Vegas, NV",36.106248,-115.212821);

INSERT INTO sightings (date,location_id,super_id) VALUES ('2022-1-22 12:22:36',1,5);
INSERT INTO sightings (date,location_id,super_id) VALUES ('2022-1-22 12:22:36',1,2);
INSERT INTO sightings (date,location_id,super_id) VALUES ('2022-2-22 22:22:36',2,3);
INSERT INTO sightings (date,location_id,super_id) VALUES ('2021-11-22 15:22:36',3,4);
INSERT INTO sightings (date,location_id,super_id) VALUES ('2022-2-22 22:22:36',2,6);
INSERT INTO sightings (location_id,super_id) VALUES (4,1);

INSERT INTO powers_has_super (powers_id,super_id) VALUES ("1","1");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("2","1");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("3","1");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("6","1");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("4","2");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("5","2");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("6","2");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("7","3");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("8","3");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("9","3");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("6","4");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("7","4");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("10","4");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("8","5");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("11","5");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("12","5");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("7","6");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("6","6");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("13","6");
INSERT INTO powers_has_super (powers_id,super_id) VALUES ("14","6");

INSERT INTO super_has_organization (organization_id,super_id) VALUES ("2","1");
INSERT INTO super_has_organization (organization_id,super_id) VALUES ("1","2");
INSERT INTO super_has_organization (organization_id,super_id) VALUES ("4","2");
INSERT INTO super_has_organization (organization_id,super_id) VALUES ("2","3");
INSERT INTO super_has_organization (organization_id,super_id) VALUES ("2","4");
INSERT INTO super_has_organization (organization_id,super_id) VALUES ("1","5");
INSERT INTO super_has_organization (organization_id,super_id) VALUES ("3","5");
INSERT INTO super_has_organization (organization_id,super_id) VALUES ("2","6");