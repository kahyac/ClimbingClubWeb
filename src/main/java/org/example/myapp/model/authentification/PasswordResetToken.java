package org.example.myapp.model.authentification;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String email;
    private LocalDateTime expirationDate;

    public PasswordResetToken() {}

    public PasswordResetToken(String token, String email, LocalDateTime expirationDate) {
        this.token = token;
        this.email = email;
        this.expirationDate = expirationDate;
    }

    public PasswordResetToken(Long id, String token, String email, LocalDateTime expirationDate) {
        this.id = id;
        this.token = token;
        this.email = email;
        this.expirationDate = expirationDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDateTime expirationDate) { this.expirationDate = expirationDate; }
}
