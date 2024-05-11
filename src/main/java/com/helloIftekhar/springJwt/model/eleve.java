package com.helloIftekhar.springJwt.model;

import jakarta.persistence.*;

@Entity
public class eleve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String niveau;

    private int progress;

    // Constructors
    public eleve() {
        // Default constructor
    }

    public eleve(String firstname, String lastname, String niveau) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.niveau = niveau;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getprogress() {
        return progress;
    }

    public void setprogress(int progress) {
        this.progress = progress;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
