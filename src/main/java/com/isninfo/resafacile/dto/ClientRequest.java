package com.isninfo.resafacile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Épisode 7 : ce que l'appelant de l'API NOUS ENVOIE (corps du POST et du PUT).
 * Épisode 8 : les règles de validation sont écrites ici, sur le DTO d'entrée.
 * <p>
 * Elles ne se déclenchent que si le contrôleur reçoit ce DTO avec @Valid.
 * Les messages sont ceux que l'appelant verra dans la réponse 400.
 */
public record ClientRequest(

        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
        String nom,

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "L'email n'est pas valide")
        String email,

        // facultatif : @Pattern accepte null, mais refuse un texte qui ne correspond pas
        @Pattern(regexp = "^[+0-9 ]{6,20}$",
                message = "Le téléphone doit contenir 6 à 20 chiffres, espaces ou +")
        String telephone) {
}
