package openGrokSearch;

public class Configuration {
    private String link;
    private String login;
    private String password;

    public static final String LINK_FIELD = "openGrokLink";
    public static final String LOGIN_FIELD = "openGrokLogin";
    public static final String PASSWORD_FIELD = "openGrokPassword";

    private static volatile Configuration instance;

    public static Configuration getInstance() {
        if (instance == null) {
            synchronized (Configuration .class){
                if (instance == null) {
                    instance = new Configuration();
                }
            }
        }

        return instance;
    }

    public static void setInstance(Configuration configuration) {
        instance = configuration;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
