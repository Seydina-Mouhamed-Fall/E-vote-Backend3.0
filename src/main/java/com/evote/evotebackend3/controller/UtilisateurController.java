package com.evote.evotebackend3.controller;

import com.evote.evotebackend3.entities.Resultat;
import com.evote.evotebackend3.entities.Utilisateur;
import com.evote.evotebackend3.services.CandidatService;
import com.evote.evotebackend3.services.ElectionService;
import com.evote.evotebackend3.services.ResultatService;
import com.evote.evotebackend3.services.UtilisateurService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.evote.evotebackend3.entities.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/utilisateur")
//@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final ResultatService resultatService;
    private final CandidatService candidatService;
    private final ElectionService electionService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService, ResultatService resultatService, CandidatService candidatService, ElectionService electionService) {
        this.utilisateurService = utilisateurService;
        this.resultatService = resultatService;
        this.candidatService = candidatService;
        this.electionService = electionService;
    }
    // Modifier les infos personnelles
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> mettreAJourUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateurDetails) {
        Utilisateur updated = utilisateurService.mettreAJourUtilisateur(id, utilisateurDetails);
        return ResponseEntity.ok(updated);
    }

    // Supprimer un utilisateur (j'ai pas compris ce que j'ai voulu faire ici ?)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimerUtilisateur(@PathVariable Long id) {
        utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.ok("Utilisateur supprimé avec succès");
    }

//    //  Authentification
//    @PostMapping("/authentifier")
//    public ResponseEntity<?> authentifierUtilisateur(@RequestBody AuthentificationDto dto) {
//        boolean isAuth = utilisateurService.authentifierUtilisateur(dto.getEmail(), dto.getMotDePasse());
//        return ResponseEntity.ok(Map.of("authenticated", isAuth));
//    }

    //  Obtenir tous les résultats (200OK)
    @GetMapping("/resultats")
    public ResponseEntity<List<Resultat>> getAllResultats() {
        return ResponseEntity.ok(resultatService.obtenirTousLesResultats());
    }

    //  Obtenir un résultat par ID (200OK)
    @GetMapping("/resultats/{id}")
    public ResponseEntity<Resultat> getResultatById(@PathVariable Long id) {
        return resultatService.obtenirResultatParId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    //  Obtenir les résultats d’un candidat (200OK)
    @GetMapping("/resultats/candidat/{idCandidat}")
    public ResponseEntity<List<Resultat>> getResultatsParCandidat(@PathVariable Long idCandidat) {
        return ResponseEntity.ok(resultatService.obtenirResultatsParCandidat(idCandidat));
    }

    //  Obtenir le classement d’une élection (200OK)
    @GetMapping("/classement/{idElection}")
    public ResponseEntity<List<Resultat>> getClassementDesCandidats(@PathVariable Long idElection) {
        return ResponseEntity.ok(resultatService.obtenirClassementDesCandidats(idElection));
    }

    // Lister toutes les élections (200K)
    @GetMapping("/elections")
    public ResponseEntity<List<Election>> listerElections() {
        return ResponseEntity.ok(electionService.listerElections());
    }

    //  Obtenir une élection (200K)
    @GetMapping("/elections/{id}")
    public ResponseEntity<Election> obtenirElection(@PathVariable Long id) {
        return electionService.obtenirElectionParId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    //  Lister tous les candidats (20OK)
    @GetMapping("/candidats")
    public ResponseEntity<List<Candidat>> listerCandidats() {
        return ResponseEntity.ok(candidatService.listerCandidats());
    }
}
