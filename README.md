# Climbing Club Web Application

## Présentation
Application web de gestion d'un club d'escalade réalisée avec **Spring Boot**, **Spring MVC**, **JPA/Hibernate** et **JSP**.  
Elle permet la gestion des membres, des catégories d'activités et des sorties d'escalade, avec une authentification sécurisée et un système de récupération de mot de passe.

## Fonctionnalités principales
- Authentification sécurisée avec Spring Security
- Gestion des membres (création, édition, consultation)
- Gestion des sorties d'escalade (création, édition, suppression)
- Gestion des catégories d'activités
- Récupération de mot de passe via e-mail (FakeSMTP pour le développement)
- Validation des formulaires (JPA/Bean Validation)
- Pagination et recherche avancée
- API REST pour les membres, catégories et sorties
- Tests unitaires (services, sécurité, DAO)

## Architecture du projet
- **Model / DAO / Repository** : Gestion des entités `Membre`, `Categorie`, `Sortie`, `PasswordResetToken`.
- **Services** : Logique métier centralisée.
- **Contrôleurs** : Contrôleurs MVC pour les vues JSP + API REST.
- **Sécurité** : Spring Security (authentification, protection CSRF, contrôle d'accès).
- **Validation** : Contraintes JPA et validation au niveau des formulaires.
- **Tests unitaires** : Couverture des principales fonctionnalités avec JUnit et Mockito.

## Technologies utilisées
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- JSP / JSTL
- HSQLDB (base de données en mémoire pour le développement)
- FakeSMTP (serveur SMTP factice pour les tests d'e-mails)
- Maven

## Déploiement local
1. Cloner le projet :
   ```bash
   git clone git@github.com:kahyac/ClimbingClubWeb.git
   cd ClimbingClubWeb
