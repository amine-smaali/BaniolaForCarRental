package com.projet.inscription_connexion.doa.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@ToString
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String tel;
    @Column(unique =true )
    private String email;
    private String password;

    public User(String n, String p, String tel, String email, String pass) {
        this.nom = n;
        this.prenom = p;
        this.tel = tel;
        this.email = email;
        this.password = pass;
    }
}
