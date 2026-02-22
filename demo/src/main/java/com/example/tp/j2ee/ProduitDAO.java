package com.example.tp.j2ee;

import java.util.List;

public interface ProduitDAO {
    void create(Produit produit);

    Produit findById(int id);

    List<Produit> findAll();

    void updatePrice(int id, double nouveauPrix);

    void deleteById(int id);
}

