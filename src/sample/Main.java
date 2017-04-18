package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * agregue un comentario!
 */

public class Main extends Application implements EventHandler<ActionEvent> {

    private int width,height;
    private MenuBar menubar;
    private Menu botonp1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.width=800;
        this.height=600;
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        BorderPane root= new BorderPane();

        //creacion menubar
        this.botonp1= new Menu("botonp1");
        this.menubar= new MenuBar();
        this.menubar.setPrefSize(width,25);
        this.menubar.getMenus().add(botonp1);
        this.menubar.setStyle("");
        root.setBottom(menubar);



        //Configuracion de la pantalla principal
        primaryStage.setTitle("Graph Drawer");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, this.width, this.height));
        primaryStage.getIcons().add(new Image("/resources/icon.png"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public void handle(ActionEvent event) {
    }
}