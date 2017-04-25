package sample;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.util.Duration;

public class Main extends Application implements EventHandler<ActionEvent> {

    private int width,height;
    private Button leftBtn,rightBtn,saveBtn;
    private Button optionMenu;
    private Button addNodeBtn;
    private Button exitBtn;
    private boolean addNodeClicked= false;
    private StackPane root;
    private BorderPane todo;
    private AnchorPane editorRoot;
    private HBox fileRoot;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.width=800;
        this.height=600;
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        this.root = new StackPane();
        this.todo= new BorderPane();
        this.editorRoot = new AnchorPane();
        this.optionMenu= new Button();
        this.optionMenu.setPrefSize(30,30);
        this.optionMenu.setStyle("-fx-background-image: url('/resources/Option.png');" +
                "-fx-backgound-size: 100% 100%;" +
                "-fx-background-repeat: no-repeat;" +
                "-fx-background-size: contain;" +
                "-fx-rotate: 180");
        this.optionMenu.setOnAction(this);
        root.getChildren().addAll(editorRoot,todo);

        //panel top
        GridPane top= new GridPane();
        top.setHgap(10);
        top.setVgap(10);

        //panel top 0

        HBox subTopPane0= new HBox();
        subTopPane0.setAlignment(Pos.CENTER);
        subTopPane0.setPadding(new Insets(5,10,5,10));
        subTopPane0.setSpacing(5);
        subTopPane0.getChildren().addAll(this.optionMenu);

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
        ScrollPane center= new ScrollPane();
        center.setPannable(true);
        center.setPrefSize(500,600);
        center.setContent(new Lienzo());

        //panel right
        Pane right=new Pane();
        right.setPrefSize(200,500);
        right.getChildren().add(new Label(" crear entradas  "));

        //Configuracion del borderPane
        todo.setTop(top);
        todo.setCenter(center);
        todo.setRight(right);
        todo.setPadding(new Insets(10,10,10,10));

        //Configuracion de la pantalla principal
        primaryStage.setTitle("Graph Drawer");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, this.width, this.height));
        primaryStage.getIcons().add(new Image("/resources/icon.png"));
        primaryStage.show();

        //Menu lateral izquierdo despegable

        this.fileRoot = new HBox();
        VBox menu = new VBox();
        menu.setStyle("-fx-background-color: lawngreen;");
        menu.setFillWidth(true);
        Button backBtn = new Button("Options");
        backBtn.setPrefWidth(150);
        backBtn.setStyle("-fx-background-color: green;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-border: none; ");
        backBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                FadeTransition hideFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
                hideFileRootTransition.setFromValue(1.0);
                hideFileRootTransition.setToValue(0.0);

                FadeTransition showEditorRootTransition = new FadeTransition(Duration.millis(500), editorRoot);
                showEditorRootTransition.setFromValue(0.0);
                showEditorRootTransition.setToValue(1.0);

                showEditorRootTransition.play();
                hideFileRootTransition.play();
                root.getChildren().remove(fileRoot);
            }
        });
        // agregar aqui botones del panel lateral
        ImageView addNodeImage= new ImageView(new Image("/resources/add-circle.png"));
        addNodeImage.setPreserveRatio(true);
        addNodeImage.setFitHeight(40);
        addNodeImage.setFitWidth(40);
        this.addNodeBtn= new Button("add node",addNodeImage);
        this.addNodeBtn.setOnAction(this);
        this.addNodeBtn.setPrefWidth(150);
        this.addNodeBtn.setStyle("-fx-text-fill: white;" +
                "-fx-border: none;" +
                "-fx-background-color: lawngreen");

        ImageView exitImage= new ImageView(new Image("/resources/exit.png"));
        exitImage.setPreserveRatio(true);
        exitImage.setFitHeight(40);
        exitImage.setFitWidth(40);
        this.exitBtn= new Button("exit",exitImage);
        this.exitBtn.setOnAction(this);
        this.exitBtn.setPrefWidth(150);
        this.exitBtn.setStyle("-fx-text-fill: white;" +
                "-fx-border: none;" +
                "-fx-background-color: lawngreen;");


        menu.getChildren().addAll(backBtn,this.addNodeBtn, this.exitBtn);
        VBox.setVgrow(this.addNodeBtn, Priority.ALWAYS);
        fileRoot.getChildren().add(menu);

        this.optionMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().add(fileRoot);
                FadeTransition hideEditorRootTransition = new FadeTransition(Duration.millis(500), editorRoot);
                hideEditorRootTransition.setFromValue(1.0);
                hideEditorRootTransition.setToValue(0.0);

                FadeTransition showFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
                showFileRootTransition.setFromValue(0.0);
                showFileRootTransition.setToValue(1.0);
                hideEditorRootTransition.play();
                showFileRootTransition.play();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }


    public void handle(ActionEvent event) {
        FadeTransition hideFileRootTransition = new FadeTransition(Duration.millis(500), fileRoot);
        hideFileRootTransition.setFromValue(1.0);
        hideFileRootTransition.setToValue(0.0);

        FadeTransition showEditorRootTransition = new FadeTransition(Duration.millis(500), editorRoot);
        showEditorRootTransition.setFromValue(0.0);
        showEditorRootTransition.setToValue(1.0);

        showEditorRootTransition.play();
        hideFileRootTransition.play();
        root.getChildren().remove(fileRoot);

        if(event.getSource()==this.exitBtn){
            System.exit(0);
        }
        if(event.getSource()==this.addNodeBtn){
            this.addNodeClicked= true;
            System.out.print("true");
        }
    }

    public boolean isAddNodeClicked() {
        return addNodeClicked;
    }

    public void setAddNodeClicked(boolean addNodeClicked) {
        this.addNodeClicked = addNodeClicked;
    }
}