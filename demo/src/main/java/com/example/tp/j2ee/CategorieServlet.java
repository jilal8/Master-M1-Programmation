package com.example.tp.j2ee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/categories")
public class CategorieServlet extends HttpServlet {

    private EntityManagerFactory getEmf() {
        return (EntityManagerFactory) getServletContext().getAttribute("emf");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        EntityManager em = getEmf().createEntityManager();

        try {
            CategorieDAO dao = new CategorieDAOImpl(em);

            if ("edit".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                req.setAttribute("categorie", dao.findById(id));
                req.getRequestDispatcher("/WEB-INF/jsp/categories/form.jsp").forward(req, resp);

            } else if ("new".equals(action)) {
                req.getRequestDispatcher("/WEB-INF/jsp/categories/form.jsp").forward(req, resp);

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                dao.deleteById(id);
                resp.sendRedirect(req.getContextPath() + "/categories");

            } else {
                req.setAttribute("categories", dao.findAll());
                req.getRequestDispatcher("/WEB-INF/jsp/categories/list.jsp").forward(req, resp);
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
            CategorieDAO dao = new CategorieDAOImpl(em);

            int id = Integer.parseInt(req.getParameter("id"));
            String nom = req.getParameter("nom");

            Categorie existing = dao.findById(id);

            if (existing != null) {
                dao.updateNom(id, nom);
            } else {
                dao.create(new Categorie(id, nom));
            }

            resp.sendRedirect(req.getContextPath() + "/categories");
        } finally {
            em.close();
        }
    }
}
