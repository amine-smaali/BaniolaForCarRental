package com.projet.inscription_connexion.doa.Entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "Voitures")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marque;
    private String model;
    private Date annee_fab;
    private int kilometrage;
    private String transmission;
    private int nbre_porte;
    private float prix_par_jour;
    private String photo;
    private String etat;

    @OneToMany(mappedBy = "car")
    private List<Reservation> reservations;

}
