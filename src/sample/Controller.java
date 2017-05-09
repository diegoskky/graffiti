package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    private Afnd afnd;
    private @FXML Button addNodeBtn;
    private @FXML Group groupPaint;
    private @FXML Button addTransicionBtn;
    private Circle previous=null;
    private Line lineToConect,line=null;
    private double orgSceneX,orgSceneY,previousX,previousY;
    private boolean inn,addNodeActivate,addTransicionActivate= false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        afnd = new Afnd();

        Circle circle= new Circle(0,0,20,Color.LIGHTGRAY);
        circle.setStroke(Color.BLACK);
        this.addNodeBtn.setGraphic(circle);
        line=new Line(0,0,35,35);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(3);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.getStrokeDashArray().setAll(5.0, 5.0);
        this.addTransicionBtn.setGraphic(line);

        this.addNodeBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
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

        this.addTransicionBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
                Circle temp_circle= createCircle(event.getX(), event.getY());
                if(addNodeActivate && !inn&&!detectCollitionsCircles(temp_circle)) { // falta agregar restricciones
                    groupPaint.getChildren().add(temp_circle);
                    addNodeActivate= false;
                    circle.setFill(Color.LIGHTGRAY);
                    event.consume();
                }
            }
        });


    }

    private Circle createCircle(double x, double y) {


        Circle circle = new Circle(x, y, 30,Color.LIGHTGRAY);

        Nodo node = new Nodo(); // todo
        this.afnd.addEstado(node); // todo

        this.afnd.matrizTransiciones();

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
            Circle c = (Circle) (t.getSource());
            c.toFront();
        });

        circle.setOnMouseDragged((t) -> {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;

                Circle c = (Circle) (t.getSource());

                c.setCenterX(c.getCenterX() + offsetX);
                c.setCenterY(c.getCenterY() + offsetY);

                orgSceneX = t.getSceneX();
                orgSceneY = t.getSceneY();
        });
        return circle;
    }


    private Line connect(Circle c1, Circle c2) {
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


    private boolean detectCollitionsCircles(Circle innCircle){
        Circle temp_circle= null;
        for (Node temp_node: groupPaint.getChildren()) {
            if(temp_node instanceof Circle){
                temp_circle= ((Circle)temp_node);
                if(temp_circle!=innCircle&&temp_circle.getBoundsInParent().intersects(innCircle.getBoundsInParent())){
                    return true;
                }
            }
        }
        return false;
    }
}
