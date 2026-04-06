<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>${produit != null ? 'Modifier' : 'Nouveau'} produit</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div class="container">
    <h1>${produit != null ? 'Modifier' : 'Nouveau'} produit</h1>
    <a href="${pageContext.request.contextPath}/produits" class="btn btn-secondary">Retour à la liste</a>

    <form method="post" action="${pageContext.request.contextPath}/produits" class="form">
        <label>ID
            <input type="number" name="id" value="${produit != null ? produit.id : ''}"
                   ${produit != null ? 'readonly' : ''} required/>
        </label>
        <label>Nom
            <input type="text" name="nom" value="<c:out value='${produit != null ? produit.nom : ""}'/>" required/>
        </label>
        <label>Prix
            <input type="number" step="0.01" name="prix" value="${produit != null ? produit.prix : ''}" required/>
        </label>
        <label>Catégorie
            <select name="categorieId">
                <option value="">— Aucune —</option>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.id}"
                            ${produit != null && produit.categorie != null && produit.categorie.id == cat.id ? 'selected' : ''}>
                        <c:out value="${cat.nom}"/>
                    </option>
                </c:forEach>
            </select>
        </label>
        <button type="submit" class="btn">${produit != null ? 'Enregistrer' : 'Créer'}</button>
    </form>
</div>
</body>
</html>
