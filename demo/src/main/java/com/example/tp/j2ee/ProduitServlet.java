package com.example.tp.j2ee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/produits")
public class ProduitServlet extends HttpServlet {

    private EntityManagerFactory getEmf() {
        return (EntityManagerFactory) getServletContext().getAttribute("emf");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        EntityManager em = getEmf().createEntityManager();

        try {
            ProduitDAO dao = new ProduitDAOImpl(em);
            CategorieDAO catDao = new CategorieDAOImpl(em);

            if ("edit".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("produit", dao.findById(id));
                req.setAttribute("categories", catDao.findAll());
                req.getRequestDispatcher("/WEB-INF/jsp/produits/form.jsp").forward(req, resp);

            } else if ("new".equals(action)) {
                req.setAttribute("categories", catDao.findAll());
                req.getRequestDispatcher("/WEB-INF/jsp/produits/form.jsp").forward(req, resp);

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                dao.deleteById(id);
                resp.sendRedirect(req.getContextPath() + "/produits");

            } else {
                req.setAttribute("produits", dao.findAll());
                req.getRequestDispatcher("/WEB-INF/jsp/produits/list.jsp").forward(req, resp);
            }
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        EntityManager em = getEmf().createEntityManager();

        try {
            ProduitDAO dao = new ProduitDAOImpl(em);
            CategorieDAO catDao = new CategorieDAOImpl(em);

            int id = Integer.parseInt(req.getParameter("id"));
            String nom = req.getParameter("nom");
            double prix = Double.parseDouble(req.getParameter("prix"));
            String catIdStr = req.getParameter("categorieId");

            Categorie cat = (catIdStr != null && !catIdStr.isEmpty())
                    ? catDao.findById(Integer.parseInt(catIdStr))
                    : null;

            Produit existing = dao.findById(id);

            if (existing != null) {
                em.getTransaction().begin();
                existing.setNom(nom);
                existing.setPrix(prix);
                existing.setCategorie(cat);
                em.getTransaction().commit(); 
            } else {
                dao.create(new Produit(id, nom, prix, cat));
            }

            resp.sendRedirect(req.getContextPath() + "/produits");
        } finally {
            em.close();
        }
    }
}
