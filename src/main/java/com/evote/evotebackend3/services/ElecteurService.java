package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.Electeur;
import com.evote.evotebackend3.repository.ElecteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElecteurService {

    private final ElecteurRepository electeurRepository;

    @Autowired
    public ElecteurService(ElecteurRepository electeurRepository) {
        this.electeurRepository = electeurRepository;
    }

    // CREATE : Ajouter un nouvel électeur
    public Electeur ajouterElecteur(Electeur electeur) {
        return electeurRepository.save(electeur);
    }

    // READ : Rechercher un électeur par ID
    public Optional<Electeur> trouverElecteurParId(Long id) {
        return electeurRepository.findById(id);
    }

    // READ : Rechercher un électeur par email
    public Optional<Electeur> trouverElecteurParEmail(String email) {
        return electeurRepository.findByEmail(email);
    }

    // READ : Lister tous les électeurs
    public List<Electeur> listerElecteurs() {
        return electeurRepository.findAll();
    }

    // UPDATE : Modifier les infos d’un électeur
    public Electeur mettreAJourElecteur(Long id, Electeur electeurDetails) {
        Optional<Electeur> electeurExistant = electeurRepository.findById(id);
        if (electeurExistant.isPresent()) {
            Electeur e = electeurExistant.get();
            e.setNom(electeurDetails.getNom());
            e.setPrenom(electeurDetails.getPrenom());
            e.setEmail(electeurDetails.getEmail());
            e.setMotDePasse(electeurDetails.getMotDePasse());
            e.setDateInscription(electeurDetails.getDateInscription());
            e.setStatutEligibilite(electeurDetails.isStatutEligibilite());
            e.setStatutVote(electeurDetails.isStatutVote());
            e.setNumCNI(electeurDetails.getNumCNI());
            e.setNumTel(electeurDetails.getNumTel());
            return electeurRepository.save(e);
        }
        throw new RuntimeException("Électeur avec ID " + id + " introuvable.");
    }

    // DELETE : Supprimer un électeur
    public void supprimerElecteur(Long id) {
        if (electeurRepository.existsById(id)) {
            electeurRepository.deleteById(id);
        } else {
            throw new RuntimeException("Électeur avec ID " + id + " introuvable.");
        }
    }

    // MÉTIER : Vérifier si un électeur est éligible pour voter
    public boolean estEligiblePourVoter(Long id) {
        return electeurRepository.findById(id)
            .map(e -> e.isStatutEligibilite() && !e.isStatutVote())
            .orElse(false);
    }

    // MÉTIER : Marquer un électeur comme ayant voté
    // Appelé uniquement depuis le VoteService
    public void marquerCommeAyantVote(Long id) {
        Optional<Electeur> electeurOptional = electeurRepository.findById(id);
        if (electeurOptional.isPresent()) {
            Electeur e = electeurOptional.get();
            e.setStatutVote(true);
            electeurRepository.save(e);
        } else {
            throw new RuntimeException("Électeur avec ID " + id + " introuvable.");
        }
    }

}
