package openGrokSearch.actions.tasks;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.ui.Messages;
import openGrokSearch.forms.SearchResultForm;
import openGrokSearch.utils.Configuration;
import openGrokSearch.utils.openGrok.Connections.Project;
import org.jetbrains.annotations.NotNull;


public class GetProjectsTask extends Task.Backgroundable {
    private Configuration configuration;
    private SearchResultForm resultForm;

    public GetProjectsTask(@org.jetbrains.annotations.Nullable com.intellij.openapi.project.Project project, Configuration configuration, SearchResultForm resultForm) {
        super(project, "Fetching projects list from OpenGrok", true);

        this.configuration = configuration;
        this.resultForm = resultForm;
    }

    @Override
    public void run(@NotNull ProgressIndicator progressIndicator) {
        try {
            Project ogp = new Project(configuration);
            configuration.setProjects(
                ogp.getProjects()
            );

            configuration.setDefaultProject(
                ogp.getDefaultProject()
            );

            resultForm.prepareProjectLists();
        } catch (IllegalArgumentException e) {
            System.out.println("Connection Error");
        }
    }
}
