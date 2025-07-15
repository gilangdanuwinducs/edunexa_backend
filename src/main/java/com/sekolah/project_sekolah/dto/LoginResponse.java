package com.sekolah.project_sekolah.dto;

public class LoginResponse {
    private String status;
    private String role;
    private String name;
    private String token;
    private String email;

    public LoginResponse(String status, String role, String name, String token, String email) {
        this.status = status;
        this.role = role;
        this.name = name;
        this.token = token;
        this.email = email;
    }

    // Getter & Setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.token = email;
    }
}
