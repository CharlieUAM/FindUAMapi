package ni.edu.uam.FindUAMapi.dto;

public class LoginRequest {

    private String correoUam;
    private String password;

    public String getCorreoUam() {
        return correoUam;
    }

    public void setCorreoUam(String correoUam) {
        this.correoUam = correoUam;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}