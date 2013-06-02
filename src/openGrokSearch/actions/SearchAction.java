package openGrokSearch.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import openGrokSearch.OpenGrokSearch;
import openGrokSearch.forms.SearchResultForm;
import openGrokSearch.utils.Configuration;
import openGrokSearch.actions.utils.SelectedText;
import openGrokSearch.actions.tasks.SearchTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchAction extends AnAction {

    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(DataKeys.EDITOR);
        if (editor != null) {
            Project project = e.getData(PlatformDataKeys.PROJECT);
            OpenGrokSearch ogs = OpenGrokSearch.getInstance(project);
            SearchResultForm srf = ogs.getSearchResultForm();

            SelectedText selectedText = new SelectedText(editor);
            String text = selectedText.getText();
            if (!text.equals("")) {
                srf.showToolWindow();
                srf.setFullSearch(text);
                srf.executeSearch();
            }
        } else {
            Messages.showMessageDialog("Please select text", "OpenGrok Search", Messages.getInformationIcon());
        }
    }
}
