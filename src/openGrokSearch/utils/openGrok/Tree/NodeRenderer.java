package openGrokSearch.utils.openGrok.Tree;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class NodeRenderer extends DefaultTreeCellRenderer {
    private JLabel label;

    NodeRenderer() {
        label = new JLabel();
        this.setBackgroundSelectionColor(null);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        try {
            Node node = (Node) value;
            label.setIcon(IconLoader.findIcon(node.getIcon()));
            label.setText((String)node.getUserObject());
        } catch (Exception e) {
            label.setIcon(null);
            label.setText("" + value);
        }

        return label;
    }
}
