package de.hsrm.mi.web.projekt.entities.benutzer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BenutzerRepository extends JpaRepository<Benutzer, Long> {
    Optional<Benutzer> findByEmail(String email);
}
