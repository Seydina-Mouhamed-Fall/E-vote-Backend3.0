package com.evote.evotebackend3.entities;

import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Administrateur extends Utilisateur {

    public Administrateur() {
        // Constructeur par défaut requis par JPA
    }

    public Administrateur(String nom, String prenom, String email, String motDePasse, LocalDate dateInscription) {
        super(nom, prenom, email, motDePasse, dateInscription);
    }

    @Override
    public String getNomComplet() {
        return super.getNomComplet();
    }


}

