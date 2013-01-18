package openGrokSearch;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class OpenGrokSearchAction extends AnAction {

    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(DataKeys.EDITOR);
        if (editor != null) {
            SelectedText selectedText = new SelectedText(editor);
            String text = selectedText.getText();
            String link = getLink(text);
            if (!link.equals("")) {
                BrowserUtil.launchBrowser(link);
            }
        } else {
            Messages.showMessageDialog("Please select text", "OpenGrok Search", Messages.getInformationIcon());
        }
    }

    private String getLink(String phrase) {
        String link = null;
        Configuration configuration = Configuration.getInstance();
        if (!configuration.getLink().equals("")) {
            try {
                link = configuration.getLink() + "/search?q=" + URLEncoder.encode(phrase, "UTF-8");
            } catch (UnsupportedEncodingException e1) {
                link = configuration.getLink() + "/search?q=" + phrase;
            }
        }

        return link;
    }
}
