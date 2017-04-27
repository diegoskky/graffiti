package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    private @FXML Button addNodeBtn;
    private @FXML Group groupPaint;

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
        //this.groupPaint

    }
}
