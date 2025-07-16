package com.projet.inscription_connexion.doa.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@Table(name = "Clients")
public class Client extends User {
    private String permis_Img;

    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;

    public Client(String n, String p, String tel, String email, String pass, String img) {
        super(n, p, tel, email, pass);
        this.permis_Img = img;
    }

}
