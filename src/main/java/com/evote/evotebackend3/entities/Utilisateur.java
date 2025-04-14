package com.evote.evotebackend3.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "utilisateur")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Long id;  // Renommé de idUtilisateur → id

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column(name = "date_inscription", nullable = false)
    private LocalDate dateInscription;

    // 🔹 Constructeur par défaut requis par JPA
    public Utilisateur() {}

    // 🔹 Constructeur utile pour initialisation
    public Utilisateur(String nom, String prenom, String email, String motDePasse, LocalDate dateInscription) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.dateInscription = dateInscription;
    }

    // Getters / Setters
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    // 🔹 Méthode utilitaire
    public String getNomComplet() {
        return nom + " " + prenom;
    }

    // 🔹 Méthode pour authentifier l'utilisateur
    public boolean sAuthentifier(String email, String motDePasse) {
        return this.email.equals(email) && this.motDePasse.equals(motDePasse);
    }

    public void demanderSupport(String message) {
        System.out.println("Demande de support de l'utilisateur " + this.email + " : " + message);
    }
}
