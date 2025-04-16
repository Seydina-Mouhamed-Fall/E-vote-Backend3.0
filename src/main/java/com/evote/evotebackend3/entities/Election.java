package com.evote.evotebackend3.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "election")
public class Election {

    @Id
    @Column(name = "id_election")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "date_debut")
    //@Temporal(TemporalType.DATE)
    private LocalDateTime dateDebut;

    @Column(name = "date_fin")
    private LocalDateTime dateFin;

    @Column(name = "cloturee", nullable = false)
    private boolean cloturee;

    @ManyToMany
    @JoinTable(
        name = "election_candidat",
        joinColumns = @JoinColumn(name = "id_election"),
        inverseJoinColumns = @JoinColumn(name = "id_candidat")
    )
    private List<Candidat> candidats = new ArrayList<>();

    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ElectionCandidat> electionCandidats = new ArrayList<>();

    // ===========================
    // Constructeurs
    // ===========================
    public Election() {}

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("EI_EXPOSE_REP2")
    public Election(String nom, LocalDateTime dateDebut, LocalDateTime dateFin, boolean cloturee) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.cloturee = cloturee;
    }

    public Election(String nom) {
        this.nom = nom;
    }

//    public Election(String s, String date, String date1, boolean b) {
//        // À compléter si nécessaire
//    }

    // ===========================
    // Getters & Setters
    // ===========================
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

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
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
