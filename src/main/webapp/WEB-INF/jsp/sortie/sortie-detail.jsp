<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Détail de la sortie</title>
    <link rel="stylesheet" href="<c:url value='/css/app.css'/>"/>
</head>
<body>
<!-- header -->
<header style="display:flex;justify-content:space-between;align-items:center;padding:1rem;background:#222;color:#fff;">
    <h1>
        <a href="<c:url value='/'/>" style="color:white;text-decoration:none">
            🧗 Club d’Escalade
        </a>
    </h1>
    <nav>
        <a href="<c:url value='/'/>">Accueil</a>
        <sec:authorize access="isAuthenticated()">
            <a href="<c:url value='/mes-sorties'/>">Mes sorties</a>
            <form action="<c:url value='/logout'/>" method="post" style="display:inline">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <button type="submit">Se déconnecter</button>
            </form>

        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <a href="<c:url value='/loginPage'/>">Se connecter</a>
        </sec:authorize>
    </nav>
</header>

<!-- main content -->
<main style="max-width:900px;margin:2rem auto;padding:1rem;background:white;border-radius:8px;
               box-shadow:0 0 10px rgba(0,0,0,0.05);">
    <h2>${sortie.nom}</h2>

    <ul style="list-style:none;padding:0">
        <li><strong>Catégorie :</strong> ${sortie.categorie.nom}</li>
        <li><strong>Date :</strong> ${sortie.dateSortie}</li>
        <sec:authorize access="isAuthenticated()">
            <li><strong>Créateur :</strong> ${sortie.createur.prenom} ${sortie.createur.nom}</li>
            <li><strong>Site web :</strong>
                <a href="${sortie.siteWeb}" target="_blank" rel="noopener">
                        ${sortie.siteWeb}
                </a>
            </li>
        </sec:authorize>
    </ul>

    <p style="margin-top:1rem"><strong>Descreption :</strong>${sortie.description}</p>

    <a href="<c:url value='/'/>" class="btn">⬅️ Retour</a>
</main>

<!-- footer -->
<footer style="background:#222;color:#fff;padding:1rem;text-align:center">
    <p>Projet Club d'Escalade – M1 IDL 2025</p>
</footer>
</body>
</html>
