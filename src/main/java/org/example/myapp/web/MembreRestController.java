package org.example.myapp.web;

import org.example.myapp.model.Membre;
import org.example.myapp.service.MembreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membres")
public class MembreRestController {

    private final MembreService membreService;

    @Autowired
    public MembreRestController(MembreService membreService) {
        this.membreService = membreService;
    }

    /**
     * Liste tous les membres.
     */
    @GetMapping
    public List<Membre> getAllMembres() {
        return membreService.getAllMembres();
    }

    /**
     * Récupère un membre par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Membre> getMembreById(@PathVariable Long id) {
        return membreService.getMembreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Récupère un membre par son email.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Membre> getMembreByEmail(@PathVariable String email) {
        return membreService.getMembreByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crée un nouveau membre.
     */
    @PostMapping
    public ResponseEntity<Membre> createMembre(@RequestBody Membre membre) {
        if (membre.getEmail() == null || membre.getNom() == null || membre.getMotDePasse() == null) {
            return ResponseEntity.badRequest().build();
        }
        Membre saved = membreService.saveMembre(membre);
        return ResponseEntity.ok(saved);
    }

    /**
     * Met à jour un membre existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Membre> updateMembre(@PathVariable Long id, @RequestBody Membre membre) {
        return membreService.getMembreById(id).map(existing -> {
            membre.setId(id);
            return ResponseEntity.ok(membreService.saveMembre(membre));
        }).orElse(ResponseEntity.notFound().build());
    }
}
