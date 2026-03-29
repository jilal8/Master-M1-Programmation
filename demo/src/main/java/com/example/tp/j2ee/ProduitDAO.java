package com.example.tp.j2ee;

import java.util.List;

public interface ProduitDAO {
    void create(Produit produit);

    Produit findById(int id);

    List<Produit> findAll();

    void updatePrice(int id, double nouveauPrix);

    void deleteById(int id);

    // TODO (examen): lister les produits d'une catégorie (nécessite un lien Produit -> Categorie)
    List<Produit> findByCategorieId(int categorieId);
}

