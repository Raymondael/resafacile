package com.isninfo.resafacile.mapper;

import com.isninfo.resafacile.dto.ClientRequest;
import com.isninfo.resafacile.dto.ClientResponse;
import com.isninfo.resafacile.model.Client;
import org.junit.jupiter.api.Test;

import java.lang.reflect.RecordComponent;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Épisode 7 : test unitaire pur, SANS Spring et SANS MySQL.
 * Il s'exécute en quelques millisecondes, même si la base est éteinte.
 */
class ClientMapperTest {

    @Test
    void toResponseCopieLesChampsPublicsDuClient() {
        Client client = new Client(7L, "Marie Nguema", "marie@example.com", "+240 222 000 001");
        client.setNoteInterne("Facture impayée : à relancer");

        ClientResponse reponse = ClientMapper.toResponse(client);

        assertThat(reponse.id()).isEqualTo(7L);
        assertThat(reponse.nom()).isEqualTo("Marie Nguema");
        assertThat(reponse.email()).isEqualTo("marie@example.com");
        assertThat(reponse.telephone()).isEqualTo("+240 222 000 001");
    }

    @Test
    void laReponseNAPasDeChampPourLaNoteInterne() {
        assertThat(ClientResponse.class.getRecordComponents())
                .extracting(RecordComponent::getName)
                .containsExactly("id", "nom", "email", "telephone");
    }

    @Test
    void toEntityCreeUnClientSansIdNiNoteInterne() {
        Client client = ClientMapper.toEntity(
                new ClientRequest("Paul Obiang", "paul@example.com", "+240 222 000 002"));

        assertThat(client.getId()).isNull();
        assertThat(client.getNom()).isEqualTo("Paul Obiang");
        assertThat(client.getEmail()).isEqualTo("paul@example.com");
        assertThat(client.getTelephone()).isEqualTo("+240 222 000 002");
        assertThat(client.getNoteInterne()).isNull();
    }
}
