-- Épisode 5 : données de test pour vérifier la table "clients" dans Workbench.
--
-- À lancer APRÈS un premier démarrage de l'application : c'est elle qui crée
-- la table. (Ce fichier n'est volontairement pas monté dans Docker.)

USE resafacile;

INSERT INTO clients (nom, email, telephone) VALUES
    ('Arnaud Dupont', 'arnaud@email.com', '2402543669'),
    ('Toto Daokou', 'toto@gmail.com', '22822556699');

SELECT * FROM clients;
