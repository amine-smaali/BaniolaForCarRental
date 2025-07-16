package com.projet.inscription_connexion.web.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.projet.inscription_connexion.buisness.services.CarService;
import com.projet.inscription_connexion.buisness.services.ClientServices;
import com.projet.inscription_connexion.buisness.services.ReservationService;
import com.projet.inscription_connexion.doa.Entity.Car;
import com.projet.inscription_connexion.doa.Entity.Client;
import com.projet.inscription_connexion.doa.Entity.Reservation;
import com.projet.inscription_connexion.web.models.Request.CarForm;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;



@Controller
@RequestMapping("/admin")

public class AdminController {

    private final ReservationService reservationService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images";
    private final CarService carService;
    private final ClientServices clientService;

    public AdminController(CarService carService, ReservationService reservationService,ClientServices clientService) {
        this.carService = carService;
        this.reservationService = reservationService;
        this.clientService = clientService;
    }

    // read
    @GetMapping
    public String showCarList(Model model) {
        model.addAttribute("cars", this.carService.getAllCars());
        model.addAttribute("reservation", this.reservationService.getAllReservation());
        return "admin";
    }

    // create
    @GetMapping("/create")
    public String showAddCarForm(Model model) {
        model.addAttribute("carForm", new CarForm());

        return "add-car";
    }

    @PostMapping("/create")
    public String createCar(@ModelAttribute("carForm") @Valid CarForm carForm,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "add-car";
        }

        if (!file.isEmpty()) {
            // Création d'un objet StringBuilder pour stocker le nom du fichier
            StringBuilder fileName = new StringBuilder();
            // Ajout du nom de fichier original à l'objet StringBuilder
            fileName.append(file.getOriginalFilename());
            // Construction du chemin complet du fichier en combinant le répertoire de
            // destination et le nom du fichier
            Path newFilePath = Paths.get(uploadDirectory, fileName.toString());

            try {
                // Écriture du contenu du fichier dans le chemin spécifié
                Files.write(newFilePath, file.getBytes());
            } catch (IOException e) {
                // Capture et affichage des erreurs éventuelles lors de l'écriture du fichier
                e.printStackTrace();
            }
            Car car = new Car();
            car.setMarque(carForm.getMarque());
            car.setModel(carForm.getModel());
            car.setAnnee_fab(carForm.getAnnee_fab());
            car.setKilometrage(carForm.getKilometrage());
            car.setTransmission(carForm.getTransmission());
            car.setNbre_porte(carForm.getNbre_porte());
            car.setPrix_par_jour(carForm.getPrix_par_jour());
            car.setPhoto(fileName.toString());
            car.setEtat("Disponible");
            this.carService.addCar(car);

        } else {
            Car car = new Car();
            car.setMarque(carForm.getMarque());
            car.setModel(carForm.getModel());
            car.setAnnee_fab(carForm.getAnnee_fab());
            car.setKilometrage(carForm.getKilometrage());
            car.setTransmission(carForm.getTransmission());
            car.setNbre_porte(carForm.getNbre_porte());
            car.setPrix_par_jour(carForm.getPrix_par_jour());
            car.setPhoto(null);
            car.setEtat("Disponible");
            this.carService.addCar(car);
        }

