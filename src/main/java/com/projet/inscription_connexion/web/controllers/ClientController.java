package com.projet.inscription_connexion.web.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.projet.inscription_connexion.buisness.services.ClientServices;
import com.projet.inscription_connexion.buisness.services.ReservationService;
import com.projet.inscription_connexion.doa.Entity.Client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ClientController {
    private final ClientServices clientServices;
    private final ReservationService reservationService;

    public ClientController(ClientServices clientServices, ReservationService reservationService) {
        this.clientServices = clientServices;
        this.reservationService = reservationService;
    }

    @GetMapping("/profil/{id}")
    public String infoclient(@PathVariable("id") Long id, Model model) {
        Client optionalClient = clientServices.getClient(id);
        //if (optionalClient.isPresent()) {
            model.addAttribute("user", optionalClient);
        //} else {
           // return "redirect:/" + id + "/client";
       // }
        return "profil";
    }

    @GetMapping("/historique/{id}")
    public String historique(@PathVariable("id") Long id, Model model) {
       Client client = clientServices.getClient(id);
        //if (optionalClient.isPresent()) {
            model.addAttribute("user", client);
       // } else {
          //  return "redirect:/" + id + "/client";
        //}
        model.addAttribute("reservations", reservationService.getReservationbyClient(client));
        return "historique";
    }

    

    @GetMapping("/logout/{id}")
    public String logout(@PathVariable("id") Long id, Model model) {

        return "logout";
    }

}
