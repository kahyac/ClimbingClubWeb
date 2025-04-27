package org.example.myapp.service;

import org.example.myapp.dao.ClubDAO;
import org.example.myapp.model.Categorie;
import org.example.myapp.model.Membre;
import org.example.myapp.model.Sortie;
import org.example.myapp.service.implementation.SortieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SortieServiceImplTest {

    private ClubDAO clubDAO;
    private SortieServiceImpl service;

    @BeforeEach
    void setUp() {
        clubDAO = mock(ClubDAO.class);
        service = new SortieServiceImpl(clubDAO);
    }

    @Test
    void shouldReturnSortieById() {
        Sortie sortie = new Sortie();
        when(clubDAO.obtenirSortieParId(1L)).thenReturn(Optional.of(sortie));

        assertTrue(service.getSortieById(1L).isPresent());
    }

    @Test
    void shouldSearchSortiesByName() {
        List<Sortie> list = List.of(new Sortie());
        when(clubDAO.rechercherSortiesParNom("Randonnée")).thenReturn(list);

        assertEquals(1, service.searchSortiesByName("Randonnée").size());
    }
}