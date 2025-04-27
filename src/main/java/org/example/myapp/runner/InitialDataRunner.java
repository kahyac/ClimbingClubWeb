package org.example.myapp.runner;

import org.example.myapp.dao.ClubDAO;
import org.example.myapp.model.Categorie;
import org.example.myapp.model.Membre;
import org.example.myapp.model.Sortie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Profile("dev")
public class InitialDataRunner implements CommandLineRunner {

    private final ClubDAO clubDAO;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    @Autowired
    public InitialDataRunner(ClubDAO clubDAO, PasswordEncoder passwordEncoder) {
        this.clubDAO = clubDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        System.out.println("✅ Initialisation des données de test…");

        // 1) créer N catégories
        int N_CAT = 20;
        List<Categorie> categories = new ArrayList<>(N_CAT);
        for (int i = 1; i <= N_CAT; i++) {
            Categorie c = clubDAO.creerCategorie(new Categorie("Catégorie " + i));
            categories.add(c);
        }

        // 2) créer M membres
        int N_MEM = 20;
        List<Membre> membres = new ArrayList<>(N_MEM);
        for (int i = 1; i <= N_MEM; i++) {
            String email = String.format("user%03d@example.com", i);
            String pwd   = "pwd" + i;
            Membre m = new Membre("Nom" + i, "Prenom" + i, email, passwordEncoder.encode(pwd));
            membres.add(clubDAO.creerOuMettreAJourMembre(m));
        }

        // 3) créer P sorties aléatoires
        int N_SORT = 40;
        for (int i = 1; i <= N_SORT; i++) {
            Membre auteur = membres.get(random.nextInt(membres.size()));
            Categorie cat = categories.get(random.nextInt(categories.size()));
            LocalDate date = LocalDate.now().plusDays(random.nextInt(365) - 180);
            Sortie s = new Sortie(
                    "Sortie n°" + i,
                    "Description de la sortie n°" + i,
                    "http://example.com/sortie/" + i,
                    date,
                    auteur,
                    cat
            );
            clubDAO.creerOuMettreAJourSortie(s);
        }

        System.out.println("✅ Généré " + N_CAT + " catégories, "
                + N_MEM + " membres et "
                + N_SORT + " sorties pour tester la scalabilité.");
    }
}