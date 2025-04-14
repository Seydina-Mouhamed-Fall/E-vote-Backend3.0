
package com.evote.evotebackend3.seed;

import com.evote.evotebackend3.entities.Electeur;
import com.evote.evotebackend3.repository.ElecteurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ElecteurSeeder implements CommandLineRunner {

    private final ElecteurRepository electeurRepository;

    public ElecteurSeeder(ElecteurRepository electeurRepository) {
        this.electeurRepository = electeurRepository;
    }

    @Override
    public void run(String... args) {
        if (electeurRepository.count() == 0) {
            Electeur electeur1 = new Electeur(
                "Fall", "Pape Modou", "pape.fall@gmail.com", "motdepasse123", LocalDate.now(),
                "771234567", true, false, "CNI123456789"
            );

            Electeur electeur2 = new Electeur(
                "Ndiaye", "Amy", "Amy.ndiaye@gmail.com", "Amypass", LocalDate.now(),
                "781234567", true, false, "CNI987654321"
            );

            electeurRepository.save(electeur1);
            electeurRepository.save(electeur2);

            System.out.println(" Electeurs seedés avec succès !");
        }
    }
}

