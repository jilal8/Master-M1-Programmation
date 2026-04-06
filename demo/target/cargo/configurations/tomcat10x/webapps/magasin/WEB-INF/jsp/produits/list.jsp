<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Produits</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div class="container">
    <h1>Liste des produits</h1>
    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Accueil</a>
    <a href="${pageContext.request.contextPath}/produits?action=new" class="btn">Nouveau produit</a>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prix</th>
            <th>Catégorie</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${produits}">
            <tr>
                <td>${p.id}</td>
                <td><c:out value="${p.nom}"/></td>
                <td>${p.prix}</td>
                <td><c:out value="${p.categorie != null ? p.categorie.nom : '—'}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/produits?action=edit&id=${p.id}" class="btn btn-small">Modifier</a>
                    <a href="${pageContext.request.contextPath}/produits?action=delete&id=${p.id}" class="btn btn-small btn-danger"
                       onclick="return confirm('Supprimer ce produit ?')">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty produits}">
            <tr><td colspan="5" class="empty">Aucun produit.</td></tr>
        </c:if>
        </tbody>
    </table>
</div>
</body>
</html>
