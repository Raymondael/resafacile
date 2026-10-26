
/**
 * Épisode 3 : CRUD sur /api/clients.
 * <p>
 * Ressource : /api/clients
 * <p>
 * GET     /api/clients        → lire la liste           → 200 OK
 * GET     /api/clients/1      → lire UN client          → 200 OK  ou 404 Not Found
 * POST    /api/clients        → créer   (corps JSON)    → 201 Created
 * PUT     /api/clients/1      → modifier (corps JSON)   → 200 OK  ou 404
 * DELETE  /api/clients/1      → supprimer               → 204 No Content ou 404
 * ```
 *
 * ==> Une API REST, c'est une façon d'organiser les adresses d'un serveur autour de **ressources** :
 * ici, les clients. L'adresse désigne ===>> — `/api/clients`, ou
 * `/api/clients/1` pour le client numéro 1.
 * Et le **HTTP** dit *ce qu'on veut faire* :
 * ==> GET pour lire,
 * ==> POST pour créer,
 * ==> PUT pour modifier,
 * ==> DELETE pour supprimer.
 * Même adresse, quatre actions différentes.
 * >
 * > Le serveur répond toujours avec un **code de statut**.
 * ==> Les 200, c'est « tout va bien » : 200 OK,
 * ==> 201 « créé »,
 * ==> 204 « c'est fait, et je n'ai rien à te renvoyer ».
 * ==> Les 400, c'est « c'est de ta faute » :
 * ==> 404, la ressource n'existe pas.
 * ==> Les 500, c'est « c'est de ma faute », le serveur a planté.
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
import com.isninfo.resafacile.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Afficher tous les clients
    @GetMapping
    public List<Client> tousClient() {
        return clientService.trouverTousClient();
    }

    // Épisode 6 : rechercher par nom, GET /api/clients/recherche?nom=dupont
    // (l'adresse littérale "recherche" passe avant le "/{id}" : Spring choisit la plus précise)
    @GetMapping("/recherche")
    public List<Client> rechercher(@RequestParam String nom) {
        return clientService.rechercherParNom(nom);
    }

    // Afficher un client par son id
    @GetMapping("/{id}")
    public ResponseEntity<Client> afficherUnClient(@PathVariable Long id) {
        return clientService.trouverParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Création d'un client
    @PostMapping
    public ResponseEntity<Client> creer(@RequestBody Client nouveau) {
        Client cree = clientService.creerClient(nouveau);
        return ResponseEntity.status(HttpStatus.CREATED).body(cree);
    }

    // Update un client
    @PutMapping("/{id}")
    public ResponseEntity<Client> modifierClient(@PathVariable Long id,
                                                 @RequestBody Client modif) {
        return clientService.modifierClient(id, modif)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

// Supprimer un client

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        if (clientService.supprimerClient(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
