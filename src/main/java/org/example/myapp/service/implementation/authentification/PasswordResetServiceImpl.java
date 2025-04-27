package org.example.myapp.service.implementation.authentification;

import jakarta.transaction.Transactional;
import org.example.myapp.model.Membre;
import org.example.myapp.model.authentification.PasswordResetToken;
import org.example.myapp.repository.MembreRepository;
import org.example.myapp.repository.authentification.PasswordResetTokenRepository;
import org.example.myapp.service.authentification.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    public MembreRepository membreRepository;
    @Autowired
    public PasswordResetTokenRepository tokenRepository;
    @Autowired
    public JavaMailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createTokenForEmail(String email) {
        Optional<Membre> membreOpt = membreRepository.findByEmail(email);
        if (membreOpt.isEmpty()) return;

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, email, LocalDateTime.now().plusHours(1));
        tokenRepository.save(resetToken);

        // ✉️ Prépare et envoie l'e-mail
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Réinitialisation de mot de passe");
        message.setText("Voici votre lien de réinitialisation : http://localhost:8080/reset-password?token=" + token);
        mailSender.send(message);
    }

    @Override
    public Membre validateTokenAndGetUser(String token) {
        Optional<PasswordResetToken> opt = tokenRepository.findByToken(token);
        if (opt.isEmpty()) return null;

        PasswordResetToken resetToken = opt.get();
        if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) return null;

        return membreRepository.findByEmail(resetToken.getEmail()).orElse(null);
    }

    @Override
    @Transactional
    public void updatePassword(String email, String newPassword) {
        Optional<Membre> membreOpt = membreRepository.findByEmail(email);
        if (membreOpt.isPresent()) {
            Membre membre = membreOpt.get();
            membre.setMotDePasse(passwordEncoder.encode(newPassword));
            membreRepository.save(membre);
            tokenRepository.deleteByEmail(email); // clean
        }
    }
}

