<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"/>
    <title>
        ${categorie.id == null ? "➕ Nouvelle catégorie" : "✏️ Modifier catégorie"}
        – Club d’Escalade
    </title>
    <link rel="stylesheet" href="<c:url value='/css/app.css'/>"/>
</head>
<body>

<!-- header identique aux autres pages -->
<header style="display:flex;justify-content:space-between;align-items:center;
                 padding:1rem;background:#222;color:#fff;">
    <h1>
        <a href="<c:url value='/'/>" style="color:white;text-decoration:none">
            🧗 Club d’Escalade
        </a>
    </h1>
    <nav>
        <sec:authorize access="isAuthenticated()">
            <a class="btn" href="<c:url value='/'/>">Accueil</a>
            <a class="btn" href="<c:url value='/mes-sorties'/>">Mes sorties</a>
            <a class="btn" href="<c:url value='/categories'/>">Catégories</a>
            <a class="btn" href="<c:url value='/membres'/>">Membres</a>
            <span style="margin:0 1rem">
                Bonjour <strong>${displayName}</strong>
            </span>
            <form action="<c:url value='/logout'/>" method="post" style="display:inline">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button class="btn" type="submit">Se déconnecter</button>
            </form>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <a class="btn" href="<c:url value='/loginPage'/>">Se connecter</a>
        </sec:authorize>
    </nav>
</header>

<main style="max-width:600px;margin:2rem auto;padding:1rem;
               background:white;border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,0.05);">
    <h2>
        ${categorie.id == null ? "➕ Création d’une catégorie" : "✏️ Édition de catégorie"}
    </h2>

    <form:form method="post"
               modelAttribute="categorie"
               cssClass="form-create">

        <!-- champ Nom -->
        <div class="form-group">
            <label for="nom">Nom de la catégorie</label>
            <form:input path="nom"
                        id="nom"
                        cssClass="form-control"
                        required="true"/>
            <form:errors path="nom" cssClass="error"/>
        </div>

        <!-- boutons -->
        <div class="form-actions">
            <button type="submit" class="btn btn-green">
                    ${categorie.id == null ? "Créer" : "Mettre à jour"}
            </button>
            <a href="<c:url value='/categories'/>" class="btn btn-gray">Annuler</a>
        </div>

    </form:form>
</main>

<footer style="background:#222;color:#fff;padding:1rem;text-align:center">
    <p>Projet Club d'Escalade – M1 IDL 2025</p>
</footer>
</body>
</html>
