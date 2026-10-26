package com.isninfo.resafacile.repository;

import com.isninfo.resafacile.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Épisode 5 : l'accès à la table "clients".
 * <p>
 * On n'écrit AUCUNE implémentation : Spring Data la génère au démarrage.
 * JpaRepository<Client, Long> = « je gère des Client dont la clé est un Long ».
 * On obtient gratuitement : findAll, findById, save, deleteById, existsById, count...
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Épisode 6 : méthode dérivée. Spring Data lit le NOM de la méthode et fabrique la requête :
    // findBy + Nom + Containing (LIKE %...%) + IgnoreCase (majuscules/minuscules sans importance)
    List<Client> findByNomContainingIgnoreCase(String nom);
}
