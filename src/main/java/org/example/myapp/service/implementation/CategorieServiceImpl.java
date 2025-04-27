package org.example.myapp.service.implementation;

import org.example.myapp.dao.ClubDAO;
import org.example.myapp.model.Categorie;
import org.example.myapp.model.Sortie;
import org.example.myapp.service.CategorieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategorieServiceImpl implements CategorieService {

    private final ClubDAO clubDAO;

    public CategorieServiceImpl(ClubDAO clubDAO) {
        this.clubDAO = clubDAO;
    }

    @Override
    public List<Categorie> getAllCategories() {
        return clubDAO.obtenirToutesLesCategories();
    }

    @Override
    public Optional<Categorie> getCategorieById(Long id) {
        return clubDAO.obtenirCategorieParId(id);
    }

    @Override
    public Categorie createCategorie(Categorie categorie) {
        return clubDAO.creerCategorie(categorie);
    }

    @Override
    public List<Sortie> getSortiesByCategorie(Long categorieId) {
        Optional<Categorie> cat = clubDAO.obtenirCategorieParId(categorieId);
        if (cat.isEmpty()) {
            return Collections.emptyList();
        }
        return clubDAO.obtenirSortiesParCategorie(cat.orElse(null));
    }

    @Override
    public Page<Categorie> getCategoriesPage(Pageable pageable) {
        return clubDAO.obtenirCategoriesPage(pageable);
    }
}
