package org.example.myapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "sorties")
public class Sortie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;
    private String description;
    private String siteWeb;

    @NotNull(message = "La date est obligatoire")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateSortie;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membre_id", nullable = false)
    private Membre createur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    public Sortie() {}

    public Sortie(String nom, String description, String siteWeb, LocalDate dateSortie, Membre createur, Categorie categorie) {
        this.nom = nom;
        this.description = description;
        this.siteWeb = siteWeb;
        this.dateSortie = dateSortie;
        this.createur = createur;
        this.categorie = categorie;
    }

    public Sortie(Long id, String nom, String description, String siteWeb, LocalDate dateSortie, Membre createur, Categorie categorie) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.siteWeb = siteWeb;
        this.dateSortie = dateSortie;
        this.createur = createur;
        this.categorie = categorie;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSiteWeb() { return siteWeb; }
    public void setSiteWeb(String siteWeb) { this.siteWeb = siteWeb; }

    public LocalDate getDateSortie() { return dateSortie; }
    public void setDateSortie(LocalDate dateSortie) { this.dateSortie = dateSortie; }

    public Membre getCreateur() { return createur; }
    public void setCreateur(Membre createur) { this.createur = createur; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
}
