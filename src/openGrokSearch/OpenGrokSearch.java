package openGrokSearch;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class OpenGrokSearch implements ProjectComponent, Configurable {

    private ConfigurationForm configurationForm;
    private Configuration configuration;
    private final PropertiesComponent propertiesComponent;

    public OpenGrokSearch(Project project) {
        propertiesComponent = PropertiesComponent.getInstance(project);
        configuration = Configuration.getInstance();
        configuration.setLink(propertiesComponent.getValue(Configuration.LINK_FIELD));
        configuration.setLogin(propertiesComponent.getValue(Configuration.LOGIN_FIELD));
        configuration.setPassword(propertiesComponent.getValue(Configuration.PASSWORD_FIELD));
    }

    @Override
    public void initComponent() {
        
    }

    @Override
    public void disposeComponent() {
        
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "openGrok search";
    }

    @Override
    public Icon getIcon() {
        return null;  
    }

    @Override
    public String getHelpTopic() {
        return null;  
    }

    @Override
    public void projectOpened() {
        
    }

    @Override
    public void projectClosed() {
        
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "openGrok search";
    }

    @Override
    public JComponent createComponent() {
        if(this.configurationForm == null) {
            this.configurationForm = new ConfigurationForm(this.configuration);
        }

        return this.configurationForm.getComponent();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        if(this.configurationForm != null) {
            configuration = configurationForm.getConfiguration();
            Configuration.setInstance(configuration);

            this.propertiesComponent.setValue(Configuration.LINK_FIELD, this.configuration.getLink());
            this.propertiesComponent.setValue(Configuration.LOGIN_FIELD, this.configuration.getLogin());
            this.propertiesComponent.setValue(Configuration.PASSWORD_FIELD, this.configuration.getPassword());
        }
    }

    @Override
    public void reset() {
        if(this.configurationForm != null) {
            this.configurationForm.setConfiguration(this.configuration);
        }
    }

    @Override
    public void disposeUIResources() {
        this.configurationForm = null;
    }
}
