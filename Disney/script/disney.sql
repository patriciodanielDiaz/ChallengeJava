-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 27-06-2021 a las 23:24:14
-- Versión del servidor: 5.7.26
-- Versión de PHP: 7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET GLOBAL time_zone = "-03:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `disney`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `characters`
--

DROP TABLE IF EXISTS `characters`;
CREATE TABLE IF NOT EXISTS `characters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `age` tinyint(4) NOT NULL,
  `weight` float NOT NULL,
  `story` text NOT NULL,
  `create_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `characters`
--

INSERT INTO `characters` (`id`, `image`, `name`, `age`, `weight`, `story`, `create_at`, `update_at`) VALUES
(12, '54654', 'Blancanieves', 21, 45.5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '2021-06-19 06:37:44', '2021-06-27 23:11:53'),
(13, '5454654', 'Cenicienta', 19, 50.5, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', '2021-06-20 03:12:45', '2021-06-27 23:11:44');

--
-- Disparadores `characters`
--
DROP TRIGGER IF EXISTS `tbi_characters_create`;
DELIMITER $$
CREATE TRIGGER `tbi_characters_create` BEFORE INSERT ON `characters` FOR EACH ROW begin
        
        set new.create_at = CURRENT_TIMESTAMP;
        set new.update_at = CURRENT_TIMESTAMP;

end
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `tbu_characters_update`;
DELIMITER $$
CREATE TRIGGER `tbu_characters_update` BEFORE UPDATE ON `characters` FOR EACH ROW begin
        
        set new.update_at = CURRENT_TIMESTAMP;

end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genres`
--

DROP TABLE IF EXISTS `genres`;
CREATE TABLE IF NOT EXISTS `genres` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `image` varchar(50) DEFAULT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `genres`
--

INSERT INTO `genres` (`id`, `name`, `image`, `create_at`, `update_at`) VALUES
(1, 'accion', NULL, '2021-06-22 21:24:55', '2021-06-22 21:24:55'),
(2, 'comedia', NULL, '2021-06-22 21:24:55', '2021-06-22 21:24:55');

--
-- Disparadores `genres`
--
DROP TRIGGER IF EXISTS `tbi_genres_create`;
DELIMITER $$
CREATE TRIGGER `tbi_genres_create` BEFORE INSERT ON `genres` FOR EACH ROW begin
        
        set new.create_at = CURRENT_TIMESTAMP;
        set new.update_at = CURRENT_TIMESTAMP;

end
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `tbu_genres_update`;
DELIMITER $$
CREATE TRIGGER `tbu_genres_update` BEFORE UPDATE ON `genres` FOR EACH ROW begin
        
        set new.update_at = CURRENT_TIMESTAMP;

end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `genre_by_movie`
--

DROP TABLE IF EXISTS `genre_by_movie`;
CREATE TABLE IF NOT EXISTS `genre_by_movie` (
  `movie_id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`genre_id`,`movie_id`),
  KEY `fk_genre_by_movie_movies` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `genre_by_movie`
--

INSERT INTO `genre_by_movie` (`movie_id`, `genre_id`, `create_at`, `update_at`) VALUES
(1, 1, '2021-06-22 21:25:50', '2021-06-22 21:25:50'),
(2, 1, '2021-06-22 21:25:50', '2021-06-22 21:25:50'),
(7, 1, '2021-06-24 02:34:37', '2021-06-24 02:34:37'),
(1, 2, '2021-06-22 21:26:18', '2021-06-22 21:26:18');

--
-- Disparadores `genre_by_movie`
--
DROP TRIGGER IF EXISTS `tbi_genre_by_movie_create`;
DELIMITER $$
CREATE TRIGGER `tbi_genre_by_movie_create` BEFORE INSERT ON `genre_by_movie` FOR EACH ROW begin
        
        set new.create_at = CURRENT_TIMESTAMP;
        set new.update_at = CURRENT_TIMESTAMP;

end
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `tbu_genre_by_movie_update`;
DELIMITER $$
CREATE TRIGGER `tbu_genre_by_movie_update` BEFORE UPDATE ON `genre_by_movie` FOR EACH ROW begin
        
        set new.update_at = CURRENT_TIMESTAMP;

end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movies`
--

DROP TABLE IF EXISTS `movies`;
CREATE TABLE IF NOT EXISTS `movies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  `creation_movie` date NOT NULL,
  `qualification` int(11) NOT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `movies`
--

