package com.evote.evotebackend3.controller;

import com.evote.evotebackend3.entities.Electeur;
import com.evote.evotebackend3.services.ElecteurService;
import com.evote.evotebackend3.services.ResultatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.evote.evotebackend3.entities.Resultat;
import java.util.List;

@RestController
@RequestMapping("/api/electeurs")
public class ElecteurController {

    private final ElecteurService electeurService;
    private final ResultatService resultatService;

    @Autowired
    public ElecteurController(ElecteurService electeurService, ResultatService resultatService) {
        this.electeurService = electeurService;
        this.resultatService = resultatService;
    }

    // ============================
    // CRUD
    // ============================

    // READ : Lister tous les électeurs
    @GetMapping
    public List<Electeur> listerTousLesElecteurs() {
        return electeurService.listerElecteurs();
    }

    // UPDATE : Modifier les infos d’un électeur
    @PutMapping("/{id}")
    public ResponseEntity<Electeur> modifierElecteur(
        @PathVariable Long id,
        @RequestBody Electeur electeurDetails) {
        try {
            Electeur updated = electeurService.mettreAJourElecteur(id, electeurDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE : Supprimer un électeur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerElecteur(@PathVariable Long id) {
        try {
            electeurService.supprimerElecteur(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ============================
    // MÉTIER
    // ============================

    // Vérifier si un électeur est éligible pour voter
    @GetMapping("/{id}/eligibilite")
    public ResponseEntity<Boolean> estEligiblePourVoter(@PathVariable Long id) {
        boolean eligible = electeurService.estEligiblePourVoter(id);
        return ResponseEntity.ok(eligible);
    }

    // ============================
    // RESULTATS
    // ============================

    // Tous les résultats
    @GetMapping("/resultats")
    public List<Resultat> obtenirTousLesResultats() {
        return resultatService.obtenirTousLesResultats();
    }


    // Résultat par ID
    @GetMapping("/resultats/{id}")
    public ResponseEntity<Resultat> obtenirResultatParId(@PathVariable Long id) {
        return resultatService.obtenirResultatParId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Résultats par candidat
    @GetMapping("/resultats/candidat/{idCandidat}")
    public List<Resultat> obtenirResultatsParCandidat(@PathVariable Long idCandidat) {
        return resultatService.obtenirResultatsParCandidat(idCandidat);
    }

    // Classement des candidats pour une élection
    @GetMapping("/resultats/election/{idElection}/classement")
    public List<Resultat> obtenirClassementDesCandidats(@PathVariable Long idElection) {
        return resultatService.obtenirClassementDesCandidats(idElection);
    }
}
