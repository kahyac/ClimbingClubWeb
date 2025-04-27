<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Membres – Club d’Escalade</title>
    <link rel="stylesheet" href="<c:url value='/css/app.css'/>"/>
</head>
<body>

<jsp:include page="/WEB-INF/jsp/common/header.jsp"/>

<main style="max-width:800px;margin:2rem auto;padding:1rem;background:white;
             border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,0.05);">
    <h2>👥 Membres (page ${currentPage + 1}/${totalPages})</h2>

    <!-- Formulaire de recherche -->
    <form method="get" action="<c:url value='/membres'/>" style="margin-bottom:1rem;display:flex;gap:0.5rem;">
        <input type="text" name="search" value="${fn:escapeXml(param.search)}" placeholder="Rechercher un membre..."
               style="flex:1;padding:0.5rem;border-radius:4px;border:1px solid #ccc;"/>
        <button type="submit" class="btn">🔍</button>
    </form>

    <ul class="list">
        <c:forEach var="m" items="${membresPage.content}">
            <li style="display:flex;justify-content:space-between;align-items:center;">
                <span>${m.prenom} ${m.nom}</span>
                <a class="btn" href="<c:url value='/membres/${m.id}'/>">Voir</a>
            </li>
        </c:forEach>
    </ul>

    <!-- Pagination -->
    <c:if test="${totalPages > 1}">
        <nav class="pagination">
            <c:forEach var="i" begin="0" end="${totalPages - 1}">
                <c:url var="urlPage" value="/membres">
                    <c:param name="page" value="${i}"/>
                    <c:param name="size" value="${size}"/>
                    <c:if test="${not empty param.search}">
                        <c:param name="search" value="${param.search}"/>
                    </c:if>
                </c:url>
                <a class="page-link ${i == currentPage ? 'active' : ''}" href="${urlPage}">
                        ${i + 1}
                </a>
            </c:forEach>
        </nav>
    </c:if>
</main>

<jsp:include page="/WEB-INF/jsp/common/footer.jsp"/>

</body>
</html>
