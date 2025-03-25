package be.integration.api.dtos;

public class LoginRequest {

    private String identifier;
    private String password;

    // Getters
    public String getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setPassword(String password) {
        this.password = password;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


}
