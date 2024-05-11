package com.helloIftekhar.springJwt.service;

import com.helloIftekhar.springJwt.model.eleve;
import com.helloIftekhar.springJwt.model.User;
import com.helloIftekhar.springJwt.repository.EleveRepository;
import com.helloIftekhar.springJwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EleveService {

    private final EleveRepository eleveRepository;
    private final UserRepository userRepository;

    @Autowired
    public EleveService(EleveRepository eleveRepository, UserRepository userRepository) {
        this.eleveRepository = eleveRepository;
        this.userRepository = userRepository;
    }

    public List<eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    public eleve getEleveById(Long id) {
        return eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Eleve not found with id " + id));
    }

    public eleve createEleve(eleve eleve, Long userId) {
        User user = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        eleve.setUser(user);

        return eleveRepository.save(eleve);
    }

    public List<eleve> getElevesByUserId(Long userId) {
        return eleveRepository.findByUserId(userId);
    }

    public boolean existById(long id) {
        return eleveRepository.existsById(id);
    }

    public eleve updateEleve(Long id, eleve eleveDetails) {
        eleve eleve = eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Eleve not found with id " + id));

        // Update eleve details
        eleve.setFirstname(eleveDetails.getFirstname());
        eleve.setLastname(eleveDetails.getLastname());
        eleve.setNiveau(eleveDetails.getNiveau());

        return eleveRepository.save(eleve);
    }

    public void deleteEleve(Long id) {
        eleveRepository.deleteById(id);
    }

    public List<String> getNiveaux() {
        // Retrieve all eleves
        List<eleve> eleves = eleveRepository.findAll();

        // Extract unique niveau labels from eleves
        List<String> niveaux = eleves.stream()
                .map(eleve::getNiveau)
                .distinct()
                .collect(Collectors.toList());

        return niveaux;
    }
    public eleve updateProgress(Long id, int progress) {
        eleve eleve = eleveRepository.findById(id).orElse(null);
        if (eleve != null) {
            eleve.setprogress(progress);
            return eleveRepository.save(eleve);
        }
        return null;
    }
    public int getProgressById(Long id) {
        eleve eleve = eleveRepository.findById(id).orElse(null);
        return eleve != null ? eleve.getprogress() : -1;
    }
}
