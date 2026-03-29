package com.example.tp.j2ee;

import java.util.List;

public interface CategorieDAO {
    // TODO (examen): créer une catégorie (transaction obligatoire)
    void create(Categorie categorie);

    // TODO (examen): lire une catégorie par id
    Categorie findById(int id);

    // TODO (examen): lister toutes les catégories (JPQL)
    List<Categorie> findAll();

    // TODO (examen): mettre à jour le nom d'une catégorie (transaction obligatoire)
    void updateNom(int id, String nouveauNom);

    // TODO (examen): supprimer une catégorie (transaction obligatoire)
    void deleteById(int id);
}

