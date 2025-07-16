package com.projet.inscription_connexion.web.models.Request.inscriptionforms;

import jakarta.validation.constraints.NotBlank;
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
public class ClientForm {
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotBlank
    private String tel;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private String permis_Img;

}
