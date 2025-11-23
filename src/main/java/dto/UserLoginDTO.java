package dto;

public class UserLoginDTO {
    public String email;
    public String password;

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
