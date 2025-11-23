package dto;

/**
 * DTO class for creating a new player via the Homo Developus test API.
 */
public class PlayerRequestDTO {
    public String currency_code;
    public String email;
    public String name;
    public String password_change;
    public String password_repeat;
    public String surname;
    public String username;

    public PlayerRequestDTO(String currency_code, String email, String name, String password_change,
                            String password_repeat, String surname, String username) {
        this.currency_code = currency_code;
        this.email = email;
        this.name = name;
        this.password_change = password_change;
        this.password_repeat = password_repeat;
        this.surname = surname;
        this.username = username;
    }
}
