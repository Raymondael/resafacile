-- Épisode 7 : démonstration de la « fuite » d'un champ interne.
--
-- À lancer dans MySQL Workbench APRÈS avoir ajouté le champ noteInterne à Client
-- et redémarré l'application une fois (Hibernate crée la colonne note_interne).

USE resafacile;

-- Une note réservée à l'équipe, qui ne doit jamais sortir par l'API
UPDATE clients
SET note_interne = 'Facture impayée : à relancer'
WHERE id = 1;

SELECT id, nom, note_interne FROM clients;

-- Pour effacer la note entre deux prises :
-- UPDATE clients SET note_interne = NULL;
