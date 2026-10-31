package com.isninfo.resafacile.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Épisode 8 : un seul endroit pour transformer les exceptions en réponses HTTP.
 * <p>
 * @RestControllerAdvice s'applique à TOUS les contrôleurs. Quand une exception
 * remonte d'un contrôleur, Spring cherche ici la méthode @ExceptionHandler qui
 * correspond à son type.
 * <p>
 * On ne gère volontairement PAS Exception.class : un « attrape-tout » intercepterait
 * aussi les 404 et 405 que Spring sait déjà traiter, et les transformerait en 500.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 : un champ ne respecte pas les règles du DTO (@NotBlank, @Email...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErreurApi> champsInvalides(MethodArgumentNotValidException ex,
                                                     HttpServletRequest requete) {
        Map<String, String> details = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(erreur -> details.putIfAbsent(erreur.getField(), erreur.getDefaultMessage()));
        return reponse(HttpStatus.BAD_REQUEST, "Certains champs sont invalides", requete, details);
    }

    // 400 : le corps de la requête n'est pas un JSON lisible
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErreurApi> jsonIllisible(HttpMessageNotReadableException ex,
                                                   HttpServletRequest requete) {
        return reponse(HttpStatus.BAD_REQUEST,
                "Le corps de la requête n'est pas un JSON valide", requete, Map.of());
    }

    // 404 : le client demandé n'existe pas
    @ExceptionHandler(ClientIntrouvableException.class)
    public ResponseEntity<ErreurApi> clientIntrouvable(ClientIntrouvableException ex,
                                                       HttpServletRequest requete) {
        return reponse(HttpStatus.NOT_FOUND, ex.getMessage(), requete, Map.of());
    }

    // 409 : la base refuse la donnée (par exemple un email déjà utilisé : contrainte UNIQUE)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErreurApi> conflit(DataIntegrityViolationException ex,
                                             HttpServletRequest requete) {
        return reponse(HttpStatus.CONFLICT,
                "Conflit avec une donnée existante (par exemple : cet email est déjà utilisé)",
                requete, Map.of());
    }

    private ResponseEntity<ErreurApi> reponse(HttpStatus statut, String message,
                                              HttpServletRequest requete,
                                              Map<String, String> details) {
        ErreurApi corps = new ErreurApi(
                LocalDateTime.now(),
                statut.value(),
                statut.getReasonPhrase(),
                message,
                requete.getRequestURI(),
                details);
        return ResponseEntity.status(statut).body(corps);
    }
}
