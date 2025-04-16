package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.Electeur;
import com.evote.evotebackend3.repository.ElecteurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ElecteurServiceTest {

    @InjectMocks
    private ElecteurService electeurService;

    @Mock
    private ElecteurRepository electeurRepository;

    private Electeur electeur;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        electeur = new Electeur();
        electeur.setId(1L);
        electeur.setNom("Test");
        electeur.setPrenom("Electeur");
        electeur.setEmail("test@example.com");
        electeur.setMotDePasse("password");
        electeur.setStatutEligibilite(true);
        electeur.setStatutVote(false);
        electeur.setNumCNI("CNI123");
        electeur.setNumTel("123456789");
    }

    @Test
    void testAjouterElecteur() {
        when(electeurRepository.save(any(Electeur.class))).thenReturn(electeur);

        Electeur result = electeurService.ajouterElecteur(electeur);

        assertNotNull(result);
        assertEquals("Test", result.getNom());
        assertEquals("Electeur", result.getPrenom());
        verify(electeurRepository, times(1)).save(any(Electeur.class));
    }

    @Test
    void testTrouverElecteurParId() {
        when(electeurRepository.findById(1L)).thenReturn(Optional.of(electeur));

        Optional<Electeur> result = electeurService.trouverElecteurParId(1L);

        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getNom());
        verify(electeurRepository, times(1)).findById(1L);
    }

    @Test
    void testTrouverElecteurParEmail() {
        when(electeurRepository.findByEmail("test@example.com")).thenReturn(Optional.of(electeur));

        Optional<Electeur> result = electeurService.trouverElecteurParEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
        verify(electeurRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testMettreAJourElecteur() {
        Electeur updatedElecteur = new Electeur();
        updatedElecteur.setNom("Updated Name");
        updatedElecteur.setPrenom("Updated Prenom");
        updatedElecteur.setEmail("updated@example.com");

        when(electeurRepository.findById(1L)).thenReturn(Optional.of(electeur));
        when(electeurRepository.save(any(Electeur.class))).thenReturn(updatedElecteur);

        Electeur result = electeurService.mettreAJourElecteur(1L, updatedElecteur);

        assertNotNull(result);
        assertEquals("Updated Name", result.getNom());
        assertEquals("Updated Prenom", result.getPrenom());
        assertEquals("updated@example.com", result.getEmail());
        verify(electeurRepository, times(1)).save(any(Electeur.class));
    }

    @Test
    void testSupprimerElecteur() {
        when(electeurRepository.existsById(1L)).thenReturn(true);

        electeurService.supprimerElecteur(1L);

        verify(electeurRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEstEligiblePourVoter() {
        when(electeurRepository.findById(1L)).thenReturn(Optional.of(electeur));

        boolean result = electeurService.estEligiblePourVoter(1L);

        assertTrue(result);
        verify(electeurRepository, times(1)).findById(1L);
    }

    @Test
    void testMarquerCommeAyantVote() {
        when(electeurRepository.findById(1L)).thenReturn(Optional.of(electeur));
        when(electeurRepository.save(any(Electeur.class))).thenReturn(electeur);

        electeurService.marquerCommeAyantVote(1L);

        assertTrue(electeur.isStatutVote());
        verify(electeurRepository, times(1)).save(any(Electeur.class));
    }

    @Test
    void testSupprimerElecteurNonExistant() {
        when(electeurRepository.existsById(1L)).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            electeurService.supprimerElecteur(1L);
        });

        assertEquals("Électeur avec ID 1 introuvable.", thrown.getMessage());
    }

    @Test
    void testMettreAJourElecteurNonExistant() {
        Electeur updatedElecteur = new Electeur();
        updatedElecteur.setNom("Updated Name");

        when(electeurRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            electeurService.mettreAJourElecteur(1L, updatedElecteur);
        });

        assertEquals("Électeur avec ID 1 introuvable.", thrown.getMessage());
    }
}