INSERT INTO `movies` (`id`, `title`, `image`, `creation_movie`, `qualification`, `create_at`, `update_at`) VALUES
(1, 'Blancanieves y los 7', '4564654', '1955-06-01', 2, '2021-06-20 02:10:15', '2021-06-27 23:12:42'),
(2, 'Cenicienta la Pelicula', '564654', '1975-06-01', 2, '2021-06-20 02:38:11', '2021-06-27 23:13:23'),
(7, 'Bambi', '564654', '2001-05-31', 2, '2021-06-24 02:34:37', '2021-06-27 23:13:39');

--
-- Disparadores `movies`
--
DROP TRIGGER IF EXISTS `tbi_movies_create`;
DELIMITER $$
CREATE TRIGGER `tbi_movies_create` BEFORE INSERT ON `movies` FOR EACH ROW begin
        
        set new.create_at = CURRENT_TIMESTAMP;
        set new.update_at = CURRENT_TIMESTAMP;

end
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `tbu_movies_update`;
DELIMITER $$
CREATE TRIGGER `tbu_movies_update` BEFORE UPDATE ON `movies` FOR EACH ROW begin
        
        set new.update_at = CURRENT_TIMESTAMP;

end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movies_by_characters`
--

DROP TABLE IF EXISTS `movies_by_characters`;
CREATE TABLE IF NOT EXISTS `movies_by_characters` (
  `movie_id` int(11) NOT NULL,
  `character_id` int(11) NOT NULL,
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`character_id`,`movie_id`),
  KEY `fk_movies_by_character_movie` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `movies_by_characters`
--

INSERT INTO `movies_by_characters` (`movie_id`, `character_id`, `create_at`, `update_at`) VALUES
(1, 12, '2021-06-20 02:28:36', '2021-06-20 02:28:36'),
(2, 12, '2021-06-20 02:38:32', '2021-06-20 02:38:32'),
(7, 12, '2021-06-24 02:34:37', '2021-06-24 02:34:37'),
(1, 13, '2021-06-20 03:13:12', '2021-06-20 03:13:12');

--
-- Disparadores `movies_by_characters`
--
DROP TRIGGER IF EXISTS `tbi_movies_by_characters_create`;
DELIMITER $$
CREATE TRIGGER `tbi_movies_by_characters_create` BEFORE INSERT ON `movies_by_characters` FOR EACH ROW begin
        
        set new.create_at = CURRENT_TIMESTAMP;
        set new.update_at = CURRENT_TIMESTAMP;

end
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `tbu_movies_by_characters_update`;
DELIMITER $$
CREATE TRIGGER `tbu_movies_by_characters_update` BEFORE UPDATE ON `movies_by_characters` FOR EACH ROW begin
        
        set new.update_at = CURRENT_TIMESTAMP;

end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_at` varchar(255) DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `is_available` bit(1) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `update_at` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `create_at`, `dni`, `firstname`, `is_available`, `lastname`, `password`, `update_at`, `user_type`, `username`, `mail`) VALUES
(1, NULL, '65464645', 'Patricio', b'1', 'Diaz', '$2a$10$pB4Cf19dt5QbiRlBR5lrFue4Yzvi/FRt/aufy4L8vK5O8B1.9ku3m', '2021-06-27 20:16:25', 'ROLE_USER', 'usuario', 'diaz.patriciodaniel@gmail.com'),
(2, NULL, '65498845', 'admin', b'1', 'admin', '$2a$10$eOdvOZ2cseeNkrCUAwRNcuA6w0HURoQQJy8XSgdPExnnizVac2gYO', '2021-06-27 20:16:57', 'ROLE_ADMIN', 'administrador', 'admin@admin.com');

--
-- Disparadores `users`
--
DROP TRIGGER IF EXISTS `tbi_users_create`;
DELIMITER $$
CREATE TRIGGER `tbi_users_create` BEFORE INSERT ON `users` FOR EACH ROW begin
        
        set new.create_at = CURRENT_TIMESTAMP;
        set new.update_at = CURRENT_TIMESTAMP;

end
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `tbu_users_update`;
DELIMITER $$
CREATE TRIGGER `tbu_users_update` BEFORE UPDATE ON `users` FOR EACH ROW begin
        
        set new.update_at = CURRENT_TIMESTAMP;

end
$$
DELIMITER ;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `genre_by_movie`
--
ALTER TABLE `genre_by_movie`
  ADD CONSTRAINT `fk_genre_by_movie_genres` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`),
  ADD CONSTRAINT `fk_genre_by_movie_movies` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`);

--
-- Filtros para la tabla `movies_by_characters`
--
ALTER TABLE `movies_by_characters`
  ADD CONSTRAINT `fk_movies_by_character_movie` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`id`),
  ADD CONSTRAINT `fk_movies_by_mcharacter_character` FOREIGN KEY (`character_id`) REFERENCES `characters` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
