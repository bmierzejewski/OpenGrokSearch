package openGrokSearch;

import javax.swing.*;

public class ConfigurationForm {
    private JTextField linkField;
    private JLabel linkLabel;
    private JPanel component;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JLabel apacheSettingsLabel;

    public ConfigurationForm(Configuration configuration) {
        this.linkField.setText(configuration.getLink());
        this.loginField.setText(configuration.getLogin());
        this.passwordField.setText(configuration.getPassword());

        this.linkLabel.setLabelFor(this.linkField);
        this.loginLabel.setLabelFor(this.loginField);
        this.loginLabel.setLabelFor(this.loginField);
    }

    public JPanel getComponent() {
        return this.component;
    }

    public void setConfiguration(Configuration configuration) {
        this.linkField.setText(configuration.getLink());
        this.loginField.setText(configuration.getLogin());
        this.passwordField.setText(configuration.getPassword());
    }

    public Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setLink(this.linkField.getText());
        configuration.setLogin(this.loginField.getText());
        configuration.setPassword(this.passwordField.getPassword().toString());

        return configuration;
    }
}
