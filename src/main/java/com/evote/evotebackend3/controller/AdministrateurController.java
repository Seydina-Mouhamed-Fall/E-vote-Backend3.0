package com.evote.evotebackend3.controller;

import com.evote.evotebackend3.entities.*;
import com.evote.evotebackend3.repository.ElecteurRepository;
import com.evote.evotebackend3.services.AdministrateurService;
import com.evote.evotebackend3.services.ElecteurService;
import com.evote.evotebackend3.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/administrateurs")
public class AdministrateurController {

    private final AdministrateurService administrateurService;
    private final ElecteurService electeurService;
    private final VoteService voteService;

    @Autowired
    public AdministrateurController(AdministrateurService administrateurService, ElecteurService electeurService, VoteService voteService) {
        this.administrateurService = administrateurService;
        this.electeurService = electeurService;
        this.voteService = voteService;

    }


    // ==================== GESTION ADMINISTRATEUR ====================

    @PostMapping
    public Administrateur creerAdministrateur(@RequestBody Administrateur admin) {
        return administrateurService.creerAdministrateur(admin);
    }

    @GetMapping
    public List<Administrateur> getTousLesAdministrateurs() {
        return administrateurService.obtenirTousLesAdministrateurs();
    }

    @GetMapping("/{id}")
    public Optional<Administrateur> getAdministrateurParId(@PathVariable Long id) {
        return administrateurService.obtenirAdministrateurParId(id);
    }

    @PutMapping("/{id}")
    public Administrateur updateAdministrateur(@PathVariable Long id, @RequestBody Administrateur admin) {
        return administrateurService.mettreAJourAdministrateur(id, admin);
    }

    @DeleteMapping("/{id}")
    public void supprimerAdministrateur(@PathVariable Long id) {
        administrateurService.supprimerAdministrateur(id);
    }

    // ==================== GESTION ELECTION ====================

    @PostMapping("/{idAdministrateur}/elections")
    public Election creerElection(@PathVariable Long idAdministrateur, @RequestBody Election election) {
        return administrateurService.creerElectionParAdministrateur(idAdministrateur, election);
    }

    @PutMapping("/{idAdministrateur}/elections/{idElection}/cloturer")
    public Election cloturerElection(@PathVariable Long idElection) {
        return administrateurService.cloturerElection(idElection);
    }

    @DeleteMapping("/{idAdministrateur}/elections/{idElection}")
    public void supprimerElection(@PathVariable Long idAdministrateur, @PathVariable Long idElection) {
        administrateurService.supprimerElectionParAdministrateur(idAdministrateur, idElection);
    }

    // ==================== GESTION CANDIDATS ====================

    @PostMapping("/{idAdministrateur}/candidats")
    public Candidat ajouterCandidat(@PathVariable Long idAdministrateur, @RequestBody Candidat candidat) {
        return administrateurService.ajouterCandidatParAdministrateur(idAdministrateur, candidat);
    }

    @GetMapping("/{idAdministrateur}/candidats/{idCandidat}")
    public Optional<Candidat> getCandidat(@PathVariable Long idAdministrateur, @PathVariable Long idCandidat) {
        return administrateurService.obtenirCandidatParAdministrateur(idAdministrateur, idCandidat);
    }

    @GetMapping("/{idAdministrateur}/candidats")
    public List<Candidat> listerCandidats(@PathVariable Long idAdministrateur) {
        return administrateurService.listerCandidatsParAdministrateur(idAdministrateur);
    }

    @PutMapping("/{idAdministrateur}/candidats/{idCandidat}")
    public Candidat updateCandidat(@PathVariable Long idAdministrateur, @PathVariable Long idCandidat, @RequestBody Candidat details) {
        return administrateurService.mettreAJourCandidatParAdministrateur(idAdministrateur, idCandidat, details);
    }

    @DeleteMapping("/{idAdministrateur}/candidats/{idCandidat}")
    public void supprimerCandidat(@PathVariable Long idAdministrateur, @PathVariable Long idCandidat) {
        administrateurService.supprimerCandidatParAdministrateur(idAdministrateur, idCandidat);
    }

    // ==================== GESTION ELECTEURS ====================

    @PostMapping("/{idAdministrateur}/electeurs")
    public Electeur ajouterElecteur(@PathVariable Long idAdministrateur, @RequestBody Electeur electeur) {
        return administrateurService.ajouterElecteurParAdministrateur(idAdministrateur, electeur);
    }

    @GetMapping("/{idAdministrateur}/electeurs/{idElecteur}")
    public Optional<Electeur> getElecteur(@PathVariable Long idAdministrateur, @PathVariable Long idElecteur) {
        return administrateurService.trouverElecteurParIdParAdministrateur(idAdministrateur, idElecteur);
    }

    @GetMapping("/{idAdministrateur}/electeurs/email")
    public Optional<Electeur> getElecteurParEmail(@PathVariable Long idAdministrateur, @RequestParam String email) {
        return administrateurService.trouverElecteurParEmailParAdministrateur(idAdministrateur, email);
    }

