package com.helloIftekhar.springJwt.service;

// ReclamationService.java
import com.helloIftekhar.springJwt.model.Reclamation;
import com.helloIftekhar.springJwt.repository.ReclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReclamationService {
    @Autowired
    private ReclamationRepository reclamationRepository;

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    public Reclamation getReclamationById(Long id) {
        return reclamationRepository.findById(id).orElse(null);
    }

    public Reclamation createReclamation(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    public Reclamation updateReclamation(Long id, Reclamation updatedReclamation) {
        if (!reclamationRepository.existsById(id)) {
            return null;
        }
        updatedReclamation.setId(id);
        return reclamationRepository.save(updatedReclamation);
    }

    public boolean deleteReclamation(Long id) {
        if (!reclamationRepository.existsById(id)) {
            return false;
        }
        reclamationRepository.deleteById(id);
        return true;
    }
}
