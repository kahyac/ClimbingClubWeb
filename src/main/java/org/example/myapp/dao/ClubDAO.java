package org.example.myapp.dao;

import java.util.List;
import java.util.Optional;

import org.example.myapp.model.Categorie;
import org.example.myapp.model.Membre;
import org.example.myapp.model.Sortie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubDAO {

    List<Categorie> obtenirToutesLesCategories();
    Optional<Categorie> obtenirCategorieParId(Long id);
    Categorie creerCategorie(Categorie categorie);
    Page<Categorie> obtenirCategoriesPage(Pageable pageable);

    Optional<Sortie> obtenirSortieParId(Long id);
    List<Sortie> rechercherSortiesParNom(String nom);
    List<Sortie> obtenirSortiesParCategorie(Categorie categorie);
    List<Sortie> obtenirSortiesParCreateur(Membre createur);
    Sortie creerOuMettreAJourSortie(Sortie sortie);
    void supprimerSortie(Long id);
    Page<Sortie> obtenirSortiesPage(Pageable pageable);
    

    List<Membre> obtenirTousLesMembres();
    Optional<Membre> obtenirMembreParId(Long id);
    Optional<Membre> obtenirMembreParEmail(String email);
    Membre creerOuMettreAJourMembre(Membre membre);
    Page<Membre>    obtenirMembresPage(Pageable pageable);
}
