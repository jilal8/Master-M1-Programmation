<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Catégories</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div class="container">
    <h1>Liste des catégories</h1>
    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Accueil</a>
    <a href="${pageContext.request.contextPath}/categories?action=new" class="btn">Nouvelle catégorie</a>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cat" items="${categories}">
            <tr>
                <td>${cat.id}</td>
                <td><c:out value="${cat.nom}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/categories?action=edit&id=${cat.id}" class="btn btn-small">Modifier</a>
                    <a href="${pageContext.request.contextPath}/categories?action=delete&id=${cat.id}" class="btn btn-small btn-danger"
                       onclick="return confirm('Supprimer cette catégorie ?')">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty categories}">
            <tr><td colspan="3" class="empty">Aucune catégorie.</td></tr>
        </c:if>
        </tbody>
    </table>
</div>
</body>
</html>
