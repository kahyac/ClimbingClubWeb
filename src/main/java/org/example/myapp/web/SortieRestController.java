package org.example.myapp.web;

import org.example.myapp.model.Categorie;
import org.example.myapp.model.Membre;
import org.example.myapp.model.Sortie;
import org.example.myapp.service.CategorieService;
import org.example.myapp.service.SortieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sorties")
public class SortieRestController {

    private final SortieService sortieService;
    private final CategorieService categorieService;

    @Autowired
    public SortieRestController(SortieService sortieService, CategorieService categorieService) {
        this.sortieService = sortieService;
        this.categorieService = categorieService;
    }

    @GetMapping
    public Page<Sortie> getSortiesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return sortieService.getSortiesPage(pageable);
    }

    @GetMapping("/{id}")
    public Optional<Sortie> getSortieById(@PathVariable Long id) {
        return sortieService.getSortieById(id);
    }

    @GetMapping("/search")
    public List<Sortie> searchSortiesByName(@RequestParam String nom) {
        return sortieService.searchSortiesByName(nom);
    }

    @GetMapping("/byCategorie/{categorieId}")
    public List<Sortie> getSortiesByCategorie(@PathVariable Long categorieId) {
        Categorie categorie = new Categorie();
        categorie.setId(categorieId);
        return sortieService.getSortiesByCategorie(categorie);
    }

    @GetMapping("/byMembre/{membreId}")
    public List<Sortie> getSortiesByMembre(@PathVariable Long membreId) {
        Membre membre = new Membre();
        membre.setId(membreId);
        return sortieService.getSortiesByCreateur(membre);
    }

    @PostMapping
    public Sortie createSortie(@RequestBody Sortie sortie) {
        return sortieService.saveSortie(sortie);
    }

    @PutMapping("/{id}")
    public Sortie updateSortie(@PathVariable Long id, @RequestBody Sortie sortie) {
        sortie.setId(id);
        return sortieService.saveSortie(sortie);
    }

    @DeleteMapping("/{id}")
    public void deleteSortie(@PathVariable Long id) {
        sortieService.deleteSortie(id);
    }
}
