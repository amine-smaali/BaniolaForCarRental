package com.projet.inscription_connexion.buisness.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projet.inscription_connexion.doa.Entity.Admin;
import com.projet.inscription_connexion.doa.Repository.AdminRepository;

@Service
public class AdminServices {
    private final AdminRepository adminRepository;

    public AdminServices(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void CreateAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public List<Admin> Alladmins() {
        return (adminRepository.findAll());
    }

    public boolean existbyid(Long id) {
        for (Admin admin : adminRepository.findAll()) {
            if (id == admin.getId()) {
                return true;
            }
        }
        return (false);
    }

    public boolean existsbyEmail(String email) {
        return (adminRepository.existsByEmail(email));
    }

    public Admin findbymail(String email) {
        return adminRepository.findByEmail(email);
    }

}
