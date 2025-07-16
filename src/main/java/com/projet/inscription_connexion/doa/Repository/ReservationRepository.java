package com.projet.inscription_connexion.doa.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projet.inscription_connexion.doa.Entity.Client;
import com.projet.inscription_connexion.doa.Entity.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

@Query("SELECT r FROM Reservation r WHERE r.car.id = :carId")
    List<Reservation> findByCarId(@Param("carId") Long carId);
   

    
    
    List<Reservation> findAllByClient(Client client);

    List<Reservation> findByStatut(String statut);

    


}
