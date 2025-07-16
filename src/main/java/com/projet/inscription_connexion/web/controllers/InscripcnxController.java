package com.projet.inscription_connexion.web.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.projet.inscription_connexion.buisness.services.AdminServices;
import com.projet.inscription_connexion.buisness.services.CarService;
import com.projet.inscription_connexion.buisness.services.ClientServices;
import com.projet.inscription_connexion.doa.Entity.Admin;
import com.projet.inscription_connexion.doa.Entity.Car;
import com.projet.inscription_connexion.doa.Entity.Client;
import com.projet.inscription_connexion.web.models.Request.connexionforms.Admincnx;
import com.projet.inscription_connexion.web.models.Request.connexionforms.Clientcnx;
import com.projet.inscription_connexion.web.models.Request.inscriptionforms.AdminForm;
import com.projet.inscription_connexion.web.models.Request.inscriptionforms.ClientForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class InscripcnxController {
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images";

    @Autowired
    private ClientServices clientservice;
    @Autowired
    private AdminServices adminservice;
    @Autowired
    private CarService carService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainpage(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        return "page_principale";
    }

    @GetMapping("/car-details/{idCar}")
    public String getCarDetails(@PathVariable("idCar") Long idCar,Model model) {
        model.addAttribute("voiture", this.carService.findCar(idCar));
        return "car-details";
    }

    @GetMapping("/car-details/{idClient}/{idCar}")
    public String getCarDetailsLogin(@PathVariable("idCar") Long idCar,@PathVariable("idClient") Long idClient, Model model) {
         model.addAttribute("voiture", this.carService.findCar(idCar));
         model.addAttribute("client", this.clientservice.getClient(idClient));
        return "car-details-login";
    }
    

    @RequestMapping(value = "/Client", method = RequestMethod.GET)
    public String clientpage(Model model) {
        return "client";
    }

    /* inscription */
    @RequestMapping(value = "/signup/admin", method = RequestMethod.GET)
    public String signupadmin(Model model) {
        model.addAttribute("adminform", new AdminForm());
        return "signupadmin";

    }

    @RequestMapping(value = "/signup/admin", method = RequestMethod.POST)
    public String createadmin(@Valid @ModelAttribute("adminform") AdminForm adminform, BindingResult result) {
        if (result.hasErrors()) {
            return "signupadmin";
        } else {
            if (adminservice.existsbyEmail(adminform.getEmail())) {
                result.rejectValue("email", "error.email", "Cet email est déjà utilisé.");
                return "signupadmin";
            }
            Admin admincreated = new Admin(adminform.getNom(), adminform.getPrenom(), adminform.getTel(),
                    adminform.getEmail(), adminform.getPassword(), adminform.getCodeAgence());
            adminservice.CreateAdmin(admincreated);
            return "redirect:/admin";
        }
    }

    @RequestMapping(value = "/signup/client", method = RequestMethod.GET)
    public String signupclient(Model model) {
        model.addAttribute("clientform", new ClientForm());
        return "signupClient";

    }

    @RequestMapping(value = "/signup/client", method = RequestMethod.POST)
    public String createclient(@Valid @ModelAttribute("clientform") ClientForm clientform, BindingResult result,
            @RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return "signupClient";
        } 
            if (clientservice.existsbyEmail(clientform.getEmail())) {
                result.rejectValue("email", "error.email", "Cet email est déjà utilisé.");
                return "signupClient";
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
                Client clientcreated = new Client(clientform.getNom(), clientform.getPrenom(), clientform.getTel(),
                        clientform.getEmail(), clientform.getPassword(), fileName.toString());
                clientservice.CreateClient(clientcreated);
            } else {
                Client clientcreated = new Client(clientform.getNom(), clientform.getPrenom(), clientform.getTel(),
                        clientform.getEmail(), clientform.getPassword(), null);
                clientservice.CreateClient(clientcreated);
            }
            return "redirect:/";
        }
    

    /* Connexion */
    @RequestMapping(value = "/login/admin", method = RequestMethod.GET)
    public String loginadmin(Model model) {
        model.addAttribute("admincnx", new Admincnx());
        return "loginadmin";

    }

    @RequestMapping(value = "/login/admin", method = RequestMethod.POST)
    public String validAdmin(@Valid @ModelAttribute("admincnx") Admincnx admincnx, BindingResult result) {
        if (result.hasErrors()) {
            return "loginadmin";
        } else {
            if (!adminservice.existsbyEmail(admincnx.getEmail())) {
                result.rejectValue("email", "error.email", "Cet email n'existe pas .");
                return "loginadmin";
            }
            Admin a = adminservice.findbymail(admincnx.getEmail());
            if (!a.getPassword().equals(admincnx.getPassword())) {
                result.rejectValue("password", "error.password", "mot de passe invalid");
                return "loginadmin";
            }

        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/login/client", method = RequestMethod.GET)
    public String loginclient(Model model) {
        model.addAttribute("clientcnx", new Clientcnx());
        return "loginclient";

    }

    @RequestMapping(value = "/login/client", method = RequestMethod.POST)
    public String validClient(@Valid @ModelAttribute("clientcnx") Clientcnx clientcnx, BindingResult result) {
        if (result.hasErrors()) {
            return "loginclient";
        } else {

            if (!clientservice.existsbyEmail(clientcnx.getEmail())) {
                result.rejectValue("email", "error.email", "Cet email n'existe pas.");
                return "loginclient";
            }
            Client a = clientservice.findbymail(clientcnx.getEmail());
            if (!a.getPassword().equals(clientcnx.getPassword())) {
                result.rejectValue("password", "error.password", "mot de passe invalid.");
                return "loginclient";
            }

        }
        return "redirect:/" + clientservice.findbymail(clientcnx.getEmail()).getId() + "/client";
    }

    @RequestMapping(value = "/{id}/client", method = RequestMethod.GET)
    public String Clientpage(@PathVariable("id") Long id, Model model) {
        Client optionalClient = clientservice.getClient(id);
        // if (optionalClient.isPresent()) {
            model.addAttribute("user", optionalClient);
        //} else {
           // return "redirect:/login/client";
        //}
        model.addAttribute("cars", carService.getAllCars());
        return "index";
    }

}
