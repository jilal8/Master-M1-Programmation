package com.example.tp.j2ee;

import java.util.List;

public interface CategorieDAO {
    void create(Categorie categorie);

    Categorie findById(int id);

    List<Categorie> findAll();

    void updateNom(int id, String nouveauNom);

    void deleteById(int id);
}
