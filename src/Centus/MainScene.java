package Centus;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.*;



public class MainScene extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        MainScene mainScene = new MainScene();
        loader.setController(mainScene);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


    LoginScene loginScene;
    String userId;
   public MainScene mainScene;
    @FXML
    Button showIdBtn;
    @FXML
    Button loginBtn;
    @FXML
    MenuBar menuBar;
    @FXML
    Menu revenuesMenu, outgoingsMenu, statisticsMenu;
    @FXML
    MenuItem addRevenue;


    ImageView imageView;
    BorderPane bp;

    public MainScene instance = null;

    public MainScene() {
      super();
      synchronized(MainScene.class){
          if(instance != null)throw new UnsupportedOperationException(
                  getClass()+"testing");
          instance = this;
      }

        imageView = new ImageView();
        bp = new BorderPane();


    }




    @FXML
    public void revenuesMenu(ActionEvent e) throws Exception {
       revenuesScene revenuesScene = new revenuesScene();
       Stage stage = new Stage();
       revenuesScene.start(stage);
    }

    @FXML
    public void outgoingsMenu() {


    }
    @FXML
    public void statisticsMenu() {

    }

        @FXML
    public void showId(ActionEvent e){
        userId = loginScene.userID;
        showIdBtn.setText(userId);
        System.out.println(userId);

    }
    @FXML
    public void Login(ActionEvent e) throws Exception {
        loginScene = new LoginScene();
        setLogin(loginScene);
        Stage stage = new Stage();
        loginScene.start(stage);
    }
    public void setLogin(LoginScene ls){

        this.loginScene = ls;
        loginScene.setMainScene(this);
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
    public MainScene getInstance(){
        return instance;
    }

}
