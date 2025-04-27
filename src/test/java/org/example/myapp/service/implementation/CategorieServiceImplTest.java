package org.example.myapp.service;

import org.example.myapp.dao.ClubDAO;
import org.example.myapp.model.Categorie;
import org.example.myapp.model.Sortie;
import org.example.myapp.service.implementation.CategorieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategorieServiceImplTest {

    private ClubDAO clubDAO;
    private CategorieServiceImpl service;

    @BeforeEach
    void setUp() {
        clubDAO = mock(ClubDAO.class);
        service = new CategorieServiceImpl(clubDAO);
    }

    @Test
    void shouldReturnAllCategories() {
        List<Categorie> expected = List.of(new Categorie("A"), new Categorie("B"));
        when(clubDAO.obtenirToutesLesCategories()).thenReturn(expected);

        List<Categorie> actual = service.getAllCategories();
        assertEquals(2, actual.size());
    }

    @Test
    void shouldReturnCategorieById() {
        Categorie cat = new Categorie("Test");
        when(clubDAO.obtenirCategorieParId(1L)).thenReturn(Optional.of(cat));

        Optional<Categorie> result = service.getCategorieById(1L);
        assertTrue(result.isPresent());
        assertEquals("Test", result.get().getNom());
    }

    @Test
    void shouldReturnPageOfCategories() {
        Page<Categorie> page = new PageImpl<>(List.of(new Categorie("C")));
        when(clubDAO.obtenirCategoriesPage(any())).thenReturn(page);

        Page<Categorie> result = service.getCategoriesPage(PageRequest.of(0, 5));
        assertEquals(1, result.getContent().size());
    }
}