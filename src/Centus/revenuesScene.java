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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class revenuesScene extends Stage implements Initializable {

    static class arrObject{
        int indx;
        String category;
        String data;
        String rev;
        public arrObject(int indx, String category, String data, String rev){
            this.category = category;
            this.data = data;
            this.indx = indx;
            this.rev = rev;
        }

        public int getIndx(){
            return indx;
        }

        @Override
        public String toString() {
            return indx + " "+category + " "+data+ " "+ rev ;
        }
    }


    @FXML
    String userId;
    @FXML
    boolean status;

    @FXML
    Button resetBtn, addBtn, deletebtn, exitBtn;
    @FXML
    DatePicker datePicker;
    @FXML
    TextField itemField;
    @FXML
    ListView<String> listView;
    ObservableList<String> revenuesList = FXCollections.observableArrayList();
    List<arrObject> arrObjList = new ArrayList<arrObject>();
    @FXML
    ComboBox<String> comboBox;
    ObservableList<String> categoryList = FXCollections.observableArrayList(
            "Salary", "Other work", "Sale", "Pension", "Other");

    @FXML
    Label statusLbl;
    int i;

    public revenuesScene(){}
    public revenuesScene(String userId) throws IOException {
        this.userId = userId;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("revenuesScene.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        i = 0;
        stage.setTitle("Revenues");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(categoryList);
        listView.setItems(revenuesList);
    }

    @FXML
    void addNewRevenue(ActionEvent e) throws ClassNotFoundException, SQLException {
        System.out.println(userId);
        String item =  itemField.getText();
        String category = comboBox.getValue();
        String dateString;
        item = item.replace(',','.');


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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydb", "admin", "12345678"
            );
            Statement statement = connection.createStatement();
            var id = statement.executeQuery("SELECT MAX(id) FROM revenues");
            id.next();
            int p = id.getInt(1)+1;
            arrObject arr = new arrObject(p,category,dateString,item);

            arrObjList.add(arr);
            revenuesList.add(arr.toString());

            listView.setItems(revenuesList);


            statement.executeUpdate("INSERT INTO revenues VALUES("+p+",'"+userId+"','"+category +"','"+dateString+"',"+item+")"); //insert testowy
            connection.close();

        }

    }
    @FXML
    public void deleteFromListView(ActionEvent e) throws ClassNotFoundException, SQLException {
        int indx =  listView.getSelectionModel().getSelectedIndex();
        System.out.println(revenuesList);
        revenuesList.remove(indx);
        System.out.println(revenuesList);
        listView.setItems(revenuesList);
        int indexInDb = arrObjList.get(indx).getIndx();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydb", "admin", "12345678"
        );
        Statement stmnt = connection.createStatement();
        stmnt.executeUpdate("DELETE FROM revenues WHERE id="+indexInDb+"");
        connection.close();


    }
    public void setUserId(String userId){
        this.userId = userId;
    }



}
