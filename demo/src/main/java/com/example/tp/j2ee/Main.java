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

            ProduitDAO dao = new ProduitDAOImpl(em);

            // Exercice 3: persist()
            dao.create(new Produit(1, "Clavier", 150.0));
            dao.create(new Produit(2, "Souris", 60.0));

            // Exercice 4: find()
            Produit produit1 = dao.findById(1);
            System.out.println("find(1) => " + produit1);

            // Exercice 4: JPQL (tous les produits)
            var produits = dao.findAll();
            System.out.println("Tous les produits => " + produits);

            // Exercice 5: update (modifier un prix)
            dao.updatePrice(2, 75.0);

            System.out.println("Après update => " + dao.findById(2));

            // Exercice 5: remove()
            dao.deleteById(1);

            System.out.println("Après delete, tous les produits => " +
                    dao.findAll());
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