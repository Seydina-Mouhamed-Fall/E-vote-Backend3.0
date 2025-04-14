package com.evote.evotebackend3.repository;

import com.evote.evotebackend3.entities.Electeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElecteurRepository extends JpaRepository<Electeur, Long> {
    Optional<Electeur> findByEmail(String email);
}
