<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <link rel="stylesheet" href="<c:url value='/css/app.css'/>"/>
</head>
<body>
<!-- header -->
<header style="display:flex;justify-content:space-between;align-items:center;
                 padding:1rem;background:#222;color:#fff;">
    <h1>
        <a href="<c:url value='/'/>"
           style="color:white;text-decoration:none">
            🧗 Club d’Escalade
        </a>
    </h1>
    <nav>
        <sec:authorize access="isAuthenticated()">
            <a class="btn" href="<c:url value='/mes-sorties'/>">Mes sorties</a>
            <span style="margin:0 1rem">
                Bonjour <strong>${displayName}</strong>
            </span>
            <form action="<c:url value='/logout'/>" method="post" style="display:inline">
                <button class="btn" type="submit">Se déconnecter</button>
            </form>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <a class="btn" href="<c:url value='/loginPage'/>">Se connecter</a>
        </sec:authorize>
    </nav>
</header>

<!-- main content -->
<main style="max-width:600px;margin:2rem auto;padding:1rem;
               background:white;border-radius:8px;box-shadow:0 0 10px rgba(0,0,0,0.05);">

    <!-- 🔙 lien retour accueil -->
    <div style="margin-bottom:1rem">
        <a href="<c:url value='/'/>" class="btn btn-gray">⬅ Retour à l'accueil</a>
    </div>

    <h2>🔐 Connexion</h2>

    <c:if test="${not empty param.error}">
        <div style="color:#a94442;background:#f2dede;padding:0.75rem;
                  border:1px solid #ebccd1;border-radius:4px;margin-bottom:1rem;">
            Identifiants invalides
        </div>
    </c:if>

    <form action="<c:url value='/perform_login'/>" method="post" style="display:flex;
                 flex-direction:column;gap:1rem;">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <input type="email" name="email" placeholder="E-mail" required
               style="padding:0.5rem;border:1px solid #ccc;border-radius:4px;"/>

        <input type="password" name="password" placeholder="Mot de passe" required
               style="padding:0.5rem;border:1px solid #ccc;border-radius:4px;"/>

        <button type="submit" class="btn btn-primary">Se connecter</button>
    </form>

    <p style="margin-top:1rem;">
        <a href="<c:url value='/forgot-password'/>">Mot de passe oublié ?</a>
    </p>
</main>

<!-- footer -->
<footer style="background:#222;color:#fff;padding:1rem;text-align:center">
    <p>Projet Club d'Escalade – M1 IDL 2025</p>
</footer>
</body>
</html>
