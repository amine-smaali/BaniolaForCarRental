package com.projet.inscription_connexion.doa.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet.inscription_connexion.doa.Entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByEmail(String email);

    Admin findByEmail(String email);
}
