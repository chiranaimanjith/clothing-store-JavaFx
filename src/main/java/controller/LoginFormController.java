package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {

    @FXML
    private JFXTextField userEmail;

    @FXML
    private JFXPasswordField userPassword;

    @FXML
    private JFXButton btnLogin;

    public void initialize() {
        userPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnLogin.fire();
            }
        });
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String email = userEmail.getText();
        String password = userPassword.getText();

        // Validate empty fields
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Please enter both email and password");
            return;
        }

        // Validate credentials
        String userType = validateLogin(email, password);
        if (userType != null) {
            showAlert("Login Successful", "Welcome " + userType + "!");

            Stage nextStage = new Stage();
            try {
                String fxmlFile = userType.equals("admin") ? "../view/admin/admin_form.fxml" : "../view/employee/employee_form.fxml";
                nextStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxmlFile))));
                nextStage.setTitle(userType.equals("admin") ? "Admin Form" : "Employee Form");
                nextStage.setResizable(false);
                nextStage.show();

                // Close the login form
                Stage loginStage = (Stage) userEmail.getScene().getWindow();
                loginStage.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert("Login Failed", "Invalid email or password.");
        }
    }

    private String validateLogin(String email, String password) {
        Connection connection;
        PreparedStatement pst;
        ResultSet resultSet;

        try {
            // Get database connection
            connection = DBConnection.getInstance().getConnection();

            // Prepare the SQL query to check email, password, and user_type
            String query = "SELECT user_type FROM users WHERE email = ? AND password = ?";
            pst = connection.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);

            // Execute the query
            resultSet = pst.executeQuery();

            // If a record is found, return user_type
            if (resultSet.next()) {
                return resultSet.getString("user_type");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error connecting to the database.");
        }
        return null;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
