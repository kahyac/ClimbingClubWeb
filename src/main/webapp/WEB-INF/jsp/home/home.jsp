<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"/>
    <title>Accueil – Club d’Escalade</title>
    <style>
        body {
            font-family: "Segoe UI", sans-serif;
            background: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        .btn {
            background-color: #007bff;
            color: white;
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            cursor: pointer;
            transition: background 0.3s;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
        }
        .table th, .table td {
            border: 1px solid #ddd;
            padding: 0.75rem;
        }
        .table th {
            background-color: #f2f2f2;
            text-align: left;
        }
        .list {
            list-style: none;
            padding-left: 1rem;
        }
        .pagination {
            margin: 1rem 0;
            display: flex;
            gap: 0.5rem;
            flex-wrap: wrap;
        }
        .page-link {
            padding: 0.5rem 0.75rem;
            background-color: #eee;
            text-decoration: none;
            border-radius: 4px;
            color: #333;
        }
        .page-link.active {
            background-color: #007bff;
            color: white;
            font-weight: bold;
        }
        main {
            max-width: 1000px;
            margin: 2rem auto;
            background: white;
            padding: 2rem;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0,0,0,0.05);
        }
        h2 {
            margin-top: 2rem;
            color: #333;
        }
        h3 {
            margin-bottom: 0.3rem;
            color: #444;
        }
        label {
            font-weight: bold;
        }
    </style>
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

<main>

    <!-- 1) Catégories avec sorties -->
    <h2>📂 Catégories & leurs sorties</h2>
    <c:forEach var="cat" items="${categories}">
        <div style="background:#f9f9f9;padding:1rem;border-radius:8px;margin-bottom:1rem;">
            <h3>📁 ${cat.nom}</h3>
            <ul class="list">
                <c:forEach var="s" items="${sortiesParCategorie[cat.id]}">
                    <li>
                        <a href="<c:url value='/sorties/${s.id}'/>">${s.nom}</a>
                        (le ${s.dateSortie})
                    </li>
                </c:forEach>
                <c:if test="${empty sortiesParCategorie[cat.id]}">
                    <li style="color:gray">Aucune sortie pour cette catégorie.</li>
                </c:if>
            </ul>
        </div>
    </c:forEach>

    <!-- 2) Membres paginés -->
    <sec:authorize access="isAuthenticated()">
        <h2>👥 Membres (page ${currentMemPage + 1}/${totalMemPages})</h2>
        <ul class="list">
            <c:forEach var="m" items="${membresPage.content}">
                <li>${m.prenom} ${m.nom}</li>
            </c:forEach>
        </ul>
        <c:if test="${totalMemPages > 1}">
            <nav class="pagination">
                <c:forEach var="i" begin="0" end="${totalMemPages - 1}">
                    <c:url var="urlMem" value="/">
                        <c:param name="memPage" value="${i}"/>
                        <c:param name="memSize" value="${memSize}"/>
                    </c:url>
                    <a class="page-link ${i == currentMemPage ? 'active' : ''}" href="${urlMem}">${i + 1}</a>
                </c:forEach>
            </nav>
        </c:if>
    </sec:authorize>

    <!-- 3) Recherche avancée -->
    <h2>🔎 Filtrer les sorties</h2>
    <form method="get" action="<c:url value='/'/>"
          style="display:grid;grid-template-columns:repeat(auto-fit,minmax(200px,1fr));gap:1rem;margin-bottom:2rem;align-items:end;">

        <div>
            <label for="search">Nom :</label>
            <input type="text" id="search" name="search"
                   value="${fn:escapeXml(param.search)}"
                   placeholder="Nom de la sortie"
                   style="width:100%;padding:0.5rem;border:1px solid #ccc;border-radius:4px;"/>
        </div>

        <div>
            <label for="categorieId">Catégorie :</label>
            <select name="categorieId" id="categorieId" style="width:100%;padding:0.5rem;">
                <option value="">Toutes</option>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.id}" ${param.categorieId == cat.id ? 'selected' : ''}>${cat.nom}</option>
                </c:forEach>
            </select>
        </div>

        <div>
            <label for="afterDate">Après le :</label>
            <input type="date" id="afterDate" name="afterDate"
                   value="${param.afterDate}" style="width:100%;padding:0.5rem;border:1px solid #ccc;border-radius:4px;"/>
        </div>

        <div>
            <button type="submit" class="btn" style="width:100%;">🔍 Rechercher</button>
        </div>
    </form>

    <!-- 4) Résultats -->
    <h2>
        🏞️
        <c:choose>
            <c:when test="${not empty param.search || not empty param.categorieId || not empty param.afterDate}">
                Résultats filtrés (${sortiesPage.totalElements} sortie(s))
            </c:when>
            <c:otherwise>
                Toutes les sorties (page ${currentPage + 1}/${totalPages})
            </c:otherwise>
        </c:choose>
    </h2>

    <table class="table">
        <thead>
        <tr>
            <th>Nom</th>
            <th>Catégorie</th>
            <sec:authorize access="isAuthenticated()"><th>Créateur</th></sec:authorize>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="s" items="${sortiesPage.content}">
            <tr>
                <td><a href="<c:url value='/sorties/${s.id}'/>">${s.nom}</a></td>
                <td>${s.categorie.nom}</td>
                <sec:authorize access="isAuthenticated()">
                    <td>${s.createur.prenom} ${s.createur.nom}</td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${totalPages > 1}">
        <nav class="pagination">
            <c:forEach var="i" begin="0" end="${totalPages - 1}">
                <c:url var="urlSort" value="/">
                    <c:param name="page" value="${i}"/>
                    <c:param name="size" value="${size}"/>
                    <c:if test="${not empty param.search}">
                        <c:param name="search" value="${param.search}"/>
                    </c:if>
                    <c:if test="${not empty param.categorieId}">
                        <c:param name="categorieId" value="${param.categorieId}"/>
                    </c:if>
                    <c:if test="${not empty param.afterDate}">
                        <c:param name="afterDate" value="${param.afterDate}"/>
                    </c:if>
                </c:url>
                <a class="page-link ${i == currentPage ? 'active' : ''}" href="${urlSort}">${i + 1}</a>
            </c:forEach>
        </nav>
    </c:if>

</main>

<!-- footer -->
<footer style="background:#222;color:#fff;padding:1rem;text-align:center">
    <p>Projet Club d'Escalade – M1 IDL 2025</p>
</footer>
</body>
</html>
