package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class Main extends Application implements EventHandler<ActionEvent> {

    private int width,height;
    private Button leftBtn,rightBtn,optionBtn,saveBtn;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.width=800;
        this.height=600;
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        BorderPane root= new BorderPane();

        //panel top
        GridPane top= new GridPane();
        top.setHgap(10);
        top.setVgap(10);

        //panel top 0
        this.optionBtn= new Button();
        this.optionBtn.setPrefSize(30,30);
        this.optionBtn.setStyle("-fx-background-image: url('/resources/Option.png');" +
                "-fx-backgound-size: 100% 100%;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: contain;");
        this.optionBtn.setOnAction(this);
        HBox subTopPane0= new HBox();
        subTopPane0.setAlignment(Pos.CENTER_LEFT);
        subTopPane0.setPadding(new Insets(5,10,5,10));
        subTopPane0.setSpacing(5);
        subTopPane0.getChildren().addAll(this.optionBtn);

        //panel top 1
        this.leftBtn= new Button();
        this.leftBtn.setPrefSize(30,30);
        this.leftBtn.setStyle("-fx-background-image: url('/resources/left.png');" +
                "-fx-backgound-size: 100% 100%;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: contain;" +
                "-fx-rotate: 180");
        this.leftBtn.setOnAction(this);
        this.rightBtn= new Button();
        this.rightBtn.setPrefSize(30,30);
        this.rightBtn.setStyle("-fx-background-image: url('/resources/right.png');" +
                "-fx-backgound-size: 100% 100%;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: contain;");
        this.rightBtn.setOnAction(this);
        HBox subTopPane1= new HBox();
        subTopPane1.setAlignment(Pos.CENTER_LEFT);
        subTopPane1.setPadding(new Insets(5,10,5,10));
        subTopPane1.setSpacing(5);
        subTopPane1.getChildren().addAll(this.leftBtn,this.rightBtn);

        //panel top 2
        this.saveBtn= new Button();
        this.saveBtn.setPrefSize(30,30);
        this.saveBtn.setStyle("-fx-background-image: url('/resources/save.png');" +
                "-fx-backgound-size: 100% 100%;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: contain;");
        this.saveBtn.setOnAction(this);
        HBox subTopPane2= new HBox();
        subTopPane2.setAlignment(Pos.CENTER_LEFT);
        subTopPane2.setPadding(new Insets(5,10,5,10));
        subTopPane2.setSpacing(5);
        subTopPane2.getChildren().addAll(this.saveBtn);

        top.add(subTopPane0,0,0);
        top.add(subTopPane1,1,0);
        top.add(subTopPane2,2,0);

        //Panel center
        //panel right
        Pane right=new Pane();
        right.setPrefSize(200,500);
        right.getChildren().add(new Label(" crear entradas  "));

        //Configuracion del borderPane
        root.setTop(top);
        root.setCenter(new Lienzo());
        root.setRight(right);
        root.setPadding(new Insets(10,10,10,10));

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