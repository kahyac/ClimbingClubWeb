package org.example.myapp.web;

import org.example.myapp.model.Categorie;
import org.example.myapp.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategorieRestController {

    private final CategorieService categorieService;

    @Autowired
    public CategorieRestController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    /**
     * Récupère toutes les catégories.
     */
    @GetMapping
    public List<Categorie> getAllCategories() {
        return categorieService.getAllCategories();
    }

    /**
     * Récupère une catégorie par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable Long id) {
        return categorieService.getCategorieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée une nouvelle catégorie.
     */
    @PostMapping
    public ResponseEntity<Categorie> createCategorie(@RequestBody Categorie categorie) {
        if (categorie.getNom() == null || categorie.getNom().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Categorie saved = categorieService.createCategorie(categorie);
        return ResponseEntity.ok(saved);
    }
}
