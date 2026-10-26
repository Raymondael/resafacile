-- Épisode 5 : création de la base et de l'utilisateur de l'application.
--
-- À exécuter dans MySQL Workbench, connecté avec le compte root
-- (ouvrir ce fichier, puis cliquer sur l'éclair jaune).
-- Le script peut être relancé sans erreur.
--
-- Les tables, elles, sont créées par Hibernate au démarrage de l'application
-- (spring.jpa.hibernate.ddl-auto=update).
--
-- ATTENTION : mot de passe de développement uniquement. Ne le réutilise jamais
-- en production.

CREATE DATABASE IF NOT EXISTS resafacile
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'resafacile'@'%' IDENTIFIED BY 'resafacile_dev';

GRANT ALL PRIVILEGES ON resafacile.* TO 'resafacile'@'%';
