package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
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
    private @FXML Button addNodeBtn;
    private @FXML Group groupPaint;
    private double orgSceneX,orgSceneY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.addNodeBtn.setGraphic(new Circle(10,Color.BLACK));
        this.addNodeBtn.setOnDragDetected(new EventHandler<MouseEvent>(){
            public void handle(MouseEvent event){
                System.out.println("drag Detected");
                Dragboard db = addNodeBtn.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(addNodeBtn.getText());
                db.setContent(content);
                event.consume();
            }
        });
        this.groupPaint.setVisible(true);
        this.groupPaint.setStyle("-fx-background-color: black");

        this.groupPaint.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("drag Entered");
            }
        });
        this.groupPaint.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("drag over");
            }
        });

        this.groupPaint.getChildren().add(this.createCircle(100,10,20));


    }
    private Circle createCircle(double x, double y, double radio) {
        Circle circle = new Circle(x, y, radio,Color.GRAY);
        circle.setStroke(Color.BLACK);
        circle.setCursor(Cursor.HAND);

        circle.setOnMousePressed((t) -> {
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

        line.setStrokeWidth(1);
        line.setStrokeLineCap(StrokeLineCap.BUTT);
        line.getStrokeDashArray().setAll(1.0, 4.0);

        return line;
    }
}
