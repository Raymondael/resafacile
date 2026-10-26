package com.isninfo.resafacile.repository;

import com.isninfo.resafacile.model.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Épisode 5 : vérifie que le repository parle bien à MySQL.
 * Nécessite que MySQL soit démarré (comme tous les tests du projet depuis l'épisode 5).
 * @Transactional : Spring annule ce que le test écrit, la base reste propre.
 */
@SpringBootTest
@Transactional
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void enregistrerPuisRelireUnClient() {
        Client enregistre = clientRepository.save(
                new Client(null, "Test Repo", "test@example.com", "000"));

        assertThat(enregistre.getId()).isNotNull();
        assertThat(clientRepository.findById(enregistre.getId())).isPresent();
    }
}
