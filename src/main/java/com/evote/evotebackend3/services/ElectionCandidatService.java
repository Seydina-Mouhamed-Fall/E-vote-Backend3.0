package com.evote.evotebackend3.services;


import com.evote.evotebackend3.entities.Candidat;
import com.evote.evotebackend3.entities.Election;
import com.evote.evotebackend3.entities.ElectionCandidat;
import com.evote.evotebackend3.repository.ElectionCandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElectionCandidatService {

    @Autowired
    private ElectionCandidatRepository electionCandidatRepository;

    // ✅ Associer un candidat à une élection (avec création si pas déjà existant)
    public ElectionCandidat associerCandidatAElection(Election election, Candidat candidat) {
        return electionCandidatRepository.findByElectionAndCandidat(election, candidat)
            .orElseGet(() -> {
                ElectionCandidat ec = new ElectionCandidat();
                ec.setElection(election);
                ec.setCandidat(candidat);
                ec.setNombreDeVotes(0);
                ec.setStatutCandidat(false);
                return electionCandidatRepository.save(ec);
            });
    }


    //  Mettre à jour le statut du candidat indépendamment
    public ElectionCandidat mettreAJourStatut(Long idElectionCandidat) {
        ElectionCandidat ec = electionCandidatRepository.findById(idElectionCandidat)
            .orElseThrow(() -> new RuntimeException("Candidat non trouvé dans l'élection"));

        ec.mettreAjourStatut();
        return electionCandidatRepository.save(ec);
    }

    //  Supprimer un candidat d'une élection
    public void supprimerAssociation(Long idElectionCandidat) {
        if (!electionCandidatRepository.existsById(idElectionCandidat)) {
            throw new RuntimeException("Association élection-candidat introuvable");
        }
        electionCandidatRepository.deleteById(idElectionCandidat);
    }

    //Lister toutes les associations élection-candidat
    public List<ElectionCandidat> listerToutesLesAssociations() {
        return electionCandidatRepository.findAll();
    }

    //  Trouver une association précise
    public Optional<ElectionCandidat> trouverParId(Long id) {
        return electionCandidatRepository.findById(id);
    }

    //  Récupérer les candidats d'une élection (optionnel, mais utile côté admin/affichage)
    public List<ElectionCandidat> listerCandidatsParElection(Election election) {
        return electionCandidatRepository.findByElection(election);
    }
}
