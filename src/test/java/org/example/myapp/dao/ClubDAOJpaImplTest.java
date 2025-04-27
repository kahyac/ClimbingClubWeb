package org.example.myapp.dao;

import org.example.myapp.model.*;
import org.example.myapp.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ClubDAOJpaImplTest {

    @Mock private CategorieRepository categorieRepo;
    @Mock private SortieRepository sortieRepo;
    @Mock private MembreRepository membreRepo;

    @InjectMocks private ClubDAOJpaImpl dao;

    private Categorie cat;
    private Membre membre;
    private Sortie sortie;

    @BeforeEach
    void setup() {
        cat = new Categorie();
        membre = new Membre(1L, "Doe", "John", "john@club.fr", "pwd");
        sortie = new Sortie(1L, "Mont Blanc", "--", "url", LocalDate.now(), membre, cat);
    }

    /* --- Catégories --- */

    @Test
    void obtenirToutesLesCategories_returnsList() {
        given(categorieRepo.findAll()).willReturn(List.of(cat));
        List<Categorie> categories = dao.obtenirToutesLesCategories();
        assertThat(categories).isNotNull().containsExactly(cat);
    }

    @Test
    void obtenirCategorieParId_present() {
        given(categorieRepo.findById(1L)).willReturn(Optional.of(cat));
        Optional<Categorie> found = dao.obtenirCategorieParId(1L);
        assertThat(found).isPresent().contains(cat);
    }

    @Test
    void creerCategorie_persists() {
        given(categorieRepo.save(cat)).willReturn(cat);
        Categorie saved = dao.creerCategorie(cat);
        assertThat(saved).isSameAs(cat);
    }

    /* --- Sorties --- */

    @Test
    void rechercherSortiesParNom_ignoreCase() {
        given(sortieRepo.findByNomContainingIgnoreCase("mont")).willReturn(List.of(sortie));
        List<Sortie> sorties = dao.rechercherSortiesParNom("mont");
        assertThat(sorties).isNotNull().containsExactly(sortie);
    }

    @Test
    void obtenirSortiesPage_returnsPage() {
        Page<Sortie> page = new PageImpl<>(List.of(sortie));
        given(sortieRepo.findAll(PageRequest.of(0, 5))).willReturn(page);
        Page<Sortie> result = dao.obtenirSortiesPage(PageRequest.of(0, 5));
        assertThat(result).isNotNull().isSameAs(page);
    }

    /* --- Membres --- */

    @Test
    void obtenirMembreParEmail_present() {
        given(membreRepo.findByEmail("john@club.fr")).willReturn(Optional.of(membre));
        Optional<Membre> found = dao.obtenirMembreParEmail("john@club.fr");
        assertThat(found).isPresent().contains(membre);
    }

    @Test
    void obtenirTousLesMembres_returnsList() {
        given(membreRepo.findAll()).willReturn(List.of(membre));
        List<Membre> membres = dao.obtenirTousLesMembres();
        assertThat(membres).isNotNull().containsExactly(membre);
    }

    @Test
    void creerOuMettreAJourMembre_persists() {
        given(membreRepo.save(membre)).willReturn(membre);
        Membre saved = dao.creerOuMettreAJourMembre(membre);
        assertThat(saved).isSameAs(membre);
    }
}
