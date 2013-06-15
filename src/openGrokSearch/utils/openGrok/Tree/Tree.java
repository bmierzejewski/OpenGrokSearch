package openGrokSearch.utils.openGrok.Tree;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import openGrokSearch.utils.Configuration;
import openGrokSearch.utils.openGrok.Result;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Tree extends JTree {
    private ArrayList<ArrayList> pathTransformations;

    public void setPathTransformations(ArrayList<ArrayList> pathTransformations) {
        this.pathTransformations = pathTransformations;
    }

    public void setProject(final Project project) {
        final Tree currentTree = this;
        this.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                Object node;
                try {
                    node = (ResultNode) currentTree.getLastSelectedPathComponent();
                    if (node == null || !(node instanceof ResultNode)) return;
                } catch (ClassCastException exception) {
                    return;
                }

                ResultNode resultNode = (ResultNode) node;

                VirtualFileManager virtualFileManager = VirtualFileManager.getInstance();
                VirtualFile virtualFile = virtualFileManager.findFileByUrl(project.getBaseDir() + resultNode.getFullPath());

                if (virtualFile != null) {
                    OpenFileDescriptor openFileDescriptor = new OpenFileDescriptor(
                            project, virtualFile, resultNode.getLine(), 0
                    );

                    FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
                    fileEditorManager.openTextEditor(openFileDescriptor, true);
                } else {
                    Messages.showMessageDialog("File not found", "OpenGrok Search", Messages.getInformationIcon());
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        // paint selected node's background and border
        int fromRow = getRowForPath( getSelectionPath());
        if (fromRow != -1) {
            int toRow = fromRow + 1;
            Rectangle fromBounds = getRowBounds(fromRow);
            Rectangle toBounds = getRowBounds(toRow - 1);
            if (fromBounds != null && toBounds != null) {
                g.setColor(new Color(115,164,209));
                g.fillRect(0, fromBounds.y, getWidth(), toBounds.y - fromBounds.y + toBounds.height);
                g.setColor(new Color(57,105,138));
                g.drawRect(0, fromBounds.y, getWidth() - 1, toBounds.y - fromBounds.y + toBounds.height);
            }
        }

        // perform operation of superclass
        setOpaque(false); // trick not to paint background
        super.paintComponent(g);
        setOpaque(false);
    }

    public void clearSearchTree(boolean showErrorMessage) {
        DefaultTreeModel model = (DefaultTreeModel) this.getModel();
        Node root = new Node("OpenGrok search results", "/actions/search.png");
        model.setRoot(root);

        while(!model.isLeaf(root))
        {
            model.removeNodeFromParent((MutableTreeNode)model.getChild(root, 0));
        }

        if (showErrorMessage) {
            model.insertNodeInto(
                new Node("No results found", "/general/error.png"), root, root.getChildCount()
            );
            this.expandPath(new TreePath(root.getPath()));
        }

        this.setCellRenderer(new NodeRenderer());
    }

    public void setResults(HashMap<String,HashMap> groupedResults) {
        clearSearchTree(groupedResults.isEmpty());

        DefaultTreeModel model = (DefaultTreeModel) this.getModel();
        Node root = (Node) model.getRoot();

        Node firstFileNode = null;
        Iterator pathIterator = groupedResults.entrySet().iterator();
        while (pathIterator.hasNext()) {
            Map.Entry pathPair = (Map.Entry)pathIterator.next();
            String path = getPath((String)pathPair.getKey());
            HashMap files = (HashMap)pathPair.getValue();

            Node pathNode = new Node(path, "/nodes/folder.png");
            model.insertNodeInto(pathNode, root, root.getChildCount());

            Iterator fileIterator = files.entrySet().iterator();
            while(fileIterator.hasNext()) {
                Map.Entry filePair = (Map.Entry)fileIterator.next();
                String file = (String)filePair.getKey();
                ArrayList<Result> lines = (ArrayList<Result>)filePair.getValue();

                Node fileNode = new Node(file, "/fileTypes/text.png");
                model.insertNodeInto(fileNode, pathNode, pathNode.getChildCount());

                for (Result result : lines) {
                    ResultNode tmp = new ResultNode(result, "");
                    model.insertNodeInto(tmp, fileNode, fileNode.getChildCount());
                }

                if (firstFileNode == null ) {
                    firstFileNode = fileNode;
                }
            }
        }

        if (firstFileNode != null) {
            this.expandPath(new TreePath(firstFileNode.getPath()));
            this.setSelectionPath(new TreePath(firstFileNode.getPath()));
        }

        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    private String getPath(String filename) {
        for (ArrayList trans : pathTransformations) {
            filename = filename.replaceAll(trans.get(0).toString(), trans.get(1).toString());
        }

        return filename;
    }
}