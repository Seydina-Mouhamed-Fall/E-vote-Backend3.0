package com.evote.evotebackend3.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "election_candidat")
public class ElectionCandidat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Avant : idElectionCandidat

    @Column(nullable = false)
    private int nombreDeVotes;

    @Column(nullable = false)
    private boolean statutCandidat;

    @ManyToOne
    @JoinColumn(name = "id_candidat", nullable = false) // Harmonisé pour la base
    private Candidat candidat;

    @ManyToOne
    @JoinColumn(name = "id_election", nullable = false) // Harmonisé pour la base
    private Election election;

    // ============================
    // Constructeurs
    // ============================
    public ElectionCandidat() {}

    public ElectionCandidat(int nombreDeVotes, boolean statutCandidat, Candidat candidat, Election election) {
        this.nombreDeVotes = nombreDeVotes;
        this.statutCandidat = statutCandidat;
        this.candidat = candidat;
        this.election = election;
    }

    // ============================
    // Getters & Setters
    // ============================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNombreDeVotes() {
        return nombreDeVotes;
    }

    public void setNombreDeVotes(int nombreDeVotes) {
        this.nombreDeVotes = nombreDeVotes;
    }

    public boolean isStatutCandidat() {
        return statutCandidat;
    }

    public void setStatutCandidat(boolean statutCandidat) {
        this.statutCandidat = statutCandidat;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public void mettreAjourStatut() {
        this.statutCandidat = this.nombreDeVotes > 0;
    }
}
