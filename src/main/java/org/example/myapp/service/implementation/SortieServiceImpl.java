package org.example.myapp.service.implementation;

import org.example.myapp.dao.ClubDAO;
import org.example.myapp.model.Categorie;
import org.example.myapp.model.Membre;
import org.example.myapp.model.Sortie;
import org.example.myapp.service.SortieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SortieServiceImpl implements SortieService {

    private final ClubDAO clubDAO;

    public SortieServiceImpl(ClubDAO clubDAO) {
        this.clubDAO = clubDAO;
    }

    @Override
    public Optional<Sortie> getSortieById(Long id) {
        return clubDAO.obtenirSortieParId(id);
    }

    @Override
    public List<Sortie> searchSortiesByName(String name) {
        return clubDAO.rechercherSortiesParNom(name);
    }

    @Override
    public List<Sortie> getSortiesByCategorie(Categorie categorie) {
        return clubDAO.obtenirSortiesParCategorie(categorie);
    }

    @Override
    public List<Sortie> getSortiesByCreateur(Membre membre) {
        return clubDAO.obtenirSortiesParCreateur(membre);
    }

    @Override
    public Sortie saveSortie(Sortie sortie) {
        return clubDAO.creerOuMettreAJourSortie(sortie);
    }

    @Override
    public void deleteSortie(Long id) {
        clubDAO.supprimerSortie(id);
    }

    @Override
    public Page<Sortie> getSortiesPage(Pageable pageable) {
        return clubDAO.obtenirSortiesPage(pageable);
    }

}
