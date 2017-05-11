package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    private @FXML Button addInitialNodeBtn;
    private @FXML Button addNodeBtn;
    private @FXML Group groupPaint;
    private @FXML Button addTransicionBtn;
    private Nodo previous=null;
    private Line lineToConect,line=null;
    private Afnd afnd;
    private double orgSceneX,orgSceneY,previousX,previousY;
    private boolean inn,addNodeActivate,addTransicionActivate,addInitialNodeActivate,addFinalNodeActivate= false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.afnd= new Afnd();

        Circle circle= new Circle(0,0,20,Color.LIGHTGRAY);
        circle.setStroke(Color.BLACK);
        this.addNodeBtn.setGraphic(circle);

        Circle circleInitial= new Circle(30,25,20,Color.LIGHTGRAY);
        circleInitial.setStroke(Color.BLACK);
        Polygon poly= new Polygon(new double[]{(double)(circleInitial.getCenterX()-30),(double)(circleInitial.getCenterY()+10),
                (double)(circleInitial.getCenterX()-20),(double)(circleInitial.getCenterY()),(double)(circleInitial.getCenterX()-30),(double)(circleInitial.getCenterY()-10)});
        Pane graficInitialNode= new Pane();
        graficInitialNode.getChildren().addAll(circleInitial,poly);
        this.addInitialNodeBtn.setGraphic(graficInitialNode);

        line=new Line(0,0,35,35);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(3);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.getStrokeDashArray().setAll(5.0, 5.0);
        this.addTransicionBtn.setGraphic(line);

        this.addInitialNodeBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                addTransicionActivate=false;
                addNodeActivate=false;
                circle.setFill(Color.LIGHTGRAY);
                line.setStartY(0);
                line.setStroke(Color.BLACK);
                previous= null;
                if(addInitialNodeActivate){
                    addInitialNodeActivate=false;
                    circleInitial.setFill(Color.LIGHTGRAY);
                } else {
                    addInitialNodeActivate=true;
                    circleInitial.setFill(Color.WHITE);
                }
            }
        });

        this.addNodeBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                addTransicionActivate=false;
                addInitialNodeActivate=false;
                addFinalNodeActivate= false;
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

        this.addTransicionBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addNodeActivate=false;
                addInitialNodeActivate=false;
                addFinalNodeActivate=false;
                circleInitial.setFill(Color.LIGHTGRAY);
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
                if(addNodeActivate && !inn&&!detectCollitionsCircles(temp_circle)) { // falta agregar restricciones
                    groupPaint.getChildren().addAll(temp_circle);
                    addNodeActivate= false;
                    circle.setFill(Color.LIGHTGRAY);
                    event.consume();
                } else if(addInitialNodeActivate &&!inn &&!detectCollitionsCircles(temp_circle)){
                    if(afnd.getEstadoInicial()==null) {
                        temp_circle.setEsInitial(true);
                        afnd.setEstadoInicial(temp_circle);
                        groupPaint.getChildren().addAll(temp_circle, temp_circle.getForInitial());
                        addInitialNodeActivate = false;
                        circleInitial.setFill(Color.LIGHTGRAY);
                        event.consume();
                    } else {
                        System.out.println("Ya existe un nodo inicial, no puede agregar otro!!");
                        addInitialNodeActivate = false;
                        circleInitial.setFill(Color.LIGHTGRAY);
                    }
                } else if(addFinalNodeActivate&&!inn &&!detectCollitionsCircles(temp_circle)){
                    temp_circle.setEsFinal(true);
                    event.consume();
                }
            }
        });


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
                }else if(previous!=null&&addTransicionActivate&&previous!=circle){// aqui sabemos que queremos conectar dos nodos.
                    lineToConect= connect(previous,circle);
                    groupPaint.getChildren().add(lineToConect);
                    addTransicionActivate=false;
                    line.setStartY(0);
                    line.setStroke(Color.BLACK);
                    previous.toFront();
                    circle.toFront();
                }
                event.consume();
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
            if(c.isEsInitial()) {
                c.getForInitial().getPoints().setAll(new Double[]{(double)(c.getCenterX() + offsetX -40),(double)(c.getCenterY() + offsetY +10),
                (double)(c.getCenterX() + offsetX -30),(double)(c.getCenterY() + offsetY ),(double)(c.getCenterX() + offsetX-40), (double)(c.getCenterY() + offsetY-10)});
            }
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
