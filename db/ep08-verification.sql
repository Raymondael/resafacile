-- Épisode 8 : contrôles autour de la contrainte UNIQUE sur l'email.
--
-- À lancer dans MySQL Workbench (une requête à la fois : Ctrl+Entrée).
-- La contrainte est créée par Hibernate au premier démarrage de l'épisode 8
-- (ddl-auto=update), mais SEULEMENT s'il n'y a aucun email en double.

USE resafacile;

-- 1. Emails en double : doit renvoyer 0 ligne AVANT de démarrer l'application
SELECT email, COUNT(*) AS nombre
FROM clients
GROUP BY email
HAVING COUNT(*) > 1;

-- 2. Les index de la table : une ligne sur la colonne email avec Non_unique = 0
--    prouve que la contrainte UNIQUE existe
SHOW INDEX FROM clients;

-- 3. Le contenu de la table
SELECT id, nom, email FROM clients;

-- Pour retirer un doublon avant de relancer (adapter l'id) :
-- DELETE FROM clients WHERE id = 3;
