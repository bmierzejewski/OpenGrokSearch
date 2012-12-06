package openGrokSearch;

public class Configuration {
    private String link;
    private String params;

    public static final String LINK_FIELD = "openGrokLink";
    public static final String PARAMS_FIELD = "openGrokParams";

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

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
