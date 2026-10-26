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

    // afficher tous les clients
    public List<Client> trouverTousClient() {
        return clientRepository.findAll();
    }

    // trouver un client par son id
    public Optional<Client> trouverParId(Long id) {
        return clientRepository.findById(id);
    }

    // creer un client
    public Client creerClient(Client nouveau) {
        // id = null : MySQL génère l'id, et on évite d'écraser un client existant
        nouveau.setId(null);
        return clientRepository.save(nouveau);
    }

    // Modifier un client
    public Optional<Client> modifierClient(Long id, Client modifier) {
        return clientRepository.findById(id)
                .map(ct -> {
                    ct.setNom(modifier.getNom());
                    ct.setEmail(modifier.getEmail());
                    ct.setTelephone(modifier.getTelephone());
                    return clientRepository.save(ct);
                });
    }

    // suppression d'un client
    public boolean supprimerClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
