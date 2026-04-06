package com.example.tp.j2ee;

import jakarta.persistence.EntityManager;

import java.util.List;

public class ProduitDAOImpl implements ProduitDAO {

    private final EntityManager em;

    public ProduitDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Produit produit) {
        inTransaction(() -> em.persist(produit));
    }

    @Override
    public Produit findById(int id) {
        return em.find(Produit.class, id);
    }

    @Override
    public List<Produit> findAll() {
        return em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
    }

    @Override
    public void updatePrice(int id, double nouveauPrix) {
        inTransaction(() -> {
            Produit managed = em.find(Produit.class, id);
            if (managed != null) {
                managed.setPrix(nouveauPrix);
            }
        });
    }

    @Override
    public void deleteById(int id) {
        inTransaction(() -> {
            Produit managed = em.find(Produit.class, id);
            if (managed != null) {
                em.remove(managed);
            }
        });
    }

    @Override
    public List<Produit> findByCategorieId(int categorieId) {
        return em.createQuery("SELECT p FROM Produit p WHERE p.categorie.id = :catId", Produit.class)
                .setParameter("catId", categorieId)
                .getResultList();
    }

    private void inTransaction(Runnable work) {
        try {
            em.getTransaction().begin();
            work.run();
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}

