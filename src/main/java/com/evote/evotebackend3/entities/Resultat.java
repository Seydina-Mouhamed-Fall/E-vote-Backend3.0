package com.evote.evotebackend3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "resultat")
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resultat", nullable = false)
    private Long id; // Avant : idResultat

    @Column(name = "total_votes", nullable = false)
    @Min(value = 0, message = "Total votes cannot be negative")
    private int totalVotes;

    @Column(name = "statut_validation", nullable = false)
    private boolean statutValidation;

    @Column(name = "nombre_voix", nullable = false)
    @Min(value = 0, message = "Nombre de voix ne peut pas être négatif")
    private int nombreVoix;

    @Column(name = "position", nullable = false)
    @Min(value = 1, message = "La position doit être supérieure ou égale à 1")
    private int position;

    @ManyToOne
    @JoinColumn(name = "id_candidat", nullable = false)
    private Candidat candidat;

    @ManyToOne
    @JoinColumn(name = "id_admin", nullable = false)
    private Administrateur administrateur;

    @ManyToOne
    @JoinColumn(name = "id_election", nullable = false)
    private Election election;

    // ============================
    // Constructeurs
    // ============================
    public Resultat() {}

    public Resultat(int totalVotes, boolean statutValidation, int nombreVoix, int position,
                    Candidat candidat, Election election, Administrateur administrateur) {
        this.totalVotes = totalVotes;
        this.statutValidation = statutValidation;
        this.nombreVoix = nombreVoix;
        this.position = position;
        this.candidat = candidat;
        this.election = election;
        this.administrateur = administrateur;
    }

    // ============================
    // Getters et Setters
    // ============================
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public boolean getStatutValidation() {
        return statutValidation;
    }

    public void setStatutValidation(boolean statutValidation) {
        this.statutValidation = statutValidation;
    }

    public int getNombreVoix() {
        return nombreVoix;
    }

    public void setNombreVoix(int nombreVoix) {
        this.nombreVoix = nombreVoix;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Administrateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    @Override
    public String toString() {
        return "Resultat{" +
            "id=" + id +
            ", totalVotes=" + totalVotes +
            ", statutValidation=" + statutValidation +
            ", nombreVoix=" + nombreVoix +
            ", position=" + position +
            ", candidat=" + (candidat != null ? candidat.getNom() : "N/A") +
            ", administrateur=" + (administrateur != null ? administrateur.getNom() : "N/A") +
            '}';
    }
}
