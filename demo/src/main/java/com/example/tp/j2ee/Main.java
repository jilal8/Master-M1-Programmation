package com.example.tp.j2ee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("magasinPU");
            em = emf.createEntityManager();

            CategorieDAO catDao = new CategorieDAOImpl(em);
            ProduitDAO prodDao = new ProduitDAOImpl(em);

            // --- Catégories : CRUD ---
            Categorie informatique = new Categorie(1, "Informatique");
            Categorie bureau = new Categorie(2, "Bureau");

            catDao.create(informatique);
            catDao.create(bureau);

            System.out.println("Toutes les catégories => " + catDao.findAll());

            catDao.updateNom(2, "Fournitures de bureau");
            System.out.println("Après update catégorie 2 => " + catDao.findById(2));

            // --- Produits avec catégorie ---
            prodDao.create(new Produit(1, "Clavier", 150.0, informatique));
            prodDao.create(new Produit(2, "Souris", 60.0, informatique));
            prodDao.create(new Produit(3, "Stylo", 5.0, bureau));

            System.out.println("Tous les produits => " + prodDao.findAll());

            System.out.println("Produits Informatique => " + prodDao.findByCategorieId(1));
            System.out.println("Produits Bureau => " + prodDao.findByCategorieId(2));

            // --- Update prix ---
            prodDao.updatePrice(2, 75.0);
            System.out.println("Après update prix Souris => " + prodDao.findById(2));

            // --- Suppression produit ---
            prodDao.deleteById(3);
            System.out.println("Après delete Stylo => " + prodDao.findAll());

            // --- Suppression catégorie (vide) ---
            catDao.deleteById(2);
            System.out.println("Après delete catégorie Bureau => " + catDao.findAll());

        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }
}
