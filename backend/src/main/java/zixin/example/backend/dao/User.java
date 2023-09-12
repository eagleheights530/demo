package zixin.example.backend.dao;

import java.sql.Timestamp;

public class User {
    private String userId;

    private String fullName;

    private String email;

    private Timestamp lastModified;

    
    public User(String userId, String fullName, String email, Timestamp lastModified) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.lastModified = lastModified;
    }

    public String getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }
}
