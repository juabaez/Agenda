CREATE DATABASE  IF NOT EXISTS `prueba`;
DROP TABLE IF EXISTS `contacto`;
CREATE TABLE `contacto` (
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `telefono` varchar(45) DEFAULT NULL,
  `direccion` varchar(45) DEFAULT NULL,
  `nameUser` varchar(45) NOT NULL,
  `idcontacto` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idcontacto`),
  KEY `contacto_idx` (`nameUser`),
  CONSTRAINT `contacto` FOREIGN KEY (`nameUser`) REFERENCES `user` (`usuario`) ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `usuario` varchar(25) NOT NULL,
  `pass` varchar(45) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`usuario`)
);
