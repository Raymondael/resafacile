package com.isninfo.resafacile.exception;

/**
 * Épisode 8 : lancée quand un client demandé n'existe pas.
 * Le GlobalExceptionHandler la transforme en réponse 404 avec un message clair.
 */
public class ClientIntrouvableException extends RuntimeException {

    public ClientIntrouvableException(Long id) {
        super("Client introuvable : " + id);
    }
}
