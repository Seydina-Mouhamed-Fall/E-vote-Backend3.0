package com.evote.evotebackend3.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "candidat")
public class Candidat extends Utilisateur {

    @Column(name = "parti", nullable = false)
    private String parti;

    @Column(name = "biographie", columnDefinition = "TEXT")
    private String biographie;

    @Column(name = "statut_election", nullable = false)
    private boolean statutElection;

    @ManyToMany(mappedBy = "candidats")
    private List<Election> elections;

    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ElectionCandidat> electionCandidats;

    // Constructeur par défaut requis par JPA
    public Candidat() {
        super();
    }

    // Constructeur avec tous les arguments
    public Candidat(String nom, String prenom, String email, String motDePasse, LocalDate dateInscription,
                    String parti, String biographie, boolean statutElection) {
        super(nom, prenom, email, motDePasse, dateInscription);
        this.parti = parti;
        this.biographie = biographie;
        this.statutElection = statutElection;
    }

    // Getters et Setters
    public String getParti() {
        return parti;
    }

    public void setParti(String parti) {
        this.parti = parti;
    }

    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    public boolean getStatutElection() {
        return statutElection;
    }

    public void setStatutElection(boolean statutElection) {
        this.statutElection = statutElection;
    }

    public List<Election> getElections() {
        return elections;
    }

    public void setElections(List<Election> elections) {
        this.elections = elections;
    }

    public List<ElectionCandidat> getElectionCandidats() {
        return electionCandidats;
    }

    public void setElectionCandidats(List<ElectionCandidat> electionCandidats) {
        this.electionCandidats = electionCandidats;
    }
}
