<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${sortie.id == null}">🆕 Créer une nouvelle sortie</c:when>
            <c:otherwise>✏️ Modifier la sortie</c:otherwise>
        </c:choose>
        – Club d’Escalade
    </title>
    <link rel="stylesheet" href="<c:url value='/css/app.css'/>"/>
    <style>
        .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 1rem; }
        .form-grid .full { grid-column: 1 / -1; }
        .form-grid label { margin-bottom: 0.25rem; font-weight: bold; }
        .form-grid .form-control { width:100%;padding:0.5rem;border:1px solid #ccc;border-radius:4px; }
        .form-actions { display:flex;gap:1rem;justify-content:flex-start; }
        .error { color:#a94442;font-size:0.9em;margin-top:0.25rem; }
    </style>
</head>
<body>
<header style="background:#222;color:#fff;padding:1rem">
    <h1>
        <a href="<c:url value='/'/>" style="color:white;text-decoration:none">
            🧗 Club d’Escalade
        </a>
    </h1>
    <nav>
        <sec:authorize access="isAuthenticated()">
            <a href="<c:url value='/mes-sorties'/>" style="color:#ddd;margin-right:1rem">Mes sorties</a>
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

<main style="max-width:900px;margin:2rem auto;">
    <h2>
        <c:choose>
            <c:when test="${sortie.id == null}">🆕 Créer une nouvelle sortie</c:when>
            <c:otherwise>✏️ Modifier la sortie</c:otherwise>
        </c:choose>
    </h2>

    <form:form method="post" modelAttribute="sortie" cssClass="form-grid">
        <!-- CSRF -->
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <!-- Nom -->
        <div>
            <label for="nom">Nom</label>
            <form:input path="nom" id="nom" cssClass="form-control" required="true"/>
            <form:errors path="nom" cssClass="error"/>
        </div>

        <!-- Date -->
        <div>
            <label for="dateSortie">Date</label>
            <form:input path="dateSortie" id="dateSortie" type="date" cssClass="form-control" required="true"/>
            <form:errors path="dateSortie" cssClass="error"/>
        </div>

        <!-- Site web -->
        <div class="full">
            <label for="siteWeb">Site Web</label>
            <form:input path="siteWeb" id="siteWeb" cssClass="form-control"/>
        </div>

        <!-- Description -->
        <div class="full">
            <label for="description">Description</label>
            <form:textarea path="description" id="description" cssClass="form-control" rows="4"/>
        </div>

        <!-- Catégorie -->
        <div class="full">
            <label for="categorie">Catégorie</label>
            <form:select path="categorie.id" id="categorie"
                         items="${categories}"
                         itemValue="id" itemLabel="nom"
                         cssClass="form-control"/>
        </div>

        <!-- Boutons -->
        <div class="full form-actions">
            <button type="submit" class="btn btn-green">
                <c:choose>
                    <c:when test="${sortie.id == null}">Créer</c:when>
                    <c:otherwise>Mettre à jour</c:otherwise>
                </c:choose>
            </button>
            <a href="<c:url value='/mes-sorties'/>" class="btn btn-gray">Retour</a>
        </div>
    </form:form>
</main>

<footer style="background:#222;color:#fff;padding:1rem;text-align:center">
    <p>Projet Club d'Escalade – M1 IDL 2025</p>
</footer>
</body>
</html>
