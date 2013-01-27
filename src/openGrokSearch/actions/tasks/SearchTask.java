package openGrokSearch.actions.tasks;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
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
        SearchResult ogp = new SearchResult(configuration);
        if (searchConditions.containsKey("phrase")) {
            ogp.setFullSearchPhrase(searchConditions.get("phrase"));
        }
        if (searchConditions.containsKey("definition")) {
            ogp.setDefinition(searchConditions.get("definition"));
        }
        if (searchConditions.containsKey("symbol")) {
            ogp.setSymbol(searchConditions.get("symbol"));
        }
        if (searchConditions.containsKey("filePath")) {
            ogp.setFilePath(searchConditions.get("filePath"));
        }
        if (searchConditions.containsKey("history")) {
            ogp.setHistory(searchConditions.get("history"));
        }
        if (searchConditions.containsKey("project")) {
            ogp.setProject(searchConditions.get("project"));
        }

        ArrayList<Result> results = ogp.search();

        HashMap<String, HashMap> groupedResults = Result.groupResults(results);

        resultForm.setResults(groupedResults);
    }

    public void addSearchCondition(String key, String value) {
        searchConditions.put(key, value);
    }
}
