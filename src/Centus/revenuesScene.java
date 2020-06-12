package Centus;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class revenuesScene extends Stage implements Initializable {

    // @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("revenuesScene.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    Button resetBtn, addBtn, deletebtn, exitBtn;
    @FXML
    DatePicker datePicker;
    @FXML
    TextField itemField;
    @FXML
    ListView listView;
    ObservableList<String> revenuesList = FXCollections.observableArrayList();
    @FXML
    ComboBox<String> comboBox;
    ObservableList<String> categoryList = FXCollections.observableArrayList(
            "Salary", "Other work", "Sale", "Pension", "Other");

    @FXML
    Label statusLbl;

    public revenuesScene(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(categoryList);
        listView.setItems(revenuesList);
    }

    @FXML
    void addNewRevenue(ActionEvent e) throws ClassNotFoundException, SQLException {
        Double item =  Double.parseDouble(itemField.getText());
        String category = comboBox.getValue();
        String dateString;


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = datePicker.getValue();
        dateString = formatter.format(date);


        if(        item.toString().isBlank()
                || dateString.isBlank()
                || category.isBlank()) statusLbl.setText("Field missing"); //kiedy nie ma itemu
        else {
            revenuesList.add(category + " " +dateString+ " "+ item.toString()+"\n");
            revenuesList.add(category + " " +dateString+ " "+ item.toString()+"\n");
            listView.setItems(revenuesList);


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydb", "admin", "12345678"
            );
            Statement statement = connection.createStatement();
            statement.executeQuery("INSERT INTO revenues VALUES('ddd','egferfd','1995-12-12',12.4)"); //insert testowy
            connection.close();

        }

    }



}
