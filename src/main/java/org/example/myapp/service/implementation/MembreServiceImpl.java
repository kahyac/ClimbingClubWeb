package org.example.myapp.service.implementation;

import org.example.myapp.dao.ClubDAO;
import org.example.myapp.model.Membre;
import org.example.myapp.service.MembreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@Transactional
public class MembreServiceImpl implements MembreService {

    private final ClubDAO clubDAO;

    public MembreServiceImpl(ClubDAO clubDAO) {
        this.clubDAO = clubDAO;
    }

    @Override
    public Optional<Membre> getMembreById(Long id) {
        return clubDAO.obtenirMembreParId(id);
    }

    @Override
    public Optional<Membre> getMembreByEmail(String email) {
        return clubDAO.obtenirMembreParEmail(email);
    }

    @Override
    public List<Membre> getAllMembres() {
        return clubDAO.obtenirTousLesMembres();
    }

    @Override
    public Membre saveMembre(Membre membre) {
        return clubDAO.creerOuMettreAJourMembre(membre);
    }

    @Override
    public Page<Membre> getMembresPage(Pageable pageable) {
        return clubDAO.obtenirMembresPage(pageable);
    }
}
