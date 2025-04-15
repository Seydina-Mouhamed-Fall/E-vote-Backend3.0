package com.evote.evotebackend3.seed;

import com.evote.evotebackend3.entities.Electeur;
import com.evote.evotebackend3.entities.ElectionCandidat;
import com.evote.evotebackend3.entities.Vote;
import com.evote.evotebackend3.repository.ElecteurRepository;
import com.evote.evotebackend3.repository.ElectionCandidatRepository;
import com.evote.evotebackend3.repository.VoteRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class VoteSeeder {

    private final VoteRepository voteRepository;
    private final ElecteurRepository electeurRepository;
    private final ElectionCandidatRepository electionCandidatRepository;

    public VoteSeeder(VoteRepository voteRepository, ElecteurRepository electeurRepository, ElectionCandidatRepository electionCandidatRepository) {
        this.voteRepository = voteRepository;
        this.electeurRepository = electeurRepository;
        this.electionCandidatRepository = electionCandidatRepository;
    }

    @PostConstruct
    public void seedVotes() {
        if (voteRepository.count() == 0) {
            Optional<Electeur> electeurOpt = electeurRepository.findById(65L);
            Optional<ElectionCandidat> candidatOpt = electionCandidatRepository.findById(71L);

            if (electeurOpt.isPresent() && candidatOpt.isPresent()) {
                Vote vote = new Vote();
                vote.setChoix("Oui"); // ou "Non", ou un nom de candidat, selon ta logique
                vote.setDateVote(new Date());
                vote.setStatutVote(true);
                vote.setElecteur(electeurOpt.get());
                vote.setElectionCandidat(candidatOpt.get());

                voteRepository.save(vote);

                System.out.println(" Vote seedé avec succès !");
            } else {
                System.out.println(" Electeur ou ElectionCandidat introuvable. Assure-toi que les entités existent.");
            }
        }
    }
}
