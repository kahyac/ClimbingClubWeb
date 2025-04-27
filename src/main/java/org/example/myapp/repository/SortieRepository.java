package org.example.myapp.repository;

import org.example.myapp.model.Sortie;
import org.example.myapp.model.Categorie;
import org.example.myapp.model.Membre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SortieRepository extends JpaRepository<Sortie, Long> {
    List<Sortie> findByCategorie(Categorie categorie);
    List<Sortie> findByCreateur(Membre createur);
    List<Sortie> findByNomContainingIgnoreCase(String nom);
    Page<Sortie> findAll(Pageable pageable);
}
