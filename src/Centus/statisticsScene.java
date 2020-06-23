package Centus;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;

public class statisticsScene extends Stage {

    static class seriesObj{
        String date;
        Number number;

        public seriesObj(String date,Number number){
            this.date = date;
            this.number = number;
        }
        public String getDate(){
            return date;
        }
        public Number getNumber(){
            return number;
        }
        public int getMonth(){
            java.sql.Date dat = java.sql.Date.valueOf(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dat);
            return cal.get(Calendar.MONTH)+1;

        }

        @Override
        public String toString() {
            return date +" "+number+"\n";
        }
    }



    @FXML
    Button exitBtn, revenuesBtn, outgoingsBtn;
    @FXML
    ChoiceBox choiceBox;
    ObservableList<String> choiceList = FXCollections.observableArrayList("Month","Year");

    @FXML LineChart<String, Number> chart;



    Number seriesValue;
    String userId;


    public statisticsScene(String userId) throws IOException, ClassNotFoundException {
        this.userId = userId;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("statisticsScene.fxml"));
        loader.setController(this);
        Stage stage = new Stage();
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Statistics");
        stage.show();

        try {
            getRevenuesAll();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Failed to connect  to to database!");
            alert.showAndWait();
        }
        choiceBox.getItems().add("Year");
        choiceBox.getItems().add("Current month");





    }

    @FXML
    public void exitBtn(ActionEvent e){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
    @FXML
    public  void revenuesBtn(ActionEvent e) throws ClassNotFoundException, SQLException, ParseException {
        chart.getData().clear();
       XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        List<seriesObj> list = new ArrayList<seriesObj>();
        List<seriesObj> arrayPoint = new ArrayList<seriesObj>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydb", "admin", "12345678"
        );
        Statement statement = connection.createStatement();
        var stmnt = statement.executeQuery("SELECT rev_date, rev FROM revenues WHERE userName = '"+userId+"' ORDER BY rev_date ASC");

        while(stmnt.next()){

            Date data = stmnt.getDate(1);
            Number db = stmnt.getDouble(2);
            String dt = data.toString();
            list.add(new seriesObj(dt,db));


        }


        if(choiceBox.getSelectionModel().getSelectedItem() == "Year") {
            while(stmnt.next()){

                Date data = stmnt.getDate(1);
                Number db = stmnt.getDouble(2);

                String dt = data.toString();
                list.add(new seriesObj(dt,db));


            }

            connection.close();

            seriesObj tmp = list.get(0);
            Number number = 0;

            for (seriesObj a : list) {

                if (tmp.getMonth() == a.getMonth()) {
                    number = number.floatValue() + tmp.getNumber().floatValue();
                } else {
                    arrayPoint.add(new seriesObj(String.valueOf(tmp.getMonth()), number));
                    tmp = a;
                    number = tmp.getNumber().floatValue();
                }
                if (list.get(list.size() - 1).equals(a)) {
                    arrayPoint.add(new seriesObj(String.valueOf(tmp.getMonth()), number));
                }

            }

            seriesValue = 0;
            for (seriesObj a : arrayPoint) {
                seriesValue = seriesValue.floatValue() + a.getNumber().floatValue();
                series.getData().add(new XYChart.Data<String, Number>(a.getDate(), seriesValue));
            }

        }else {

          Calendar cal =   Calendar.getInstance();
            LocalDate curr = LocalDate.now();
          int currMth = cal.get(Calendar.MONTH)+1;

            stmnt = statement.executeQuery("SELECT rev_date, rev FROM revenues WHERE userName = '"+userId+"' ORDER BY rev_date ASC");
            while(stmnt.next()){

                Date data = stmnt.getDate(1);
                Number db = stmnt.getDouble(2);
                String dt = data.toString();
                list.add(new seriesObj(dt,db));

            }

            connection.close();



            seriesObj tmp = list.get(0);
            Number number = 0;

            for (seriesObj a : list) {

                if (tmp.getDate() == a.getDate() &&  tmp.getMonth() == currMth) {
                    number = number.floatValue() + tmp.getNumber().floatValue();
                } else if(tmp.getDate() != a.getDate() && tmp.getMonth() == currMth) {
                    arrayPoint.add(new seriesObj(tmp.getDate(), number));
                    tmp = a;
                    number = tmp.getNumber().floatValue();
                }


            }

            seriesValue = 0;
            for (seriesObj a : arrayPoint) {
                seriesValue = seriesValue.floatValue() + a.getNumber().floatValue();
                series.getData().add(new XYChart.Data<String, Number>(a.getDate(), seriesValue));
            }



        }
        chart.setCreateSymbols(false);
        chart.getData().add(series);

    }




    @FXML
    public void outgoingsBtn(ActionEvent e) throws SQLException, ClassNotFoundException {
        chart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        List<seriesObj> list = new ArrayList<seriesObj>();
        List<seriesObj> arrayPoint = new ArrayList<seriesObj>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydb", "admin", "12345678"
        );
        Statement statement = connection.createStatement();
        var stmnt = statement.executeQuery("SELECT rev_date, rev FROM outgoings WHERE userName = '"+userId+"' ORDER BY rev_date ASC");

        while(stmnt.next()){

            Date data = stmnt.getDate(1);
            Number db = stmnt.getDouble(2);
            String dt = data.toString();
            list.add(new seriesObj(dt,db));


        }


        if(choiceBox.getSelectionModel().getSelectedItem() == "Year") {
            while(stmnt.next()){

                Date data = stmnt.getDate(1);
                Number db = stmnt.getDouble(2);

                String dt = data.toString();
                list.add(new seriesObj(dt,db));


            }

            connection.close();

            seriesObj tmp = list.get(0);
            Number number = 0;

            for (seriesObj a : list) {

                if (tmp.getMonth() == a.getMonth()) {
                    number = number.floatValue() + tmp.getNumber().floatValue();
                } else {
                    arrayPoint.add(new seriesObj(String.valueOf(tmp.getMonth()), number));
                    tmp = a;
                    number = tmp.getNumber().floatValue();
                }
                if (list.get(list.size() - 1).equals(a)) {
                    arrayPoint.add(new seriesObj(String.valueOf(tmp.getMonth()), number));
                }

            }

            seriesValue = 0;
            for (seriesObj a : arrayPoint) {
                seriesValue = seriesValue.floatValue() + a.getNumber().floatValue();
                series.getData().add(new XYChart.Data<String, Number>(a.getDate(), seriesValue));
            }

        }else {

            Calendar cal =   Calendar.getInstance();
            LocalDate curr = LocalDate.now();
            int currMth = cal.get(Calendar.MONTH)+1;

            stmnt = statement.executeQuery("SELECT rev_date, rev FROM outgoings WHERE userName = '"+userId+"' ORDER BY rev_date ASC");
            while(stmnt.next()){

                Date data = stmnt.getDate(1);
                Number db = stmnt.getDouble(2);
                String dt = data.toString();
                list.add(new seriesObj(dt,db));

            }

            connection.close();



            seriesObj tmp = list.get(0);
            Number number = 0;

            for (seriesObj a : list) {

                if (tmp.getDate() == a.getDate() &&  tmp.getMonth() == currMth) {
                    number = number.floatValue() + tmp.getNumber().floatValue();
                } else if(tmp.getDate() != a.getDate() && tmp.getMonth() == currMth) {
                    arrayPoint.add(new seriesObj(tmp.getDate(), number));
                    tmp = a;
                    number = tmp.getNumber().floatValue();
                }


            }

            seriesValue = 0;
            for (seriesObj a : arrayPoint) {
                seriesValue = seriesValue.floatValue() + a.getNumber().floatValue();
                series.getData().add(new XYChart.Data<String, Number>(a.getDate(), seriesValue));
            }



        }
        chart.setCreateSymbols(false);
        chart.getData().add(series);

    }

    private void getRevenuesAll() throws SQLException, ClassNotFoundException {
        List<seriesObj> list = new ArrayList<seriesObj>();
        List<seriesObj> arrayPoint = new ArrayList<seriesObj>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydb", "admin", "12345678"
        );
        Statement statement = connection.createStatement();
        var stmnt = statement.executeQuery("SELECT rev_date, rev FROM revenues WHERE userName = '"+userId+"' ORDER BY rev_date ASC");
        while(stmnt.next()){

            Date data = stmnt.getDate(1);
            Number db = stmnt.getDouble(2);
            String dt = data.toString();
            list.add(new seriesObj(dt,db));

        }
        connection.close();

        seriesValue = 0;
        for(seriesObj a:list){
            seriesValue = seriesValue.floatValue() + a.getNumber().floatValue();
        }

    }

}




