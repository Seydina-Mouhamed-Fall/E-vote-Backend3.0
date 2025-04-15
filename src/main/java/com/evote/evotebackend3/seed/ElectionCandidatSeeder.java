package com.evote.evotebackend3.seed;

import com.evote.evotebackend3.entities.Candidat;
import com.evote.evotebackend3.entities.Election;
import com.evote.evotebackend3.entities.ElectionCandidat;
import com.evote.evotebackend3.repository.CandidatRepository;
import com.evote.evotebackend3.repository.ElectionCandidatRepository;
import com.evote.evotebackend3.repository.ElectionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class ElectionCandidatSeeder {

    @Autowired
    private ElectionCandidatRepository electionCandidatRepository;

    @Autowired
    private CandidatRepository candidatRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @PostConstruct
    public void seedData() {
        // Vérification si des données existent déjà, sinon on insère les nouvelles données
        if (electionCandidatRepository.count() == 0) {

            // Création d'une election exemple
            Election election = new Election();
            election.setNom("Election Présidentielle 2025");

            election.setDateDebut(LocalDateTime.of(2025, 5, 1, 0, 0));
            election.setDateFin(LocalDateTime.of(2025, 5, 10, 23, 59));
            electionRepository.save(election);

            // Création de candidats exemple
            Candidat candidat1 = new Candidat(
                "Bouba", "Yasmine", "YasBouba01@gmail.com", "motdepasse123",
                LocalDate.now(), "Nouvel Élan Citoyen (NEC)", "Yasmine Bouba, économiste de formation et entrepreneure sociale, est connue pour ses engagements en faveur de l'autonomisation des jeunes et des femmes en milieu rural. Originaire de la région de Tambacounda, elle milite pour une gouvernance participative et transparente. Fondatrice de plusieurs coopératives agricoles, elle souhaite insuffler une nouvelle dynamique citoyenne au Sénégal avec son parti, le Nouvel Élan Citoyen.", true
            );

            Candidat candidat2 = new Candidat(
                "Tine", "Luc Eugénie", "LucTine@gmail.com", "motdepasse456",
                LocalDate.now(), "Union pour la Renaissance Populaire (URP)", "Luc Eugénie Tine est un ancien diplomate et professeur de droit international. Ayant représenté le Sénégal à l'ONU pendant plus de dix ans, il revient sur la scène nationale avec une vision panafricaniste et humaniste. Avec l’URP, il veut restaurer la confiance entre les institutions et le peuple en prônant l’éducation, la justice et la réforme institutionnelle.", true
            );

            Candidat candidat3 = new Candidat(
                "Diop", "Yacine", "Yacine0101@gmail.com", "motdepasse456",
                LocalDate.now(), "Voix du Peuple Uni (VPU)", "Yacine Diop, juriste et activiste des droits humains, est très impliquée dans les mouvements citoyens depuis la crise sociale de 2012. Native de Dakar, elle défend une politique inclusive, axée sur la justice sociale, l’emploi des jeunes et la réforme du système judiciaire. Son parti, la Voix du Peuple Uni, porte l’ambition d’un Sénégal plus équitable et accessible à tous.", true
            );

            Candidat candidat4= new Candidat(
                "Tazi", "Samir", "Samir@gmail.com", "motdepasse456",
                LocalDate.now(), "Alliance pour l'Innovation Nationale (AIN)", "Originaire de Saint-Louis, Samir Tazi est ingénieur en technologies de l’information et expert en cybersécurité. Après une carrière internationale dans les télécommunications, il revient au pays pour proposer une refonte numérique de l’administration. Avec l’AIN, il propose un programme axé sur la modernisation de l’État, la digitalisation des services publics, et la transition écologique.", true
            );
            candidatRepository.saveAll(Arrays.asList(candidat1, candidat2,candidat3,candidat4));

            // Ajout des candidats à l'élection
            ElectionCandidat electionCandidat1 = new ElectionCandidat(0, false, candidat1, election);
            ElectionCandidat electionCandidat2 = new ElectionCandidat(0, false, candidat2, election);
            ElectionCandidat electionCandidat3 = new ElectionCandidat(0, false, candidat3, election);
            ElectionCandidat electionCandidat4 = new ElectionCandidat(0, false, candidat4, election);

            // Sauvegarde des candidats dans l'élection
            electionCandidatRepository.saveAll(Arrays.asList(electionCandidat1, electionCandidat2,
                electionCandidat3, electionCandidat4

                )
            );
        }
    }
}
