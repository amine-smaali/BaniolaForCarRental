package com.projet.inscription_connexion.doa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet.inscription_connexion.doa.Entity.Client;
import com.projet.inscription_connexion.doa.Entity.Reservation;





@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByEmail(String email);
    Client findByEmail(String email);
   

}
