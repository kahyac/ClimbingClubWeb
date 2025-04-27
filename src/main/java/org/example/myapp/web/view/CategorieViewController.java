package org.example.myapp.web.view;

import jakarta.validation.Valid;
import org.example.myapp.model.Categorie;
import org.example.myapp.model.Sortie;
import org.example.myapp.service.CategorieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategorieViewController {

    private final CategorieService categorieService;

    public CategorieViewController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    /** 1) Liste paginée des catégories */
    @GetMapping
    public String listCategories(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        Page<Categorie> categoriesPage =
                categorieService.getCategoriesPage(PageRequest.of(page, size));

        model.addAttribute("categoriesPage", categoriesPage);
        model.addAttribute("currentCatPage", page);
        model.addAttribute("totalCatPages", categoriesPage.getTotalPages());
        model.addAttribute("catSize", size);
        return "categorie/mes-categories";
    }

    /** 2) Formulaire de création */
    @GetMapping("/nouveau")
    public String showCreateForm(Model model) {
        model.addAttribute("categorie", new Categorie());
        return "categorie/creer-categorie";
    }

    /** 3) Création – avec validation */
    @PostMapping("/nouveau")
    public String createCategorie(
            @Valid @ModelAttribute("categorie") Categorie categorie,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (result.hasErrors()) {
            return "categorie/creer-categorie";
        }
        categorieService.createCategorie(categorie);
        redirectAttrs.addFlashAttribute("success", "Catégorie créée avec succès !");
        return "redirect:/categories";
    }

    /** 4) Formulaire d’édition */
    @GetMapping("/modifier/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttrs
    ) {
        Optional<Categorie> opt = categorieService.getCategorieById(id);
        if (opt.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "Catégorie introuvable !");
            return "redirect:/categories";
        }
        model.addAttribute("categorie", opt.get());
        return "categorie/creer-categorie";
    }

    /** 5) Mise à jour – avec validation */
    @PostMapping("/modifier/{id}")
    public String updateCategorie(
            @PathVariable Long id,
            @Valid @ModelAttribute("categorie") Categorie formCat,
            BindingResult result,
            RedirectAttributes redirectAttrs
    ) {
        if (result.hasErrors()) {
            return "categorie/creer-categorie";
        }
        Optional<Categorie> opt = categorieService.getCategorieById(id);
        if (opt.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "Catégorie introuvable !");
            return "redirect:/categories";
        }
        Categorie exist = opt.get();
        exist.setNom(formCat.getNom());
        categorieService.createCategorie(exist);
        redirectAttrs.addFlashAttribute("success", "Catégorie mise à jour !");
        return "redirect:/categories";
    }

    /** 6) Détail d’une catégorie + ses sorties */
    @GetMapping("/{id}")
    public String showCategorieDetail(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttrs
    ) {
        Optional<Categorie> optCat = categorieService.getCategorieById(id);
        if (optCat.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "Catégorie introuvable");
            return "redirect:/categories";
        }
        Categorie categorie = optCat.get();
        List<Sortie> sorties = categorieService.getSortiesByCategorie(id);

        model.addAttribute("categorie", categorie);
        model.addAttribute("sorties",    sorties);
        return "categorie/categorie-detail";
    }
}
