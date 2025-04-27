package org.example.myapp.repository;

import org.example.myapp.model.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MembreRepository extends JpaRepository<Membre, Long> {
    /**
     * Find a member by email.
     *
     * @param email the email to search for
     * @return an Optional containing the member if found
     */
    Optional<Membre> findByEmail(String email);
}
