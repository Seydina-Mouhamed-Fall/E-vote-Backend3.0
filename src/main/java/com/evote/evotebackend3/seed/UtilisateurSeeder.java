package com.evote.evotebackend3.seed;

import com.evote.evotebackend3.entities.Candidat;
import com.evote.evotebackend3.entities.Utilisateur;
import com.evote.evotebackend3.repository.CandidatRepository;
import com.evote.evotebackend3.repository.UtilisateurRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class UtilisateurSeeder {


    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurSeeder(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostConstruct
    public void seedUtilisateurs() {
        if (utilisateurRepository.count() == 0) {
            List <Utilisateur> utilisateurs = Arrays.asList(
                new Utilisateur("Bouba", "Yasmine", "yasmine.bouba@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Tine", "Luc Eugénie", "luc.eugenie.tine@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Diop", "Yacine", "yacine.diop@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Tazi", "Samir", "samir.tazi@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Fall", "Seydina Mouhamed", "seydina.fall@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Ndiaye", "Fatou", "fatou.ndiaye@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Ba", "Abdoulaye", "abdoulaye.ba@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Sow", "Aminata", "aminata.sow@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Faye", "Cheikh", "cheikh.faye@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Diallo", "Mariam", "mariam.diallo@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Sy", "Moussa", "moussa.sy@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Camara", "Ndeye", "ndeye.camara@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Kane", "Aliou", "aliou.kane@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Barry", "Khady", "khady.barry@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Ndour", "Modou", "modou.ndour@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Traoré", "Awa", "awa.traore@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Keita", "Moussa", "moussa.keita@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Ouattara", "Fatimata", "fatimata.ouattara@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Sanogo", "Abdoulaye", "abdoulaye.sanogo@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Coulibaly", "Mariétou", "marietou.coulibaly@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Diarra", "Ismaël", "ismael.diarra@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Konaté", "Adama", "adama.konate@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Sow", "Ndèye", "ndeye.sow@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Mbaye", "Babacar", "babacar.mbaye@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Kouyaté", "Hadja", "hadja.kouyate@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Balde", "Thierno", "thierno.balde@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Sylla", "Mame Diarra", "diarra.sylla@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Diallo", "Alpha", "alpha.diallo@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Seck", "Nafissatou", "nafissatou.seck@gmail.com", "password123", LocalDate.now()),
                new Utilisateur("Ndiaye", "Bara Lamine", "Bara.lamine@gmail.com", "password123", LocalDate.now())

                );

            utilisateurRepository.saveAll(utilisateurs);
            utilisateurRepository.flush();
            System.out.println("Les Utilisateurs ont été insérés avec succès !");
        } else {
            System.out.println(" Les Utilisateurs existent déjà. Aucun seed nécessaire.");
        }
    }
}
