package org.example.myapp.web.view;

import org.example.myapp.model.Membre;
import org.example.myapp.model.Sortie;
import org.example.myapp.service.MembreService;
import org.example.myapp.service.SortieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/membres")
public class MembreViewController {

    private final MembreService membreService;
    private final PasswordEncoder passwordEncoder;
    private final SortieService sortieService;

    @Autowired
    public MembreViewController(MembreService membreService,
                                PasswordEncoder passwordEncoder,
                                SortieService sortieService) {
        this.membreService = membreService;
        this.passwordEncoder = passwordEncoder;
        this.sortieService = sortieService;
    }

    /* Liste paginée avec recherche */
    @GetMapping
    public String listMembres(
            Model model,
            Principal principal,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        if (principal == null) return "redirect:/loginPage";

        Page<Membre> membresPage;

        if (search != null && !search.isBlank()) {
            List<Membre> allMembres = membreService.getAllMembres();
            List<Membre> filtered = allMembres.stream()
                    .filter(m -> m.getPrenom().toLowerCase().contains(search.toLowerCase()) ||
                            m.getNom().toLowerCase().contains(search.toLowerCase()))
                    .toList();

            int from = page * size;
            int to = Math.min(from + size, filtered.size());
            membresPage = new PageImpl<>(filtered.subList(from, to), PageRequest.of(page, size), filtered.size());
        } else {
            membresPage = membreService.getMembresPage(PageRequest.of(page, size));
        }

        model.addAttribute("membresPage", membresPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", membresPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("search", search);

        return "membre/mes-membres"; // Assure-toi que ce fichier JSP contient bien le champ de recherche
    }


    @GetMapping("/nouveau")
    public String showCreateForm(Model model, Principal principal) {
        if (principal != null) return "redirect:/";
        model.addAttribute("membre", new Membre());
        return "membre/creer-membre";
    }

    @PostMapping("/nouveau")
    public String createMembre(@ModelAttribute("membre") Membre membre,
                               RedirectAttributes redirectAttrs) {
        try {
            membre.setMotDePasse(passwordEncoder.encode(membre.getMotDePasse()));
            membreService.saveMembre(membre);
            return "redirect:/loginPage?registerSuccess";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Une erreur est survenue lors de la création du compte.");
            return "redirect:/membres/nouveau?error";
        }
    }

    @GetMapping("/modifier/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model,
                               RedirectAttributes redirectAttrs,
                               Principal principal) {
        if (principal == null) return "redirect:/loginPage";
        Optional<Membre> opt = membreService.getMembreById(id);
        if (opt.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "Membre introuvable !");
            return "redirect:/";
        }
        Membre cible = opt.get();
        if (!principal.getName().equals(cible.getEmail())) {
            redirectAttrs.addFlashAttribute("error", "Vous ne pouvez modifier que votre propre profil.");
            return "redirect:/";
        }
        model.addAttribute("membre", cible);
        return "membre/creer-membre";
    }

    @PostMapping("/modifier/{id}")
    public String updateMembre(@PathVariable Long id,
                               @ModelAttribute("membre") Membre membre,
                               RedirectAttributes redirectAttrs,
                               Principal principal) {
        if (principal == null) return "redirect:/loginPage";
        Optional<Membre> opt = membreService.getMembreById(id);
        if (opt.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "Membre introuvable !");
            return "redirect:/";
        }
        Membre cible = opt.get();
        if (!principal.getName().equals(cible.getEmail())) {
            redirectAttrs.addFlashAttribute("error", "Vous ne pouvez modifier que votre propre profil.");
            return "redirect:/";
        }

        cible.setPrenom(membre.getPrenom());
        cible.setNom(membre.getNom());
        cible.setEmail(membre.getEmail());
        if (membre.getMotDePasse() != null && !membre.getMotDePasse().isBlank()) {
            cible.setMotDePasse(passwordEncoder.encode(membre.getMotDePasse()));
        }
        membreService.saveMembre(cible);
        redirectAttrs.addFlashAttribute("success", "Profil mis à jour avec succès !");
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showMemberDetail(@PathVariable Long id,
                                   Model model,
                                   RedirectAttributes redirectAttrs,
                                   Principal principal) {
        if (principal == null) return "redirect:/loginPage";
        Optional<Membre> opt = membreService.getMembreById(id);
        if (opt.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "Membre introuvable !");
            return "redirect:/membres";
        }
        Membre membre = opt.get();
        List<Sortie> sorties = sortieService.getSortiesByCreateur(membre);

        model.addAttribute("membre", membre);
        model.addAttribute("sorties", sorties);
        return "membre/membre-detail";
    }
}