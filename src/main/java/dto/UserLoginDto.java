package dto;

public class UserLoginDto {
    public String email;
    public String password;

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
