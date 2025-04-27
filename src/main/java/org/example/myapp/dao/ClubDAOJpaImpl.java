package org.example.myapp.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.example.myapp.model.Categorie;
import org.example.myapp.model.Membre;
import org.example.myapp.model.Sortie;
import org.example.myapp.repository.CategorieRepository;
import org.example.myapp.repository.MembreRepository;
import org.example.myapp.repository.SortieRepository;
@Repository
@Transactional
public class ClubDAOJpaImpl implements ClubDAO {

    @Autowired
    private CategorieRepository categorieRepository;
    
    @Autowired
    private SortieRepository sortieRepository;
    
    @Autowired
    private MembreRepository membreRepository;
    
    // catégories
    @Override
    public List<Categorie> obtenirToutesLesCategories() {
        return categorieRepository.findAll();
    }

    @Override
    public Optional<Categorie> obtenirCategorieParId(Long id) {
        return categorieRepository.findById(id);
    }
    
    @Override
    public Categorie creerCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public Page<Categorie> obtenirCategoriesPage(Pageable pageable) {
        return categorieRepository.findAll(pageable);
    }
    // sorties
    @Override
    public Optional<Sortie> obtenirSortieParId(Long id) {
    return sortieRepository.findById(id);
    }
    
    @Override
    public List<Sortie> rechercherSortiesParNom(String nom) {
        return sortieRepository.findByNomContainingIgnoreCase(nom);
    }
    
    @Override
    public List<Sortie> obtenirSortiesParCategorie(Categorie categorie) {
        return sortieRepository.findByCategorie(categorie);
    }
    
    @Override
    public List<Sortie> obtenirSortiesParCreateur(Membre createur) {
        return sortieRepository.findByCreateur(createur);
    }
    
    @Override
    public Sortie creerOuMettreAJourSortie(Sortie sortie) {
        return sortieRepository.save(sortie);
    }
    
    @Override
    public void supprimerSortie(Long id) {
        sortieRepository.deleteById(id);
    }

    @Override
    public Page<Sortie> obtenirSortiesPage(Pageable pageable) {
        return sortieRepository.findAll(pageable);
    }

    // membres
    @Override
    public List<Membre> obtenirTousLesMembres() {
        return membreRepository.findAll();
    }
    
    @Override
    public Optional<Membre> obtenirMembreParId(Long id) {
        return membreRepository.findById(id);

    }
    
    @Override
    public Optional<Membre> obtenirMembreParEmail(String email) {
        return membreRepository.findByEmail(email);
    }
    
    @Override
    public Membre creerOuMettreAJourMembre(Membre membre) {
        return membreRepository.save(membre);
    }

    @Override
    public Page<Membre> obtenirMembresPage(Pageable pageable) {
        return membreRepository.findAll(pageable);
    }
}
