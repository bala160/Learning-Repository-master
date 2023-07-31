package com.example.swagger.formdesign;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

public class DynamicFormCreator extends JFrame {
    private List<FormField> formFields;

    public DynamicFormCreator() {
        setTitle("Dynamic Form Creator");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        formFields = new ArrayList<>();

        // Create a panel to hold the form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // Add a button to add new form fields dynamically
        JButton addButton = new JButton("Add Form Field");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFieldInputDialog();
            }
        });

        // Add a button to save the form configuration
        JButton saveButton = new JButton("Save Form Configuration");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFormConfiguration();
            }
        });

        // Add the form panel and the buttons to the main frame
        add(formPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Display the main frame
        setVisible(true);
    }

    private void showFieldInputDialog() {
        // Show a dialog to get user input for the form field
        String fieldName = JOptionPane.showInputDialog(this, "Enter Field Name:");
        if (fieldName != null && !fieldName.trim().isEmpty()) {
            String fieldType = (String) JOptionPane.showInputDialog(this, "Select Field Type:",
                    "Field Type", JOptionPane.QUESTION_MESSAGE, null,
                    new String[]{"Text Field", "Check Box", "Combo Box"}, "Text Field");

            if (fieldType != null) {
                formFields.add(new FormField(fieldName, fieldType));
                JOptionPane.showMessageDialog(this, "Form field added successfully.");
            }
        }
    }

    private void saveFormConfiguration() {
        // Get the form name from the user
        String formName = JOptionPane.showInputDialog(this, "Enter Form Name:");

        if (formName != null && !formName.trim().isEmpty()) {
            // Serialize the formFields list to JSON format
            Gson gson = new Gson();
            String formConfigJson = gson.toJson(formFields);

            String url = "jdbc:mysql://localhost:3306/baladb";
            //String url = "jdbc:mysql://localhost:3306/mydatabase"; // Replace "mydatabase" with your database name
            String username = "root"; // Replace "your_username" with your database username
            String password = "Krish@1997";
            Connection connection = null;
            // Save the form configuration to the database
            try  {
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Step 2: Create the JDBC connection
                connection = DriverManager.getConnection(url, username, password);
                String sql = "INSERT INTO dynamic_form_configs (form_name, form_config) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, formName);
                statement.setString(2, formConfigJson);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Form configuration saved successfully.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error saving form configuration: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DynamicFormCreator());
    }
}



