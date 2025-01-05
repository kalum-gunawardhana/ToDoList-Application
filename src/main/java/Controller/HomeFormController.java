package Controller;

import DBConnection.DBConnection;
import Model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;

public class HomeFormController implements Initializable {
    public TableView tblActive;
    public TableColumn colId;
    public TableColumn colTitle;
    public TableColumn colDes;
    public TableColumn colDate;
    public TableView tblCompleted;
    public TableColumn colID2;
    public TableColumn colTitle2;
    public TableColumn colDes2;
    public TableColumn colDate2;
    public TextField txtId;
    public TextField txtName;
    public TextField txtDescription;
    public TextField txtDate;

    public void btnAddTaskOnAction(ActionEvent actionEvent) {
        System.out.println("clicked");

        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO active_tasks (task_title, task_description, created_date) VALUES (?, ?, ?)");
            preparedStatement.setString(1,txtName.getText());
            preparedStatement.setString(2,txtDescription.getText());
            preparedStatement.setString(3,txtDate.getText());

            int update = preparedStatement.executeUpdate();

            txtName.clear();
            txtDescription.clear();
            txtDate.clear();

            if(update>0){
                // Create an Alert of type INFORMATION
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("System Alert");
                alert.setHeaderText("Task Added Successfully");
                alert.setContentText("Your Task Has Been Added..");

                // Show the alert and wait for user response
                alert.showAndWait();
            }

            loadActiveTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchTaskOnAction(ActionEvent actionEvent) {
        System.out.println("clicked");

        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM active_tasks WHERE task_title = ?");
            preparedStatement.setString(1,txtName.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                txtDescription.setText(resultSet.getString("task_description"));
                txtDate.setText(resultSet.getString("created_date"));
            }

            System.out.println(resultSet.next());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTaskOnAction(ActionEvent actionEvent) {
        System.out.println("clicked");

    }

    public void deleteTaskOnAction(ActionEvent actionEvent) {
        tblActive.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            System.out.println(t1);

            if(t1!=null){
                removeActiveTask(t1);
                addCompletedTask(t1);
            }

        });

        loadActiveTable();
        loadCompletedTable();
    }

    private void loadActiveTable(){
        ObservableList<Task> observableTaskList1 = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM active_tasks");
            while (resultSet.next()){
                String id = resultSet.getString("task_id");
                String title = resultSet.getString("task_title");
                String description = resultSet.getString("task_description");
                String date = resultSet.getString("created_date");

                Task task = new Task(title, description, date);
                observableTaskList1.add(task);
            }
            System.out.println(observableTaskList1.toString());

            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colDes.setCellValueFactory(new PropertyValueFactory<>("description"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

            tblActive.setItems(observableTaskList1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCompletedTable(){
        ObservableList<Task> observableTaskList2 = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM completed_tasks");
            while (resultSet.next()){
                String id = resultSet.getString("task_id");
                String title = resultSet.getString("task_title");
                String description = resultSet.getString("task_description");
                String date = resultSet.getString("completion_date");

                Task task = new Task(title, description, date);
                observableTaskList2.add(task);
            }
            System.out.println(observableTaskList2.toString());

            colTitle2.setCellValueFactory(new PropertyValueFactory<>("title"));
            colDes2.setCellValueFactory(new PropertyValueFactory<>("description"));
            colDate2.setCellValueFactory(new PropertyValueFactory<>("date"));

            tblCompleted.setItems(observableTaskList2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadActiveTable();
        loadCompletedTable();

    }

    public void removeActiveTask(Object t1){
        Task task =(Task) t1;
        try {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM active_tasks WHERE task_title = ?");
            preparedStatement.setString(1, task.getTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCompletedTask(Object t1){
        Task task = null;

        if (t1 !=null){
            task =(Task) t1;
        }

        PreparedStatement preparedStatement;
        try {
            preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO completed_tasks (task_title, task_description, completion_date) VALUES (?, ?, ?)");
            preparedStatement.setString(1,task.getTitle());
            preparedStatement.setString(2,task.getDescription());
            preparedStatement.setString(3, task.getDate());

            int update = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
