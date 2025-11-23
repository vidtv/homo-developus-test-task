package dto;

/**
 * DTO class for user authorisation and getting a Bearer token.
 */
public class UserLoginDTO {
    public String email;
    public String password;

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
