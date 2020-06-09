package Centus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginScene {
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

    public void onBtnClick(ActionEvent e) {
        String p = new String();
        String userName = loginTF.getText();
        String password = passwordPF.getText();
        if (userName.equals(p) || password.equals(p))
            statusLbl.setText("Login or password missing");
        else {
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
                        Stage primaryStage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
                        Scene scene = new Scene(root);
                        primaryStage.setScene(scene);
                        primaryStage.show();


                        break;
                    } else statusLbl.setText("Acces denied. Login or password is incorrect");
                }
                connection.close();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                statusLbl.setText("Failed to connect to databse");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    public void deleteAccountBtn(ActionEvent e) throws IOException {

        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DeleteAccountScene.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public void exitBtn(ActionEvent e) {
        System.exit(0);
    }

}
