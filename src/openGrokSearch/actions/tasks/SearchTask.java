package openGrokSearch.actions.tasks;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import openGrokSearch.forms.SearchResultForm;
import openGrokSearch.utils.Configuration;
import openGrokSearch.utils.openGrok.Connections.SearchResult;
import openGrokSearch.utils.openGrok.Result;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SearchTask extends Task.Backgroundable {
    private HashMap<String, String> searchConditions;
    private Configuration configuration;
    private SearchResultForm resultForm;

    public SearchTask(@org.jetbrains.annotations.Nullable Project project, Configuration configuration, SearchResultForm resultForm) {
        super(project, "Searching in OpenGrok", true);

        this.configuration = configuration;
        this.resultForm = resultForm;
        searchConditions = new HashMap<String, String>();
    }

    @Override
    public void run(@NotNull ProgressIndicator progressIndicator) {
        ApplicationManager.getApplication().invokeLater(new HideForms());

        boolean dataTyped = false;
        SearchResult ogp = new SearchResult(configuration);
        if (searchConditions.containsKey("phrase")) {
            ogp.setFullSearchPhrase(searchConditions.get("phrase"));
            dataTyped = true;
        }
        if (searchConditions.containsKey("definition")) {
            ogp.setDefinition(searchConditions.get("definition"));
            dataTyped = true;
        }
        if (searchConditions.containsKey("symbol")) {
            ogp.setSymbol(searchConditions.get("symbol"));
            dataTyped = true;
        }
        if (searchConditions.containsKey("filePath")) {
            ogp.setFilePath(searchConditions.get("filePath"));
            dataTyped = true;
        }
        if (searchConditions.containsKey("history")) {
            ogp.setHistory(searchConditions.get("history"));
            dataTyped = true;
        }
        if (searchConditions.containsKey("project")) {
            ogp.setProject(searchConditions.get("project"));
        }

        if (dataTyped) {
            try {
                ArrayList<Result> results = ogp.search();

                HashMap<String, HashMap> groupedResults = Result.groupResults(results);

                resultForm.setResults(groupedResults);
            } catch (Exception e) {
                Notifications.Bus.notify(
                    new Notification(
                        "OpenGrok Search", "Error", "Could not connect to openGrok",
                        NotificationType.ERROR
                    )
                );
            }
        }
    }

    public void addSearchCondition(String key, String value) {
        searchConditions.put(key, value);
    }

    public void onSuccess() {
        ApplicationManager.getApplication().invokeLater(new ShowForms());
    }

    private class HideForms implements Runnable {
        public void run() {
            resultForm.getSearchTree().setVisible(false);
        }
    }

    private class ShowForms implements Runnable {
        public void run() {
            resultForm.getSearchTree().setVisible(true);
            resultForm.getSearchTree().repaint();
        }
    }
}
