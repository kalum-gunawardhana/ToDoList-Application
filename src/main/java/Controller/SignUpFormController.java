package Controller;

import DBConnection.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpFormController {
    public TextField txtUserName;
    public TextField txtPassword;

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO users (phone_number, password_hash) VALUES (?, ?)");
            preparedStatement.setString(1,txtUserName.getText());
            preparedStatement.setString(2,txtPassword.getText());

            int executeUpdate = preparedStatement.executeUpdate();

            if(executeUpdate>0){
                // Create an Alert of type INFORMATION
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("System Alert");
                alert.setHeaderText("Successfully SignUp");
                alert.setContentText("Your Account Successfully Created");

                // Show the alert and wait for user response
                alert.showAndWait();
            }else{
                // Create an Alert of type INFORMATION
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("System Alert");
                alert.setHeaderText("SignUp Failed");
                alert.setContentText("Invalid UserName or Password");

                // Show the alert and wait for user response
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
