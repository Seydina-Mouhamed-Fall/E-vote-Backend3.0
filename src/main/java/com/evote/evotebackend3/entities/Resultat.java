package com.evote.evotebackend3.entities;

import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "resultat")
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResultat;

    @Column(name = "total_votes",nullable = false)
    private int totalVotes;

    @Column(name = "statut_validation", nullable = false)
    private boolean statutValidation;

    @Column(name = "nombre_voix", nullable = false)
    private int nombreVoix;

    @Column(name = "position", nullable = false)
    private int position;

    @ElementCollection
    @CollectionTable(
        name = "score_candidat",
        joinColumns = @JoinColumn(name = "resultat_id")
    )
    @MapKeyJoinColumn(name = "candidat_id")
    @Column(name = "score")
    private Map<Candidat, Integer> scoreCandidat = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "id_admin")
    private Administrateur administrateur;

    // Constructeurs
    public Resultat() {}

    public Resultat(int totalVotes, boolean statutValidation, int nombreVoix, int position) {
        this.totalVotes = totalVotes;
        this.statutValidation = statutValidation;
        this.nombreVoix = nombreVoix;
        this.position = position;
    }

    // Getters et Setters
    public Long getIdResultat() {
        return idResultat;
    }

    public void setIdResultat(Long idResultat) {
        this.idResultat = idResultat;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public boolean isStatutValidation() {
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

    public Map<Candidat, Integer> getScoreCandidat() {
        return scoreCandidat;
    }

    public void setScoreCandidat(Map<Candidat, Integer> scoreCandidat) {
        this.scoreCandidat = scoreCandidat;
    }

    public Administrateur getAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(Administrateur administrateur) {
        this.administrateur = administrateur;
    }

    // toString
    @Override
    public String toString() {
        return "Resultat{" +
            "idResultat=" + idResultat +
            ", totalVotes=" + totalVotes +
            ", statutValidation=" + statutValidation +
            ", nombreVoix=" + nombreVoix +
            ", position=" + position +
            '}';
    }
}
