package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.Election;
import com.evote.evotebackend3.repository.ElectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ElectionServiceTest {

    @Mock
    private ElectionRepository electionRepository;

    @InjectMocks
    private ElectionService electionService;

    private Election election;

    @BeforeEach
    void setUp() {
        election = new Election();
        election.setId(1L);
        election.setNom("Election Présidentielle");
        election.setDateDebut(LocalDateTime.of(2025, 4, 1, 9, 0));
        election.setDateFin(LocalDateTime.of(2025, 4, 10, 18, 0));
        election.setCloturee(false);
    }

    @Test
    void testCreerElection() {
        when(electionRepository.save(any(Election.class))).thenReturn(election);

        Election result = electionService.creerElection(election);

        assertNotNull(result);
        assertEquals("Election Présidentielle", result.getNom());
        verify(electionRepository, times(1)).save(election);
    }

    @Test
    void testObtenirElectionParId() {
        when(electionRepository.findById(1L)).thenReturn(Optional.of(election));

        Optional<Election> result = electionService.obtenirElectionParId(1L);

        assertTrue(result.isPresent());
        assertEquals("Election Présidentielle", result.get().getNom());
        verify(electionRepository, times(1)).findById(1L);
    }

    @Test
    void testMettreAJourElection() {
        Election updated = new Election();
        updated.setNom("Election Législative");
        updated.setDateDebut(LocalDateTime.of(2025, 5, 1, 10, 0));
        updated.setDateFin(LocalDateTime.of(2025, 5, 10, 18, 0));

        when(electionRepository.findById(1L)).thenReturn(Optional.of(election));
        when(electionRepository.save(any(Election.class))).thenReturn(updated);

        Election result = electionService.mettreAJourElection(1L, updated);

        assertEquals("Election Législative", result.getNom());
        assertEquals(updated.getDateDebut(), result.getDateDebut());
        verify(electionRepository).save(any(Election.class));
    }

    @Test
    void testSupprimerElection() {
        when(electionRepository.existsById(1L)).thenReturn(true);

        electionService.supprimerElection(1L);

        verify(electionRepository).deleteById(1L);
    }

    @Test
    void testSupprimerElectionNonExistante() {
        when(electionRepository.existsById(2L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> electionService.supprimerElection(2L));

        assertEquals("Élection introuvable.", exception.getMessage());
    }

    @Test
    void testListerElections() {
        Election election2 = new Election();
        election2.setId(2L);
        election2.setNom("Référendum");

        when(electionRepository.findAll()).thenReturn(Arrays.asList(election, election2));

        List<Election> result = electionService.listerElections();

        assertEquals(2, result.size());
        verify(electionRepository).findAll();
    }

    @Test
    void testCloturerElection() {
        election.setDateFin(LocalDateTime.now().minusDays(1)); // Date fin passée

        when(electionRepository.findById(1L)).thenReturn(Optional.of(election));
        when(electionRepository.save(any(Election.class))).thenAnswer(i -> i.getArgument(0));

        Election result = electionService.cloturerElection(1L);

        assertTrue(result.isCloturee());
        verify(electionRepository).save(election);
    }

    @Test
    void testCloturerElectionAvantFin() {
        election.setDateFin(LocalDateTime.now().plusDays(1)); // Date fin future

        when(electionRepository.findById(1L)).thenReturn(Optional.of(election));

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> electionService.cloturerElection(1L));

        assertEquals("L'élection ne peut pas être clôturée avant la date de fin.", exception.getMessage());
    }
}
