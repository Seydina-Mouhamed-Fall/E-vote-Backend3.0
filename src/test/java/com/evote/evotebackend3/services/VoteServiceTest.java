package com.evote.evotebackend3.services;

import com.evote.evotebackend3.entities.*;
import com.evote.evotebackend3.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private CandidatRepository candidatRepository;

    @Mock
    private ElecteurRepository electeurRepository;

    @Mock
    private ElectionCandidatRepository electionCandidatRepository;

    @Mock
    private ElecteurService electeurService;

    @InjectMocks
    private VoteService voteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnregistrerEtValiderVote_Success() {
        Long electeurId = 1L;
        Long electionCandidatId = 10L;
        String choix = "OUI";

        Electeur electeur = new Electeur();
        electeur.setId(electeurId);

        ElectionCandidat electionCandidat = new ElectionCandidat();
        electionCandidat.setId(electionCandidatId);

        Vote savedVote = new Vote();
        savedVote.setId(100L);

        when(electeurRepository.findById(electeurId)).thenReturn(Optional.of(electeur));
        when(electionCandidatRepository.findById(electionCandidatId)).thenReturn(Optional.of(electionCandidat));
        when(electeurService.estEligiblePourVoter(electeurId)).thenReturn(true);
        when(voteRepository.save(any(Vote.class))).thenReturn(savedVote);

        Vote result = voteService.enregistrerEtValiderVote(electeurId, electionCandidatId, choix);

        assertNotNull(result);
        assertEquals(100L, result.getId());
        verify(electeurService).marquerCommeAyantVote(electeurId);
    }

    @Test
    void testEnregistrerEtValiderVote_ElecteurIneligible() {
        Long electeurId = 1L;
        Long electionCandidatId = 10L;

        Electeur electeur = new Electeur();
        electeur.setId(electeurId);

        ElectionCandidat electionCandidat = new ElectionCandidat();
        electionCandidat.setId(electionCandidatId);

        when(electeurRepository.findById(electeurId)).thenReturn(Optional.of(electeur));
        when(electionCandidatRepository.findById(electionCandidatId)).thenReturn(Optional.of(electionCandidat));
        when(electeurService.estEligiblePourVoter(electeurId)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            voteService.enregistrerEtValiderVote(electeurId, electionCandidatId, "OUI"));

        assertEquals("L'électeur ne peut pas voter pour ce candidat.", exception.getMessage());
    }

    @Test
    void testRejeterVote_Success() {
        Long voteId = 5L;
        Vote vote = new Vote();
        vote.setId(voteId);
        vote.setStatutVote(true);

        when(voteRepository.findById(voteId)).thenReturn(Optional.of(vote));
        when(voteRepository.save(any(Vote.class))).thenReturn(vote);

            voteService.rejeterVote(voteId);

        assertFalse(vote.isStatutVote());
        verify(voteRepository).save(vote);
    }

    @Test
    void testPeutVoter_Success() {
        Long electeurId = 1L;
        Long electionCandidatId = 2L;

        when(electeurRepository.findById(electeurId)).thenReturn(Optional.of(new Electeur()));
        when(electionCandidatRepository.findById(electionCandidatId)).thenReturn(Optional.of(new ElectionCandidat()));
        when(electeurService.estEligiblePourVoter(electeurId)).thenReturn(true);

        assertTrue(voteService.peutVoter(electeurId, electionCandidatId));
    }

    @Test
    void testListerTousLesVotes() {
        List<Vote> votes = Arrays.asList(new Vote(), new Vote());
        when(voteRepository.findAll()).thenReturn(votes);

        List<Vote> result = voteService.listerTousLesVotes();

        assertEquals(2, result.size());
        verify(voteRepository).findAll();
    }

    @Test
    void testObtenirVoteParId() {
        Long id = 1L;
        Vote vote = new Vote();
        vote.setId(id);
        when(voteRepository.findById(id)).thenReturn(Optional.of(vote));

        Optional<Vote> result = voteService.obtenirVoteParId(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void testAfficherVotesElecteur() {
        Electeur electeur = new Electeur();
        List<Vote> votes = List.of(new Vote(), new Vote());

        when(voteRepository.findByElecteur(electeur)).thenReturn(votes);

        List<Vote> result = voteService.afficherVotesElecteur(electeur);

        assertEquals(2, result.size());
    }

    @Test
    void testCompterVotesPourCandidat() {
        Candidat candidat = new Candidat();
        when(voteRepository.countByElectionCandidat_Candidat(candidat)).thenReturn(3L);

        long count = voteService.compterVotesPourCandidat(candidat);

        assertEquals(3L, count);
    }
}
