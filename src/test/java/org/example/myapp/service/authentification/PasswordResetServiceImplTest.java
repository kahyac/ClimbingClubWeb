package org.example.myapp.service.authentification;

import org.example.myapp.model.Membre;
import org.example.myapp.model.authentification.PasswordResetToken;
import org.example.myapp.repository.MembreRepository;
import org.example.myapp.repository.authentification.PasswordResetTokenRepository;
import org.example.myapp.service.implementation.authentification.PasswordResetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PasswordResetServiceImplTest {

    private MembreRepository membreRepository;
    private PasswordResetTokenRepository tokenRepository;
    private JavaMailSender mailSender;
    private PasswordResetServiceImpl service;

    @BeforeEach
    void setUp() {
        membreRepository = mock(MembreRepository.class);
        tokenRepository = mock(PasswordResetTokenRepository.class);
        mailSender = mock(JavaMailSender.class);
        service = new PasswordResetServiceImpl();
        service.membreRepository = membreRepository;
        service.tokenRepository = tokenRepository;
        service.mailSender = mailSender;
    }

    @Test
    void shouldCreateTokenAndSendEmail() {
        when(membreRepository.findByEmail(anyString())).thenReturn(Optional.of(new Membre()));

        service.createTokenForEmail("test@example.com");

        verify(tokenRepository, times(1)).save(any(PasswordResetToken.class));
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void shouldValidateTokenCorrectly() {
        PasswordResetToken token = new PasswordResetToken("token123", "test@example.com", java.time.LocalDateTime.now().plusMinutes(10));
        when(tokenRepository.findByToken("token123")).thenReturn(Optional.of(token));
        when(membreRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new Membre()));

        assertNotNull(service.validateTokenAndGetUser("token123"));
    }
}