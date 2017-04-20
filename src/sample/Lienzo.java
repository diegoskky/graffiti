package sample;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;

/**
 * Created by Freyja on 20-04-2017.
 * clase para dibujar, aqui se mostraran los automatas
 */
public class Lienzo extends ScrollPane {
    public Lienzo(){
        this.setPrefSize(800,600);
        this.setStyle("-fx-border-color: black;");


    }
}
