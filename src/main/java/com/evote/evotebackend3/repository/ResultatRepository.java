package com.evote.evotebackend3.repository;

import com.evote.evotebackend3.entities.Resultat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Long> {
    List<Resultat> findByCandidatId(Long idCandidat);

}
