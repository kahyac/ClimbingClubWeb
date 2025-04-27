<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"/>
    <title>Détail de la catégorie – ${categorie.nom}</title>
    <link rel="stylesheet" href="<c:url value='/css/app.css'/>"/>
</head>
<body>

<!-- même header -->
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

<main style="max-width:700px;margin:2rem auto;padding:1rem;
                   background:white;border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,0.05);">
    <h2>🔍 Détail de la catégorie « ${categorie.nom} »</h2>

    <p><strong>ID :</strong> ${categorie.id}</p>
    <p><strong>Nom :</strong> ${categorie.nom}</p>

    <h3>🚩 Sorties de cette catégorie</h3>
    <c:if test="${empty sorties}">
        <p>Aucune sortie pour cette catégorie.</p>
    </c:if>
    <ul class="list">
        <c:forEach var="s" items="${sorties}">
            <li>
                <a href="<c:url value='/sorties/${s.id}'/>">
                        ${s.nom}
                </a>
                (le ${s.dateSortie})
            </li>
        </c:forEach>
    </ul>

    <div style="margin-top:1rem;">
        <a href="<c:url value='/categories'/>" class="btn btn-gray">
            ← Retour à la liste
        </a>
        <a href="<c:url value='/categories/modifier/${categorie.id}'/>"
           class="btn btn-green">
            ✏️ Modifier
        </a>
    </div>
</main>

<footer style="background:#222;color:#fff;padding:1rem;text-align:center">
    <p>Projet Club d'Escalade – M1 IDL 2025</p>
</footer>
</body>
</html>
