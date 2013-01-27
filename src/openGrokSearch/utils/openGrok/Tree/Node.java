package openGrokSearch.utils.openGrok.Tree;

import org.apache.commons.lang.StringEscapeUtils;

import javax.swing.tree.DefaultMutableTreeNode;

public class Node extends DefaultMutableTreeNode {
    private String text;
    private String icon;

    public String getIcon() {
        return icon;
    }

    public Node(String text, String icon) {
        super();
        this.icon = icon;
        this.text = text;
        this.userObject = this.getFormatedText();
    }

    public void setUserObject(String text) {
        this.text = text;
        this.userObject = this.getFormatedText();
    }

    private String getFormatedText() {
        if (this.text.charAt(0) == '/') {
            this.text = this.text.substring(1);
        }
        this.text = this.text.replace("<line_start>", "<font color=\"gray\"><i>( ");
        this.text = this.text.replace("</line_start>", " )</i></font>");

        this.text = this.text.replace("<phrase_start>", "<b>");
        this.text = this.text.replace("</phrase_start>", " </b>");

        this.text = "<html>" + this.text + "</html>";

        return this.text;
    }
}
