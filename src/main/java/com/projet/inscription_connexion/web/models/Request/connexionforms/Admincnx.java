package com.projet.inscription_connexion.web.models.Request.connexionforms;

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
public class Admincnx {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
