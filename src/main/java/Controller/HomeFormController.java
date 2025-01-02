package Controller;

import DBConnection.DBConnection;
import Model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

    public void btnAddTaskOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/AddTaskForm.fxml"))));
        stage.show();

    }

    public void btnSearchTaskOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/SearchTaskForm.fxml"))));
        stage.show();
    }

    public void updateTaskOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/UpdateTaskForm.fxml"))));
        stage.show();
    }

    public void deleteTaskOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/DeleteTaskForm.fxml"))));
        stage.show();
    }

    ObservableList<Task> observableTaskList1 = FXCollections.observableArrayList();
    private void loadActiveTable(){

        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM active_tasks");
            while (resultSet.next()){
                String id = resultSet.getString("task_id");
                String title = resultSet.getString("task_title");
                String description = resultSet.getString("task_description");
                String date = resultSet.getString("created_date");

                Task task = new Task(id, title, description, date);
                observableTaskList1.add(task);
            }
            System.out.println(observableTaskList1.toString());

            for(Task task:observableTaskList1){
                colId.setCellValueFactory(new PropertyValueFactory<>("id"));
                colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
                colDes.setCellValueFactory(new PropertyValueFactory<>("description"));
                colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

                tblActive.setItems(observableTaskList1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    ObservableList<Task> observableTaskList2 = FXCollections.observableArrayList();
    private void loadCompletedTable(){

        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM completed_tasks");
            while (resultSet.next()){
                String id = resultSet.getString("task_id");
                String title = resultSet.getString("task_title");
                String description = resultSet.getString("task_description");
                String date = resultSet.getString("completion_date");

                Task task = new Task(id, title, description, date);
                observableTaskList2.add(task);
            }
            System.out.println(observableTaskList2.toString());

            for(Task task:observableTaskList2){
                colID2.setCellValueFactory(new PropertyValueFactory<>("id"));
                colTitle2.setCellValueFactory(new PropertyValueFactory<>("title"));
                colDes2.setCellValueFactory(new PropertyValueFactory<>("description"));
                colDate2.setCellValueFactory(new PropertyValueFactory<>("date"));

                tblCompleted.setItems(observableTaskList2);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadActiveTable();
        loadCompletedTable();
    }
}
