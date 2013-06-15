package openGrokSearch.forms;

import openGrokSearch.utils.Configuration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class ConfigurationForm {
    private JTextField linkField;
    private JLabel linkLabel;
    private JPanel component;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JLabel tomcatSettingsLabel;
    private JCheckBox accessProtected;
    private JTable pathTransformTable;
    private JScrollPane pathTransformPane;

    public ConfigurationForm(Configuration configuration) {
        this.linkField.setText(configuration.getLink());
        this.loginField.setText(configuration.getLogin());
        this.passwordField.setText(configuration.getPassword());

        this.linkLabel.setLabelFor(this.linkField);
        this.loginLabel.setLabelFor(this.loginField);

        String columnNames[] = {"Path from","Path to"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (ArrayList row : configuration.getPaths()) {
            tableModel.addRow(new Vector(row));
        }
        tableModel.setNumRows(10);
        pathTransformTable.setModel(tableModel);
        pathTransformPane.setPreferredSize(new Dimension(pathTransformPane.getWidth(), 190));
    }

    public JPanel getComponent() {
        return this.component;
    }

    public void reset(Configuration configuration) {
        this.linkField.setText(configuration.getLink());
        this.loginField.setText(configuration.getLogin());
        this.passwordField.setText(configuration.getPassword());
        this.accessProtected.setSelected(configuration.isSelected());

        DefaultTableModel tableModel = new DefaultTableModel();
        for (ArrayList row : configuration.getPaths()) {
            tableModel.addRow(new Vector(row));
        }
        tableModel.setNumRows(10);
    }

    public void apply(Configuration configuration) {
        configuration.setLink(this.linkField.getText());
        configuration.setLogin(this.loginField.getText());
        configuration.setPassword(this.passwordField.getText());
        configuration.setSelected(this.accessProtected.isSelected());

        ArrayList<ArrayList> paths = new ArrayList<ArrayList>();
        for (int i = 0; i < pathTransformTable.getRowCount(); i++) {
            try {
                ArrayList<String> elem = new ArrayList<String>();
                elem.add(pathTransformTable.getValueAt(i, 0).toString());
                elem.add(pathTransformTable.getValueAt(i, 1).toString());
                paths.add(elem);
            } catch (NullPointerException e) {

            }
        }
        configuration.setPaths(paths);
        System.out.println(paths);
    }
}
