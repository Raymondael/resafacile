package com.isninfo.resafacile.dto;

/**
 * Épisode 7 : ce que l'appelant de l'API NOUS ENVOIE (corps du POST et du PUT).
 * <p>
 * Trois champs seulement : pas d'id (c'est MySQL qui le génère) et pas de
 * champ interne. Ce que l'API accepte n'est plus décidé par la table.
 * <p>
 * Un record est une classe de données immuable : le compilateur écrit pour nous
 * le constructeur, les accesseurs (nom(), email()...), equals, hashCode et toString.
 */
public record ClientRequest(String nom, String email, String telephone) {
}
