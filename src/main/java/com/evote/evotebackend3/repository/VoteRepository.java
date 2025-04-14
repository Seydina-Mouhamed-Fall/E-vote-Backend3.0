package com.evote.evotebackend3.repository;


import com.evote.evotebackend3.entities.Candidat;
import com.evote.evotebackend3.entities.Electeur;
import com.evote.evotebackend3.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByElecteur(Electeur electeur);

    List<Vote> findByElectionCandidat_Candidat(Candidat candidat);

    long countByElectionCandidat_Candidat(Candidat candidat);
}
