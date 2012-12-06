package openGrokSearch;

import javax.swing.*;

public class ConfigurationForm {
    private JTextField linkField;
    private JLabel linkLabel;
    private JTextField paramsField;
    private JLabel paramsLabel;
    private JPanel component;

    public ConfigurationForm(Configuration configuration) {
        this.linkField.setText(configuration.getLink());
        this.paramsField.setText(configuration.getParams());

        this.linkLabel.setLabelFor(this.linkField);
        this.paramsLabel.setLabelFor(this.paramsField);
    }

    public JPanel getComponent() {
        return this.component;
    }

    public void setConfiguration(Configuration configuration) {
        this.linkField.setText(configuration.getLink());
        this.paramsField.setText(configuration.getParams());
    }

    public Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setLink(this.linkField.getText());
        configuration.setParams(this.paramsField.getText());

        return configuration;
    }
}
