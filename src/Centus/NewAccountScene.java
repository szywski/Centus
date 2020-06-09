package Centus;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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

        if (loginValue.isBlank() || passValue.isBlank() || reppassValue.isBlank()) {
            statusL.setText("Empty space. Cannot add user");
        } else if (!reppassValue.equals(passValue)) {
            statusL.setText("Passwords are not same. Cannot add user");
        } else {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/mydb", "admin", "12345678"
                );

                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM users");
                boolean exist = false;
                int lastInt = 0;
                while (rs.next()) {
                    if (rs.getString(2).equals(loginValue)) {
                        statusL.setText("This login is in use. Choose another");
                        exist = true;
                        break;

                    }
                    lastInt = rs.getInt(1);
                }
                if (!exist) {
                    lastInt += 1;

                    try {
                        st.executeUpdate("INSERT INTO users VALUES(" + lastInt + ",'"
                                + loginValue + "','" + passValue + "' )");
                        statusL.setText("User added succefully");
                    } catch (SQLException sqlE) {
                        sqlE.printStackTrace();
                        statusL.setText("Cannot add user due to database malfunction");

                    }
                }


                connection.close();
            } catch (ClassNotFoundException | SQLException classNotFoundException) {
                classNotFoundException.printStackTrace();


            }

        }

    }
}

