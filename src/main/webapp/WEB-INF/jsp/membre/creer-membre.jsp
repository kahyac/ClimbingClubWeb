<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Créer ou modifier un membre</title>
    <link rel="stylesheet" href="<c:url value='/css/app.css'/>"/>
</head>
<body>

<h2>${membre.id == null ? "➕ Nouveau membre" : "✏️ Modifier membre"}</h2>

<form:form method="post" modelAttribute="membre" cssClass="form-grid">

    <label>Prénom</label>
    <form:input path="prenom" required="required"/>

    <label>Nom</label>
    <form:input path="nom" required="required"/>

    <label>Email</label>
    <form:input path="email" type="email" required="required"/>

    <label>Mot de passe</label>
    <form:input path="motDePasse" type="password" required="required"/>

    <div class="form-actions">
        <button class="btn btn-green" type="submit">
                ${membre.id == null ? "Créer" : "Mettre à jour"}
        </button>
        <a class="btn btn-gray" href="<c:url value='/membres'/>">Annuler</a>
    </div>
</form:form>

</body>
</html>