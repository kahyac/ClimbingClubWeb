<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"/>
    <title>Mes sorties – Club d’Escalade</title>
    <link rel="stylesheet" href="<c:url value='/css/app.css'/>"/>
</head>
<body>

<!-- header -->
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
            <a class="btn" href="<c:url value='/membres/nouveau'/>">Créer un compte</a>
        </sec:authorize>
    </nav>
</header>

<!-- main content -->
<main style="max-width:900px;margin:2rem auto;padding:1rem;background:white;
               border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,0.05);">
    <div class="mes-sorties-header"
         style="display:flex;justify-content:space-between;align-items:center;margin-bottom:1rem;">
        <h2>📋 Mes sorties</h2>
        <a class="btn btn-green" href="<c:url value='/mes-sorties/nouvelle'/>">➕ Nouvelle sortie</a>
    </div>

    <c:choose>
        <c:when test="${empty mesSorties}">
            <p>Aucune sortie trouvée.</p>
        </c:when>
        <c:otherwise>
            <table class="table">
                <thead>
                <tr>
                    <th>Nom</th>
                    <th>Catégorie</th>
                    <th>Date</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="s" items="${mesSorties}">
                    <tr>
                        <td>${s.nom}</td>
                        <td>${s.categorie.nom}</td>
                        <td>${s.dateSortie}</td>
                        <td>
                            <a class="btn btn-blue"
                               href="<c:url value='/sorties/${s.id}'/>">Voir</a>
                            <a class="btn btn-gray"
                               href="<c:url value='/mes-sorties/${s.id}/edit'/>">✏️</a>
                            <form action="<c:url value='/mes-sorties/${s.id}/delete'/>"
                                  method="post" style="display:inline">
                                <input type="hidden" name="${_csrf.parameterName}"
                                       value="${_csrf.token}" />
                                <button class="btn btn-red"
                                        onclick="return confirm('Supprimer ?')">🗑️</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</main>

<!-- footer -->
<footer style="background:#222;color:#fff;padding:1rem;text-align:center">
    <p>Projet Club d'Escalade – M1 IDL 2025</p>
</footer>

</body>
</html>
