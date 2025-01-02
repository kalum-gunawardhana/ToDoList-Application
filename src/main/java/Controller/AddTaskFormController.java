package Controller;

import DBConnection.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddTaskFormController {

    public TextField txtId;
    public TextField txtTile;
    public TextField txtDes;
    public TextField txtDate;

    public void btnAddTaskOnAction(ActionEvent actionEvent) {
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO active_tasks (task_id, task_title, task_description, created_date) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1,txtId.getText());
            preparedStatement.setString(2,txtTile.getText());
            preparedStatement.setString(3,txtDes.getText());
            preparedStatement.setString(4,txtDate.getText());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
