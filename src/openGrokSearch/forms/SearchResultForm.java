package openGrokSearch.forms;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import openGrokSearch.actions.tasks.GetProjectsTask;
import openGrokSearch.actions.tasks.SearchTask;
import openGrokSearch.utils.Configuration;
import openGrokSearch.utils.openGrok.Result;
import openGrokSearch.utils.openGrok.Tree.Tree;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SearchResultForm implements ActionListener {
    private static final String TOOL_WINDOW_ID = "OpenGrok Search";

    private ToolWindowManager toolWindowManager;
    private ToolWindow toolWindow;
    private Tree searchTree;
    private JScrollPane scrollPane;
    private Project mainProject;
    private JPanel mainPanel;
    private JTextField fullSearch;
    private JTextField definition;
    private JTextField symbol;
    private JTextField filePath;
    private JTextField history;
    private JButton searchButton;
    private JComboBox project;
    private JToolBar toolBar;
    private Configuration configuration;

    public SearchResultForm(Project project, Configuration configuration) {
        mainProject = project;
        this.configuration = configuration;

        setToolWindow();
        searchTree.clearSearchTree(true);
        searchTree.setProject(project);
        prepareProjectLists();
        setSearchHandler();
    }

    private void setToolWindow() {
        toolWindowManager = ToolWindowManager.getInstance(mainProject);
        toolWindow = toolWindowManager.registerToolWindow(TOOL_WINDOW_ID, true, ToolWindowAnchor.BOTTOM);
        toolWindow.setIcon(IconLoader.findIcon("/actions/find.png"));

        ContentFactory contentFactory = toolWindow.getContentManager().getFactory();
        Content content = contentFactory.createContent(mainPanel, "Results", true);
        toolWindow.getContentManager().addContent(content);

        toolWindow.setAutoHide(false);
        toolWindow.setAvailable(true, null);

        ActionManager actionManager = ActionManager.getInstance();
        ActionGroup toolbarGroup = (ActionGroup) actionManager.getAction("openGrokSearch.ToolBar");
        ActionToolbar actionToolbar = actionManager.createActionToolbar(TOOL_WINDOW_ID, toolbarGroup, false);
        toolBar.add(actionToolbar.getComponent());
        toolBar.setFloatable(false);
    }

    public void prepareProjectLists() {
        int selectedItem = 0;
        int index = 0;

        project.removeAllItems();
        for (String item : configuration.getProjects()) {
            project.addItem(item);
            if (item.equals(configuration.getDefaultProject())) {
                selectedItem = index;
            }
            index++;
        }

        if (index > 0) {
            project.setSelectedIndex(selectedItem);
        }
    }

    private void setSearchHandler() {
        searchButton.addActionListener(this);
        fullSearch.addActionListener(this);
        definition.addActionListener(this);
        symbol.addActionListener(this);
        filePath.addActionListener(this);
        history.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        SearchTask task = new SearchTask(mainProject, configuration, this);
        if (!fullSearch.getText().isEmpty()) {
            task.addSearchCondition("phrase", fullSearch.getText());
        }
        if (!definition.getText().isEmpty()) {
            task.addSearchCondition("definition", definition.getText());
        }
        if (!symbol.getText().isEmpty()) {
            task.addSearchCondition("symbol", symbol.getText());
        }
        if (!filePath.getText().isEmpty()) {
            task.addSearchCondition("filePath", filePath.getText());
        }
        if (!history.getText().isEmpty()) {
            task.addSearchCondition("history", history.getText());
        }
        if (project.getItemCount() > 0) {
            task.addSearchCondition("project", project.getSelectedItem().toString());
        }
        ProgressManager.getInstance().run(task);
    }

    public void setFullSearch(String phrase) {
        fullSearch.setText(phrase);
    }

    public void executeSearch() {
        this.actionPerformed(null);
    }

    public void showToolWindow() {
        toolWindow.show(new Runnable() {
            public void run() {
                // do nothing
            }
        });
    }

    public void setResults(HashMap<String, HashMap> groupedResults) {
        searchTree.setPathTransformations(configuration.getPaths());
        searchTree.setResults(groupedResults);
    }

    public Tree getSearchTree() {
        return searchTree;
    }

    public JComboBox getProject() {
        return project;
    }

    public void expandResults() {
        searchTree.expandAll();
    }

    public void collapseResults() {
        searchTree.collapseAll();
    }
}