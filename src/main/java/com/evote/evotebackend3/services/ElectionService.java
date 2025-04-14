package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.Election;
import com.evote.evotebackend3.repository.ElectionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ElectionService {

    private final ElectionRepository electionRepository;

    public ElectionService(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    // Créer une élection
    public Election creerElection(Election election) {
        return electionRepository.save(election);
    }

    // Obtenir une élection par ID
    public Optional<Election> obtenirElectionParId(Long id) {
        return electionRepository.findById(id);
    }

    public Election mettreAJourElection(Long id, Election updatedElection) {
        Election election = electionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Élection non trouvée avec l'ID : " + id));

        election.setNom(updatedElection.getNom());
        election.setDateDebut(updatedElection.getDateDebut());
        election.setDateFin(updatedElection.getDateFin());
        // Ajoute ici d'autres champs modifiables si nécessaire

        return electionRepository.save(election);
    }


    // Supprimer une élection
    public void supprimerElection(Long id) {
        if (!electionRepository.existsById(id)) {
            throw new RuntimeException("Élection introuvable.");
        }
        electionRepository.deleteById(id);
    }

    // Lister toutes les élections
    public List<Election> listerElections() {
        return electionRepository.findAll();
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
        election.setCloturee(true); // Assure-toi d'avoir un champ 'cloturee' ou équivalent dans ton entité Election.

        // Sauvegarder les modifications
        return electionRepository.save(election);
    }
}

