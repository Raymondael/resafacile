-- Épisode 5 : requêtes de contrôle à lancer dans MySQL Workbench
-- (une par une : sélectionner la ligne, puis Ctrl+Entrée).

-- 1. La base existe-t-elle ?
SHOW DATABASES LIKE 'resafacile';

-- 2. Les droits de l'utilisateur de l'application
SHOW GRANTS FOR 'resafacile'@'%';

USE resafacile;

-- 3. La table a-t-elle été créée par Hibernate ?
SHOW TABLES;

-- 4. Sa structure : id (clé primaire, AUTO_INCREMENT), nom (NOT NULL), email, telephone
DESCRIBE clients;

-- 5. Son contenu
SELECT * FROM clients;

-- 6. Combien de clients ?
SELECT COUNT(*) AS nombre_de_clients FROM clients;
