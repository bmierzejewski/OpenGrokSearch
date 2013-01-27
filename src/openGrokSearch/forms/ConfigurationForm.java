package openGrokSearch.forms;

import openGrokSearch.utils.Configuration;

import javax.swing.*;

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

    public ConfigurationForm(Configuration configuration) {
        this.linkField.setText(configuration.getLink());
        this.loginField.setText(configuration.getLogin());
        this.passwordField.setText(configuration.getPassword());

        this.linkLabel.setLabelFor(this.linkField);
        this.loginLabel.setLabelFor(this.loginField);
    }

    public JPanel getComponent() {
        return this.component;
    }

    public void reset(Configuration configuration) {
        this.linkField.setText(configuration.getLink());
        this.loginField.setText(configuration.getLogin());
        this.passwordField.setText(configuration.getPassword());
        this.accessProtected.setSelected(configuration.isSelected());
    }

    public void apply(Configuration configuration) {
        configuration.setLink(this.linkField.getText());
        configuration.setLogin(this.loginField.getText());
        configuration.setPassword(this.passwordField.getText());
        configuration.setSelected(this.accessProtected.isSelected());
    }
}