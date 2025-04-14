package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.Resultat;
import com.evote.evotebackend3.repository.ResultatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultatService {

    private final  ResultatRepository resultatRepository;

    public ResultatService(ResultatRepository resultatRepository) {
        this.resultatRepository = resultatRepository;
    }

    public Resultat creerResultat(Resultat resultat) {
        return resultatRepository.save(resultat);
    }

    public List<Resultat> obtenirTousLesResultats() {
        return resultatRepository.findAll();
    }

    public Optional<Resultat> obtenirResultatParId(Long id) {
        return resultatRepository.findById(id);
    }

    public Resultat mettreAJourResultat(Long id, Resultat resultatDetails) {
        return resultatRepository.findById(id).map(resultat -> {
            resultat.setNombreVoix(resultatDetails.getNombreVoix());
            resultat.setPosition(resultatDetails.getPosition());
            resultat.setStatutValidation(resultatDetails.getStatutValidation());
            resultat.setTotalVotes(resultatDetails.getTotalVotes());
            return resultatRepository.save(resultat);
        }).orElseThrow(() -> new RuntimeException("Résultat introuvable avec l'id : " + id));
    }

    public void supprimerResultat(Long id) {
        resultatRepository.deleteById(id);
    }

    public void validerResultat(Long idResultat) {
        Optional<Resultat> resultatOptional = resultatRepository.findById(idResultat);
        resultatOptional.ifPresent(resultat -> {
            resultat.setStatutValidation(true);
            resultatRepository.save(resultat);
        });
    }


    // Recommandé si relation avec Candidat
    public List<Resultat> obtenirResultatsParCandidat(Long idCandidat) {
        return resultatRepository.findByCandidat_Id(idCandidat);
    }

//
//    public List<Resultat> obtenirClassementDesCandidats(Long idElection) {
//        return resultatRepository.findByElection_IdElectionOrderByNombreVoixDesc(idElection);
//    }


    public double calculerTauxParticipation(Long idElection) {
        List<Resultat> resultats = resultatRepository.findByElection_Id(idElection);

        int totalVotes = resultats.stream()
            .mapToInt(Resultat::getNombreVoix)
            .sum();

        int totalElecteurs = resultats.isEmpty() ? 0 : resultats.get(0).getTotalVotes(); // Assumons que chaque résultat contient totalVotes identique

        if (totalElecteurs == 0) return 0.0;

        return ((double) totalVotes / totalElecteurs) * 100;
    }

    public boolean verifierSiResultatsValides(Long idElection) {
        List<Resultat> resultats = resultatRepository.findByElection_Id(idElection);
        return resultats.stream().allMatch(Resultat::getStatutValidation);
    }

}

