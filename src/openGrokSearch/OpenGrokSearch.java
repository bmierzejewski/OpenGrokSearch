package openGrokSearch;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import openGrokSearch.actions.tasks.GetProjectsTask;
import openGrokSearch.forms.SearchResultForm;
import openGrokSearch.utils.Configuration;
import openGrokSearch.forms.ConfigurationForm;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class OpenGrokSearch implements ProjectComponent, Configurable {

    private ConfigurationForm configurationForm;
    private SearchResultForm searchResultForm;
    private Configuration configuration;
    private Project project;


    public OpenGrokSearch(Project project) {
        configuration = Configuration.getInstance();

        this.project = project;
    }

    public static OpenGrokSearch getInstance(Project project) {
        return project.getComponent(OpenGrokSearch.class);
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
        return "OpenGrok search";
    }

    public Icon getIcon() {
        return IconLoader.findIcon("/actions/find.png");
    }

    @Override
    public String getHelpTopic() {
        return null;  
    }

    @Override
    public void projectOpened() {
        searchResultForm = new SearchResultForm(project, configuration);
    }

    @Override
    public void projectClosed() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return "OpenGrok Search";
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
            configurationForm.apply(configuration);

            GetProjectsTask task = new GetProjectsTask(project, configuration, searchResultForm);
            ProgressManager.getInstance().run(task);
        }
    }

    @Override
    public void reset() {
        if(this.configurationForm != null) {
            this.configurationForm.reset(this.configuration);
        }
    }

    @Override
    public void disposeUIResources() {
        this.configurationForm = null;
    }

    public SearchResultForm getSearchResultForm() {
        return searchResultForm;
    }
}
