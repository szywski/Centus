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
        String item =  itemField.getText();
        String category = comboBox.getValue();
        String dateString;
        item = item.replace(',','.');
        System.out.println(item);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(datePicker.getValue() == null) dateString = "";
        else {
            LocalDate date = datePicker.getValue();
            dateString = formatter.format(date);
        }

        if(        item.isBlank()
                || dateString.isBlank()
                || category.isBlank()) {
            statusLbl.setText("Field missing"); //kiedy jakies pole jest puste albo wszystkie albo dwa
        }
        else {
            revenuesList.add(category + " " +dateString+ " "+ item.toString()+"\n");
            listView.setItems(revenuesList);


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydb", "admin", "12345678"
            );
            Statement statement = connection.createStatement();
            //userName jest ustawiony jakis tam bo nie wiem jak go przekazać z loginu to mainScena
            statement.executeUpdate("INSERT INTO revenues VALUES('ddd','"+category +"','"+dateString+"',"+item+")"); //insert testowy
            connection.close();

        }

    }



}
