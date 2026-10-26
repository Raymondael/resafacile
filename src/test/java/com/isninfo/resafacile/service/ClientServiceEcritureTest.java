package com.isninfo.resafacile.service;

import com.isninfo.resafacile.model.Client;
import com.isninfo.resafacile.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Épisode 6 : vérifie la création, la modification, la suppression et la recherche dans MySQL.
 * Nécessite que MySQL soit démarré.
 * @Transactional : Spring annule ce que chaque test écrit, la base reste propre.
 */
@SpringBootTest
@Transactional
class ClientServiceEcritureTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Test
    void creerUnClientLuiDonneUnIdMySql() {
        Client cree = clientService.creerClient(
                new Client(null, "Nouveau Client", "nouveau@example.com", "111"));

        assertThat(cree.getId()).isNotNull();
        assertThat(clientRepository.existsById(cree.getId())).isTrue();
    }

    @Test
    void creerIgnoreUnIdFourniParLAppelant() {
        Client cree = clientService.creerClient(
                new Client(999999L, "Id Impose", "impose@example.com", "222"));

        assertThat(cree.getId()).isNotEqualTo(999999L);
    }

    @Test
    void modifierChangeLesChampsEtGardeLId() {
        Client cree = clientService.creerClient(
                new Client(null, "Avant", "avant@example.com", "1"));

        Optional<Client> modifie = clientService.modifierClient(
                cree.getId(), new Client(null, "Apres", "apres@example.com", "2"));

        assertThat(modifie).isPresent();
        assertThat(modifie.get().getId()).isEqualTo(cree.getId());
        assertThat(modifie.get().getNom()).isEqualTo("Apres");
        assertThat(modifie.get().getEmail()).isEqualTo("apres@example.com");
    }

    @Test
    void modifierUnClientInexistantDonneUnOptionalVide() {
        Optional<Client> modifie = clientService.modifierClient(
                -1L, new Client(null, "Personne", "personne@example.com", "0"));

        assertThat(modifie).isEmpty();
    }

    @Test
    void supprimerUnClientExistantLeRetireDeLaTable() {
        Client cree = clientService.creerClient(
                new Client(null, "A Supprimer", "supprimer@example.com", "3"));

        assertThat(clientService.supprimerClient(cree.getId())).isTrue();
        assertThat(clientRepository.existsById(cree.getId())).isFalse();
    }

    @Test
    void supprimerUnClientInexistantRenvoieFalse() {
        assertThat(clientService.supprimerClient(-1L)).isFalse();
    }

    @Test
    void rechercherParNomIgnoreLaCasse() {
        clientService.creerClient(
                new Client(null, "Marie Nguema", "marie@example.com", "4"));

        assertThat(clientService.rechercherParNom("nguema"))
                .extracting(Client::getNom)
                .contains("Marie Nguema");
        assertThat(clientService.rechercherParNom("zzz-introuvable")).isEmpty();
    }
}
