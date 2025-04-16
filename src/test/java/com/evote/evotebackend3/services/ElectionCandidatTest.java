package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.Candidat;
import com.evote.evotebackend3.entities.Election;
import com.evote.evotebackend3.entities.ElectionCandidat;
import com.evote.evotebackend3.repository.ElectionCandidatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ElectionCandidatServiceTest {

    @InjectMocks
    private ElectionCandidatService electionCandidatService;

    @Mock
    private ElectionCandidatRepository electionCandidatRepository;

    private Election election;
    private Candidat candidat;
    private ElectionCandidat electionCandidat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        election = new Election();
        election.setId(1L);
        election.setNom("Présidentielle 2025");

        candidat = new Candidat();
        candidat.setId(1L);
        candidat.setNom("Candidat Test");
        candidat.setPrenom("Test");

        electionCandidat = new ElectionCandidat();
        electionCandidat.setId(1L);
        electionCandidat.setElection(election);
        electionCandidat.setCandidat(candidat);
        electionCandidat.setNombreDeVotes(0);
        electionCandidat.setStatutCandidat(false);
    }

    @Test
    void testAssocierCandidatAElection() {
        when(electionCandidatRepository.findByElectionAndCandidat(election, candidat)).thenReturn(Optional.empty());
        when(electionCandidatRepository.save(any(ElectionCandidat.class))).thenReturn(electionCandidat);

        ElectionCandidat result = electionCandidatService.associerCandidatAElection(election, candidat);

        assertNotNull(result);
        assertEquals(election, result.getElection());
        assertEquals(candidat, result.getCandidat());
        verify(electionCandidatRepository, times(1)).save(any(ElectionCandidat.class));
    }

    @Test
    void testMettreAJourStatut() {
        when(electionCandidatRepository.findById(1L)).thenReturn(Optional.of(electionCandidat));
        when(electionCandidatRepository.save(any(ElectionCandidat.class))).thenReturn(electionCandidat);

        ElectionCandidat result = electionCandidatService.mettreAJourStatut(1L);

        assertNotNull(result);
        assertTrue(result.isStatutCandidat());
        verify(electionCandidatRepository, times(1)).save(any(ElectionCandidat.class));
    }

    @Test
    void testSupprimerAssociation() {
        when(electionCandidatRepository.existsById(1L)).thenReturn(true);

        electionCandidatService.supprimerAssociation(1L);

        verify(electionCandidatRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSupprimerAssociationNonExistante() {
        when(electionCandidatRepository.existsById(1L)).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            electionCandidatService.supprimerAssociation(1L);
        });

        assertEquals("Association élection-candidat introuvable", thrown.getMessage());
    }

    @Test
    void testListerToutesLesAssociations() {
        when(electionCandidatRepository.findAll()).thenReturn(List.of(electionCandidat));

        List<ElectionCandidat> result = electionCandidatService.listerToutesLesAssociations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(electionCandidat, result.get(0));
        verify(electionCandidatRepository, times(1)).findAll();
    }

    @Test
    void testTrouverParId() {
        when(electionCandidatRepository.findById(1L)).thenReturn(Optional.of(electionCandidat));

        Optional<ElectionCandidat> result = electionCandidatService.trouverParId(1L);

        assertTrue(result.isPresent());
        assertEquals(electionCandidat, result.get());
        verify(electionCandidatRepository, times(1)).findById(1L);
    }

    @Test
    void testListerCandidatsParElection() {
        when(electionCandidatRepository.findByElection(election)).thenReturn(List.of(electionCandidat));

        List<ElectionCandidat> result = electionCandidatService.listerCandidatsParElection(election);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(electionCandidat, result.get(0));
        verify(electionCandidatRepository, times(1)).findByElection(election);
    }
}
