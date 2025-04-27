package org.example.myapp.web.view;

import jakarta.validation.Valid;
import org.example.myapp.model.Membre;
import org.example.myapp.model.Sortie;
import org.example.myapp.service.CategorieService;
import org.example.myapp.service.MembreService;
import org.example.myapp.service.SortieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/mes-sorties")
public class SortieViewController {

    private final SortieService   sortieService;
    private final MembreService    membreService;
    private final CategorieService categorieService;

    @Autowired
    public SortieViewController(SortieService sortieService,
                                MembreService membreService,
                                CategorieService categorieService) {
        this.sortieService   = sortieService;
        this.membreService   = membreService;
        this.categorieService = categorieService;
    }

    /*------------------------------------------------------------
     * 1) Liste des sorties de l’utilisateur
     *-----------------------------------------------------------*/
    @GetMapping
    public String mesSorties(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/loginPage";
        }
        String email = principal.getName();
        Membre membre = membreService.getMembreByEmail(email)
                .orElseThrow();
        List<Sortie> sorties = sortieService.getSortiesByCreateur(membre);
        model.addAttribute("mesSorties", sorties);
        return "sortie/mes-sorties";
    }

    /*------------------------------------------------------------
     * 2) Affichage du formulaire de création
     *-----------------------------------------------------------*/
    @GetMapping("/nouvelle")
    public String afficherFormulaireCreation(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/loginPage";
        }
        model.addAttribute("sortie", new Sortie());
        model.addAttribute("categories", categorieService.getAllCategories());
        return "sortie/creer-sortie";
    }

    /*------------------------------------------------------------
     * 3) Traitement de la création – avec validation
     *-----------------------------------------------------------*/
    @PostMapping("/nouvelle")
    public String creerNouvelleSortie(
            @Valid @ModelAttribute("sortie") Sortie sortie,
            BindingResult result,
            Model model,
            Principal principal
    ) {
        if (principal == null) {
            return "redirect:/loginPage";
        }
        if (result.hasErrors()) {
            model.addAttribute("categories", categorieService.getAllCategories());
            return "sortie/creer-sortie";
        }
        Membre createur = membreService.getMembreByEmail(principal.getName())
                .orElseThrow();
        sortie.setCreateur(createur);
        sortieService.saveSortie(sortie);
        return "redirect:/mes-sorties";
    }

    /*------------------------------------------------------------
     * 4) Affichage du formulaire d’édition
     *-----------------------------------------------------------*/
    @GetMapping("/{id}/edit")
    public String afficherFormulaireEdition(
            @PathVariable Long id,
            Model model,
            Principal principal
    ) {
        if (principal == null) {
            return "redirect:/loginPage";
        }
        Sortie sortie = sortieService.getSortieById(id)
                .orElseThrow();
        if (!sortie.getCreateur().getEmail().equals(principal.getName())) {
            return "redirect:/mes-sorties?unauthorized=true";
        }
        model.addAttribute("sortie", sortie);
        model.addAttribute("categories", categorieService.getAllCategories());
        return "sortie/creer-sortie";
    }

    /*------------------------------------------------------------
     * 5) Traitement de la modification – avec validation
     *-----------------------------------------------------------*/
    @PostMapping("/{id}/edit")
    public String modifierSortie(
            @PathVariable Long id,
            @Valid @ModelAttribute("sortie") Sortie formSortie,
            BindingResult result,
            Model model,
            Principal principal
    ) {
        if (principal == null) {
            return "redirect:/loginPage";
        }
        Sortie exist = sortieService.getSortieById(id)
                .orElseThrow();
        if (!exist.getCreateur().getEmail().equals(principal.getName())) {
            return "redirect:/mes-sorties?unauthorized=true";
        }
        if (result.hasErrors()) {
            model.addAttribute("categories", categorieService.getAllCategories());
            return "sortie/creer-sortie";
        }
        // Met à jour les champs
        formSortie.setId(id);
        formSortie.setCreateur(exist.getCreateur());
        sortieService.saveSortie(formSortie);
        return "redirect:/mes-sorties";
    }

    /*------------------------------------------------------------
     * 6) Suppression d’une sortie
     *-----------------------------------------------------------*/
    @PostMapping("/{id}/delete")
    public String supprimerSortie(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/loginPage";
        }
        Sortie sortie = sortieService.getSortieById(id)
                .orElseThrow();
        if (!sortie.getCreateur().getEmail().equals(principal.getName())) {
            return "redirect:/mes-sorties?unauthorized=true";
        }
        sortieService.deleteSortie(id);
        return "redirect:/mes-sorties";
    }
}
