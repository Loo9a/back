package com.helloIftekhar.springJwt.model;

public class AuthenticationResponse {
    private final long id;
    private String token;
    private String message;
    private Role role;

    public AuthenticationResponse(String token, String message ,Role role ,long id ) {
        this.token = token;
        this.message = message;
        this.role = role;
        this.id = id;
    }
public Role getRole(){return role;}
    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

public long getId(){return id;}
}
