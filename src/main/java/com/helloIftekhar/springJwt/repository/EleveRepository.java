package com.helloIftekhar.springJwt.repository;

import com.helloIftekhar.springJwt.model.eleve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EleveRepository extends JpaRepository<eleve, Long> {
    static boolean existById(long id) {
        return false;
    }
    List<eleve> findByUserId(Long userId);
}
