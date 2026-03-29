package com.example.tp.j2ee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorie")
public class Categorie {

    @Id
    private int id;

    @Column(nullable = false, length = 100)
    private String nom;

    public Categorie() {
        // TODO (examen): constructeur vide requis par JPA
    }

    public Categorie(int id, String nom) {
        // TODO (examen): initialiser les champs
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        // TODO (examen): retourner l'id
        return id;
    }

    public void setId(int id) {
        // TODO (examen): setter id
        this.id = id;
    }

    public String getNom() {
        // TODO (examen): retourner le nom
        return nom;
    }

    public void setNom(String nom) {
        // TODO (examen): setter nom
        this.nom = nom;
    }

    @Override
    public String toString() {
        // TODO (examen): améliorer l'affichage si besoin
        return "Categorie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}

