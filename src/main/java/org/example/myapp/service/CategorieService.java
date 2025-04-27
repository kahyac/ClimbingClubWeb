package org.example.myapp.service;

import org.example.myapp.model.Categorie;
import org.example.myapp.model.Sortie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategorieService {
    List<Categorie> getAllCategories();
    Optional<Categorie> getCategorieById(Long id);
    Categorie createCategorie(Categorie categorie);

    List<Sortie> getSortiesByCategorie(Long categorieId);
    Page<Categorie> getCategoriesPage(Pageable pageable);
}
