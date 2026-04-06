package com.example.tp.j2ee;

import jakarta.persistence.EntityManager;

import java.util.List;

public class CategorieDAOImpl implements CategorieDAO {

    private final EntityManager em;

    public CategorieDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Categorie categorie) {
        inTransaction(() -> em.persist(categorie));
    }

    @Override
    public Categorie findById(int id) {
        return em.find(Categorie.class, id);
    }

    @Override
    public List<Categorie> findAll() {
        return em.createQuery("SELECT c FROM Categorie c", Categorie.class).getResultList();
    }

    @Override
    public void updateNom(int id, String nouveauNom) {
        inTransaction(() -> {
            Categorie managed = em.find(Categorie.class, id);
            if (managed != null) {
                managed.setNom(nouveauNom);
            }
        });
    }

    @Override
    public void deleteById(int id) {
        inTransaction(() -> {
            Categorie managed = em.find(Categorie.class, id);
            if (managed != null) {
                em.remove(managed);
            }
        });
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
