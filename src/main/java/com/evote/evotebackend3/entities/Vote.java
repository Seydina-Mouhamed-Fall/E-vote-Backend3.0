package com.evote.evotebackend3.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_session_vote")
    private Long id;

    @Column(name = "choix", nullable = false)
    private String choix;

    @Column(name = "date_vote", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateVote;

    @Column(name = "statut_vote", nullable = false)
    private boolean statutVote;

    @ManyToOne
    @JoinColumn(name = "electeur_id", nullable = false)
    private Electeur electeur;

    @ManyToOne
    @JoinColumn(name = "election_candidat_id")
    private ElectionCandidat electionCandidat;

    // 🔹 Constructeur vide requis par JPA
    public Vote() {}

    // 🔹 Constructeur utile pour initialisation rapide
    public Vote(String choix, Date dateVote, boolean statutVote) {
        this.choix = choix;
        this.dateVote = dateVote;
        this.statutVote = statutVote;
    }

    // Getters / Setters
    public Long getId() {
        return id;
    }

    public String getChoix() {
        return choix;
    }

    public void setChoix(String choix) {
        this.choix = choix;
    }

    public Date getDateVote() {
        return dateVote;
    }

    public void setDateVote(Date dateVote) {
        this.dateVote = dateVote;
    }

    public boolean isStatutVote() {
        return statutVote;
    }

    public void setStatutVote(boolean statutVote) {
        this.statutVote = statutVote;
    }

    public Electeur getElecteur() {
        return electeur;
    }

    public void setElecteur(Electeur electeur) {
        this.electeur = electeur;
    }

    public ElectionCandidat getElectionCandidat() {
        return electionCandidat;
    }

    public void setElectionCandidat(ElectionCandidat electionCandidat) {
        this.electionCandidat = electionCandidat;
    }
}
