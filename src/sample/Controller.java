package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    private @FXML TextField readLanguageTextField;
    private @FXML ToggleButton addStartNode; // todo
    private @FXML ToggleButton addNode;
    private @FXML ToggleButton addTransition;
    private @FXML ToggleButton addFinal; // todo
    private @FXML Group groupPaint;

    private Nodo previous=null;
    private Line lineToConect,line=null;
    private Afnd afnd;
    private double orgSceneX,orgSceneY,previousX,previousY;
    private boolean inn,addNodeActivate,addTransicionActivate= false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.afnd= new Afnd();


        Circle circle= new Circle(0,0,20,Color.LIGHTGRAY);
        circle.setStroke(Color.BLACK);
        this.addNode.setGraphic(circle);

        line=new Line(0,0,35,35);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(3);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.getStrokeDashArray().setAll(5.0, 5.0);
        this.addTransition.setGraphic(line);

        this.addNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                addTransicionActivate=false;
                line.setStartY(0);
                line.setStroke(Color.BLACK);
                previous= null;
                if(addNodeActivate){
                    addNodeActivate=false;
                    circle.setFill(Color.LIGHTGRAY);
                } else {
                    addNodeActivate=true;
                    circle.setFill(Color.WHITE);
                }
            }
        });

        this.addTransition.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addNodeActivate=false;
                previous=null;
                circle.setFill(Color.LIGHTGRAY);
                if(addTransicionActivate){
                    addTransicionActivate=false;
                    line.setStartY(0);
                    line.setStroke(Color.BLACK);
                }else{
                    addTransicionActivate=true;
                    line.setStartY(35);
                    line.setStroke(Color.GRAY);

                }
            }
        });

        this.groupPaint.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Nodo temp_circle= createCircle(event.getX(), event.getY(),false,false);
                if( addNodeActivate && !inn&& !detectCollitionsCircles(temp_circle) ) { // falta agregar restricciones
                    groupPaint.getChildren().addAll(temp_circle);
                    addNodeActivate= false;
                    circle.setFill(Color.LIGHTGRAY);
                    event.consume();



                    afnd.addEstado(temp_circle);
                    afnd.printTransiciones();
                }

            }
        });



        /**
         * Reads dynamically from the language text box.
         */
        this.readLanguageTextField.textProperty().addListener((observable, oldValue, newValue) -> {

                //this.alphabetLabel.textProperty().setValue(newValue);

            this.afnd.setAlfabeto(newValue); // Asigna el alfabeto del textbox al AFND.
            System.out.println(this.afnd.getAlfabeto());



            //System.out.println(newValue.trim().toCharArray()); try the language in the console
        });


    }

    private static void invalidDraw(String title, String headerText) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(null);
        alert.showAndWait();
    }



    private Nodo createCircle(double x, double y, boolean esInicial, boolean esFinal) {
        Nodo circle = new Nodo(x, y);
        circle.setStroke(Color.BLACK);
        circle.setCursor(Cursor.HAND);
        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                circle.setFill(Color.WHITE);
                inn=true;
                event.consume();
            }
        });
        circle.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                circle.setFill(Color.LIGHTGRAY);
                inn=false;
                event.consume();
            }
        });

        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(previous==null&&addTransicionActivate){
                    previous=circle;
                }else if(previous!=null&&addTransicionActivate&&previous!=circle){
                    lineToConect= connect(previous,circle);
                    groupPaint.getChildren().add(lineToConect);
                    addTransicionActivate=false;
                    line.setStartY(0);
                    line.setStroke(Color.BLACK);
                    previous.toFront();
                    circle.toFront();
                }
            }
        });

        circle.setOnMousePressed((t) -> {
            System.out.println("pressed");
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            Nodo c = (Nodo) (t.getSource());
            c.toFront();
        });

        circle.setOnMouseDragged((t) -> {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;

                Nodo c = (Nodo) (t.getSource());

                c.setCenterX(c.getCenterX() + offsetX);
                c.setCenterY(c.getCenterY() + offsetY);

                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
        });
        return circle;
    }


    private Line connect(Nodo c1, Nodo c2) {
        Line line = new Line();

        line.startXProperty().bind(c1.centerXProperty());
        line.startYProperty().bind(c1.centerYProperty());

        line.endXProperty().bind(c2.centerXProperty());
        line.endYProperty().bind(c2.centerYProperty());

        line.setStrokeWidth(3);
        line.setStroke(Color.GRAY);
        line.setStrokeLineCap(StrokeLineCap.SQUARE);
        line.getStrokeDashArray().setAll(5.0, 5.0);
        return line;
    }


    private boolean detectCollitionsCircles(Nodo innCircle){
        Nodo temp_circle= null;
        for (Node temp_node: groupPaint.getChildren()) {
            if(temp_node instanceof Nodo){
                temp_circle= ((Nodo)temp_node);

                invalidDraw(
                        "Operación Invalida",
                        "No es posible dibujar el nodo indicado.");

                if(temp_circle!=innCircle&&temp_circle.getBoundsInParent().intersects(innCircle.getBoundsInParent())){

                    return true;

                }


            }

        }
        return false;
    }


    private Image textToImage(String text) {
        Label label = new Label(text);
        label.setMinSize(125, 125);
        label.setMaxSize(125, 125);
        label.setPrefSize(125, 125);
        label.setStyle("-fx-background-color: transparent; -fx-text-fill:black;");
        label.setWrapText(true);
        Scene scene = new Scene(new Group(label));
        WritableImage img = new WritableImage(125, 125) ;
        scene.snapshot(img);
        return img ;
    }



}
