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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    private static final String MESSAGE_ELECTEUR_INTROUVABLE = "Électeur introuvable";
    private static final String MESSAGE_ELECTION_CANDIDAT_INTROUVABLE = "Candidat à cette élection introuvable";
    private static final String MESSAGE_VOTE_INTROUVABLE = "Vote introuvable";
    private static final String MESSAGE_ELECTEUR_NON_ELIGIBLE = "L'électeur ne peut pas voter pour ce candidat.";

    private final VoteRepository voteRepository;
    private final CandidatRepository candidatRepository;
    private final ElecteurRepository electeurRepository;
    private final ElectionCandidatRepository electionCandidatRepository;
    private final ElecteurService electeurService;

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

    @Transactional
    public Vote enregistrerVote(Long electeurId, Long electionCandidatId, String choix) {
        Electeur electeur = findElecteurById(electeurId);
        ElectionCandidat electionCandidat = findElectionCandidatById(electionCandidatId);

        if (!electeurService.estEligiblePourVoter(electeurId)) {
            throw new RuntimeException(MESSAGE_ELECTEUR_NON_ELIGIBLE);
        }

        Vote vote = new Vote();
        vote.setElecteur(electeur);
        vote.setElectionCandidat(electionCandidat);
        vote.setChoix(choix);
        vote.setDateVote(new Date());
        vote.setStatutVote(false);

        return voteRepository.save(vote);
    }

    @Transactional
    public void validerVote(Long voteId) {
        Vote vote = findVoteById(voteId);
        vote.setStatutVote(true);
        voteRepository.save(vote);
        electeurService.marquerCommeAyantVote(vote.getElecteur().getIdUtilisateur());
    }

    @Transactional
    public void rejeterVote(Long voteId) {
        Vote vote = findVoteById(voteId);
        vote.setStatutVote(false);
        voteRepository.save(vote);
    }

    public boolean peutVoter(Long electeurId, Long electionCandidatId) {
        return electeurRepository.findById(electeurId).isPresent()
            && electionCandidatRepository.findById(electionCandidatId).isPresent()
            && electeurService.estEligiblePourVoter(electeurId);
    }

    public List<Vote> afficherVotesElecteur(Electeur electeur) {
        return voteRepository.findByElecteur(electeur);
    }

    public long compterVotesPourCandidat(Candidat candidat) {
        return voteRepository.countByElectionCandidat_Candidat(candidat);
    }

    public List<Vote> listerTousLesVotes() {
        return voteRepository.findAll();
    }

    public Optional<Vote> obtenirVoteParId(Long voteId) {
        return voteRepository.findById(voteId);
    }

    // Méthodes utilitaires privées pour factoriser la récupération avec gestion d’erreur
    private Electeur findElecteurById(Long id) {
        return electeurRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(MESSAGE_ELECTEUR_INTROUVABLE));
    }

    private ElectionCandidat findElectionCandidatById(Long id) {
        return electionCandidatRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(MESSAGE_ELECTION_CANDIDAT_INTROUVABLE));
    }

    private Vote findVoteById(Long id) {
        return voteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(MESSAGE_VOTE_INTROUVABLE));
    }
}

