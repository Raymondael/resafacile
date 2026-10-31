package com.isninfo.resafacile.exception;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Épisode 8 : le format UNIQUE de toutes les erreurs de l'API.
 * <p>
 * Celui qui appelle l'API (un front Angular, par exemple) sait toujours où lire
 * le code, le message et, pour une validation, le détail champ par champ.
 *
 * @param details champ invalide -> message (vide quand l'erreur ne concerne pas un champ)
 */
public record ErreurApi(
        LocalDateTime horodatage,
        int statut,
        String erreur,
        String message,
        String chemin,
        Map<String, String> details) {
}
