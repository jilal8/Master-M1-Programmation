<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Magasin — Accueil</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<div class="container">
    <h1>Magasin — Gestion des produits</h1>
    <div class="card-grid">
        <a href="${pageContext.request.contextPath}/categories" class="card">
            <h2>Catégories</h2>
            <p>Lister, ajouter, modifier ou supprimer des catégories.</p>
        </a>
        <a href="${pageContext.request.contextPath}/produits" class="card">
            <h2>Produits</h2>
            <p>Lister, ajouter, modifier ou supprimer des produits.</p>
        </a>
    </div>
</div>
</body>
</html>
