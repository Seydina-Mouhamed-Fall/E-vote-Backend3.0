/*
package com.evote.evotebackend3.seed;

import com.evote.evotebackend3.entities.Election;
import com.evote.evotebackend3.repository.ElectionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class ElectionSeeder implements CommandLineRunner {

    private final ElectionRepository electionRepository;

    public ElectionSeeder(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    @Override
    public void run(String... args) {
        if (electionRepository.count() == 0) {
            System.out.println(" Insertion des élections de test...");

            List<Election> elections = Arrays.asList(
                new Election(
                    "Élection Présidentielle 2025",
                    LocalDateTime.now().plusDays(10), // dateDebut actuelle
                    LocalDateTime.now().plusDays(10),
                    false
                ),
                new Election(
                    "Élections Législatives",
                    LocalDateTime.now().plusDays(5),
                    LocalDateTime.now().plusDays(5),
                    false
                ),
                new Election(
                    "Élections Communales",
                    LocalDateTime.now().plusDays(3),
                    LocalDateTime.now().plusDays(3),
                    false
                )
            );

            electionRepository.saveAll(elections);
            System.out.println(" Élections insérées avec succès.");
        } else {
            System.out.println("Des élections existent déjà, insertion ignorée.");
        }
    }
}
*/


