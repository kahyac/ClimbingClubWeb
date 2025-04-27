<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"/>
    <title>Catégories – Club d’Escalade</title>
    <link rel="stylesheet" href="<c:url value='/css/app.css'/>"/>
</head>
<body>

<!-- Header commun -->
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

    <div style="display:flex;justify-content:space-between;align-items:center;
                margin-bottom:1rem;">
        <h2>📂 Catégories (page ${currentCatPage + 1}/${totalCatPages})</h2>
        <sec:authorize access="isAuthenticated()">
            <a class="btn btn-green" href="<c:url value='/categories/nouveau'/>">
                ➕ Nouvelle catégorie
            </a>
        </sec:authorize>
    </div>

    <c:if test="${empty categoriesPage.content}">
        <p>Aucune catégorie trouvée.</p>
    </c:if>
    <ul class="list">
        <c:forEach var="cat" items="${categoriesPage.content}">
            <li style="display:flex;justify-content:space-between;padding:0.5rem 0;">
                <span>${cat.nom}</span>
                <span>
            <a class="btn btn-blue" href="<c:url value='/categories/${cat.id}'/>">Voir</a>
            <sec:authorize access="isAuthenticated()">
    <a href="<c:url value='/categories/modifier/${categorie.id}'/>"
       class="btn btn-green">
        ✏️ Modifier
    </a>
            </sec:authorize>
          </span>
            </li>
        </c:forEach>
    </ul>

    <c:if test="${totalCatPages > 1}">
        <nav class="pagination">
            <c:forEach var="i" begin="0" end="${totalCatPages - 1}">
                <c:url var="pageUrl" value="/categories">
                    <c:param name="page" value="${i}"/>
                    <c:param name="size" value="${catSize}"/>
                </c:url>
                <a class="page-link ${i == currentCatPage ? 'active' : ''}"
                   href="${pageUrl}">${i + 1}</a>
            </c:forEach>
        </nav>
    </c:if>

</main>

<!-- Footer commun -->
<footer style="background:#222;color:#fff;padding:1rem;text-align:center">
    <p>Projet Club d'Escalade – M1 IDL 2025</p>
</footer>
</body>
</html>
