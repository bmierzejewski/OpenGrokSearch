package openGrokSearch.actions.tasks;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
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
        ApplicationManager.getApplication().invokeLater(new HideForms());

        try {
            Project ogp = new Project(configuration);
            configuration.setProjects(
                ogp.getProjects()
            );

            configuration.setDefaultProject(
                ogp.getDefaultProject()
            );

            resultForm.prepareProjectLists();
        } catch (Exception e) {
            Notifications.Bus.notify(
                    new Notification(
                            "OpenGrok Search", "Error", "Could not connect to openGrok",
                            NotificationType.ERROR
                    )
            );
        }
    }

    public void onSuccess() {
        ApplicationManager.getApplication().invokeLater(new ShowForms());
    }

    private class HideForms implements Runnable {
        public void run() {
            resultForm.getProject().setVisible(false);
        }
    }

    private class ShowForms implements Runnable {
        public void run() {
            resultForm.getProject().setVisible(true);
            resultForm.getProject().repaint();
        }
    }
}
