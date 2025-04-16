package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.Administrateur;
import com.evote.evotebackend3.repository.AdministrateurRepository;
import com.evote.evotebackend3.services.AdministrateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdministrateurServiceTest {

    @Mock
    private AdministrateurRepository administrateurRepository;

    @InjectMocks
    private AdministrateurService administrateurService;

    private Administrateur administrateur;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        administrateur = new Administrateur();
        administrateur.setId(1L);
        administrateur.setNom("Test");
        administrateur.setPrenom("Admin");
        administrateur.setEmail("admin@test.com");
        administrateur.setMotDePasse("password");
    }

    @Test
    public void testCreerAdministrateur() {
        when(administrateurRepository.save(any(Administrateur.class))).thenReturn(administrateur);

        Administrateur createdAdmin = administrateurService.creerAdministrateur(administrateur);

        assertNotNull(createdAdmin);
        assertEquals(administrateur.getNom(), createdAdmin.getNom());
        verify(administrateurRepository, times(1)).save(administrateur);
    }

    @Test
    public void testObtenirAdministrateurParId() {
        when(administrateurRepository.findById(1L)).thenReturn(Optional.of(administrateur));

        Optional<Administrateur> foundAdmin = administrateurService.obtenirAdministrateurParId(1L);

        assertTrue(foundAdmin.isPresent());
        assertEquals(administrateur.getId(), foundAdmin.get().getId());
    }

    @Test
    public void testMettreAJourAdministrateur() {
        Administrateur updatedAdmin = new Administrateur();
        updatedAdmin.setNom("Updated");
        updatedAdmin.setPrenom("Admin");
        updatedAdmin.setEmail("adminupdated@test.com");
        updatedAdmin.setMotDePasse("newpassword");

        when(administrateurRepository.findById(1L)).thenReturn(Optional.of(administrateur));
        when(administrateurRepository.save(any(Administrateur.class))).thenReturn(updatedAdmin);

        Administrateur result = administrateurService.mettreAJourAdministrateur(1L, updatedAdmin);

        assertNotNull(result);
        assertEquals("Updated", result.getNom());
    }

    @Test
    public void testSupprimerAdministrateur() {
        doNothing().when(administrateurRepository).deleteById(1L);

        administrateurService.supprimerAdministrateur(1L);

        verify(administrateurRepository, times(1)).deleteById(1L);
    }
}
