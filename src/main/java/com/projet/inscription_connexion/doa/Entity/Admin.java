package com.projet.inscription_connexion.doa.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Admins")
public class Admin extends User {
    private int codeAgence;

    public Admin(String n, String p, String tel, String email, String pass, int codeAgence) {
        super(n, p, tel, email, pass);
        this.codeAgence = codeAgence;
    }
}
