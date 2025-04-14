package com.evote.evotebackend3.repository;

import com.evote.evotebackend3.entities.Candidat;
import com.evote.evotebackend3.entities.Election;
import com.evote.evotebackend3.entities.ElectionCandidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElectionCandidatRepository extends JpaRepository<ElectionCandidat, Long> {

    Optional<ElectionCandidat> findByElectionAndCandidat(Election election, Candidat candidat);

}
