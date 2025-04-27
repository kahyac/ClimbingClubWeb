package org.example.myapp.config.security;

import org.example.myapp.model.Membre;
import org.example.myapp.repository.MembreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    MembreRepository membreRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("🔍 Tentative de login avec : " + email);

        Membre membre = membreRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Aucun membre trouvé avec l'email " + email));

        System.out.println("✅ Authentification réussie : " + membre.getEmail());

        return User.builder()
                .username(membre.getEmail())
                .password(membre.getMotDePasse())
                .roles("USER")
                .build();
    }
}
