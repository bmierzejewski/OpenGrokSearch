package openGrokSearch.utils;

import com.intellij.ide.util.PropertiesComponent;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import com.google.gson.Gson;

public class Configuration {
    private PropertiesComponent propertiesComponent;

    public static final String LINK_FIELD = "openGrokLink";
    public static final String LOGIN_FIELD = "openGrokLogin";
    public static final String PASSWORD_FIELD = "openGrokPassword";
    public static final String PROJECTS_FIELD = "openGrokProjects";
    public static final String DEFAULT_PROJECT_FIELD = "openGrokDefaultProject";
    public static final String SELECTED_FIELD = "openGrokSelectedField";
    public static final String PATHS_FIELD = "openGrokPathsField";
    public static final String LIMIT_FIELD = "openGrokLimitField";

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

    public void setPaths(ArrayList<ArrayList> paths) {
        Gson gson = new Gson();
        String pathsJson = gson.toJson(paths);

        propertiesComponent.setValue(Configuration.PATHS_FIELD, pathsJson);
    }

    public ArrayList<ArrayList> getPaths() {
        Gson gson = new Gson();

        try {
            return gson.fromJson(
                propertiesComponent.getValue(Configuration.PATHS_FIELD),
                ArrayList.class
            );
        } catch (NullPointerException e) {
            return new ArrayList<ArrayList>();
        }
    }

    public String getLimit() {
        try {
            String limit = propertiesComponent.getValue(Configuration.LIMIT_FIELD);

            if (limit.equals("")) {
                limit = "50";
            }

            return limit;
        } catch (NullPointerException e) {
            return "50";
        }
    }

    public void setLimit(String limit) {
        int tmp = Integer.parseInt(limit);
        propertiesComponent.setValue(Configuration.LIMIT_FIELD, String.valueOf(tmp));
    }
}
