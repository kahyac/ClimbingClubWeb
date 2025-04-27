package org.example.myapp.service;

import org.example.myapp.model.Categorie;
import org.example.myapp.model.Membre;
import org.example.myapp.model.Sortie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SortieService {
    Optional<Sortie> getSortieById(Long id);
    List<Sortie> searchSortiesByName(String name);
    List<Sortie> getSortiesByCategorie(Categorie categorie);
    List<Sortie> getSortiesByCreateur(Membre membre);
    Sortie saveSortie(Sortie sortie);
    void deleteSortie(Long id);
    Page<Sortie> getSortiesPage(Pageable pageable);

}
