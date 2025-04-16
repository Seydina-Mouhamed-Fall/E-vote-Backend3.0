package com.evote.evotebackend3.controller;

import com.evote.evotebackend3.entities.*;
import com.evote.evotebackend3.services.CandidatService;
import com.evote.evotebackend3.services.ElectionCandidatService;
import com.evote.evotebackend3.services.ResultatService;
import com.evote.evotebackend3.services.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidats")
@RequiredArgsConstructor
public class CandidatController {

    private final CandidatService candidatService;
    private final ElectionCandidatService electionCandidatService;
    private final ResultatService resultatService;
    private final VoteService voteService;

    // === [ADMIN] ===

    @PostMapping("/voter") //(200OK)
    public ResponseEntity<Vote> enregistrerEtValiderVoteParAdmin(
        @RequestParam Long electeurId,
        @RequestParam Long electionCandidatId,
        @RequestParam String choix) {
        try {
            Vote vote = voteService.enregistrerEtValiderVote(electeurId, electionCandidatId, choix);
            return ResponseEntity.ok(vote);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidat> mettreAJourCandidat(@PathVariable Long id, @RequestBody Candidat candidat) {
        return ResponseEntity.ok(candidatService.mettreAJourCandidat(id, candidat));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCandidat(@PathVariable Long id) {
        candidatService.supprimerCandidat(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Candidat>> listerCandidats() {
        return ResponseEntity.ok(candidatService.listerCandidats());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidat> obtenirCandidat(@PathVariable Long id) {
        return candidatService.obtenirCandidat(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/{id}/resultats") //(200OK)
    public ResponseEntity<List<Resultat>> voirSesResultats(@PathVariable Long id) {
        return ResponseEntity.ok(resultatService.obtenirResultatsParCandidat(id));
    }

    // === [ANALYSE] ===

    @GetMapping("/election/{idElection}/classement") //(200OK)
    public ResponseEntity<List<Resultat>> obtenirClassement(@PathVariable Long idElection) {
        return ResponseEntity.ok(resultatService.obtenirClassementDesCandidats(idElection));
    }

    @GetMapping("/election/{idElection}/validite")
    public ResponseEntity<Boolean> verifierValiditeResultats(@PathVariable Long idElection) {
        return ResponseEntity.ok(resultatService.verifierSiResultatsValides(idElection));
    }

    @GetMapping("/resultats")
    public ResponseEntity<List<Resultat>> obtenirTousLesResultats() {
        return ResponseEntity.ok(resultatService.obtenirTousLesResultats());
    }

    @GetMapping("/resultats/{id}")
    public ResponseEntity<Resultat> obtenirResultatParId(@PathVariable Long id) {
        return resultatService.obtenirResultatParId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
