package com.isninfo.resafacile.service;

import com.isninfo.resafacile.model.Client;
import com.isninfo.resafacile.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // afficher tous les clients : lecture dans MySQL
    public List<Client> trouverTousClient() {
        return clientRepository.findAll();
    }

    // trouver un client par son id : lecture dans MySQL
    public Optional<Client> trouverParId(Long id) {
        return clientRepository.findById(id);
    }

    // Épisode 06 : la création, la modification et la suppression
    // passeront aussi par le repository.
    public Client creerClient(Client nouveau) {
        throw new UnsupportedOperationException("Écriture dans MySQL : épisode 06");
    }

    public Optional<Client> modifierClient(Long id, Client modifier) {
        throw new UnsupportedOperationException("Écriture dans MySQL : épisode 06");
    }

    public boolean supprimerClient(Long id) {
        throw new UnsupportedOperationException("Écriture dans MySQL : épisode 06");
    }
}
