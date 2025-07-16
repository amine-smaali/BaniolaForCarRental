package com.projet.inscription_connexion.doa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projet.inscription_connexion.doa.Entity.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
    
}