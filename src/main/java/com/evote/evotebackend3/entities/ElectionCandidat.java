package com.evote.evotebackend3.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "election_candidat")
public class ElectionCandidat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idElectionCandidat;

    @Column(nullable = false)
    private int nombreDeVotes;

    @Column(nullable = false)
    private boolean statutCandidat;

    @ManyToOne
    @JoinColumn(name = "idCandidat", nullable = false)
    private Candidat candidat;

    @ManyToOne
    @JoinColumn(name = "idElection", nullable = false)
    private Election election;

    public ElectionCandidat() {}

    public ElectionCandidat(int nombreDeVotes, boolean statutCandidat, Candidat candidat, Election election) {
        this.nombreDeVotes = nombreDeVotes;
        this.statutCandidat = statutCandidat;
        this.candidat = candidat;
        this.election = election;
    }

    public long getIdElectionCandidat() {
        return idElectionCandidat;
    }

    public void setIdElectionCandidat(long idElectionCandidat) {
        this.idElectionCandidat = idElectionCandidat;
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
        // Exemple simple : si le candidat a au moins 1 vote, il devient "actif"
        if (this.nombreDeVotes > 0) {
            this.statutCandidat = true;
        } else {
            this.statutCandidat = false;
        }
    }

}