        return "redirect:/admin";
    }

    // delete
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        Car car = this.carService.findCar(id);
        car.setEtat("Non disponible");
        this.carService.updateCar(id, car);
        return "redirect:/admin";
    }

    // update

    @GetMapping("/edit/{id}")
    public String showEditCarForm(@PathVariable("id") Long id, Model model) {

        Car car = this.carService.findCar(id);

        CarForm carForm = new CarForm(car.getMarque(), car.getModel(), car.getAnnee_fab(), car.getKilometrage(),
                car.getTransmission(), car.getNbre_porte(), car.getPrix_par_jour(), car.getPhoto(), car.getEtat());
        model.addAttribute("carForm", carForm);
        // model.addAttribute("id", car.getId());
        return "edit-car";

    }

    @PostMapping("/edit/{id}")
    public String updateCar(@PathVariable("id") Long id,
            @ModelAttribute("carForm") @Valid CarForm carForm,
            BindingResult bindingResult,
            Model model,
            @RequestParam MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "edit-car";
        }

        if (!file.isEmpty()) {
            // Création d'un objet StringBuilder pour stocker le nom du fichier
            StringBuilder fileName = new StringBuilder();
            // Ajout du nom de fichier original à l'objet StringBuilder
            fileName.append(file.getOriginalFilename());
            // Construction du chemin complet du fichier en combinant le répertoire de
            // destination et le nom du fichier
            Path newFilePath = Paths.get(uploadDirectory, fileName.toString());

            try {
                // Écriture du contenu du fichier dans le chemin spécifié
                Files.write(newFilePath, file.getBytes());
            } catch (IOException e) {
                // Capture et affichage des erreurs éventuelles lors de l'écriture du fichier
                e.printStackTrace();
            }
            Car car = this.carService.findCar(id);
            car.setMarque(carForm.getMarque());
            car.setModel(carForm.getModel());
            car.setAnnee_fab(carForm.getAnnee_fab());
            car.setKilometrage(carForm.getKilometrage());
            car.setTransmission(carForm.getTransmission());
            car.setNbre_porte(carForm.getNbre_porte());
            car.setPrix_par_jour(carForm.getPrix_par_jour());
            car.setPhoto(fileName.toString());
            car.setEtat(carForm.getEtat());

            this.carService.updateCar(id, car);

        } else {
            Car car = this.carService.findCar(id);
            car.setMarque(carForm.getMarque());
            car.setModel(carForm.getModel());
            car.setAnnee_fab(carForm.getAnnee_fab());
            car.setKilometrage(carForm.getKilometrage());
            car.setTransmission(carForm.getTransmission());
            car.setNbre_porte(carForm.getNbre_porte());
            car.setPrix_par_jour(carForm.getPrix_par_jour());
            car.setEtat(carForm.getEtat());
            this.carService.updateCar(id, car);

        }

        return "redirect:/admin";
    }

    @GetMapping("/details/{id}")
    public String getCarDetails(@PathVariable("id") Long id,Model model) {
        model.addAttribute("car", this.carService.findCar(id));
        return "admin-car-details";
    }

    @GetMapping("/reservations")
    public String getReservationList( Model model) {
        model.addAttribute("reservations", this.reservationService.getUnconfirmedReservations());
        
        
        return "admin-reservations";
    }

    @GetMapping("/reservations/{idReservation}")
    public String getReservationDetails(@PathVariable("idReservation") Long idReservation,
                                        Model model) {
        
        Reservation reservation = reservationService.getReservationById(idReservation);
        model.addAttribute("client", reservation.getClient());
        model.addAttribute("car", reservation.getCar());
        model.addAttribute("reservation", reservation);
        
        return "admin-reservation-details";
    }
    

    @PostMapping("/reservations/{idReservation}/confirmer")
    public String confirmReservation(@PathVariable("idReservation") Long idReservation) {
        this.reservationService.manageReservation(idReservation, "confirmée");
        Reservation res = this.reservationService.getReservationById(idReservation);
        Car car = res.getCar();
        car.setEtat("Louée");
        this.carService.updateCar(car.getId(),car);
        return "redirect:/admin";
    }

    @GetMapping("/reservations/{idReservation}/refuser")
    public String refuseReservation(@PathVariable("idReservation") Long idReservation) {
        this.reservationService.manageReservation(idReservation, "refusée");
        Reservation res = this.reservationService.getReservationById(idReservation);
        Car car = res.getCar();
        car.setEtat("Disponible");
        this.carService.updateCar(car.getId(),car);
        return "redirect:/admin";
    }
    
    
    

}