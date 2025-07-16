package com.projet.inscription_connexion.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.projet.inscription_connexion.buisness.services.CarService;
import com.projet.inscription_connexion.buisness.services.ClientServices;
import com.projet.inscription_connexion.buisness.services.ReservationService;
import com.projet.inscription_connexion.doa.Entity.Car;
import com.projet.inscription_connexion.doa.Entity.Reservation;
import com.projet.inscription_connexion.web.models.Request.ReservationForm;

@Controller
public class ReservationController {
    private final ReservationService reservationService;
    private final CarService carService;
    private final ClientServices clientService;

    public ReservationController(ReservationService reservationService, CarService carService,ClientServices clientService) {
        this.reservationService = reservationService;
        this.carService = carService;
        this.clientService = clientService;
    }

    @GetMapping("/{client_id}/{car_id}/reservation")
    public String showReservationForm(@PathVariable("car_id") Long car_id, @PathVariable("client_id") Long client_id,
            Model model) {
        Reservation reservation = new Reservation();
        
        model.addAttribute("car_id", car_id);
        model.addAttribute("client_id", client_id);
        model.addAttribute("reservationform", reservation);
        return "reservation";
    }

    @PostMapping("/{client_id}/{car_id}/reservation")
    public String createReservation(@PathVariable("car_id") Long car_id,@PathVariable("client_id") Long client_id,
            @ModelAttribute("reservationform") @Valid ReservationForm reservationForm,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "reservation";
        }
        Reservation reservation = new Reservation(null, this.clientService.getClient(client_id), this.carService.findCar(car_id),
                reservationForm.getDate_deb(), reservationForm.getDate_fin(), "en attente",
                (float) this.reservationService.calculatePrice(this.carService.findCar(car_id).getPrix_par_jour(),
                 reservationForm.getDate_deb(), reservationForm.getDate_fin()));
        this.reservationService.addReservation(reservation);
        Car car = this.carService.findCar(car_id);
        car.setEtat("Non disponible");
        this.carService.updateCar(car_id, car);
        return "redirect:/" + client_id + "/client";
    }

    @PostMapping("/annuler/{idReservation}")
    public String annulerReservation(@PathVariable("idReservation") Long idReservation) {
        
        Reservation res = this.reservationService.getReservationById(idReservation);
        Long idclient = res.getClient().getId();
        Car car = res.getCar();
        Long idCar = car.getId();
        car.setEtat("Disponible");
        this.carService.updateCar(idCar, car);
        this.reservationService.manageReservation(idReservation, "Annulée par le client");
        
        return "redirect:/historique/" + idclient; 
    }

}