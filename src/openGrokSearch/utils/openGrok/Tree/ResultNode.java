package openGrokSearch.utils.openGrok.Tree;

import openGrokSearch.utils.openGrok.Result;

public class ResultNode extends Node{
    private Result node;

    public ResultNode(Result node, String icon) {
        super(node.getPhrase(), icon);
        this.node = node;
    }

    public int getLine() {
        return node.getLine() - 1;
    }

    public String getFullPath() {
        String path = node.getPath().replaceFirst("master/", "");
        return path + "/" + node.getFile();
    }
}
