package org.example.myapp.service;

import org.example.myapp.dao.ClubDAO;
import org.example.myapp.model.Membre;
import org.example.myapp.service.implementation.MembreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MembreServiceImplTest {

    private ClubDAO clubDAO;
    private MembreServiceImpl service;

    @BeforeEach
    void setUp() {
        clubDAO = mock(ClubDAO.class);
        service = new MembreServiceImpl(clubDAO);
    }

    @Test
    void shouldReturnMembreById() {
        Membre membre = new Membre("Nom", "Prenom", "email@example.com", "pass");
        when(clubDAO.obtenirMembreParId(1L)).thenReturn(Optional.of(membre));

        Optional<Membre> result = service.getMembreById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnAllMembres() {
        List<Membre> membres = List.of(new Membre(), new Membre());
        when(clubDAO.obtenirTousLesMembres()).thenReturn(membres);

        assertEquals(2, service.getAllMembres().size());
    }
}