    @GetMapping("/{idAdministrateur}/electeurs")
    public List<Electeur> listerElecteurs(@PathVariable Long idAdministrateur) {
        return administrateurService.listerElecteursParAdministrateur(idAdministrateur);
    }

    @PutMapping("/{idAdministrateur}/electeurs/{idElecteur}")
    public Electeur updateElecteur(@PathVariable Long idAdministrateur, @PathVariable Long idElecteur, @RequestBody Electeur details) {
        return administrateurService.mettreAJourElecteurParAdministrateur(idAdministrateur, idElecteur, details);
    }

    @DeleteMapping("/{idAdministrateur}/electeurs/{idElecteur}")
    public void supprimerElecteur(@PathVariable Long idAdministrateur, @PathVariable Long idElecteur) {
        administrateurService.supprimerElecteurParAdministrateur(idAdministrateur, idElecteur);
    }

    @GetMapping("/{idAdministrateur}/electeurs/{idElecteur}/eligibilite")
    public boolean verifierEligibilite(@PathVariable Long idAdministrateur, @PathVariable Long idElecteur) {
        return administrateurService.verifierEligibiliteElecteurParAdministrateur(idAdministrateur, idElecteur);
    }

    @PostMapping("/{idAdministrateur}/electeurs/{idElecteur}/marquer-vote")
    public void marquerCommeAyantVote(@PathVariable Long idAdministrateur, @PathVariable Long idElecteur) {
        administrateurService.marquerElecteurCommeAyantVoteParAdministrateur(idAdministrateur, idElecteur);
    }

    // ==================== GESTION ELECTION-CANDIDAT ====================

    @PostMapping("/{idAdministrateur}/election-candidat")
    public ElectionCandidat associerCandidatAElection(@PathVariable Long idAdministrateur, @RequestBody Election election, @RequestBody Candidat candidat) {
        return administrateurService.associerCandidatAElectionParAdministrateur(idAdministrateur, election, candidat);
    }

    @PutMapping("/{idAdministrateur}/election-candidat/{idElectionCandidat}")
    public ElectionCandidat updateStatutCandidat(@PathVariable Long idAdministrateur, @PathVariable Long idElectionCandidat) {
        return administrateurService.mettreAJourStatutCandidatParAdministrateur(idAdministrateur, idElectionCandidat);
    }

    @DeleteMapping("/{idAdministrateur}/election-candidat/{idElectionCandidat}")
    public void supprimerAssociation(@PathVariable Long idAdministrateur, @PathVariable Long idElectionCandidat) {
        administrateurService.supprimerAssociationCandidatElectionParAdministrateur(idAdministrateur, idElectionCandidat);
    }

    @GetMapping("/{idAdministrateur}/election-candidat")
    public List<ElectionCandidat> listerAssociations(@PathVariable Long idAdministrateur) {
        return administrateurService.listerToutesAssociationsCandidatElectionParAdministrateur(idAdministrateur);
    }

    @GetMapping("/{idAdministrateur}/election-candidat/{id}")
    public Optional<ElectionCandidat> getAssociation(@PathVariable Long idAdministrateur, @PathVariable Long id) {
        return administrateurService.trouverAssociationParIdParAdministrateur(idAdministrateur, id);
    }


    // ==================== GESTION VOTES ====================



