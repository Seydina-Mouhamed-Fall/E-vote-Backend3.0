package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.Utilisateur;
import com.evote.evotebackend3.repository.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @InjectMocks
    private UtilisateurService utilisateurService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnregistrerUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("Fall");

        when(utilisateurRepository.save(utilisateur)).thenReturn(utilisateur);

        Utilisateur saved = utilisateurService.enregistrerUtilisateur(utilisateur);
        assertEquals("Fall", saved.getNom());
        verify(utilisateurRepository, times(1)).save(utilisateur);
    }

    @Test
    void testObtenirTousLesUtilisateurs() {
        Utilisateur u1 = new Utilisateur();
        Utilisateur u2 = new Utilisateur();

        when(utilisateurRepository.findAll()).thenReturn(List.of(u1, u2));

        List<Utilisateur> utilisateurs = utilisateurService.obtenirTousLesUtilisateurs();
        assertEquals(2, utilisateurs.size());
    }

    @Test
    void testObtenirUtilisateurParId() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));

        Optional<Utilisateur> found = utilisateurService.obtenirUtilisateurParId(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    void testObtenirUtilisateurParEmail() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@email.com");

        when(utilisateurRepository.findByEmail("test@email.com")).thenReturn(Optional.of(utilisateur));

        Optional<Utilisateur> found = utilisateurService.obtenirUtilisateurParEmail("test@email.com");
        assertTrue(found.isPresent());
        assertEquals("test@email.com", found.get().getEmail());
    }

    @Test
    void testMettreAJourUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setNom("AncienNom");

        Utilisateur maj = new Utilisateur();
        maj.setNom("NouveauNom");
        maj.setPrenom("NouveauPrenom");
        maj.setEmail("new@email.com");
        maj.setMotDePasse("1234");
        maj.setDateInscription(LocalDate.now());

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(maj);

        Utilisateur resultat = utilisateurService.mettreAJourUtilisateur(1L, maj);
        assertEquals("NouveauNom", resultat.getNom());
        assertEquals("NouveauPrenom", resultat.getPrenom());
        assertEquals("new@email.com", resultat.getEmail());
    }

    @Test
    void testSupprimerUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        doNothing().when(utilisateurRepository).delete(utilisateur);

        assertDoesNotThrow(() -> utilisateurService.supprimerUtilisateur(1L));
        verify(utilisateurRepository).delete(utilisateur);
    }

    @Test
    void testAuthentifierUtilisateur_Succes() {
        Utilisateur utilisateur = mock(Utilisateur.class);
        when(utilisateur.sAuthentifier("test@email.com", "pass123")).thenReturn(true);

        when(utilisateurRepository.findByEmail("test@email.com")).thenReturn(Optional.of(utilisateur));

        boolean result = utilisateurService.authentifierUtilisateur("test@email.com", "pass123");
        assertTrue(result);
        verify(utilisateur).sAuthentifier("test@email.com", "pass123");
    }

    @Test
    void testAuthentifierUtilisateur_Echec() {
        when(utilisateurRepository.findByEmail("inexistant@email.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
            utilisateurService.authentifierUtilisateur("inexistant@email.com", "pass"));

        assertEquals("Utilisateur non trouvé", exception.getMessage());
    }

    @Test
    void testDemanderSupport() {
        Utilisateur utilisateur = mock(Utilisateur.class);
        when(utilisateurRepository.findByEmail("support@email.com")).thenReturn(Optional.of(utilisateur));

        utilisateurService.demanderSupport("support@email.com", "Message de test");
        verify(utilisateur).demanderSupport("Message de test");
    }
}
