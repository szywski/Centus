package Centus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;

public class DeleteAccountScene {
    @FXML
    TextField loginL;
    @FXML
    PasswordField passwordL;
    @FXML
    Label statusL;
    @FXML
    Button deleteAccountBtn;
    @FXML
    Button exitBtn;

    public void deleteAccount(ActionEvent e){
        String loginValue = loginL.getText();
        String passwordValue = passwordL.getText();

        if(loginValue.isBlank() || passwordValue.isBlank()){
            statusL.setText("Login or password missing");
        }else {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/mydb", "admin", "12345678"
                );

                Statement st = connection.createStatement();
                boolean exist = false;

                try {
                    ResultSet rs = st.executeQuery("SELECT * FROM users WHERE userName='" + loginValue + "'");
                    rs.next();
                    if(rs.getString(2).equals(loginValue) && rs.getString(3).equals(passwordValue)){
                        exist = true;
                    }else statusL.setText("Login or password is incorrect");

                }catch (SQLException sqlE){
                    statusL.setText("There is not such account");
                }
                    if(exist){
                    try{
                    st.executeUpdate("DELETE FROM users WHERE userName = '"+loginValue+"'");
                    statusL.setText("Account deleted");
                }catch (SQLException sqlE ){
                        sqlE.printStackTrace();
                    statusL.setText("Cannot delete account due to database malfunction");
                    }
                }else statusL.setText("There is no such account");

                connection.close();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        }

    }


}
