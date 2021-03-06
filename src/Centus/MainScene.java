package Centus;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;


public class MainScene extends Stage {


    public String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isStatus() {
        return status;
    }

    @FXML
    private boolean status;

    @FXML
    Button loginBtn;
    @FXML
    MenuBar menuBar;
    @FXML
    Menu revenuesMenu, outgoingsMenu, statisticsMenu;
    @FXML
    MenuItem addRevenue, statisticsItem, addOutgoing;
    @FXML
   private Label userNameLbl;


    public MainScene instance = null;

    public MainScene(){}

    public MainScene( String userId, boolean status) throws Exception {
        if(instance == null) instance = this;


        setUserId(userId);
        setStatus(status);
        Label userNameLbl = new Label();
        userNameLbl.setText(this.getUserId());


        this.userId = this.getUserId();
        Stage stage = new Stage();
        stage.setTitle("Centus");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
         loader.setController(this);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }




    @FXML
    public void revenuesMenu(ActionEvent e) throws Exception {
        if(status != false) {
            revenuesScene revenuesScene = new revenuesScene(userId);
            revenuesScene.setUserId(userId);
        }else
            {Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Information");
                alert.setHeaderText("You have to be logged in");
                alert.setContentText("Do you want to log in?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    Stage stage = (Stage) menuBar.getScene().getWindow();
                    stage.close();
                    LoginScene ls = new LoginScene();

                    ls.start(stage);

                }
        }
    }

    @FXML
    public void outM(ActionEvent e) throws Exception {
        if(status != false) {

            outgoingsScene outgoingsScene = new outgoingsScene(userId);
            outgoingsScene.setUserId(userId);
        }else
        {Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information");
            alert.setHeaderText("You have to be logged in");
            alert.setContentText("Do you want to log in?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) menuBar.getScene().getWindow();
                stage.close();
                LoginScene ls = new LoginScene();

                ls.start(stage);
            }
        }
    }


    @FXML
    public void statisticsMenu() throws Exception {
        if(status != false) {

            statisticsScene statisticsScene = new statisticsScene(userId);

        }else
        {Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Information");
            alert.setHeaderText("You have to be logged in");
            alert.setContentText("Do you want to log in?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Stage stage = (Stage) menuBar.getScene().getWindow();
                stage.close();
                LoginScene ls = new LoginScene();

                ls.start(stage);

        }
    }}

    @FXML
    public void logOut(ActionEvent e) throws Exception {
        userId = null;
        status = false;
        userNameLbl.setText("Bye!");


    }
    public void setLogin(LoginScene ls){

//        this.loginScene = ls;
//        loginScene.setMainScene(this);
   }

    public void setId(String s) {
        this.userId = s;


    }
    public void printUserid(ActionEvent e){
        System.out.println(userId);
    }


    public MainScene getInstance(){
        return instance;
    }

    public void setUserNameLbl(String s){
        this.userNameLbl.setText(s);
    }
    public void setLoginBtn(boolean status){
        if(status == true)
       this.loginBtn.setText("Logout");
        else this.loginBtn.setText("Login");
    }
    public void setStatus(boolean s){
        this.status = s;
    }

}
