package com.example.tp.j2ee;

import jakarta.persistence.EntityManager;

import java.util.List;

public class CategorieDAOImpl implements CategorieDAO {

    @SuppressWarnings("unused")
    private final EntityManager em;

    public CategorieDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Categorie categorie) {
        // TODO (examen): begin -> persist(categorie) -> commit (rollback si exception)
        throw new UnsupportedOperationException("TODO (examen): implémenter create(Categorie)");
    }

    @Override
    public Categorie findById(int id) {
        // TODO (examen): utiliser em.find(Categorie.class, id)
        throw new UnsupportedOperationException("TODO (examen): implémenter findById(int)");
    }

    @Override
    public List<Categorie> findAll() {
        // TODO (examen): JPQL: \"SELECT c FROM Categorie c\"
        throw new UnsupportedOperationException("TODO (examen): implémenter findAll()");
    }

    @Override
    public void updateNom(int id, String nouveauNom) {
        // TODO (examen): begin -> find -> setNom -> commit
        throw new UnsupportedOperationException("TODO (examen): implémenter updateNom(int, String)");
    }

    @Override
    public void deleteById(int id) {
        // TODO (examen): begin -> find -> remove -> commit
        throw new UnsupportedOperationException("TODO (examen): implémenter deleteById(int)");
    }
}

