package openGrokSearch.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import openGrokSearch.OpenGrokSearch;
import org.jetbrains.annotations.Nullable;

public class CollapseAllAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        OpenGrokSearch openGrokSearch = getOpeGrokSearch(anActionEvent);
        if(openGrokSearch != null) {
            openGrokSearch.getSearchResultForm().collapseResults();
        }
    }

    @Nullable
    protected OpenGrokSearch getOpeGrokSearch(AnActionEvent event) {
        Project project = LangDataKeys.PROJECT.getData(event.getDataContext());
        if(project == null) {
            return null;
        }
        return OpenGrokSearch.getInstance(project);
    }
}
