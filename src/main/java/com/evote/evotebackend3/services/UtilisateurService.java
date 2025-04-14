package com.evote.evotebackend3.services;


import com.evote.evotebackend3.entities.Utilisateur;
import com.evote.evotebackend3.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Création ou mise à jour d'un utilisateur (création ou modification)
    @Transactional
    public Utilisateur enregistrerUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    // Récupérer tous les utilisateurs
    public List<Utilisateur> obtenirTousLesUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    // Récupérer un utilisateur par son ID
    public Optional<Utilisateur> obtenirUtilisateurParId(Long id) {
        return utilisateurRepository.findById(id);
    }

    // Récupérer un utilisateur par son email
    public Optional<Utilisateur> obtenirUtilisateurParEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    // Mise à jour d'un utilisateur
    @Transactional
    public Utilisateur mettreAJourUtilisateur(Long id, Utilisateur utilisateurDetails) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        utilisateur.setNom(utilisateurDetails.getNom());
        utilisateur.setPrenom(utilisateurDetails.getPrenom());
        utilisateur.setEmail(utilisateurDetails.getEmail());
        utilisateur.setMotDePasse(utilisateurDetails.getMotDePasse());
        utilisateur.setDateInscription(utilisateurDetails.getDateInscription());
        return utilisateurRepository.save(utilisateur);
    }

    // Suppression d'un utilisateur
    @Transactional
    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        utilisateurRepository.delete(utilisateur);
    }

    // Méthode pour authentifier un utilisateur
    public boolean authentifierUtilisateur(String email, String motDePasse) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(email);
        if (utilisateurOptional.isPresent()) {
            return utilisateurOptional.get().sAuthentifier(email, motDePasse);
        } else {
            throw new RuntimeException("Utilisateur non trouvé");
        }
    }

//    // Logique de déconnexion
//    public void deconnecterUtilisateur(String email) {
//        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(email);
//        utilisateurOptional.ifPresent(Utilisateur::seDeconnecter);
//    }

    // Logique pour demander un support
    public void demanderSupport(String email, String message) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByEmail(email);
        utilisateurOptional.ifPresent(utilisateur -> utilisateur.demanderSupport(message));
    }
}
