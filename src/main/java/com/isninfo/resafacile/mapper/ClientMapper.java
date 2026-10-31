package com.isninfo.resafacile.mapper;

import com.isninfo.resafacile.dto.ClientRequest;
import com.isninfo.resafacile.dto.ClientResponse;
import com.isninfo.resafacile.model.Client;

/**
 * Épisode 7 : traduit entre les DTO (ce qu'on échange) et l'entité (ce qu'on stocke).
 * <p>
 * Écrit à la main pour qu'on voie chaque champ passer. Des outils comme MapStruct
 * génèrent ce code automatiquement ; on les verra quand le projet grossira.
 */
public final class ClientMapper {

    private ClientMapper() {
        // classe utilitaire : on n'en fabrique pas d'instance
    }

    // DTO d'entrée -> entité (nouvelle, sans id)
    public static Client toEntity(ClientRequest requete) {
        Client client = new Client();
        client.setNom(requete.nom());
        client.setEmail(requete.email());
        client.setTelephone(requete.telephone());
        return client;
    }

    // entité -> DTO de sortie
    public static ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getNom(),
                client.getEmail(),
                client.getTelephone());
    }
}
