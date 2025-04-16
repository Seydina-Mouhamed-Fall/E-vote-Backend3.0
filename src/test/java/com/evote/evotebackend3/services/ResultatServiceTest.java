package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.Resultat;
import com.evote.evotebackend3.repository.ResultatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResultatServiceTest {

    @Mock
    private ResultatRepository resultatRepository;

    @InjectMocks
    private ResultatService resultatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreerResultat() {
        Resultat resultat = new Resultat();
        when(resultatRepository.save(resultat)).thenReturn(resultat);

        Resultat saved = resultatService.creerResultat(resultat);
        assertEquals(resultat, saved);
        verify(resultatRepository, times(1)).save(resultat);
    }

    @Test
    void testObtenirTousLesResultats() {
        Resultat r1 = new Resultat();
        Resultat r2 = new Resultat();
        when(resultatRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Resultat> resultats = resultatService.obtenirTousLesResultats();
        assertEquals(2, resultats.size());
    }

    @Test
    void testObtenirResultatParId() {
        Resultat r = new Resultat();
        r.setId(1L);
        when(resultatRepository.findById(1L)).thenReturn(Optional.of(r));

        Optional<Resultat> found = resultatService.obtenirResultatParId(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    void testMettreAJourResultat() {
        Resultat r = new Resultat();
        r.setId(1L);
        r.setNombreVoix(10);

        Resultat updated = new Resultat();
        updated.setNombreVoix(100);
        updated.setPosition(1);
        updated.setStatutValidation(true);
        updated.setTotalVotes(200);

        when(resultatRepository.findById(1L)).thenReturn(Optional.of(r));
        when(resultatRepository.save(any(Resultat.class))).thenReturn(updated);

        Resultat res = resultatService.mettreAJourResultat(1L, updated);
        assertEquals(100, res.getNombreVoix());
    }

    @Test
    void testSupprimerResultat() {
        resultatService.supprimerResultat(1L);
        verify(resultatRepository, times(1)).deleteById(1L);
    }

    @Test
    void testValiderResultat() {
        Resultat r = new Resultat();
        r.setId(1L);
        r.setStatutValidation(false);
        when(resultatRepository.findById(1L)).thenReturn(Optional.of(r));

        resultatService.validerResultat(1L);
        assertTrue(r.getStatutValidation());
        verify(resultatRepository).save(r);
    }

    @Test
    void testCalculerTauxParticipation() {
        Resultat r1 = new Resultat();
        r1.setNombreVoix(100);
        r1.setTotalVotes(200);

        Resultat r2 = new Resultat();
        r2.setNombreVoix(50);
        r2.setTotalVotes(200);

        when(resultatRepository.findByElection_Id(1L)).thenReturn(Arrays.asList(r1, r2));

        double taux = resultatService.calculerTauxParticipation(1L);
        assertEquals(75.0, taux);
    }

    @Test
    void testVerifierSiResultatsValides() {
        Resultat r1 = new Resultat();
        r1.setStatutValidation(true);

        Resultat r2 = new Resultat();
        r2.setStatutValidation(true);

        when(resultatRepository.findByElection_Id(1L)).thenReturn(Arrays.asList(r1, r2));

        assertTrue(resultatService.verifierSiResultatsValides(1L));
    }

    @Test
    void testVerifierSiResultatsInvalide() {
        Resultat r1 = new Resultat();
        r1.setStatutValidation(true);

        Resultat r2 = new Resultat();
        r2.setStatutValidation(false);

        when(resultatRepository.findByElection_Id(1L)).thenReturn(Arrays.asList(r1, r2));

        assertFalse(resultatService.verifierSiResultatsValides(1L));
    }
}
