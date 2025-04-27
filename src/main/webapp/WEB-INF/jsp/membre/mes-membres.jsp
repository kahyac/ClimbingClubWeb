<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"/>
    <title>Membres – Club d’Escalade</title>
    <link rel="stylesheet" href="<c:url value='/css/app.css'/>"/>
</head>
<body>

<!-- header -->
<header style="display:flex;justify-content:space-between;align-items:center;
               padding:1rem;background:#222;color:#fff;">
    <h1><a href="<c:url value='/'/>" style="color:white;text-decoration:none">🧗 Club d’Escalade</a></h1>
    <nav>
        <sec:authorize access="isAuthenticated()">
            <a class="btn" href="<c:url value='/'/>">Accueil</a>
            <a class="btn" href="<c:url value='/mes-sorties'/>">Mes sorties</a>
            <a class="btn" href="<c:url value='/categories'/>">Catégories</a>
            <a class="btn" href="<c:url value='/membres'/>">Membres</a>
            <span style="margin:0 1rem">Bonjour <strong>${displayName}</strong></span>
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

<main style="max-width:900px;margin:2rem auto;padding:1rem;background:white;
             border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,0.05);">

    <h2>👥 Membres (page ${currentPage + 1}/${totalPages})</h2>

    <!-- Recherche -->
    <form method="get" action="<c:url value='/membres'/>"
          style="margin:1rem 0;display:flex;gap:0.5rem;">
        <input type="text" name="search" value="${fn:escapeXml(param.search)}"
               placeholder="Rechercher un membre…"
               style="flex:1;padding:0.5rem;border:1px solid #ccc;border-radius:4px;"/>
        <button type="submit" class="btn">🔍</button>
    </form>

    <!-- Liste des membres -->
    <c:if test="${empty membresPage.content}">
        <p>Aucun membre trouvé.</p>
    </c:if>

    <ul class="list">
        <c:forEach var="m" items="${membresPage.content}">
            <li style="display:flex;justify-content:space-between;align-items:center;padding:0.5rem 0;">
                <span>${m.prenom} ${m.nom}</span>
                <a class="btn btn-blue" href="<c:url value='/membres/${m.id}'/>">Voir</a>
            </li>
        </c:forEach>
    </ul>

    <!-- Pagination -->
    <c:if test="${totalPages > 1}">
        <nav class="pagination" style="margin-top:1rem;">
            <c:forEach var="i" begin="0" end="${totalPages - 1}">
                <c:url var="pageUrl" value="/membres">
                    <c:param name="page" value="${i}"/>
                    <c:param name="size" value="${size}"/>
                    <c:if test="${not empty param.search}">
                        <c:param name="search" value="${param.search}"/>
                    </c:if>
                </c:url>
                <a class="page-link ${i == currentPage ? 'active' : ''}" href="${pageUrl}">
                        ${i + 1}
                </a>
            </c:forEach>
        </nav>
    </c:if>

</main>

<footer style="background:#222;color:#fff;padding:1rem;text-align:center">
    <p>Projet Club d'Escalade – M1 IDL 2025</p>
</footer>
</body>
</html>
