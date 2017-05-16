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
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
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
    private @FXML TreeView treeView;

    private Nodo previous=null;
    private Line lineToConect,line=null;
    private Afnd afnd;
    private double orgSceneX,orgSceneY,previousX,previousY;
    private boolean inn,addNodeActivate,addTransicionActivate,addInitialNodeActivate,addFinalNodeActivate=false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.afnd= new Afnd();

        Circle c= createCircle(100,100,false,false);
        c.setFill(new ImagePattern(textToImage("q0","lightgray")));

        this.groupPaint.getChildren().add(c);

        Circle circle= new Circle(0,0,10,Color.LIGHTGRAY);
        circle.setStroke(Color.BLACK);
        this.addNode.setGraphic(circle);

        Circle circleInitial= new Circle(15,10,10,Color.LIGHTGRAY);
        circleInitial.setStroke(Color.BLACK);
        Polygon poly= new Polygon(new double[]{(double)(circleInitial.getCenterX()-15),(double)(circleInitial.getCenterY()+5),
                (double)(circleInitial.getCenterX()-10),(double)(circleInitial.getCenterY()),(double)(circleInitial.getCenterX()-15),(double)(circleInitial.getCenterY()-5)});
        Pane graficInitialNode= new Pane();
        graficInitialNode.getChildren().addAll(circleInitial,poly);
        this.addStartNode.setGraphic(graficInitialNode);

        Circle circleFinal= new Circle(0,0,10,Color.LIGHTGRAY);
        circleFinal.setStroke(Color.BLACK);
        circleFinal.setStrokeWidth(3);
        this.addFinal.setGraphic(circleFinal);

        line=new Line(0,0,15,15);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.getStrokeDashArray().setAll(5.0, 5.0);
        this.addTransition.setGraphic(line);

        this.addStartNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                addNodeActivate=false;
                addNode.setSelected(false);
                circle.setFill(Color.LIGHTGRAY);

                addFinal.setSelected(false);
                addFinalNodeActivate=false;
                circleFinal.setFill(Color.LIGHTGRAY);

                addTransition.setSelected(false);
                addTransicionActivate=false;
                line.setStartY(0);
                line.setStroke(Color.BLACK);
                previous= null;
                if(addInitialNodeActivate){
                    addStartNode.setSelected(false);
                    addInitialNodeActivate=false;
                    circleInitial.setFill(Color.LIGHTGRAY);
                } else {
                    addStartNode.setSelected(true);
                    addInitialNodeActivate=true;
                    circleInitial.setFill(Color.WHITE);
                }
            }
        });

        this.addNode.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                addInitialNodeActivate=false;
                addStartNode.setSelected(false);
                circleInitial.setFill(Color.LIGHTGRAY);

                addFinal.setSelected(false);
                addFinalNodeActivate=false;
                circleFinal.setFill(Color.LIGHTGRAY);

                addTransicionActivate=false;
                addTransition.setSelected(false);
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

        this.addFinal.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addInitialNodeActivate=false;
                addStartNode.setSelected(false);
                circleInitial.setFill(Color.LIGHTGRAY);

                addNode.setSelected(false);
                addNodeActivate=false;
                circle.setFill(Color.LIGHTGRAY);

                addTransicionActivate=false;
                addTransition.setSelected(false);
                line.setStartY(0);
                line.setStroke(Color.BLACK);
                previous= null;
                if(addFinalNodeActivate){
                    addFinalNodeActivate=false;
                    circleFinal.setFill(Color.LIGHTGRAY);
                }else{
                    addFinalNodeActivate=true;
                    circleFinal.setFill(Color.WHITE);
                }
            }
        });

        this.addTransition.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addInitialNodeActivate=false;
                circleInitial.setFill(Color.LIGHTGRAY);
                addStartNode.setSelected(false);

                addNode.setSelected(false);
                addNodeActivate=false;
                circle.setFill(Color.LIGHTGRAY);

                addFinalNodeActivate=false;
                addFinal.setSelected(false);
                circleFinal.setFill(Color.LIGHTGRAY);
                if(addTransicionActivate){
                    addTransicionActivate=false;
                    line.setStartY(0);
                    line.setStroke(Color.BLACK);
                }else{
                    addTransicionActivate=true;
                    line.setStartY(15);
                    line.setStroke(Color.GRAY);

                }
            }
        });

        this.groupPaint.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Nodo temp_circle= createCircle(event.getX(), event.getY(),false,false);
                String input= "";
                if(addNodeActivate && !inn&&!detectCollitionsCircles(temp_circle)) { // falta agregar restricciones
                    //agregar alert
                    temp_circle.setFill(new ImagePattern(textToImage(input,"lightgray")));
                    groupPaint.getChildren().addAll(temp_circle);
                    addNodeActivate= false;
                    addNode.setSelected(false);
                    circle.setFill(Color.LIGHTGRAY);
                    event.consume();
                } else if(addInitialNodeActivate &&!inn &&!detectCollitionsCircles(temp_circle)){
                    if(afnd.getEstadoInicial()==null) {
                        //agregar alert
                        temp_circle.setFill(new ImagePattern(textToImage(input,"lightgray")));
                        temp_circle.setEsInitial(true);
                        afnd.setEstadoInicial(temp_circle);
                        groupPaint.getChildren().addAll(temp_circle, temp_circle.getForInitial());
                        addInitialNodeActivate = false;
                        addStartNode.setSelected(false);
                        circleInitial.setFill(Color.LIGHTGRAY);
                        event.consume();
                    } else {
                        genericAlert("Accion invalida","Ya existe un nodo incial",null, Alert.AlertType.WARNING);
                        addInitialNodeActivate = false;
                        addStartNode.setSelected(false);
                        circleInitial.setFill(Color.LIGHTGRAY);
                    }
                } else if(addFinalNodeActivate&&!inn &&!detectCollitionsCircles(temp_circle)){
                    temp_circle.setEsFinal(true);
                    temp_circle.setStrokeWidth(4);
                    addFinal.setSelected(false);
                    addFinalNodeActivate=false;
                    circleFinal.setFill(Color.LIGHTGRAY);
                    temp_circle.setFill(new ImagePattern(textToImage(input,"lightgray")));
                    groupPaint.getChildren().add(temp_circle);
                    event.consume();
                }
            }
        });



        /**
         * Reads dynamically from the language text box.
         */
        this.readLanguageTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            //this.alphabetLabel.textProperty().setValue(newValue);

            this.afnd.setAlfabeto(newValue); // Asigna el alfabeto del textbox al AFND.

            //System.out.println(this.afnd.getAlfabeto());


            //System.out.println(newValue.trim().toCharArray()); try the language in the console
        });


    }

    private void invalidDraw() {//borrar y ocupar el generico
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Operación Invalida");
        alert.setHeaderText("No es posible dibujar el nodo indicado.");
        alert.setContentText(null);
        alert.showAndWait();
    }

    /**
     * Alerta para el usuario (retroalimentacion del programa)
     * @param title
     * @param headerText
     * @param contentText
     * @param alertType
     */
    private void genericAlert(String title, String headerText, String contentText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    /**
     *
     * @param x
     * @param y
     * @param esInicial
     * @param esFinal
     * @return
     */
    private Nodo createCircle(double x, double y, boolean esInicial, boolean esFinal) {
        Nodo circle = new Nodo(x, y);
        circle.setStroke(Color.BLACK);
        circle.setCursor(Cursor.HAND);
        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                circle.setFill(new ImagePattern(textToImage(circle.getEstado(),"white")));
                inn=true;
                event.consume();
            }
        });
        circle.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                circle.setFill(new ImagePattern(textToImage(circle.getEstado(),"lightgray")));
                inn=false;
                event.consume();
            }
        });

        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {//coneccion entre ambos nodos
                if(previous==null&&addTransicionActivate){
                    previous=circle;
                    event.consume();
                }else if(previous!=null&&addTransicionActivate&&previous!=circle){
                    lineToConect= connect(previous,circle);
                    groupPaint.getChildren().add(lineToConect);
                    addTransicionActivate=false;
                    addTransition.setSelected(false);
                    line.setStartY(0);
                    line.setStroke(Color.BLACK);
                    previous.toFront();
                    circle.toFront();
                    previous=null;
                    event.consume();
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
            if(c.isEsInitial()) {
                c.getForInitial().getPoints().setAll(new Double[]{(double)(c.getCenterX() + offsetX -30),(double)(c.getCenterY() + offsetY +10),
                        (double)(c.getCenterX() + offsetX -20),(double)(c.getCenterY() + offsetY ),(double)(c.getCenterX() + offsetX-30), (double)(c.getCenterY() + offsetY-10)});
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


    private Image textToImage(String text, String color) {
        Label label = new Label(" "+text);
        label.setMinSize(30, 30);
        label.setMaxSize(30, 30);
        label.setPrefSize(30, 30);
        label.setStyle("-fx-background-color: "+color+"; -fx-text-fill:black;");
        label.setWrapText(true);
        Scene scene = new Scene(new Group(label));
        WritableImage img = new WritableImage(25, 30) ;
        scene.snapshot(img);
        return img ;
    }



}
