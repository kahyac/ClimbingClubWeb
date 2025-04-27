package org.example.myapp.service.authentification;

import org.example.myapp.model.Membre;
import org.example.myapp.model.authentification.PasswordResetToken;

public interface PasswordResetService {
    void createTokenForEmail(String email);
    Membre validateTokenAndGetUser(String token);
    void updatePassword(String email, String newPassword);
}
