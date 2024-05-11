package com.helloIftekhar.springJwt.repository;

import com.helloIftekhar.springJwt.model.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
}