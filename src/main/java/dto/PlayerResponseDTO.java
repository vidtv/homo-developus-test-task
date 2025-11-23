package dto;

/**
 * DTO class for deserializing the response from the Homo Developus test API.
 */
public class PlayerResponseDTO {
    public String id;
    public String currency_code;
    public String email;
    public String name;
    public String surname;
    public String username;

    public String getName() {
        return name;
    }
}
