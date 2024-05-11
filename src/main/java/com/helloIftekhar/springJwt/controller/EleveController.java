package com.helloIftekhar.springJwt.controller;

import com.helloIftekhar.springJwt.model.eleve;
import com.helloIftekhar.springJwt.service.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/eleve")
public class EleveController {

    @Autowired
    private EleveService eleveService;

    @GetMapping("/get")
    public List<eleve> getAllEleves() {
        return eleveService.getAllEleves();

    }

    @GetMapping("/get/{id}")
    public eleve getEleveById(@PathVariable(value = "id") Long eleveId) {
        return eleveService.getEleveById(eleveId);
    }

    @PostMapping("/add")
    public eleve createEleve(@RequestBody eleve eleve, @RequestParam(value = "userid") Long userId) {
        return eleveService.createEleve(eleve, userId);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<eleve> updateEleve(@PathVariable(value = "id") Long eleveId, @RequestBody eleve eleveDetails) {
        eleve updatedEleve = eleveService.updateEleve(eleveId, eleveDetails);
        return ResponseEntity.ok(updatedEleve);
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<eleve>> getElevesByUserId(@PathVariable(value = "userId") Long userId) {
        List<eleve> eleves = eleveService.getElevesByUserId(userId);
        if (eleves.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(eleves, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEleve(@PathVariable(value = "id") Long eleveId) {
        if (eleveService.existById(eleveId)) {
            eleveService.deleteEleve(eleveId);
            HashMap<String, String> message = new HashMap<>();
            message.put("message", "Task with id" + " " + eleveId + " " + "Delete successfully.");

            //Return type HasMap
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            HashMap<String, String> message = new HashMap<>();
            message.put("message", eleveId + " task not found or matched");

            //Return type HasMap
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
    @GetMapping("/getNiveaux")
    public ResponseEntity<List<String>> getNiveaux() {
        List<String> niveaux = eleveService.getNiveaux(); // Assuming getNiveaux() method returns a list of niveau labels
        if (niveaux.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(niveaux, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}/progress")
    public eleve updateProgress(@PathVariable Long id, @RequestParam int progress) {
        return eleveService.updateProgress(id, progress);
    }

    @GetMapping("/{id}/progress")
    public int getProgressById(@PathVariable Long id) {
        return eleveService.getProgressById(id);
    }



}
