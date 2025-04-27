package org.example.myapp.web.view;

import org.example.myapp.model.Categorie;
import org.example.myapp.model.Membre;
import org.example.myapp.model.Sortie;
import org.example.myapp.service.CategorieService;
import org.example.myapp.service.MembreService;
import org.example.myapp.service.SortieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Controller
public class MainController {

    @Autowired
    private CategorieService categorieService;

    @Autowired
    private MembreService membreService;

    @Autowired
    private SortieService sortieService;

    @GetMapping("/accueil")
    public String accueil() {
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(
            Model model,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "categorieId", required = false) Long categorieId,
            @RequestParam(name = "afterDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate afterDate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) {
        List<Categorie> categories = categorieService.getAllCategories();
        model.addAttribute("categories", categories);

        Map<Long, List<Sortie>> sortiesParCategorie = new java.util.LinkedHashMap<>();
        for (Categorie cat : categories) {
            sortiesParCategorie.put(cat.getId(), sortieService.getSortiesByCategorie(cat));
        }
        model.addAttribute("sortiesParCategorie", sortiesParCategorie);

        List<Sortie> all = sortieService.getSortiesPage(PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        List<Sortie> filtered = all.stream()
                .filter(s -> search == null || s.getNom().toLowerCase().contains(search.toLowerCase()))
                .filter(s -> categorieId == null || s.getCategorie().getId().equals(categorieId))
                .filter(s -> afterDate == null || !s.getDateSortie().isBefore(afterDate))
                .toList();

        int from = page * size;
        int to = Math.min(from + size, filtered.size());
        Page<Sortie> sortiesPage = new PageImpl<>(filtered.subList(from, to), PageRequest.of(page, size), filtered.size());

        model.addAttribute("sortiesPage", sortiesPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sortiesPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("search", search);
        model.addAttribute("categorieId", categorieId);
        model.addAttribute("afterDate", afterDate);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserDetails ud) {
            Page<Membre> membresPage = membreService.getMembresPage(PageRequest.of(0, 5));
            model.addAttribute("membresPage", membresPage);
            model.addAttribute("currentMemPage", 0);
            model.addAttribute("totalMemPages", membresPage.getTotalPages());
            model.addAttribute("memSize", 5);

            String cleaned = ud.getUsername()
                    .replaceAll("@.*", "")
                    .replaceAll("[^a-zA-Z]", " ")
                    .replaceAll("\\s+", " ")
                    .trim();
            String displayName = Pattern.compile("\\b[a-z]")
                    .matcher(cleaned)
                    .replaceAll(m -> m.group().toUpperCase());
            model.addAttribute("displayName", displayName);
        }

        return "home/home";
    }

    @GetMapping("/loginPage")
    public String login() {
        return "auth/loginPage";
    }

    @GetMapping("/sorties/{id}")
    public String getSortieDetailPublic(@PathVariable Long id, Model model) {
        Optional<Sortie> sortie = sortieService.getSortieById(id);
        if (sortie.isEmpty()) {
            return "redirect:/?notfound=true";
        }
        model.addAttribute("sortie", sortie.get());
        return "sortie/sortie-detail";
    }

}