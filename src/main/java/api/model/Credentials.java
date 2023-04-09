package api.model;

public class Credentials {
    private String email;
    private String password;

    private Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Credentials from(User user) {
        return new Credentials(user.getEmail() , user.getPassword());
    }

    public static Credentials getCredentialsWithWrongEmail(User user) {
        return new Credentials(user.getEmail() + getRandomNumber(), user.getPassword());
    }

    public static Credentials getCredentialsWithWrongPassword(User user) {
        return new Credentials(user.getEmail(), user.getPassword() + getRandomNumber());
    }

    public static Credentials getCredentialsWithWrongData(User user) {
        return new Credentials(user.getEmail() + getRandomNumber(), user.getPassword() + getRandomNumber());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private static double getRandomNumber(){
        double x = Math.random();
        return x;
    }
}