package com.isninfo.resafacile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Épisode 5 : Client devient une entité JPA.
 * Une classe annotée @Entity = une table dans MySQL, un objet = une ligne.
 */
@Entity
@Table(name = "clients")
public class Client {

    // Clé primaire, générée par MySQL (AUTO_INCREMENT)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    private String email;
    private String telephone;

    // Épisode 7 : champ INTERNE, réservé à l'équipe. Il est dans la table,
    // mais il ne doit jamais sortir par l'API : c'est le rôle des DTO.
    private String noteInterne;

    // Constructeur vide : obligatoire pour JPA (et pour Jackson)
    public Client() {
    }

    public Client(Long id, String nom, String email, String telephone) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNoteInterne() {
        return noteInterne;
    }

    public void setNoteInterne(String noteInterne) {
        this.noteInterne = noteInterne;
    }
}
