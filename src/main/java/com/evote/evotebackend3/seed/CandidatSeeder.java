package com.evote.evotebackend3.seed;

import com.evote.evotebackend3.entities.Candidat;
import com.evote.evotebackend3.repository.CandidatRepository;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Component
public class CandidatSeeder {

    private final CandidatRepository candidatRepository;

    public CandidatSeeder(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @PostConstruct
    public void seedData() {
        if (candidatRepository.count() == 0) {
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

            candidatRepository.saveAll(List.of(candidat1, candidat2,candidat3,candidat4));
            System.out.println(" Candidats ajoutés !");
        } else {
            System.out.println(" Candidats déjà présents, aucun ajout.");
        }
    }
}
