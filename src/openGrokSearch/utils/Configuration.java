package openGrokSearch.utils;

import com.intellij.ide.util.PropertiesComponent;
import org.apache.commons.lang.StringUtils;

public class Configuration {
    private PropertiesComponent propertiesComponent;

    public static final String LINK_FIELD = "openGrokLink";
    public static final String LOGIN_FIELD = "openGrokLogin";
    public static final String PASSWORD_FIELD = "openGrokPassword";
    public static final String PROJECTS_FIELD = "openGrokProjects";
    public static final String DEFAULT_PROJECT_FIELD = "openGrokDefaultProject";
    public static final String SELECTED_FIELD = "openGrokSelectedField";

    private static Configuration instance;

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }

        return instance;
    }

    private Configuration() {
        propertiesComponent = PropertiesComponent.getInstance();
    }

    public String getLink() {
        return propertiesComponent.getValue(Configuration.LINK_FIELD);
    }

    public void setLink(String link) {
        propertiesComponent.setValue(Configuration.LINK_FIELD, link);
    }

    public String getLogin() {
        return propertiesComponent.getValue(Configuration.LOGIN_FIELD);
    }

    public void setLogin(String login) {
        propertiesComponent.setValue(Configuration.LOGIN_FIELD, login);
    }

    public String getPassword() {
        return propertiesComponent.getValue(Configuration.PASSWORD_FIELD);
    }

    public void setPassword(String password) {
        propertiesComponent.setValue(Configuration.PASSWORD_FIELD, password);
    }

    public String[] getProjects() {
        String projects = propertiesComponent.getValue(Configuration.PROJECTS_FIELD);
        if (projects !=null) {
            return projects.split(",");
        } else {
            return new String[0];
        }
    }

    public void setProjects(String[] projects) {
        propertiesComponent.setValue(Configuration.PROJECTS_FIELD, StringUtils.join(projects, ","));
    }

    public String getDefaultProject() {
        return propertiesComponent.getValue(Configuration.DEFAULT_PROJECT_FIELD);
    }

    public void setDefaultProject(String project) {
        propertiesComponent.setValue(Configuration.DEFAULT_PROJECT_FIELD, project);
    }

    public boolean isSelected() {
        return Boolean.valueOf(propertiesComponent.getValue(Configuration.SELECTED_FIELD));
    }

    public void setSelected(boolean selected) {
        propertiesComponent.setValue(Configuration.SELECTED_FIELD, String.valueOf(selected));
    }
}