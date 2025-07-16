package com.projet.inscription_connexion.web.models.Request;

import java.sql.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CarForm {
    @NotBlank
    private String marque;
    @NotBlank
    private String model;
    private Date annee_fab;
    @NotNull
    private int kilometrage;
    @NotBlank
    @Pattern(regexp = "^(Manuelle|Automatique|Hybride)$", message = "La transmission doit être 'Manuelle' ou 'Automatique' ou 'Hybride' ")
    private String transmission;
    @NotNull
    @Min(2)
    @Max(6)
    private int nbre_porte;
    @NotNull
    @Min(20)
    private float prix_par_jour;

    private String photo;
    @Pattern(regexp = "^(Disponible|Louée|Non disponible)$", message = "Le statut doit être 'Disponnible' ou 'Louée' ou 'Non disponible' ")
    private String etat;
}
