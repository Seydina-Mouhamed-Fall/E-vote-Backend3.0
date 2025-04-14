package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.Candidat;
import com.evote.evotebackend3.entities.Electeur;
import com.evote.evotebackend3.entities.ElectionCandidat;
import com.evote.evotebackend3.entities.Vote;
import com.evote.evotebackend3.repository.CandidatRepository;
import com.evote.evotebackend3.repository.ElecteurRepository;
import com.evote.evotebackend3.repository.ElectionCandidatRepository;
import com.evote.evotebackend3.repository.VoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final CandidatRepository candidatRepository;
    private final ElecteurRepository electeurRepository;
    private final ElectionCandidatRepository electionCandidatRepository;
    private final ElecteurService electeurService; // Dépendance sur ElecteurService

    // Constructeur pour injecter les repositories et le service ElecteurService
    @Autowired
    public VoteService(VoteRepository voteRepository,
                       CandidatRepository candidatRepository,
                       ElecteurRepository electeurRepository,
                       ElectionCandidatRepository electionCandidatRepository,
                       ElecteurService electeurService) {
        this.voteRepository = voteRepository;
        this.candidatRepository = candidatRepository;
        this.electeurRepository = electeurRepository;
        this.electionCandidatRepository = electionCandidatRepository;
        this.electeurService = electeurService;
    }

    // Enregistrer un nouveau vote
    @Transactional
    public Vote enregistrerVote(Long electeurId, Long electionCandidatId, String choix) {
        Electeur electeur = electeurRepository.findById(electeurId).orElseThrow(() -> new RuntimeException("Électeur introuvable"));
        ElectionCandidat electionCandidat = electionCandidatRepository.findById(electionCandidatId).orElseThrow(() -> new RuntimeException("Candidat à cette election introuvable"));

        // Vérifier si l'électeur peut voter
        if (!electeurService.estEligiblePourVoter(electeurId)) {
            throw new RuntimeException("L'électeur ne peut pas voter pour ce candidat.");
        }

        // Créer le vote
        Vote vote = new Vote();
        vote.setElecteur(electeur);
        vote.setElectionCandidat(electionCandidat);
        vote.setChoix(choix);
        vote.setDateVote(new java.util.Date());
        vote.setStatutVote(false); // initialement le vote n'est pas validé
        return voteRepository.save(vote);
    }

    // Valider un vote
    @Transactional
    public void validerVote(Long voteId) {
        Optional<Vote> voteOptional = voteRepository.findById(voteId);
        if (voteOptional.isPresent()) {
            Vote vote = voteOptional.get();
            vote.setStatutVote(true);
            voteRepository.save(vote);
            // Marquer l'électeur comme ayant voté
            electeurService.marquerCommeAyantVote(vote.getElecteur().getIdUtilisateur());
        } else {
            throw new RuntimeException("Vote introuvable");
        }
    }

    // Rejeter un vote
    @Transactional
    public void rejeterVote(Long voteId) {
        Optional<Vote> voteOptional = voteRepository.findById(voteId);
        if (voteOptional.isPresent()) {
            Vote vote = voteOptional.get();
            vote.setStatutVote(false);
            voteRepository.save(vote);
        } else {
            throw new RuntimeException("Vote introuvable");
        }
    }

    // Vérifier si l'électeur peut voter pour un candidat
    public boolean peutVoter(Long electeurId, Long electionCandidatId) {
        Electeur electeur = electeurRepository.findById(electeurId).orElse(null);
        ElectionCandidat electionCandidat = electionCandidatRepository.findById(electionCandidatId).orElse(null);

        if (electeur == null || electionCandidat == null) {
            return false;
        }

        // Utiliser la méthode de ElecteurService pour vérifier l'éligibilité de l'électeur
        return electeurService.estEligiblePourVoter(electeurId);
    }

    // Afficher les votes d'un électeur spécifique
    public List<Vote> afficherVotesElecteur(Electeur electeur) {
        return voteRepository.findByElecteur(electeur);
    }

    // Compter le nombre de votes pour un candidat
    public long compterVotesPourCandidat(Candidat candidat) {
        return voteRepository.countByElectionCandidat_Candidat(candidat);
    }

    // Obtenir tous les votes
    public List<Vote> listerTousLesVotes() {
        return voteRepository.findAll();
    }

    // Obtenir un vote spécifique par son ID
    public Optional<Vote> obtenirVoteParId(Long voteId) {
        return voteRepository.findById(voteId);
    }
}
