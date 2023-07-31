package com.example.swagger.formdesign;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DynamicFormApp extends JFrame {
    private List<FormField> formFields;

    public DynamicFormApp(List<FormField> formFields) {
        setTitle("Dynamic Form App");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.formFields = formFields;

        // Create a panel to hold the form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // Add form fields to the form panel
        for (FormField field : formFields) {
            addFieldToPanel(formPanel, field);
        }

        // Add a button to submit the form
        JButton submitButton = new JButton("Submit Form");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFormData();
            }
        });

        // Add the form panel and the submit button to the main frame
        add(formPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);

        // Display the main frame
        setVisible(true);
    }

    private void addFieldToPanel(JPanel formPanel, FormField field) {
        JPanel fieldPanel = new JPanel(new FlowLayout());
        fieldPanel.add(new JLabel(field.getName() + ":"));

        if (field.getType().equals("Text Field")) {
            JTextField textField = new JTextField(20);
            formPanel.add(textField);
        } else if (field.getType().equals("Check Box")) {
            JCheckBox checkBox = new JCheckBox();
            formPanel.add(checkBox);
        } else if (field.getType().equals("Combo Box")) {
            String[] options = {"Option 1", "Option 2", "Option 3"};
            JComboBox<String> comboBox = new JComboBox<>(options);
            formPanel.add(comboBox);
        }
    }

    private void saveFormData() {
        // Get the form data from user input
        String formName = JOptionPane.showInputDialog(this, "Enter Form Name:");
        if (formName != null && !formName.trim().isEmpty()) {
            StringBuilder formDataBuilder = new StringBuilder();
            for (FormField field : formFields) {
                formDataBuilder.append(field.getName()).append(": ");
                Component component = getContentPane().getComponent(0);
                if (component instanceof JTextField) {
                    formDataBuilder.append(((JTextField) component).getText());
                } else if (component instanceof JCheckBox) {
                    formDataBuilder.append(((JCheckBox) component).isSelected());
                } else if (component instanceof JComboBox) {
                    formDataBuilder.append(((JComboBox) component).getSelectedItem());
                }
                formDataBuilder.append("\n");
            }

            String formData = formDataBuilder.toString();

            // Save the form data to the database
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/baladb",
                    "root", "Krish@1997")) {
                String sql = "INSERT INTO form_data (form_name, form_data) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, formName);
                statement.setString(2, formData);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Form data saved successfully.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error saving form data: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        // Retrieve the form configuration from the database
        List<FormField> formFields = retrieveFormConfigurationFromDatabase();

        // Create the dynamic form app
        SwingUtilities.invokeLater(() -> new DynamicFormApp(formFields));
    }

    private static List<FormField> retrieveFormConfigurationFromDatabase() {
        List<FormField> formFields = null;

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/baladb",
                "root", "Krish@1997")) {
            String sql = "SELECT form_config FROM dynamic_form_configs WHERE form_name = ?";
            String formName = JOptionPane.showInputDialog(null, "Enter Form Name:");

            if (formName != null && !formName.trim().isEmpty()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, formName);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    String formConfigJson = resultSet.getString("form_config");

                    Gson gson = new Gson();
                    formFields = gson.fromJson(formConfigJson, new TypeToken<List<FormField>>(){}.getType());
                } else {
                    JOptionPane.showMessageDialog(null, "Form configuration not found for the given form name.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error retrieving form configuration: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return formFields;
    }
}

