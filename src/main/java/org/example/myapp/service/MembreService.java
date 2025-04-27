package org.example.myapp.service;

import org.example.myapp.model.Membre;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MembreService {
    Optional<Membre> getMembreById(Long id);
    Optional<Membre> getMembreByEmail(String email);
    List<Membre> getAllMembres();
    Membre saveMembre(Membre membre);

    Page<Membre> getMembresPage(Pageable pageable);
}
