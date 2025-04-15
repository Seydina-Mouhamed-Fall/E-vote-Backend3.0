package com.evote.evotebackend3.seed;

import com.evote.evotebackend3.entities.Administrateur;
import com.evote.evotebackend3.entities.Candidat;
import com.evote.evotebackend3.entities.Election;
import com.evote.evotebackend3.entities.Resultat;
import com.evote.evotebackend3.repository.AdministrateurRepository;
import com.evote.evotebackend3.repository.CandidatRepository;
import com.evote.evotebackend3.repository.ElectionRepository;
import com.evote.evotebackend3.repository.ResultatRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ResultatSeeder {

    private final ResultatRepository resultatRepository;
    private final CandidatRepository candidatRepository;
    private final ElectionRepository electionRepository;
    private final AdministrateurRepository administrateurRepository;

    public ResultatSeeder(ResultatRepository resultatRepository,
                          CandidatRepository candidatRepository,
                          ElectionRepository electionRepository,
                          AdministrateurRepository administrateurRepository) {
        this.resultatRepository = resultatRepository;
        this.candidatRepository = candidatRepository;
        this.electionRepository = electionRepository;
        this.administrateurRepository = administrateurRepository;
    }

    @PostConstruct
    public void seedResultat() {
        if (resultatRepository.count() == 0) {
            Optional<Candidat> candidatOpt = candidatRepository.findById(1L);
            Optional<Election> electionOpt = electionRepository.findById(1L);
            Optional<Administrateur> adminOpt = administrateurRepository.findById(5L);

            if (candidatOpt.isPresent() && electionOpt.isPresent() && adminOpt.isPresent()) {
                Resultat resultat = new Resultat();
                resultat.setTotalVotes(100);
                resultat.setStatutValidation(true);
                resultat.setNombreVoix(55);
                resultat.setPosition(1);
                resultat.setCandidat(candidatOpt.get());
                resultat.setElection(electionOpt.get());
                resultat.setAdministrateur(adminOpt.get());

                resultatRepository.save(resultat);
                System.out.println(" Résultat seedé avec succès !");
            } else {
                System.out.println("❌ Impossible de seeder Resultat : Candidat, Election ou Admin introuvable.");
            }
        }
    }
}

