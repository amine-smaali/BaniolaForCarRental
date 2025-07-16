package com.projet.inscription_connexion.buisness.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.projet.inscription_connexion.doa.Entity.Client;
import com.projet.inscription_connexion.doa.Repository.ClientRepository;

@Service
public class ClientServices {
    private final ClientRepository clientRepository;

    public ClientServices(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void CreateClient(Client client) {
        clientRepository.save(client);
    }

    public Client getClient(Long id) {
        return clientRepository.findById(id).get();
    }

    public List<Client> AllClients() {
        return (clientRepository.findAll());
    }

    public boolean existbyid(Long id) {
        for (Client client : clientRepository.findAll()) {
            if (id == client.getId()) {
                return true;
            }
        }
        return (false);
    }

    public Client findbymail(String email) {
        return clientRepository.findByEmail(email);
    }

    public boolean existsbyEmail(String email) {
        return (clientRepository.existsByEmail(email));
    }
}

    
