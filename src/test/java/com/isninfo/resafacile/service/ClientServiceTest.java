package com.isninfo.resafacile.service;

import com.isninfo.resafacile.model.Client;
import com.isninfo.resafacile.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Épisode 5 : vérifie que le service lit bien les clients dans MySQL.
 * Nécessite que MySQL soit démarré.
 * @Transactional : Spring annule ce que le test écrit, la base reste propre.
 */
@SpringBootTest
@Transactional
class ClientServiceTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Test
    void leServiceLitLesClientsDansMySql() {
        Client enregistre = clientRepository.save(
                new Client(null, "Test Service", "service@example.com", "000"));

        assertThat(clientService.trouverTousClient())
                .extracting(Client::getNom)
                .contains("Test Service");
        assertThat(clientService.trouverParId(enregistre.getId())).isPresent();
    }

    @Test
    void unClientInexistantDonneUnOptionalVide() {
        assertThat(clientService.trouverParId(-1L)).isEmpty();
    }
}
