package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.Candidat;
import com.evote.evotebackend3.repository.CandidatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatService {

    @Autowired
    private CandidatRepository candidatRepository;

    // Ajouter un candidat
    public Candidat ajouterCandidat(Candidat candidat) {
        return candidatRepository.save(candidat);
    }

    // Obtenir un candidat par son ID
    public Optional<Candidat> obtenirCandidat(Long id) {
        return candidatRepository.findById(id);
    }

    // Lister tous les candidats
    public List<Candidat> listerCandidats() {
        return candidatRepository.findAll();
    }

    // Supprimer un candidat
    public void supprimerCandidat(Long id) {
        if (candidatRepository.existsById(id)) {
            candidatRepository.deleteById(id);
        } else {
            throw new RuntimeException("Candidat avec l'ID " + id + " n'existe pas.");
        }
    }

    // Mise à jour d'un candidat
    public Candidat mettreAJourCandidat(Long id, Candidat candidatDetails) {
        Optional<Candidat> candidatOptional = candidatRepository.findById(id);
        if (candidatOptional.isPresent()) {
            Candidat candidat = candidatOptional.get();
            candidat.setNom(candidatDetails.getNom());
            candidat.setPrenom(candidatDetails.getPrenom());
            candidat.setEmail(candidatDetails.getEmail());
            candidat.setMotDePasse(candidatDetails.getMotDePasse());
            candidat.setDateInscription(candidatDetails.getDateInscription());
            candidat.setParti(candidatDetails.getParti());
            candidat.setBiographie(candidatDetails.getBiographie());
            candidat.setStatutElection(candidatDetails.getStatutElection());
            return candidatRepository.save(candidat);
        }
        throw new RuntimeException("Candidat avec l'ID " + id + " n'existe pas.");
    }
}

