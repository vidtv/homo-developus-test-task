package utils;

public class Credentials {
    private final static String DEFAULT_EMAIL = "nikit1534@gmail.com";
    private final static String DEFAULT_PASSWORD = "Wq5MPEmi83A5";

    public static String getEmail() {
        return System.getProperty("email", DEFAULT_EMAIL);
    }

    public static String getPassword() {
        return System.getProperty("password", DEFAULT_PASSWORD);
    }
}
