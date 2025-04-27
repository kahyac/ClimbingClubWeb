package org.example.myapp.web.view;

import org.example.myapp.model.Membre;
import org.example.myapp.service.authentification.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @Autowired
    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    /**
     * Affiche le formulaire de demande de réinitialisation de mot de passe.
     */
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "auth/forgot-password";
    }

    /**
     * Gère la soumission du formulaire de demande.
     */
    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email, Model model) {
        passwordResetService.createTokenForEmail(email);
        model.addAttribute("message", "Un lien de réinitialisation a été envoyé à votre adresse.");
        return "auth/forgot-password";
    }

    /**
     * Affiche le formulaire pour définir un nouveau mot de passe.
     */
    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam("token") String token, Model model) {
        Membre membre = passwordResetService.validateTokenAndGetUser(token);
        if (membre == null) {
            return "redirect:/forgot-password?invalidToken=true";
        }

        model.addAttribute("token", token);
        return "auth/reset-password";
    }

    /**
     * Gère la soumission du nouveau mot de passe.
     */
    @PostMapping("/reset-password")
    public String handleReset(
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword
    ) {
        Membre membre = passwordResetService.validateTokenAndGetUser(token);
        if (membre != null) {
            passwordResetService.updatePassword(membre.getEmail(), newPassword);
            return "redirect:/login?resetSuccess=true";
        }

        return "redirect:/forgot-password?invalidToken=true";
    }
}
