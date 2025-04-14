
package com.evote.evotebackend3.seed;

import com.evote.evotebackend3.entities.Electeur;
import com.evote.evotebackend3.repository.ElecteurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

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

            Electeur electeur3 = new Electeur(
                "Diop", "Moussa", "moussa.diop@gmail.com", "pass123", LocalDate.now(),
                "761234561", true, false, "CNI000000003"
            );

            Electeur electeur4 = new Electeur(
                "Ba", "Fatou", "fatou.ba@gmail.com", "fatoupass", LocalDate.now(),
                "761234562", true, false, "CNI000000004"
            );

            Electeur electeur5 = new Electeur(
                "Sy", "Abdou", "abdou.sy@gmail.com", "syPass2024", LocalDate.now(),
                "761234563", true, false, "CNI000000005"
            );

            Electeur electeur6 = new Electeur(
                "Sow", "Khadija", "khadija.sow@gmail.com", "khaPass", LocalDate.now(),
                "761234564", true, false, "CNI000000006"
            );

            Electeur electeur7 = new Electeur(
                "Gueye", "Ibrahima", "ibrahima.gueye@gmail.com", "ibrahima2024", LocalDate.now(),
                "761234565", true, false, "CNI000000007"
            );

            Electeur electeur8 = new Electeur(
                "Faye", "Mame Diarra", "diarra.faye@gmail.com", "diarraPass", LocalDate.now(),
                "761234566", true, false, "CNI000000008"
            );

            Electeur electeur9 = new Electeur(
                "Kane", "Ousmane", "ousmane.kane@gmail.com", "ousmane2024", LocalDate.now(),
                "761234567", true, false, "CNI000000009"
            );

            Electeur electeur10 = new Electeur(
                "Lo", "Aissatou", "aissatou.lo@gmail.com", "aissapass", LocalDate.now(),
                "761234568", true, false, "CNI000000010"
            );

// On continue...

            Electeur electeur11 = new Electeur("Diallo", "Mamadou", "mamadou.diallo@gmail.com", "diallo123", LocalDate.now(), "761234569", true, false, "CNI000000011");
            Electeur electeur12 = new Electeur("Ndoye", "Seynabou", "seynabou.ndoye@gmail.com", "seynaPass", LocalDate.now(), "761234570", true, false, "CNI000000012");
            Electeur electeur13 = new Electeur("Camara", "Cheikh", "cheikh.camara@gmail.com", "cheikh2024", LocalDate.now(), "761234571", true, false, "CNI000000013");
            Electeur electeur14 = new Electeur("Sagna", "Adama", "adama.sagna@gmail.com", "adamapass", LocalDate.now(), "761234572", true, false, "CNI000000014");
            Electeur electeur15 = new Electeur("Diouf", "Awa", "awa.diouf@gmail.com", "awapass", LocalDate.now(), "761234573", true, false, "CNI000000015");
            Electeur electeur16 = new Electeur("Mbaye", "Alioune", "alioune.mbaye@gmail.com", "alioune2024", LocalDate.now(), "761234574", true, false, "CNI000000016");
            Electeur electeur17 = new Electeur("Toure", "Binta", "binta.toure@gmail.com", "bintaPass", LocalDate.now(), "761234575", true, false, "CNI000000017");
            Electeur electeur18 = new Electeur("Seck", "Thierno", "thierno.seck@gmail.com", "thierno123", LocalDate.now(), "761234576", true, false, "CNI000000018");
            Electeur electeur19 = new Electeur("Barry", "Aminata", "aminata.barry@gmail.com", "aminaPass", LocalDate.now(), "761234577", true, false, "CNI000000019");
            Electeur electeur20 = new Electeur("Kouyaté", "Lamine", "lamine.kouyate@gmail.com", "lamine2024", LocalDate.now(), "761234578", true, false, "CNI000000020");

            Electeur electeur21 = new Electeur("Cissé", "Mariama", "mariama.cisse@gmail.com", "mariamaPass", LocalDate.now(), "761234579", true, false, "CNI000000021");
            Electeur electeur22 = new Electeur("Dia", "Serigne", "serigne.dia@gmail.com", "serigne2024", LocalDate.now(), "761234580", true, false, "CNI000000022");
            Electeur electeur23 = new Electeur("Thiam", "Astou", "astou.thiam@gmail.com", "astouPass", LocalDate.now(), "761234581", true, false, "CNI000000023");
            Electeur electeur24 = new Electeur("Ngom", "Demba", "demba.ngom@gmail.com", "demba2024", LocalDate.now(), "761234582", true, false, "CNI000000024");
            Electeur electeur25 = new Electeur("Tall", "Sokhna", "sokhna.tall@gmail.com", "sokhnaPass", LocalDate.now(), "761234583", true, false, "CNI000000025");
            Electeur electeur26 = new Electeur("Konaté", "Moussa", "moussa.konate@gmail.com", "konate2024", LocalDate.now(), "761234584", true, false, "CNI000000026");
            Electeur electeur27 = new Electeur("Diaw", "Ndeye", "ndeye.diaw@gmail.com", "ndeyePass", LocalDate.now(), "761234585", true, false, "CNI000000027");
            Electeur electeur28 = new Electeur("Ly", "Issa", "issa.ly@gmail.com", "issaPass", LocalDate.now(), "761234586", true, false, "CNI000000028");
            Electeur electeur29 = new Electeur("Sarr", "Aïcha", "aicha.sarr@gmail.com", "aichaPass", LocalDate.now(), "761234587", true, false, "CNI000000029");
            Electeur electeur30 = new Electeur("Niang", "Cheikh", "cheikh.niang@gmail.com", "cheikh123", LocalDate.now(), "761234588", true, false, "CNI000000030");


            electeurRepository.saveAll(List.of(
                electeur1, electeur2, electeur3, electeur4, electeur5,
                electeur6, electeur7, electeur8, electeur9, electeur10,
                electeur11, electeur12, electeur13, electeur14, electeur15,
                electeur16, electeur17, electeur18, electeur19, electeur20,
                electeur21, electeur22, electeur23, electeur24, electeur25,
                electeur26, electeur27, electeur28, electeur29, electeur30
            ));


            System.out.println(" Electeurs seedés avec succès !");
        }
    }
}

