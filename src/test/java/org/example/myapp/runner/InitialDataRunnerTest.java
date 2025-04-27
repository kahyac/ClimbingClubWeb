package org.example.myapp.runner;

import org.example.myapp.dao.ClubDAO;
import org.example.myapp.model.Categorie;
import org.example.myapp.model.Membre;
import org.example.myapp.model.Sortie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

class InitialDataRunnerTest {

    private ClubDAO clubDAO;
    private PasswordEncoder passwordEncoder;
    private InitialDataRunner runner;

    @BeforeEach
    void setUp() {
        clubDAO = mock(ClubDAO.class);
        passwordEncoder = mock(PasswordEncoder.class);
        runner = new InitialDataRunner(clubDAO, passwordEncoder);
    }

    @Test
    void shouldInitializeDataWithoutErrors() {
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        runner.run();
        verify(clubDAO, atLeast(20)).creerCategorie(any(Categorie.class));
        verify(clubDAO, atLeast(20)).creerOuMettreAJourMembre(any(Membre.class));
        verify(clubDAO, atLeast(40)).creerOuMettreAJourSortie(any(Sortie.class));
    }
}