    @PostMapping("/voter")
    public ResponseEntity<Vote> enregistrerEtValiderVoteParAdmin(
        @RequestParam Long electeurId,
        @RequestParam Long electionCandidatId,
        @RequestParam String choix) {
        try {
            Vote vote = voteService.enregistrerEtValiderVote(electeurId, electionCandidatId, choix);
            return ResponseEntity.ok(vote);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @GetMapping("/{idAdministrateur}/votes")
    public List<Vote> getTousLesVotes(@PathVariable Long idAdministrateur) {
        return administrateurService.obtenirTousLesVotesParAdministrateur(idAdministrateur);
    }


    @GetMapping("/{idAdministrateur}/votes/electeur/{idElecteur}")
    public List<Vote> getVotesParElecteur(@PathVariable Long idAdministrateur, @PathVariable Long idElecteur) {
        return administrateurService.obtenirVotesParElecteurParAdministrateur(idAdministrateur, idElecteur);
    }

    @GetMapping("/{idAdministrateur}/votes/candidat/{idCandidat}/compter")
    public Long compterVotesPourCandidat(@PathVariable Long idAdministrateur, @PathVariable Long idCandidat) {
        return administrateurService.compterVotesPourCandidatParAdministrateur(idAdministrateur, idCandidat);
    }

    // ======================== GESTION RESULTAT =============================

    @PostMapping("/{idAdministrateur}/resultats")
    public Resultat creerResultat(@PathVariable Long idAdministrateur, @RequestBody Resultat resultat) {
        return administrateurService.creerResultatParAdministrateur(idAdministrateur, resultat);
    }

    @GetMapping("/{idAdministrateur}/resultats")
    public List<Resultat> getTousLesResultats(@PathVariable Long idAdministrateur) {
        return administrateurService.obtenirTousLesResultatsParAdministrateur(idAdministrateur);
    }

    @GetMapping("/{idAdministrateur}/resultats/{id}")
    public Optional<Resultat> getResultatParId(@PathVariable Long idAdministrateur, @PathVariable Long id) {
        return administrateurService.obtenirResultatParIdParAdministrateur(idAdministrateur, id);
    }

    @PutMapping("/{idAdministrateur}/resultats/{id}")
    public Resultat updateResultat(@PathVariable Long idAdministrateur, @PathVariable Long id, @RequestBody Resultat resultat) {
        return administrateurService.mettreAJourResultatParAdministrateur(idAdministrateur, id, resultat);
    }

    @DeleteMapping("/{idAdministrateur}/resultats/{id}")
    public void deleteResultat(@PathVariable Long idAdministrateur, @PathVariable Long id) {
        administrateurService.supprimerResultatParAdministrateur(idAdministrateur, id);
    }

    @PostMapping("/{idAdministrateur}/resultats/{id}/valider")
    public void validerResultat(@PathVariable Long idAdministrateur, @PathVariable Long id) {
        administrateurService.validerResultatParAdministrateur(idAdministrateur, id);
    }

    @GetMapping("/{idAdministrateur}/resultats/candidat/{idCandidat}")
    public List<Resultat> getResultatsParCandidat(@PathVariable Long idAdministrateur, @PathVariable Long idCandidat) {
        return administrateurService.obtenirResultatsParCandidatParAdministrateur(idAdministrateur, idCandidat);
    }

    @GetMapping("/{idAdministrateur}/resultats/election/{idElection}/classement")
    public List<Resultat> getClassementCandidats(@PathVariable Long idAdministrateur, @PathVariable Long idElection) {
        return administrateurService.obtenirClassementDesCandidatsParAdministrateur(idAdministrateur, idElection);
    }

    @GetMapping("/{idAdministrateur}/resultats/election/{idElection}/taux-participation")
    public double getTauxParticipation(@PathVariable Long idAdministrateur, @PathVariable Long idElection) {
        return administrateurService.calculerTauxParticipationParAdministrateur(idAdministrateur, idElection);
    }

    @GetMapping("/{idAdministrateur}/resultats/election/{idElection}/valide")
    public boolean resultatsValidite(@PathVariable Long idAdministrateur, @PathVariable Long idElection) {
        return administrateurService.verifierResultatsValiditeParAdministrateur(idAdministrateur, idElection);
    }

    // ============================= GESTION UTILISATEUR ============================

    @GetMapping("/{idAdministrateur}/utilisateurs")
    public List<Utilisateur> getTousLesUtilisateurs(@PathVariable Long idAdministrateur) {
        return administrateurService.obtenirTousLesUtilisateursParAdministrateur(idAdministrateur);
    }

    @GetMapping("/{idAdministrateur}/utilisateurs/{idUtilisateur}")
    public Optional<Utilisateur> getUtilisateurParId(@PathVariable Long idAdministrateur, @PathVariable Long idUtilisateur) {
        return administrateurService.obtenirUtilisateurParIdParAdministrateur(idAdministrateur, idUtilisateur);
    }

    @GetMapping("/{idAdministrateur}/utilisateurs/email/{email}")
    public Optional<Utilisateur> getUtilisateurParEmail(@PathVariable Long idAdministrateur, @PathVariable String email) {
        return administrateurService.obtenirUtilisateurParEmailParAdministrateur(idAdministrateur, email);
    }

    @PutMapping("/{idAdministrateur}/utilisateurs/{idUtilisateur}")
    public Utilisateur updateUtilisateur(@PathVariable Long idAdministrateur, @PathVariable Long idUtilisateur, @RequestBody Utilisateur utilisateur) {
        return administrateurService.mettreAJourUtilisateurParAdministrateur(idAdministrateur, idUtilisateur, utilisateur);
    }

    @DeleteMapping("/{idAdministrateur}/utilisateurs/{idUtilisateur}")
    public void deleteUtilisateur(@PathVariable Long idAdministrateur, @PathVariable Long idUtilisateur) {
        administrateurService.supprimerUtilisateurParAdministrateur(idAdministrateur, idUtilisateur);
    }

    @PostMapping("/{idAdministrateur}/utilisateurs/authentifier")
    public boolean authentifierUtilisateur(@PathVariable Long idAdministrateur, @RequestParam String email, @RequestParam String motDePasse) {
        return administrateurService.authentifierUtilisateurParAdministrateur(idAdministrateur, email, motDePasse);
    }

    @PostMapping("/{idAdministrateur}/utilisateurs/support")
    public void traiterDemandeSupport(@PathVariable Long idAdministrateur, @RequestParam String email, @RequestParam String message) {
        administrateurService.traiterDemandeSupportParAdministrateur(idAdministrateur, email, message);
    }

}
