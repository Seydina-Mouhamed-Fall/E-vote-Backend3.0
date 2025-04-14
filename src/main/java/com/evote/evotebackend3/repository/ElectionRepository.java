package com.evote.evotebackend3.repository;

import com.evote.evotebackend3.entities.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {
//    List<Election> findByEnCours(boolean b);
    // Tu peux ajouter des méthodes personnalisées si nécessaire
}
