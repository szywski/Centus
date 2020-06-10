package Centus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.*;


public class MainScene extends Stage {

    LoginScene loginScene = new LoginScene();

    String userId;

    @FXML
    Button showIdBtn;
    @FXML
    Button loginBtn;






    @FXML
    public void Login(ActionEvent e) throws Exception {

        Stage stage = new Stage();
        loginScene.start(stage);


    }

    public void setId(String s) {
        this.userId = s;


    }

    public void setUserId() throws IOException {
        BufferedReader name = new BufferedReader(new FileReader(new File("currentUser.txt")));
        userId = name.readLine();
        name.close();
        File curUs = new File("currentUser.txt");
        curUs.delete();
    }


}
