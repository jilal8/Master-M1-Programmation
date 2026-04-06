<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>${categorie != null ? 'Modifier' : 'Nouvelle'} catégorie</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div class="container">
    <h1>${categorie != null ? 'Modifier' : 'Nouvelle'} catégorie</h1>
    <a href="${pageContext.request.contextPath}/categories" class="btn btn-secondary">Retour à la liste</a>

    <form method="post" action="${pageContext.request.contextPath}/categories" class="form">
        <label>ID
            <input type="number" name="id" value="${categorie != null ? categorie.id : ''}"
                   ${categorie != null ? 'readonly' : ''} required/>
        </label>
        <label>Nom
            <input type="text" name="nom" value="<c:out value='${categorie != null ? categorie.nom : ""}'/>" required/>
        </label>
        <button type="submit" class="btn">${categorie != null ? 'Enregistrer' : 'Créer'}</button>
    </form>
</div>
</body>
</html>
