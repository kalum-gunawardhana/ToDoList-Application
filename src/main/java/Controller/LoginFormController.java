package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class LoginFormController {

    public TextField txtUserName;
    public TextField txtPassword;

    private String userName="0764341883";
    private String password="123";

    public void btnLoginOnAction(javafx.event.ActionEvent actionEvent) {
        if(txtUserName.getText().equals(userName) && txtPassword.getText().equals(password)){
            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/HomeForm.fxml"))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.show();
        }else{
            System.out.println("enter user name and password");

            // Create an Alert of type INFORMATION
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System Alert");
            alert.setHeaderText("login Failed");
            alert.setContentText("Invalid UserName or Password");

            // Show the alert and wait for user response
            alert.showAndWait();
        }
    }
}
