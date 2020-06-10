package Centus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

import static javafx.application.Application.launch;

public class LoginScene extends Stage {

    // @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    String userID;


    @FXML
    Button SignInBtn;
    @FXML
    Button exitBtn;
    @FXML
    Button signUpBtn;
    @FXML
    Button deleteAccountBtn;
    @FXML
    TextField loginTF;
    @FXML
    PasswordField passwordPF;
    @FXML
    Label statusLbl;
    @FXML
    AnchorPane loginAnchorPane;


    @FXML
    public void onSignUpBtnClick(ActionEvent e) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("NewAccountScene.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    public String onBtnClick(ActionEvent e) {
        String p = new String();
        String userName = loginTF.getText();
        String password = passwordPF.getText();
        if (userName.equals(p) || password.equals(p)) {
            statusLbl.setText("Login or password missing");
            return null;
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/mydb", "admin", "12345678"
                );

                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM users");
                while (rs.next()) {
                    if (rs.getString(2).equals(userName) && rs.getString(3).equals(password)) {

                        statusLbl.setText("Acces granted");
                        userID = userName;

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Succesfull log in!");
                        alert.showAndWait();

                        Stage stage = (Stage) signUpBtn.getScene().getWindow();
// to jest polepione w tym miejscu. Uznałem że skoro mam problem z przkazaniem wartości userID do klasy MainScene
                        //to sobie zrobię plik a w drugiej klasie go odczytam ale toe jest gowno
                        FileWriter user = new FileWriter("currentUser.txt");
                        BufferedWriter curUser = new BufferedWriter(user);
                        curUser.write(userName);
                        curUser.close();

// w tym miejscu trzeba przesłać dane do MainScene. Status akcji logowania: udane lub nie
                        // i nazwę użytkownika


                        stage.close();


                    } else {
                        statusLbl.setText("Acces denied. Login or password is incorrect");

                    }
                }
                connection.close();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                statusLbl.setText("Failed to connect to databse");
                return null;


            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return null;
        }
    }

    @FXML
    public void deleteAccountBtn(ActionEvent e) throws IOException {

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DeleteAccountScene.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    public void exitBtn(ActionEvent e) {
        System.exit(0);
    }
}

