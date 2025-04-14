/*
package com.evote.evotebackend3.repository;

import com.evote.evotebackend3.entities.Candidat;
import com.evote.evotebackend3.entities.Resultat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Long> {
    //List<Resultat> findByCandidatId(Long idCandidat);
    List<Resultat> findByCandidat(Long  idCandidat);
    List<Resultat> findByElection_IdOrderByNombreVoixDesc(Long idElection);
    List<Resultat> findByElectionId(Long idElection);


}
*/

package com.evote.evotebackend3.repository;

import com.evote.evotebackend3.entities.Resultat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Long> {

    List<Resultat> findByCandidat_Id(Long id);

    // Récupérer les résultats d'une élection par son ID
    List<Resultat> findByElection_Id(Long idElection);
    List<Resultat> findByElection_IdOrderByNombreVoixDesc(Long id);

}
