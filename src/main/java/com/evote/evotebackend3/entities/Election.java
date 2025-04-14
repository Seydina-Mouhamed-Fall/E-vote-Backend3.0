package com.evote.evotebackend3.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "election")
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idElection;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private LocalDateTime dateFin;

    @Column(name = "cloturee", nullable =  false)
    private boolean cloturee;

    // Relation directe pour accéder aux candidats (convenance)
    @ManyToMany
    @JoinTable(
        name = "election_candidat",
        joinColumns = @JoinColumn(name = "idElection"),
        inverseJoinColumns = @JoinColumn(name = "idCandidat")
    )
    private List<Candidat> candidats = new ArrayList<>();

    // Relation avec l'entité d'association ElectionCandidat (relation métier)
    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ElectionCandidat> electionCandidats = new ArrayList<>();

    // ===========================
    // Constructeurs
    // ===========================
    public Election() {}

    public Election(String nom, Date dateDebut, LocalDateTime dateFin,boolean cloturee) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.cloturee = cloturee;
    }

    public Election(String nom) {
        this.nom = nom;
    }

    public Election(String s, String date, String date1, boolean b) {
        // À compléter si nécessaire
    }

    // ===========================
    // Getters & Setters
    // ===========================
    public long getIdElection() {
        return idElection;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }


    public LocalDateTime getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public List<Candidat> getCandidats() {
        return candidats;
    }

    public void setCandidats(List<Candidat> candidats) {
        this.candidats = candidats;
    }

    public List<ElectionCandidat> getElectionCandidats() {
        return electionCandidats;
    }

    public void setElectionCandidats(List<ElectionCandidat> electionCandidats) {
        this.electionCandidats = electionCandidats;
    }

    public boolean isCloturee() {
        return cloturee;
    }

    public void setCloturee(boolean cloturee) {
        this.cloturee = cloturee;
    }
}
