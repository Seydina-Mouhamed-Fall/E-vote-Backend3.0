package com.evote.evotebackend3.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "electeur")
public class Electeur extends Utilisateur {

    @Column(name = "num_tel", nullable = false)
    private String numTel;

    @Column(name = "statut_eligibilite", nullable = false)
    private boolean statutEligibilite;

    @Column(name = "statut_vote", nullable = false)
    private boolean statutVote;

    @Column(name = "num_cni", nullable = false, unique = true)
    private String numCNI;

    @OneToMany(mappedBy = "electeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vote> votes;

    public Electeur() {
        // Constructeur par défaut requis par JPA
    }

    public Electeur(String nom, String prenom, String email, String motDePasse, LocalDate dateInscription,
                    String numTel, boolean statutEligibilite, boolean statutVote, String numCNI) {
        super(nom, prenom, email, motDePasse, dateInscription);
        this.numTel = numTel;
        this.statutEligibilite = statutEligibilite;
        this.statutVote = statutVote;
        this.numCNI = numCNI;
    }

    // Getters et Setters
    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public boolean isStatutEligibilite() {
        return statutEligibilite;
    }

    public void setStatutEligibilite(boolean statutEligibilite) {
        this.statutEligibilite = statutEligibilite;
    }

    public boolean isStatutVote() {
        return statutVote;
    }

    public void setStatutVote(boolean statutVote) {
        this.statutVote = statutVote;
    }

    public String getNumCNI() {
        return numCNI;
    }

    public void setNumCNI(String numCNI) {
        this.numCNI = numCNI;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }
}
