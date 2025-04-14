package com.evote.evotebackend3.seed;

import com.evote.evotebackend3.entities.Administrateur;
import com.evote.evotebackend3.entities.Utilisateur;
import com.evote.evotebackend3.repository.AdministrateurRepository;
import com.evote.evotebackend3.repository.UtilisateurRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdministrateurSeeder {

    private final AdministrateurRepository administrateurRepository;
    private final UtilisateurRepository utilisateurRepository;

    public AdministrateurSeeder(AdministrateurRepository administrateurRepository,
                                UtilisateurRepository utilisateurRepository) {
        this.administrateurRepository = administrateurRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostConstruct
    public void seedAdministrateur() {
        Long adminId = 5L; // ID de Seydina Mouhamed Fall dans la table utilisateur

        // Vérifie s’il est déjà admin
        if (administrateurRepository.existsById(adminId)) {
            System.out.println("L'utilisateur avec l'ID 5 est déjà administrateur.");
            return;
        }

        // Recherche de l'utilisateur
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(adminId);

        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();

            // Création de l'admin avec les mêmes données
            Administrateur admin = new Administrateur(
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getMotDePasse(),
                utilisateur.getDateInscription()
            );

            // Reprend l'ID existant
            admin.setId(utilisateur.getId());

            // Sauvegarde dans la table administrateur
            administrateurRepository.save(admin);
            System.out.println("Seydina Mouhamed Fall a été défini comme administrateur.");
        } else {
            System.out.println("Aucun utilisateur avec l'ID 5 trouvé.");
        }
    }
}
