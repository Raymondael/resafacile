package com.isninfo.resafacile.dto;

/**
 * Épisode 7 : ce que l'API RENVOIE à l'appelant.
 * <p>
 * Exactement les champs que l'on veut montrer. Un champ ajouté plus tard dans
 * l'entité Client n'apparaît pas ici tant qu'on ne l'ajoute pas volontairement.
 */
public record ClientResponse(Long id, String nom, String email, String telephone) {
}
