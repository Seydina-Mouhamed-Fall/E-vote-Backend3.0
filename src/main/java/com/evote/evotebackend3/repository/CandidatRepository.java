package com.evote.evotebackend3.repository;

import com.evote.evotebackend3.entities.Candidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long> {
    //Object findByElectionId(long l);
    //List<Candidat> findByElectionId(Long election);
}
