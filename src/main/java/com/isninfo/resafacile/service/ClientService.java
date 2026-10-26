package com.isninfo.resafacile.service;

import com.isninfo.resafacile.model.Client;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ClientService {
    private  final List<Client> clients = new ArrayList<>();
    private  final AtomicLong compteurId = new AtomicLong(1);

    public  ClientService(){
        clients.add(new Client(compteurId.getAndIncrement(),
                "Araud Dupont",
                "arnaud@email.com",
                "2402543669"));
        clients.add(new Client(compteurId.getAndIncrement(),
                "Toto Daokou",
                "toto@gmail.com",
                "22822556699"));
    }
    // afficher tous les clients
    public  List<Client> trouverTousClient(){
        return clients;
    }

    // trouver un client par son id
    public Optional<Client> trouverParId(Long id){
        return clients.stream()
                .filter(ct ->ct.getId().equals(id))
                .findFirst();
    }

    // creer un client
    public  Client creerClient(Client nouveau){
        nouveau.setId(compteurId.getAndIncrement());
        clients.add(nouveau);
        return  nouveau;
    }

    // Modifier un client
    public  Optional<Client> modifierClient(Long id, Client modifier){
        return trouverParId(id).map( ct -> {
                    ct.setNom(modifier.getNom());
                    ct.setEmail(modifier.getEmail());
                    ct.setTelephone(modifier.getTelephone());
                    return  ct;
                });
    }


    // suppresssion d'un client
    public boolean supprimerClient(Long id){
        return  clients.removeIf(ct -> ct.getId().equals(id));
    }

}
