package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.Candidat;
import com.evote.evotebackend3.repository.CandidatRepository;
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

class CandidatServiceTest {

    @InjectMocks
    private CandidatService candidatService;

    @Mock
    private CandidatRepository candidatRepository;

    private Candidat candidat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        candidat = new Candidat();
        candidat.setId(1L);
        candidat.setNom("Fall");
        candidat.setPrenom("Seydina");
        candidat.setEmail("Dina.fall@example.com");
        candidat.setMotDePasse("password");
        candidat.setParti("Parti A");
        candidat.setBiographie("Biographie du candidat.");
    }

    @Test
    void testAjouterCandidat() {
        when(candidatRepository.save(any(Candidat.class))).thenReturn(candidat);

        Candidat result = candidatService.ajouterCandidat(candidat);

        assertNotNull(result);
        assertEquals("Fall", result.getNom());
        assertEquals("Seydina", result.getPrenom());
        verify(candidatRepository, times(1)).save(candidat);
    }

    @Test
    void testObtenirCandidat() {
        when(candidatRepository.findById(1L)).thenReturn(Optional.of(candidat));

        Optional<Candidat> result = candidatService.obtenirCandidat(1L);

        assertTrue(result.isPresent());
        assertEquals("Fall", result.get().getNom());
    }

    @Test
    void testListerCandidats() {
        when(candidatRepository.findAll()).thenReturn(Arrays.asList(candidat));

        List<Candidat> result = candidatService.listerCandidats();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Fall", result.get(0).getNom());
    }

    @Test
    void testSupprimerCandidat() {
        when(candidatRepository.existsById(1L)).thenReturn(true);

        candidatService.supprimerCandidat(1L);

        verify(candidatRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSupprimerCandidatNonExistant() {
        when(candidatRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> candidatService.supprimerCandidat(1L));

        assertEquals("Candidat avec l'ID 1 n'existe pas.", exception.getMessage());
    }

    @Test
    void testMettreAJourCandidat() {
        Candidat updatedCandidat = new Candidat();
        updatedCandidat.setNom("Updated Name");
        updatedCandidat.setPrenom("Updated Prenom");

        when(candidatRepository.findById(1L)).thenReturn(Optional.of(candidat));
        when(candidatRepository.save(any(Candidat.class))).thenReturn(updatedCandidat);

        Candidat result = candidatService.mettreAJourCandidat(1L, updatedCandidat);

        assertNotNull(result);
        assertEquals("Updated Name", result.getNom());
        assertEquals("Updated Prenom", result.getPrenom());
        verify(candidatRepository, times(1)).save(any(Candidat.class)); // Vérifie que save a été appelé avec n'importe quel Candidat
    }

    @Test
    void testMettreAJourCandidatNonExistant() {
        Candidat updatedCandidat = new Candidat();
        updatedCandidat.setNom("Updated Name");
        updatedCandidat.setPrenom("Updated Prenom");

        when(candidatRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> candidatService.mettreAJourCandidat(1L, updatedCandidat));

        assertEquals("Candidat avec l'ID 1 n'existe pas.", exception.getMessage());
    }
}

