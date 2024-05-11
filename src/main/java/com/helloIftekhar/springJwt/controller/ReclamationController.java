package com.helloIftekhar.springJwt.controller;

// ReclamationController.java
import com.helloIftekhar.springJwt.model.Reclamation;
import com.helloIftekhar.springJwt.service.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reclamations")
public class ReclamationController {
    @Autowired
    private ReclamationService reclamationService;

    @GetMapping("/get")
    public ResponseEntity<List<Reclamation>> getAllReclamations() {
        List<Reclamation> reclamations = reclamationService.getAllReclamations();
        return ResponseEntity.ok(reclamations);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable Long id) {
        Reclamation reclamation = reclamationService.getReclamationById(id);
        if (reclamation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reclamation);
    }

    @PostMapping("/add")
    public ResponseEntity<Reclamation> createReclamation(@RequestBody Reclamation reclamation) {
        Reclamation createdReclamation = reclamationService.createReclamation(reclamation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReclamation);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<Reclamation> updateReclamation(@PathVariable Long id, @RequestBody Reclamation reclamation) {
        Reclamation updatedReclamation = reclamationService.updateReclamation(id, reclamation);
        if (updatedReclamation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedReclamation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long id) {
        boolean deleted = reclamationService.deleteReclamation(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
