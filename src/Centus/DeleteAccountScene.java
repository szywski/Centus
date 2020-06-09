package Centus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setHeaderText(null);
                        alert.setContentText("Succesfully deleted user!");
                        alert.showAndWait();

                        Stage stage = (Stage) deleteAccountBtn.getScene().getWindow();
                        stage.close();

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
    public void exitDeleteAccountWindow(ActionEvent e){

        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();

    }


}
