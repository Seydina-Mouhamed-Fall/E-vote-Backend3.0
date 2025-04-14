package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.*;
import com.evote.evotebackend3.repository.AdministrateurRepository;
import com.evote.evotebackend3.repository.ElectionRepository;
import com.evote.evotebackend3.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdministrateurService {

    // ON INJECTE LES BEANS PAR CONSTRUCTEUR
    private final AdministrateurRepository administrateurRepository;
    private final ElectionService electionService;
    private final CandidatService candidatService;
    private final ElecteurService electeurService;
    private final ResultatService resultatService;
    private final VoteService voteService;
    private final ElectionCandidatService electionCandidatService;
    private final ElectionRepository electionRepository;
    private final UtilisateurService utilisateurService;

    public AdministrateurService(
        AdministrateurRepository administrateurRepository,
        ElectionService electionService,
        CandidatService candidatService,
        ElecteurService electeurService,
        ResultatService resultatService,
        VoteService voteService,
        ElectionCandidatService electionCandidatService,
        ElectionRepository electionRepository,
        UtilisateurService utilisateurService
    ) {
        this.administrateurRepository = administrateurRepository;
        this.electionService = electionService;
        this.candidatService = candidatService;
        this.electeurService = electeurService;
        this.resultatService = resultatService;
        this.voteService = voteService;
        this.electionCandidatService = electionCandidatService;
        this.electionRepository = electionRepository;
        this.utilisateurService = utilisateurService;
    }


    // ==================== GESTION ADMINISTRATEUR ====================

    public Administrateur creerAdministrateur(Administrateur admin) {
        return administrateurRepository.save(admin);
    }

    public List<Administrateur> obtenirTousLesAdministrateurs() {
        return administrateurRepository.findAll();
    }

    public Optional<Administrateur> obtenirAdministrateurParId(Long id) {
        return administrateurRepository.findById(id);
    }

    public Administrateur mettreAJourAdministrateur(Long id, Administrateur details) {
        return administrateurRepository.findById(id).map(admin -> {
            admin.setNom(details.getNom());
            admin.setPrenom(details.getPrenom());
            admin.setEmail(details.getEmail());
            admin.setMotDePasse(details.getMotDePasse());
            return administrateurRepository.save(admin);
        }).orElseThrow(() -> new RuntimeException("Administrateur introuvable"));
    }

    public void supprimerAdministrateur(Long id) {
        administrateurRepository.deleteById(id);
    }

    // ==================== GESTION ÉLECTION ====================

    // Créer une élection en tant qu’administrateur
    public Election creerElectionParAdministrateur(Long idAdministrateur, Election election) {
        Optional<Administrateur> adminOpt = administrateurRepository.findById(idAdministrateur);

        if (adminOpt.isPresent()) {
            // On peut ici associer l'admin à l'élection si nécessaire
            // election.setCreePar(adminOpt.get()); // si champ dispo
            return electionService.creerElection(election);
        } else {
            throw new RuntimeException("Administrateur introuvable avec l’ID : " + idAdministrateur);
        }
    }

    public Election cloturerElection(Long idElection) {
        // Vérifier si l'élection existe
        Election election = electionRepository.findById(idElection)
            .orElseThrow(() -> new RuntimeException("Élection introuvable avec l'ID : " + idElection));

        // Vérifier si la date actuelle est après la date de fin de l'élection
        if (LocalDateTime.now().isBefore(election.getDateFin())) {
            throw new RuntimeException("L'élection ne peut pas être clôturée avant la date de fin.");
        }

        // Mettre à jour le statut ou d'autres champs pour marquer l'élection comme clôturée
        election.setCloturee(true);

        // Sauvegarder les modifications
        return electionRepository.save(election);
    }

    // Supprimer une élection en tant qu’administrateur
    public void supprimerElectionParAdministrateur(Long idAdministrateur, Long idElection) {
        Optional<Administrateur> adminOpt = administrateurRepository.findById(idAdministrateur);

        if (adminOpt.isPresent()) {
            electionService.supprimerElection(idElection);
        } else {
            throw new RuntimeException("Administrateur introuvable avec l’ID : " + idAdministrateur);
        }
    }

// ==================== GESTION CANDIDATS ====================

    private void verifierAdministrateurExistant(Long idAdministrateur) {
        if (!administrateurRepository.existsById(idAdministrateur)) {
            throw new RuntimeException("Administrateur introuvable avec l’ID : " + idAdministrateur);
        }
    }


    //  Ajouter un candidat
    public Candidat ajouterCandidatParAdministrateur(Long idAdministrateur, Candidat candidat) {
        verifierAdministrateurExistant(idAdministrateur);
        return candidatService.ajouterCandidat(candidat);
    }

    //  Obtenir un candidat par son ID
    public Optional<Candidat> obtenirCandidatParAdministrateur(Long idAdministrateur, Long idCandidat) {
        verifierAdministrateurExistant(idAdministrateur);
        return candidatService.obtenirCandidat(idCandidat);
    }

    // Lister tous les candidats
    public List<Candidat> listerCandidatsParAdministrateur(Long idAdministrateur) {
        verifierAdministrateurExistant(idAdministrateur);
        return candidatService.listerCandidats();
    }

    //  Supprimer un candidat
    public void supprimerCandidatParAdministrateur(Long idAdministrateur, Long idCandidat) {
        verifierAdministrateurExistant(idAdministrateur);
        candidatService.supprimerCandidat(idCandidat);
    }

    //  Mettre à jour un candidat
    public Candidat mettreAJourCandidatParAdministrateur(Long idAdministrateur, Long idCandidat, Candidat candidatDetails) {
        verifierAdministrateurExistant(idAdministrateur);
        return candidatService.mettreAJourCandidat(idCandidat, candidatDetails);
    }


    /// ===================== GESTION ELECTEURS ====================================

    // Ajouter un électeur
    public Electeur ajouterElecteurParAdministrateur(Long idAdministrateur, Electeur electeur) {
        verifierAdministrateurExistant(idAdministrateur);
        return electeurService.ajouterElecteur(electeur);
    }

    //  Obtenir un électeur par ID
    public Optional<Electeur> trouverElecteurParIdParAdministrateur(Long idAdministrateur, Long idElecteur) {
        verifierAdministrateurExistant(idAdministrateur);
        return electeurService.trouverElecteurParId(idElecteur);
    }

    //  Obtenir un électeur par email
    public Optional<Electeur> trouverElecteurParEmailParAdministrateur(Long idAdministrateur, String email) {
        verifierAdministrateurExistant(idAdministrateur);
        return electeurService.trouverElecteurParEmail(email);
    }

    //  Lister tous les électeurs
    public List<Electeur> listerElecteursParAdministrateur(Long idAdministrateur) {
        verifierAdministrateurExistant(idAdministrateur);
        return electeurService.listerElecteurs();
    }

    //  Mettre à jour un électeur
    public Electeur mettreAJourElecteurParAdministrateur(Long idAdministrateur, Long idElecteur, Electeur electeurDetails) {
        verifierAdministrateurExistant(idAdministrateur);
        return electeurService.mettreAJourElecteur(idElecteur, electeurDetails);
    }

    // Supprimer un électeur
    public void supprimerElecteurParAdministrateur(Long idAdministrateur, Long idElecteur) {
        verifierAdministrateurExistant(idAdministrateur);
        electeurService.supprimerElecteur(idElecteur);
    }

    //  Vérifier si un électeur est éligible au vote
    public boolean verifierEligibiliteElecteurParAdministrateur(Long idAdministrateur, Long idElecteur) {
        verifierAdministrateurExistant(idAdministrateur);
        return electeurService.estEligiblePourVoter(idElecteur);
    }

    //  Marquer un électeur comme ayant voté (action exceptionnelle, mais dispo pour l’admin)
    public void marquerElecteurCommeAyantVoteParAdministrateur(Long idAdministrateur, Long idElecteur) {
        verifierAdministrateurExistant(idAdministrateur);
        electeurService.marquerCommeAyantVote(idElecteur);
    }

    /// ==================== GESTION ELECTIONCANDIDAT ====================================

    //  Associer un candidat à une élection
    public ElectionCandidat associerCandidatAElectionParAdministrateur(Long idAdministrateur, Election election, Candidat candidat) {
        verifierAdministrateurExistant(idAdministrateur);
        return electionCandidatService.associerCandidatAElection(election, candidat);
    }

    // Mettre à jour le statut du candidat dans une élection
    public ElectionCandidat mettreAJourStatutCandidatParAdministrateur(Long idAdministrateur, Long idElectionCandidat) {
        verifierAdministrateurExistant(idAdministrateur);
        return electionCandidatService.mettreAJourStatut(idElectionCandidat);
    }

    //  Supprimer une association candidat-élection
    public void supprimerAssociationCandidatElectionParAdministrateur(Long idAdministrateur, Long idElectionCandidat) {
        verifierAdministrateurExistant(idAdministrateur);
        electionCandidatService.supprimerAssociation(idElectionCandidat);
    }

    // ✅ Lister toutes les associations élection-candidat
    public List<ElectionCandidat> listerToutesAssociationsCandidatElectionParAdministrateur(Long idAdministrateur) {
        verifierAdministrateurExistant(idAdministrateur);
        return electionCandidatService.listerToutesLesAssociations();
    }

    // Trouver une association spécifique par son ID
    public Optional<ElectionCandidat> trouverAssociationParIdParAdministrateur(Long idAdministrateur, Long id) {
        verifierAdministrateurExistant(idAdministrateur);
        return electionCandidatService.trouverParId(id);
    }

    // Lister tous les candidats d'une élection
    public List<ElectionCandidat> listerCandidatsParElectionParAdministrateur(Long idAdministrateur, Election election) {
        verifierAdministrateurExistant(idAdministrateur);
        return electionCandidatService.listerCandidatsParElection(election);
    }

    ///  ======================== GESTION RESULTAT ===================================
//  Créer un résultat
    public Resultat creerResultatParAdministrateur(Long idAdministrateur, Resultat resultat) {
        verifierAdministrateurExistant(idAdministrateur);
        return resultatService.creerResultat(resultat);
    }

    //  Obtenir tous les résultats
    public List<Resultat> obtenirTousLesResultatsParAdministrateur(Long idAdministrateur) {
        verifierAdministrateurExistant(idAdministrateur);
        return resultatService.obtenirTousLesResultats();
    }

    //  Obtenir un résultat par ID
    public Optional<Resultat> obtenirResultatParIdParAdministrateur(Long idAdministrateur, Long id) {
        verifierAdministrateurExistant(idAdministrateur);
        return resultatService.obtenirResultatParId(id);
    }

    //  Mettre à jour un résultat
    public Resultat mettreAJourResultatParAdministrateur(Long idAdministrateur, Long id, Resultat resultatDetails) {
        verifierAdministrateurExistant(idAdministrateur);
        return resultatService.mettreAJourResultat(id, resultatDetails);
    }

    //  Supprimer un résultat
    public void supprimerResultatParAdministrateur(Long idAdministrateur, Long id) {
        verifierAdministrateurExistant(idAdministrateur);
        resultatService.supprimerResultat(id);
    }

    //  Valider un résultat
    public void validerResultatParAdministrateur(Long idAdministrateur, Long idResultat) {
        verifierAdministrateurExistant(idAdministrateur);
        resultatService.validerResultat(idResultat);
    }

    //  Obtenir les résultats d’un candidat
    public List<Resultat> obtenirResultatsParCandidatParAdministrateur(Long idAdministrateur, Long idCandidat) {
        verifierAdministrateurExistant(idAdministrateur);
        return resultatService.obtenirResultatsParCandidat(idCandidat);
    }

    //  Obtenir le classement des candidats d’une élection
    public List<Resultat> obtenirClassementDesCandidatsParAdministrateur(Long idAdministrateur, Long idElection) {
        verifierAdministrateurExistant(idAdministrateur);
        return resultatService.obtenirClassementDesCandidats(idElection);
    }

    //  Calculer le taux de participation d’une élection
    public double calculerTauxParticipationParAdministrateur(Long idAdministrateur, Long idElection) {
        verifierAdministrateurExistant(idAdministrateur);
        return resultatService.calculerTauxParticipation(idElection);
    }

    // Vérifier si les résultats d’une élection sont tous validés
    public boolean verifierResultatsValiditeParAdministrateur(Long idAdministrateur, Long idElection) {
        verifierAdministrateurExistant(idAdministrateur);
        return resultatService.verifierSiResultatsValides(idElection);
    }

    // ============================= GESTION UTILISATEUR ==========================

    //  Obtenir tous les utilisateurs
    public List<Utilisateur> obtenirTousLesUtilisateursParAdministrateur(Long idAdministrateur) {
        verifierAdministrateurExistant(idAdministrateur);
        return utilisateurService.obtenirTousLesUtilisateurs();
    }

    //  Obtenir un utilisateur par ID
    public Optional<Utilisateur> obtenirUtilisateurParIdParAdministrateur(Long idAdministrateur, Long idUtilisateur) {
        verifierAdministrateurExistant(idAdministrateur);
        return utilisateurService.obtenirUtilisateurParId(idUtilisateur);
    }

    //  Obtenir un utilisateur par email
    public Optional<Utilisateur> obtenirUtilisateurParEmailParAdministrateur(Long idAdministrateur, String email) {
        verifierAdministrateurExistant(idAdministrateur);
        return utilisateurService.obtenirUtilisateurParEmail(email);
    }

    // Mettre à jour un utilisateur
    public Utilisateur mettreAJourUtilisateurParAdministrateur(Long idAdministrateur, Long idUtilisateur, Utilisateur utilisateurDetails) {
        verifierAdministrateurExistant(idAdministrateur);
        return utilisateurService.mettreAJourUtilisateur(idUtilisateur, utilisateurDetails);
    }

    //  Supprimer un utilisateur
    public void supprimerUtilisateurParAdministrateur(Long idAdministrateur, Long idUtilisateur) {
        verifierAdministrateurExistant(idAdministrateur);
        utilisateurService.supprimerUtilisateur(idUtilisateur);
    }

    // Authentifier un utilisateur (utile si l’admin veut tester un accès par email/mot de passe)
    public boolean authentifierUtilisateurParAdministrateur(Long idAdministrateur, String email, String motDePasse) {
        verifierAdministrateurExistant(idAdministrateur);
        return utilisateurService.authentifierUtilisateur(email, motDePasse);
    }

    //  Gérer une demande de support pour un utilisateur
    public void traiterDemandeSupportParAdministrateur(Long idAdministrateur, String email, String message) {
        verifierAdministrateurExistant(idAdministrateur);
        utilisateurService.demanderSupport(email, message);
    }

    //====================================== GESTION VOTE ==========================================

    //  Lister tous les votes
    public List<Vote> obtenirTousLesVotesParAdministrateur(Long idAdministrateur) {
        verifierAdministrateurExistant(idAdministrateur);
        return voteService.listerTousLesVotes();
    }

    //  Obtenir un vote par son ID
    public Optional<Vote> obtenirVoteParIdParAdministrateur(Long idAdministrateur, Long voteId) {
        verifierAdministrateurExistant(idAdministrateur);
        return voteService.obtenirVoteParId(voteId);
    }


    //  Vérifier si un électeur peut voter
    public boolean verifierEligibiliteVoteParAdministrateur(Long idAdministrateur, Long electeurId, Long electionCandidatId) {
        verifierAdministrateurExistant(idAdministrateur);
        return voteService.peutVoter(electeurId, electionCandidatId);
    }

    //  Afficher tous les votes d’un électeur
    public List<Vote> obtenirVotesParElecteurParAdministrateur(Long idAdministrateur, Long idElecteur) {
        verifierAdministrateurExistant(idAdministrateur);
        Electeur electeur = electeurService.trouverElecteurParId(idElecteur)
            .orElseThrow(() -> new RuntimeException("Électeur introuvable avec l'ID : " + idElecteur));
        return voteService.afficherVotesElecteur(electeur);
    }

    //  Compter les votes pour un candidat
    public long compterVotesPourCandidatParAdministrateur(Long idAdministrateur, Long idCandidat) {
        verifierAdministrateurExistant(idAdministrateur);
        Candidat candidat = candidatService.obtenirCandidat(idCandidat)
            .orElseThrow(() -> new RuntimeException("Candidat introuvable avec l'ID : " + idCandidat));
        return voteService.compterVotesPourCandidat(candidat);
    }


}
