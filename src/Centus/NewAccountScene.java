package Centus;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.*;


public class NewAccountScene {

    @FXML
    TextField loginL;
    @FXML
    PasswordField passwordL;
    @FXML
    PasswordField repeatPasswordL;
    @FXML
    Label statusL;
    @FXML
    Button addBtn;
    @FXML
    Button exitBtn;

    public void addAccount(ActionEvent e) {

        String loginValue = loginL.getText();
        String passValue = passwordL.getText();
        String reppassValue = repeatPasswordL.getText();

        if (loginValue.isBlank() || passValue.isBlank() || reppassValue.isBlank()) { //jesli cos jest puste to finitocontitio
            statusL.setText("Empty space. Cannot add user");
        } else if (!reppassValue.equals(passValue)) { //jesli hasla sa rozne
            statusL.setText("Passwords are not same. Cannot add user");
        } else {

            try {//laczenie z baza danych
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/mydb", "admin", "12345678"
                );

                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM users");
                boolean exist = false;
                int lastInt = 0;
                while (rs.next()) {
                    if (rs.getString(2).equals(loginValue)) { //sprawdzenie czy jest juz taki uzytkownik
                        statusL.setText("This login is in use. Choose another");
                        exist = true;
                        break;

                    }
                    lastInt = rs.getInt(1);
                }
                if (!exist) { //jak nie ma takiego uzytkownika to jazda i dodawanie
                    lastInt += 1; //ustawienie id na 1 wieksze niz ostatnie

                    try {
                        st.executeUpdate("INSERT INTO users VALUES(" + lastInt + ",'"
                                + loginValue + "','" + passValue + "' )");
                        statusL.setText("User added succefully");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Succesfully added user!");
                        alert.showAndWait();

                        Stage stage = (Stage) addBtn.getScene().getWindow();
                        stage.close();

                    } catch (SQLException sqlE) {
                        sqlE.printStackTrace();
                        statusL.setText("Cannot add user due to database malfunction\nTry again");

                    }
                }


                connection.close();
            } catch (ClassNotFoundException | SQLException classNotFoundException) {
                classNotFoundException.printStackTrace();
                statusL.setText("Cannot add user due to database malfunction");

            }

        }

    }

    public void exitNewAccountWindow(ActionEvent e) {

        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();

    }
}

