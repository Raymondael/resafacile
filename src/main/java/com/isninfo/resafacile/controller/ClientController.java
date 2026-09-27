
/**
 * Épisode 3 : CRUD sur /api/clients.
 *
 * Ressource : /api/clients
 *
 * GET     /api/clients        → lire la liste           → 200 OK
 * GET     /api/clients/1      → lire UN client          → 200 OK  ou 404 Not Found
 * POST    /api/clients        → créer   (corps JSON)    → 201 Created
 * PUT     /api/clients/1      → modifier (corps JSON)   → 200 OK  ou 404
 * DELETE  /api/clients/1      → supprimer               → 204 No Content ou 404
 * ```
 *
 *  ==> Une API REST, c'est une façon d'organiser les adresses d'un serveur autour de **ressources** :
 *    ici, les clients. L'adresse désigne ===>> — `/api/clients`, ou
 *    `/api/clients/1` pour le client numéro 1.
 *    Et le **HTTP** dit *ce qu'on veut faire* :
 *  ==> GET pour lire,
 *  ==> POST pour créer,
 *  ==> PUT pour modifier,
 *  ==> DELETE pour supprimer.
 *  Même adresse, quatre actions différentes.
 * >
 * > Le serveur répond toujours avec un **code de statut**.
 *  ==> Les 200, c'est « tout va bien » : 200 OK,
 *  ==> 201 « créé »,
 *  ==> 204 « c'est fait, et je n'ai rien à te renvoyer ».
 *  ==> Les 400, c'est « c'est de ta faute » :
 *  ==> 404, la ressource n'existe pas.
 *  ==> Les 500, c'est « c'est de ma faute », le serveur a planté.
 * >
 * > Retenez ce tableau, on va le coder ligne par ligne.
 *
 * Pourquoi c'est important ? Parce que Jackson,
 * qui transforme le JSON en objet et inversement,
 * **utilise ces méthodes**. Quand il reçoit du JSON,
 * il appelle le constructeur vide puis les setters.
 * Quand il envoie du JSON, il appelle les getters.
 * Pas de getter, pas de champ dans le JSON.
 * Gardez ça en tête pour l'erreur de fin d'épisode.
 **/

package com.isninfo.resafacile.controller;

import com.isninfo.resafacile.model.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private  final List<Client> clients = new ArrayList<>();
    private  final AtomicLong compteurId = new AtomicLong(1);

    public  ClientController(){
        clients.add(new Client(compteurId.getAndIncrement(),
                "Arand Dupont",
                "arnaud@email.com",
                "2402543669"));
        clients.add(new Client(compteurId.getAndIncrement(),
                "Toto Daokou",
                "toto@gmail.com",
                "22822556699"));
    }
// Afficher tous les clients
    @GetMapping
    public  List<Client> tousClient(){
        return clients;
    }

// Afficher un client par son id
    @GetMapping("/{id}")
    public ResponseEntity<Client> afficherUnClient(@PathVariable Long id){
        for(Client ct : clients){
            if(ct.getId().equals(id)){
                return ResponseEntity.ok(ct);
            }
        }
        return  ResponseEntity.notFound().build();
    }

  // Création d'un client
  @PostMapping
  public  ResponseEntity<Client> creerClient(@RequestBody Client nouveau){
        nouveau.setId(compteurId.getAndIncrement());
        clients.add(nouveau);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveau);
  }

  // Update un client
    @PutMapping("/{id}")
    public  ResponseEntity<Client> modifierClient(@PathVariable Long id,
                                                  @RequestBody Client modif){
        for(Client ct : clients){
            if(ct.getId().equals(id)){
                ct.setNom(modif.getNom());
                ct.setEmail(modif.getEmail());
                ct.setTelephone(modif.getTelephone());
                ResponseEntity.ok(ct);
            }
        }
        return  ResponseEntity.notFound().build();
    }

  // Supprimer un client

  @DeleteMapping("/{id}")
  public  ResponseEntity<Void> supprimer(
          @PathVariable Long id ){
        boolean supprim = clients.removeIf(
                ct -> ct.getId().equals(id) );
        if(supprim){
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.notFound().build();
  }
}
