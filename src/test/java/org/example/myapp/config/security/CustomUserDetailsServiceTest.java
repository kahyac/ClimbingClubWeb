package org.example.myapp.config.security;

import org.example.myapp.model.Membre;
import org.example.myapp.repository.MembreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    private final MembreRepository membreRepository = mock(MembreRepository.class);
    private final CustomUserDetailsService service = new CustomUserDetailsService();

    public CustomUserDetailsServiceTest() {
        service.membreRepository = membreRepository;
    }

    @Test
    void shouldLoadUserByUsername_WhenEmailExists() {
        Membre membre = new Membre(1L, "Dupont", "Jean", "jean@example.com", "hashedpwd");
        when(membreRepository.findByEmail("jean@example.com")).thenReturn(Optional.of(membre));

        UserDetails userDetails = service.loadUserByUsername("jean@example.com");

        assertEquals("jean@example.com", userDetails.getUsername());
        assertEquals("hashedpwd", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void shouldThrowException_WhenEmailNotFound() {
        when(membreRepository.findByEmail("inexistant@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () ->
                service.loadUserByUsername("inexistant@example.com"));
    }
}
