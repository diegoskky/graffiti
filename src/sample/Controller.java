package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.file.Files;
import java.time.Duration;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.awt.SystemColor.window;

public class Controller implements Initializable{
    private @FXML TextField readLanguageTextField;
    private @FXML TextField inWordTF;
    private @FXML Button checkWordBtn;
    private @FXML ToggleButton addStartNode;
    private @FXML ToggleButton addNode;
    private @FXML ToggleButton addTransition;
    private @FXML ToggleButton addFinal;
    private @FXML Group groupPaint;
    private @FXML ListView<String> listView;
    private @FXML Button integrityButton;
    private @FXML Label listViewLabel;
    private @FXML VBox panelDeTransiciones;
    private @FXML Label statusBar;
    private @FXML Button leftBtn;
    private @FXML Button rightBtn;
    private @FXML Button openBtn;
    private @FXML Button guardarBtn;
    private @FXML VBox lateralBox;

    private Circle circleInitial,circleN,circleFinal;
    private Nodo previous=null;
    private Line lineToConect,line=null;
    private Afnd afnd;
    private double orgSceneX,orgSceneY,previousX,previousY;
    private boolean inn,addNodeActivate,addTransicionActivate,addInitialNodeActivate,addFinalNodeActivate=false;


    private ObservableList<String> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.afnd= new Afnd();
        updateTransitionMatrix();
        String statusBarDefault = statusBar.getText();


        circleN= new Circle(0,0,20,Color.LIGHTGRAY);
        circleN.setStroke(Color.BLACK);
        this.addNode.setGraphic(circleN);

        circleInitial= new Circle(32,35,20,Color.LIGHTGRAY);
        circleInitial.setStroke(Color.BLACK);
        Polygon poly= new Polygon(new double[]{(double)(circleInitial.getCenterX()-30),(double)(circleInitial.getCenterY()+10),
                (double)(circleInitial.getCenterX()-20),(double)(circleInitial.getCenterY()),(double)(circleInitial.getCenterX()-30),(double)(circleInitial.getCenterY()-10)});
        Pane graficInitialNode= new Pane();
        graficInitialNode.getChildren().addAll(circleInitial,poly);
        this.addStartNode.setGraphic(graficInitialNode);

        circleFinal= new Circle(0,0,20,Color.LIGHTGRAY);
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
                circleN.setFill(Color.LIGHTGRAY);

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
        //comentario todo mostrar autohide poppy cuando vas a dibujar un nodo encima de otro, o una transicion con elementos entre medio
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
                    circleN.setFill(Color.LIGHTGRAY);
                } else {
                    addNodeActivate=true;
                    circleN.setFill(Color.WHITE);
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
                circleN.setFill(Color.LIGHTGRAY);

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
                circleN.setFill(Color.LIGHTGRAY);

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



        /**
         * Listener all adds of software
         */
        this.groupPaint.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Nodo temp_circle= createCircle(event.getX(), event.getY(),false,false);
                String input = ""; // ignore me.

                if(addNodeActivate && !inn&&!detectCollitionsCircles(temp_circle)) { // falta agregar restricciones
                    input= genericAlertInput("Ingrese nombre del nodo", null, "Nodo: ");
                    addNodeActivate = false;
                    addNode.setSelected(false);
                    circleN.setFill(Color.LIGHTGRAY);
                    if (input != null&&!afnd.existeNodo(input)) {
                        temp_circle.setEstado(input);
                        temp_circle.setFill(new ImagePattern(textToImage(input, "lightgray")));
                        afnd.addEstado(temp_circle); // adds a node to the AFND.

                        updateTransitionMatrix(); // actualiza la matriz de estados

                        groupPaint.getChildren().addAll(temp_circle);

                    }else if(input != null&&afnd.existeNodo(input)){
                        autohideAlert("Ya existe un nodo con el nombre: "+input,2000);
                    }
                } else if(addInitialNodeActivate &&!inn &&!detectCollitionsCircles(temp_circle)){
                    if(afnd.getEstadoInicial()==null) {
                        input= genericAlertInput("Ingrese nombre del nodo", null, "Nodo: ");
                        addInitialNodeActivate = false;
                        addStartNode.setSelected(false);
                        circleInitial.setFill(Color.LIGHTGRAY);

                        if (input != null&&!afnd.existeNodo(input)) {
                            temp_circle.setEstado(input);
                            temp_circle.setFill(new ImagePattern(textToImage(input, "lightgray")));
                            temp_circle.setEsInitial(true);
                            afnd.setEstadoInicial(temp_circle); // Sets the Automata initial state.

                            updateTransitionMatrix(); // Actualiza la matriz de estados

                            groupPaint.getChildren().addAll(temp_circle, temp_circle.getForInitial());
                        }else if(input != null&&afnd.existeNodo(input)){
                            autohideAlert("Ya existe un nodo con el nombre: "+input,2000);
                        }
                        event.consume();
                    } else {
                        genericAlert("Acción inválida","Ya existe un nodo inicial",null, Alert.AlertType.WARNING);
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
                    input= genericAlertInput("Ingrese nombre del nodo final", null, "Nodo: ");
                    if (input != null&&!afnd.existeNodo(input)) {
                        temp_circle.setEstado(input);
                        temp_circle.setFill(new ImagePattern(textToImage(input, "lightgray")));
                        groupPaint.getChildren().add(temp_circle);

                        afnd.addEstado(temp_circle);

                        updateTransitionMatrix(); // actualiza la matriz de estados.

                        event.consume();
                    }else if(input != null&&afnd.existeNodo(input)){
                        autohideAlert("Ya existe un nodo con el nombre: "+input,2000);
                    }
                }else if(detectCollitionsCircles(temp_circle)){
                    if(addFinalNodeActivate|addNodeActivate|addFinalNodeActivate)
                        autohideAlert("No hay espacio para insertar un nodo aquí.",2000);
                }
            }
        });



        /**
         * Reads dynamically from the language text box.
         */
        this.readLanguageTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            //this.alphabetLabel.textProperty().setValue(newValue);

            String[] alphabet = newValue.split(",");

            String[] alphabetChecked = checkAlphabet(alphabet);

            this.afnd.setAlfabeto(alphabetChecked);
            System.out.println("Alphabet: " + this.afnd.getAlfabeto());


            //System.out.println(newValue.trim().toCharArray()); try the language in the console
        });

        Tooltip tooltipReadAlphabet = new Tooltip();
        tooltipReadAlphabet.setText(
                "Para separar un caracter utilice (,)\n" +
                        "Ej: hello,world!\n"
        );
        tooltipReadAlphabet.setFont(Font.font(13));
        tooltipReadAlphabet.contentDisplayProperty();
        tooltipReadAlphabet.setWrapText(true);

        /**
         * It adds a tooltip to the alphabet input textbox.
         */
        this.readLanguageTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            Tooltip tooltip = genericTooltip(
                    "Para separar un caracter utilice (,)\n" +
                            "Ej: hello,world!\n"
            );
            if (newValue) {
                tooltip.show(readLanguageTextField.getScene().getWindow());
            } else {
                tooltip.hide();
            }
        });

        addStartNode.setTooltip(new Tooltip("Agrega un nodo inicial"));
        addNode.setTooltip(new Tooltip("Agrega un nodo"));
        addTransition.setTooltip(new Tooltip("Agrega una transición entre dos nodos"));
        addFinal.setTooltip(new Tooltip("Agrega un nodo final"));
            integrityButton.setTooltip(new Tooltip("Verifica la integridad del Autómata"));
        readLanguageTextField.setTooltip(new Tooltip("Ingrese un alfabeto"));
        inWordTF.setTooltip(new Tooltip("Ingrese una palabra"));
        checkWordBtn.setTooltip(new Tooltip("Verifica la validez de una palabra"));
        openBtn.setTooltip(new Tooltip("Abre un archivo"));
        guardarBtn.setTooltip(new Tooltip("Guarda un archivo"));
        leftBtn.setTooltip(new Tooltip("Deshacer"));
        rightBtn.setTooltip(new Tooltip("Rehacer"));

        this.integrityButton.setOnAction(event -> {
            addInitialNodeActivate=false;
            circleInitial.setFill(Color.LIGHTGRAY);
            addStartNode.setSelected(false);

            addNode.setSelected(false);
            addNodeActivate=false;
            circleN.setFill(Color.LIGHTGRAY);

            addFinalNodeActivate=false;
            addFinal.setSelected(false);
            circleFinal.setFill(Color.LIGHTGRAY);

            addTransicionActivate=false;
            addTransition.setSelected(false);
            line.setStartY(0);
            line.setStroke(Color.BLACK);
            previous= null;

            boolean integrityState = checkIntegrity(this.afnd);

            if (integrityState&&this.collisionNodes()==false) {
                autohideAlert(
                        "El autómata es válida.",
                        2000);
            } else {
                autohideAlert(
                        "El autómata es inválido.",
                        2000);
            }

        });

        this.inWordTF.textProperty().addListener((observable, oldValue, newValue) -> {

            System.out.println("Word: " + newValue);
            this.checkWordBtn.onActionProperty().setValue(e -> checkWord(newValue));

        });


        openBtn.setOnAction(e -> openFile());
        guardarBtn.setOnAction(e -> saveFile());
        leftBtn.setOnAction(e -> undo());
        rightBtn.setOnAction(e -> redo());

    }

    private boolean collisionNodes(){
        for(Node node1 : this.groupPaint.getChildren()){
            for(Node node2 : this.groupPaint.getChildren()){
                if(node1!=node2) {
                    if (collisions(node1, node2)) {
                        genericAlert("Automata invalido", "Reordene Nodos y transiciones para que no collsionen.", null, Alert.AlertType.WARNING);
                        return true;
                    }
                }
            }
        }
        System.out.println("AFND válido");
        return false;
    }

    private boolean collisions(Node node1, Node node2){
        ArrayList<String> collitions= new ArrayList<>();
        if(node1 instanceof Nodo){
            if(node2 instanceof Nodo&& node1.getBoundsInParent().intersects(node2.getBoundsInParent())){
                autohideAlert("¡Collision encontrada!- Nodo: "+((Nodo)node1).getEstado()+", con nodo: "+((Nodo)node2).getEstado()+".",5000);
                return true;
            }else if(node2 instanceof Transicion.Anchor &&node1.getBoundsInParent().intersects(node2.getBoundsInParent())){
                Transicion.Anchor anchor =(Transicion.Anchor) node2;
                Transicion temp_t= getTransicion(anchor);
                Nodo temp_n= getNodoWithTransicion(temp_t);
                autohideAlert("¡Collision encontrada!- Nodo: "+((Nodo)node1).getEstado()+
                        ", con transicion: f("+temp_n.getEstado()+","+temp_t.getTransiciones().get(0)+")= "+temp_t.getEstadoLlegada().getEstado()+".",5000);
                return true;
            }else if(node2 instanceof CubicCurve){
                CubicCurve curve =(CubicCurve) node2;
                Transicion temp_t= getTransicion(curve);
                Nodo temp_n= getNodoWithTransicion(temp_t);
                if (temp_t!=null) {
                    double[] arrowShape = new double[] { 0,0,1,1,-1,1 };//forma de la flechas
                    ArrayList<Transicion.Arrow> arrows= new ArrayList<>();
                    for(float i= 0.08f;i<=0.92f;i+=0.001f) {
                        arrows.add(new Transicion.Arrow(curve, i, arrowShape));
                    }
                    //temp_t.setArrows(arrows);
                    for(Transicion.Arrow arrow: arrows){
                        if(((node1).getBoundsInParent()).intersects(arrow.getBoundsInParent())&&
                                !(((Nodo) node1).getEstado()).equals(temp_t.getEstadoLlegada().getEstado())&&
                                !((Nodo)node1).getEstado().equals(temp_n.getEstado())) {

                            autohideAlert("¡Collision encontrada!- Nodo: "+((Nodo)((Nodo) node1)).getEstado()+
                                    " ,con transicion: f("+temp_n.getEstado()+","+temp_t.getTransiciones().get(0)+")= "+temp_t.getEstadoLlegada().getEstado()+".",5000);
                            return true;
                        }
                    }
                }
            }
        }else if(node1 instanceof Transicion.Anchor){
            if(node2 instanceof Nodo&&node1.getBoundsInParent().intersects(node2.getBoundsInParent())){
                Transicion.Anchor anchor0 =(Transicion.Anchor) node1;
                Transicion temp_t0= getTransicion(anchor0);
                Nodo temp_n0= getNodoWithTransicion(temp_t0);
                autohideAlert("¡Collision encontrada!- transicion: f("+temp_n0.getEstado()+","+temp_t0.getTransiciones().get(0)+")= "+temp_t0.getEstadoLlegada().getEstado()+
                        ", con nodo: "+((Nodo)node2).getEstado()+".",5000);
                return true;
            }else if(node2 instanceof Transicion.Anchor&&node1.getBoundsInParent().intersects(node2.getBoundsInParent())){
                Transicion.Anchor anchor0 =(Transicion.Anchor) node1;
                Transicion temp_t0= getTransicion(anchor0);
                Nodo temp_n0= getNodoWithTransicion(temp_t0);
                Transicion.Anchor anchor =(Transicion.Anchor) node2;
                Transicion temp_t= getTransicion(anchor);
                Nodo temp_n= getNodoWithTransicion(temp_t);
                autohideAlert("¡Collision encontrada!- Transicion: f("+temp_n0.getEstado()+","+temp_t0.getTransiciones().get(0)+")= "+temp_t0.getEstadoLlegada().getEstado()+
                        ", con transicion: f("+temp_n.getEstado()+","+temp_t.getTransiciones().get(0)+")= "+temp_t.getEstadoLlegada().getEstado()+".",5000);
                return true;
            }else if(node2 instanceof CubicCurve){
                CubicCurve curve =(CubicCurve) node2;
                Transicion temp_t= getTransicion(curve);
                Nodo temp_n= getNodoWithTransicion(temp_t);
                if (temp_t!=null) {
                    double[] arrowShape = new double[] { 0,0,1,1,-1,1 };//forma de la flechas
                    ArrayList<Transicion.Arrow> arrows= new ArrayList<>();
                    for(float i= 0.08f;i<=0.92f;i+=0.001f) {
                        arrows.add(new Transicion.Arrow(curve, i, arrowShape));
                    }
                    //temp_t.setArrows(arrows);
                    for(Transicion.Arrow arrow: arrows){
                        if(((node1).getBoundsInParent()).intersects(arrow.getBoundsInParent())&&
                                ((Transicion.Anchor)node1)!=temp_t.getAnchor()) {
                            Transicion temp_t0= getTransicion((Transicion.Anchor)node1);
                            Nodo temp_n0= getNodoWithTransicion(temp_t0);
                            autohideAlert("¡Collision encontrada!- Transicion: f("+temp_n0.getEstado()+","+temp_t0.getTransiciones().get(0)+")= "+temp_t0.getEstadoLlegada().getEstado()+
                                    ", con transicion: f("+temp_n.getEstado()+","+temp_t.getTransiciones().get(0)+")= "+temp_t.getEstadoLlegada().getEstado()+".",5000);
                            return true;
                        }
                    }
                }
            }
        }else if(node1 instanceof CubicCurve){
            if(node2 instanceof Nodo) {
                CubicCurve curve0 = (CubicCurve) node1;
                Transicion temp_t0 = getTransicion(curve0);
                Nodo temp_n0 = getNodoWithTransicion(temp_t0);
                if (temp_t0 != null) {
                    double[] arrowShape = new double[]{0, 0, 1, 1, -1, 1};//forma de la flechas
                    ArrayList<Transicion.Arrow> arrows = new ArrayList<>();
                    for (float i = 0.08f; i <= 0.92f; i += 0.001f) {
                        arrows.add(new Transicion.Arrow(curve0, i, arrowShape));
                    }
                    for (Transicion.Arrow arrow : arrows) {
                        if (((node2).getBoundsInParent()).intersects(arrow.getBoundsInParent()) &&
                                !(((Nodo) node2).getEstado()).equals(temp_t0.getEstadoLlegada().getEstado()) &&
                                !((Nodo) node2).getEstado().equals(temp_n0.getEstado())) {

                            autohideAlert("¡Collision encontrada!- Transicion: f(" + temp_n0.getEstado() + "," + temp_t0.getTransiciones().get(0) + ")= " + temp_t0.getEstadoLlegada().getEstado() +
                                    ", con nodo: "+(((Nodo) node2)).getEstado()+".", 5000);
                            return true;
                        }
                    }
                }
            }else if(node2 instanceof Transicion.Anchor){
                CubicCurve curve =(CubicCurve) node1;
                Transicion temp_t= getTransicion(curve);
                Nodo temp_n= getNodoWithTransicion(temp_t);
                if (temp_t!=null) {
                    double[] arrowShape = new double[] { 0,0,1,1,-1,1 };//forma de la flechas
                    ArrayList<Transicion.Arrow> arrows= new ArrayList<>();
                    for(float i= 0.08f;i<=0.92f;i+=0.001f) {
                        arrows.add(new Transicion.Arrow(curve, i, arrowShape));
                    }
                    //temp_t.setArrows(arrows);
                    for(Transicion.Arrow arrow: arrows){
                        if(((node2).getBoundsInParent()).intersects(arrow.getBoundsInParent())&&
                                ((Transicion.Anchor)node2)!=temp_t.getAnchor()) {
                            Transicion temp_t0= getTransicion((Transicion.Anchor)node2);
                            Nodo temp_n0= getNodoWithTransicion(temp_t0);
                            autohideAlert("¡Collision encontrada!- Transicion: f("+temp_n.getEstado()+","+temp_t.getTransiciones().get(0)+")= "+temp_t.getEstadoLlegada().getEstado()+
                                    ", con transicion: f("+temp_n0.getEstado()+","+temp_t0.getTransiciones().get(0)+")= "+temp_t0.getEstadoLlegada().getEstado()+".",5000);
                            return true;
                        }
                    }
                }
            }else if(node2 instanceof CubicCurve){//caso dificil curvas con curvas
                CubicCurve curve =(CubicCurve) node1;
                Transicion temp_t= getTransicion(curve);
                Nodo temp_n= getNodoWithTransicion(temp_t);
                CubicCurve curve2 =(CubicCurve) node2;
                Transicion temp_t2= getTransicion(curve2);
                Nodo temp_n2= getNodoWithTransicion(temp_t2);
                if(curve!=null&&curve2!=null){
                    double[] arrowShape = new double[] { 0,0,1,1,-1,1 };//forma de la flechas
                    ArrayList<Transicion.Arrow> arrows= new ArrayList<>();
                    ArrayList<Transicion.Arrow> arrows2= new ArrayList<>();
                    for(float i= 0.08f;i<=0.92f;i+=0.001f) {
                        arrows.add(new Transicion.Arrow(curve, i, arrowShape));
                        arrows2.add(new Transicion.Arrow(curve2, i, arrowShape));
                    }
                    for(Transicion.Arrow arrow: arrows){
                        for(Transicion.Arrow arrow2: arrows2){
                            if(arrow.getBoundsInParent().intersects(arrow2.getBoundsInParent())){
                                autohideAlert("¡Collision encontrada!- Transicion: f("+temp_n.getEstado()+","+temp_t.getTransiciones().get(0)+")= "+temp_t.getEstadoLlegada().getEstado()+
                                        ", con transicion: f("+temp_n2.getEstado()+","+temp_t2.getTransiciones().get(0)+")= "+temp_t2.getEstadoLlegada().getEstado()+".",5000);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private Transicion getTransicion(CubicCurve curve){
        for(Nodo temp_nodo: this.afnd.getEstados()){
            for (Transicion temp_t: temp_nodo.getTransiciones()){
                if (temp_t.getCurve()==curve)
                    return temp_t;

        }
        }if(afnd.getEstadoInicial()!=null) {
            for (Transicion temp_t :afnd.getEstadoInicial().getTransiciones()) {
                if (temp_t.getCurve()==curve)
                    return temp_t;
            }
        }
        return null;
    }

    private Transicion getTransicion(Transicion.Anchor anchor){
        for(Nodo temp_nodo: this.afnd.getEstados()){
            for (Transicion temp_t: temp_nodo.getTransiciones()){
                if (temp_t.getAnchor()==anchor)
                    return temp_t;

            }
        }if(afnd.getEstadoInicial()!=null) {
            for (Transicion temp_t :afnd.getEstadoInicial().getTransiciones()) {
                if (temp_t.getAnchor()==anchor)
                    return temp_t;
            }
        }
        return null;
    }

    private Nodo getNodoWithTransicion(Transicion transicion){
        for(Nodo temp_nodo: this.afnd.getEstados()) {
            for (Transicion temp_t : temp_nodo.getTransiciones()) {
                if (temp_t == transicion)
                    return temp_nodo;
            }
        }if(afnd.getEstadoInicial()!=null) {
            for (Transicion temp_t :afnd.getEstadoInicial().getTransiciones()) {
                if (temp_t== transicion)
                    return afnd.getEstadoInicial();
            }
        }
        return null;
    }

    private void redo() {
        autohideAlert("Esta función aún no esta disponible.", 2000);
    }

    private void undo() {
        autohideAlert("Esta función aún no esta disponible.", 2000);
    }

    private void saveFile() {
        autohideAlert("Esta función aún no esta disponible.", 2000);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Configuración AFND");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("NODO files (*.nod)", "*.nod");
        fileChooser.getExtensionFilters().add(extFilter);
        //File save = fileChooser.showSaveDialog(guardarBtn.getScene().getWindow());

    }

    private void openFile() {
        autohideAlert("Esta función aún no esta disponible.", 2000);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir una configuración de AFND");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("NODO files (*.nod)", "*.nod");
        fileChooser.getExtensionFilters().add(extFilter);
        //File open = fileChooser.showOpenDialog(openBtn.getScene().getWindow());

    }

    /**
     * Generates a genetic Tooltip with a given message.
     *
     * @param message what text is displayed as a Tooltip
     * @return a Tooltip with a message
     */
    private Tooltip genericTooltip(String message) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(message);
        tooltip.setFont(Font.font(13));
        tooltip.contentDisplayProperty();
        tooltip.autoHideProperty().set(true);
        tooltip.setWrapText(true);


        return tooltip;
    }

    private void updateTransitionMatrix() {


        observableList.clear(); // Borra los elementos previos

        panelDeTransiciones.setVgrow(listView, Priority.ALWAYS);
        listViewLabel.setText("Matriz de Transiciones");

        observableList.addAll(this.afnd.getArrayEstados());

        listView.itemsProperty().addListener((observable, oldValue, newValue) -> {

        });
        listView.setItems(observableList);

    }

    /**
     * @param alphabet arreglo de String dividido por el caracter (,)
     * @return retorna un alfabeto valido en caso de si el input es correcto
     * y un alfabeto vacio de lo contrario.
     * También muestra una alerta si el alfabeto no es válido.
     */
    private String[] checkAlphabet(String[] alphabet) {

        for (String a :alphabet) {
            if (a.length() > 1||a.equals(" ")||a.equals("")) {
                autohideAlert("No es una entrada válida, siga las instrucciones", 2000);

                return new String[0];
            }
        }
        return alphabet;
    }

    public void autohideAlert(String title, int wait){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        //alert.setContentText(content);
        //ButtonType buttonTypeOne = new ButtonType("Yes");
        //ButtonType buttonTypeCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        //alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Thread thread = new Thread(() -> {
            try {
                // Wait for 5 secs
                Thread.sleep(wait);
                if (alert.isShowing()) {
                    Platform.runLater(() -> alert.close());
                }
            } catch (Exception exp) {
                exp.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
        Optional<ButtonType> result = alert.showAndWait();
    }

    /**
     * Check the integrity for any AFND.
     *
     * @param afnd an automata finite non-deterministic.
     * @return true for a valid automata, false otherwise.
     */
    private boolean checkIntegrity(Afnd afnd) {
        return afnd.comprobarAutomata();
    }

    /**
     *
     * @param word represents the word to be check by the alphabet.
     */
    private void checkWord( String word ) {
        if(comprobarPalabraIngresada(word)) {
            System.out.println(word);
            System.out.println(this.afnd.getAlfabeto());

            if (this.afnd.comprobarPalabra2(word)) {
                genericAlertConfirmation("Palabra Valida", "Palabra Válida", "La palabra ingresada pertenece al lenguaje.");
            } else {
                genericAlert("Palabra invalida", "Palabra Invalida", "La palabra ingresada NO pertenece al autómata.");
            }
        }else{
            genericAlert("Formato Incorrecto", "La palabra ingresada no es valida", "La palabra solo puede contener letras y numeros");
        }
    }

    private void genericAlertConfirmation(String title, String header, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);

        alert.showAndWait();
    }


    /**
     * Alerta para el usuario (retroalimentacion del programa)
     *
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
                boolean inputOfUser= true;
                if(addInitialNodeActivate){
                    autohideAlert("No se puede insertar un nodo sobre otro.",2000);
                    addInitialNodeActivate=false;
                    circleInitial.setFill(Color.LIGHTGRAY);
                    addStartNode.setSelected(false);
                }else if(addNodeActivate) {
                    autohideAlert("No se puede insertar un nodo sobre otro.",2000);
                    addNode.setSelected(false);
                    addNodeActivate = false;
                    circleN.setFill(Color.LIGHTGRAY);
                }else if(addFinalNodeActivate){
                    autohideAlert("No se puede insertar un nodo sobre otro.",2000);
                    addFinalNodeActivate=false;
                    addFinal.setSelected(false);
                    circleFinal.setFill(Color.LIGHTGRAY);
                }
                if(previous==null&&addTransicionActivate){//asignar nodo previous
                    previous=circle;
                    event.consume();
                }else if(previous!=null&&addTransicionActivate&&previous!=circle){//agrega una transicion normal entre dos nodos
                    System.out.println(previous.getEstado());
                    String nameOfTheTransition = genericAlertInput(
                            "Ingrese el caracter de la Transición",
                            "Nodo Inicio: " + previous.getEstado() + " to Nodo llegada: " + circle.getEstado()+"\n  separe lo caracteres con (,)",
                            "Caracter");
                    if(nameOfTheTransition.equals(" ")) {
                        nameOfTheTransition = null;
                    }
                    System.out.println("Transición: |" + nameOfTheTransition+"|");
                    if(nameOfTheTransition!=null){
                        if(checkWordsInTransicion(nameOfTheTransition)){
                            for(Transicion t_temp :previous.getTransiciones()){
                                if(t_temp.getEstadoLlegada()==circle){
                                    t_temp.addTransicion(getCharsForTransicion(nameOfTheTransition));
                                    t_temp.updateAnchor();
                                    inputOfUser=false;
                                    addTransicionActivate=false;
                                    addTransition.setSelected(false);
                                    line.setStartY(0);
                                    line.setStroke(Color.BLACK);
                                    previous.toFront();
                                    circle.toFront();
                                    previous=null;
                                    updateTransitionMatrix();
                                    event.consume();
                                }
                            }
                        }else{
                            inputOfUser=false;
                            addTransicionActivate=false;
                            addTransition.setSelected(false);
                            line.setStartY(0);
                            line.setStroke(Color.BLACK);
                            previous.toFront();
                            circle.toFront();
                            previous=null;
                            updateTransitionMatrix();
                            event.consume();
                        }
                    }else{
                        autohideAlert("No se puede agregar una Transicion vacia",2000);
                        inputOfUser=false;
                        addTransicionActivate=false;
                        addTransition.setSelected(false);
                        line.setStartY(0);
                        line.setStroke(Color.BLACK);
                        previous.toFront();
                        circle.toFront();
                        previous=null;
                        updateTransitionMatrix();
                        event.consume();
                    }

                    if (nameOfTheTransition != null && inputOfUser==true) {
                        CubicCurve curve = conectTo(previous,circle);
                        Transicion transicion=new Transicion(circle, nameOfTheTransition,curve);
                        Transicion.Anchor anchor= transicion.getAnchor();
                        anchor.setOnMousePressed(t -> {
                            orgSceneX = t.getSceneX();
                            orgSceneY = t.getSceneY();

                            Transicion temp_t = getTransicion((Transicion.Anchor)t.getSource());
                            Nodo temp_n= getNodoWithTransicion(temp_t);
                            if (t.isSecondaryButtonDown()) {
                                ContextMenu contextMenu = new ContextMenu();
                                MenuItem erase = new MenuItem("Eliminar Transicion");
                                MenuItem edit = new MenuItem("Editar nombre");

                                contextMenu.getItems().addAll(erase,edit);
                                contextMenu.setX(t.getSceneX() + orgSceneX);
                                contextMenu.setY( t.getSceneY());


                                erase.setOnAction(e -> {
                                    groupPaint.getChildren().remove(temp_t.getAnchor());
                                    groupPaint.getChildren().remove(temp_t.getCurve());
                                    for(Transicion.Arrow temp_arrow: temp_t.getArrows()){
                                        groupPaint.getChildren().remove(temp_arrow);
                                    }
                                    if(!temp_n.isEsInitial()) {
                                        for (Nodo nodo : afnd.getEstados())
                                            if(nodo.getEstado().equals(temp_n.getEstado()))
                                                nodo.getTransiciones().remove(temp_t);
                                    }else {
                                        afnd.getEstadoInicial().removeThisInTransition(temp_t);
                                    }
                                    updateTransitionMatrix();
                                });

                                edit.setOnAction(e -> {
                                    String defaultValue="";
                                    for(Character ca: temp_t.getTransiciones()){
                                        defaultValue+=ca+",";
                                    }
                                    String input = genericAlertInput2(
                                            "Ingrese el caracter de la Transición",
                                            "Separe lo caracteres con (,)",
                                            "Caracter",defaultValue);
                                    if(input!=null&&input.equals(" ")) {
                                        input = null;
                                    }
                                    System.out.println("Transición: |" + input+"|");
                                    if(input!=null) {
                                        if (checkWordsInTransicion(input)) {
                                            temp_t.getTransiciones().clear();
                                            temp_t.addTransicion(getCharsForTransicion(input));
                                            temp_t.updateAnchor();
                                            updateTransitionMatrix();
                                            event.consume();
                                        }
                                    }
                                });

                                contextMenu.show(((Transicion.Anchor)t.getSource()).getScene().getWindow());
                            }
                        });
                        List<Transicion.Arrow> arrows= transicion.getArrows();
                        previous.addTransicion(transicion); // Adds a transition to the a Preview Node.

                        updateTransitionMatrix();

                        groupPaint.getChildren().addAll(curve,anchor);
                        for(Transicion.Arrow temp_value :arrows){
                            groupPaint.getChildren().add(temp_value);
                        }
                        addTransicionActivate=false;
                        addTransition.setSelected(false);
                        line.setStartY(0);
                        line.setStroke(Color.BLACK);
                        previous.toFront();
                        circle.toFront();
                        previous=null;
                        event.consume();
                    }
                }else if(previous!=null&&addTransicionActivate&&previous==circle){//agrega una transicion ciclica
                    inputOfUser= true;
                    System.out.println(previous.getEstado());
                    String nameOfTheTransition;
                    nameOfTheTransition = genericAlertInput(
                            "Ingrese los caracteres de la Transición ",
                            "Nodo Inicio: " + previous.getEstado() + " to Nodo llegada: " + circle.getEstado()+ "\n  separe lo caracteres con (,)",
                            "Caracter");
                    System.out.println("Transición: " + nameOfTheTransition);
                    //Busco si ya existe la transicion ingresada
                    if(nameOfTheTransition!=null){
                        if(checkWordsInTransicion(nameOfTheTransition)){
                            for(Transicion t_temp :previous.getTransiciones()){
                                if(t_temp.getEstadoLlegada()==previous){
                                    t_temp.addTransicion(getCharsForTransicion(nameOfTheTransition));
                                    t_temp.updateAnchor();
                                    updateTransitionMatrix();
                                    inputOfUser=false;
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
                        }else{
                            inputOfUser=false;
                            addTransicionActivate=false;
                            addTransition.setSelected(false);
                            line.setStartY(0);
                            line.setStroke(Color.BLACK);
                            previous.toFront();
                            circle.toFront();
                            previous=null;
                            event.consume();
                        }
                    }else{
                        addTransicionActivate=false;
                        addTransition.setSelected(false);
                        line.setStartY(0);
                        line.setStroke(Color.BLACK);
                        previous.toFront();
                        circle.toFront();
                        previous=null;
                        event.consume();
                    }


                    if (nameOfTheTransition != null && inputOfUser==true) {



                        CubicCurve curve = conectTo2(previous,circle);
                        Transicion transicion=new Transicion(circle, nameOfTheTransition,curve,true);
                        Transicion.Anchor anchor= transicion.getAnchor();
                        anchor.setOnMousePressed(t -> {
                            orgSceneX = t.getSceneX();
                            orgSceneY = t.getSceneY();

                            Transicion temp_t = getTransicion((Transicion.Anchor)t.getSource());
                            Nodo temp_n= getNodoWithTransicion(temp_t);
                            if (t.isSecondaryButtonDown()) {
                                ContextMenu contextMenu = new ContextMenu();
                                MenuItem erase = new MenuItem("Eliminar Transicion");
                                MenuItem edit = new MenuItem("Editar nombre");

                                contextMenu.getItems().addAll(erase,edit);
                                contextMenu.setX(t.getSceneX() + orgSceneX);
                                contextMenu.setY( t.getSceneY());


                                erase.setOnAction(e -> {
                                    groupPaint.getChildren().remove(temp_t.getAnchor());
                                    groupPaint.getChildren().remove(temp_t.getCurve());
                                    for(Transicion.Arrow temp_arrow: temp_t.getArrows()){
                                        groupPaint.getChildren().remove(temp_arrow);
                                    }
                                    if(!temp_n.isEsInitial()) {
                                        for (Nodo nodo : afnd.getEstados())
                                            if(nodo.getEstado().equals(temp_n.getEstado()))
                                                nodo.getTransiciones().remove(temp_t);
                                    }else {
                                        afnd.getEstadoInicial().removeThisInTransition(temp_t);
                                    }
                                    updateTransitionMatrix();
                                });

                                edit.setOnAction(e -> {
                                    String defaultValue="";
                                    for(Character ca: temp_t.getTransiciones()){
                                        defaultValue+=ca+",";
                                    }
                                    String input = genericAlertInput2(
                                            "Ingrese el caracter de la Transición",
                                            "Separe lo caracteres con (,)",
                                            "Caracter",defaultValue);
                                    if(input!=null&&input.equals(" ")) {
                                        input = null;
                                    }
                                    System.out.println("Transición: |" + input+"|");
                                    if(input!=null) {
                                        if (checkWordsInTransicion(input)) {
                                            temp_t.getTransiciones().clear();
                                            temp_t.addTransicion(getCharsForTransicion(input));
                                            temp_t.updateAnchor();
                                            updateTransitionMatrix();
                                            event.consume();
                                        }
                                    }
                                });

                                contextMenu.show(((Transicion.Anchor)t.getSource()).getScene().getWindow());
                            }
                        });
                        List<Transicion.Arrow> arrows= transicion.getArrows();
                        previous.addTransicion(transicion); // Adds a transition to the a Preview Node.
                        groupPaint.getChildren().addAll(curve,anchor);
                        for(Transicion.Arrow temp_value :arrows){
                            groupPaint.getChildren().add(temp_value);
                        }
                        addTransicionActivate=false;
                        addTransition.setSelected(false);
                        line.setStartY(0);
                        line.setStroke(Color.BLACK);
                        previous.toFront();
                        circle.toFront();
                        previous=null;
                        updateTransitionMatrix();
                        event.consume();
                    }
                }
            }
        });


        circle.setOnMousePressed((t) -> {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            Nodo c = (Nodo) (t.getSource());
            c.toFront();

            if (t.isSecondaryButtonDown()) {
                ContextMenu contextMenu = new ContextMenu();

//                Menu type = new Menu("Cambiar Tipo");
//                MenuItem startNode = new MenuItem("Nodo Inicio");
//                MenuItem node = new MenuItem("Nodo");
//                MenuItem endNode = new MenuItem("Nodo Final");
//                type.getItems().addAll(startNode, node, endNode);

//                MenuItem rename = new MenuItem("Cambiar Nombre");
                MenuItem erase = new MenuItem("Eliminar Nodo");
                MenuItem edit = new MenuItem("Editar nombre");

                contextMenu.getItems().addAll(erase,edit);
                contextMenu.setX(t.getSceneX() + orgSceneX);
                contextMenu.setY( t.getSceneY());

                erase.setOnAction(e -> {
                    //Buscamos las transiciones que apunten al nodo seleccionado
                    ArrayList<Transicion> listToEraser= new ArrayList();
                    for (Nodo nodo: afnd.getEstados()){
                        for(Transicion temp_t: nodo.getTransiciones()){
                            if(temp_t.getEstadoLlegada()==c){
                                listToEraser.add(temp_t);
                                this.groupPaint.getChildren().remove(temp_t.getAnchor());
                                this.groupPaint.getChildren().remove(temp_t.getCurve());
                                for(Transicion.Arrow temp_arrow: temp_t.getArrows()){
                                    this.groupPaint.getChildren().remove(temp_arrow);
                                }
                            }
                        }
                        for(Transicion temp_t: listToEraser){
                            nodo.getTransiciones().remove(temp_t);
                        }
                    }
                    if(this.afnd.getEstadoInicial()!=null&&!c.isEsInitial()){
                        listToEraser= new ArrayList<>();
                        for(Transicion temp_t: this.afnd.getEstadoInicial().getTransiciones()){
                            if(temp_t.getEstadoLlegada()==c)
                                listToEraser.add(temp_t);
                        }
                        for (Transicion temp_t: listToEraser) {
                            afnd.getEstadoInicial().removeThisInTransition(temp_t);
                            this.groupPaint.getChildren().remove(temp_t.getAnchor());
                            this.groupPaint.getChildren().remove(temp_t.getCurve());
                            for(Transicion.Arrow temp_arrow: temp_t.getArrows()){
                                this.groupPaint.getChildren().remove(temp_arrow);
                            }
                        }
                    }
                    Nodo global = c;
                    //Ahora borramos el nodo y sus transiciones
                    //borramos la parte grafica
                    for(Transicion temp_t: c.getTransiciones()){
                        this.groupPaint.getChildren().remove(temp_t.getAnchor());
                        this.groupPaint.getChildren().remove(temp_t.getCurve());
                        for(Transicion.Arrow temp_arrow: temp_t.getArrows()){
                            this.groupPaint.getChildren().remove(temp_arrow);
                        }
                    }
                    this.groupPaint.getChildren().remove(c);
                    if(this.afnd.getEstadoInicial()!=null&&c.isEsInitial())
                        this.groupPaint.getChildren().remove(c.getForInitial());
                    //ahora el backend
                    c.setTransiciones(new ArrayList<>());
                    if(c.isEsInitial()){
                        this.afnd.setEstadoInicial(null);
                    }else{
                        this.afnd.removeNode(c);
                    }


                    updateTransitionMatrix();

                });
                edit.setOnAction(e -> {
                    String input= genericAlertInput("Ingrese nombre del Nodo", null, "Nodo: ");

                    if (input != null&&!this.afnd.existeNodo(input)) {
                        c.setEstado(input);
                        c.setFill(new ImagePattern(textToImage(input, "lightgray")));

                        updateTransitionMatrix(); // actualiza la matriz de estados

                    }else if(input != null&&this.afnd.existeNodo(input)){
                        autohideAlert("Ya existe un nodo con el nombre: "+input,2000);
                    }

                });
                contextMenu.show(c.getScene().getWindow());
            }

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
            for(Nodo temp_node:afnd.getEstados2()){
                temp_node.update();
            }
            if(afnd.getEstadoInicial()!=null){
                afnd.getEstadoInicial().update();
            }
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
        });
        return circle;
    }

    /**
     * It displays an alert and returns a text form the user.
     *
     * @param title title of the alert.
     * @param header header of the alert box.
     * @param contentText text displaying the problem information.
     * @return A text from the user.
     */
    private String genericAlertInput(String title, String header, String contentText) {
        TextInputDialog dialog = new TextInputDialog(null);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(contentText);
        Optional<String> result = dialog.showAndWait();
        String[] character = {new String()};
        // The Java 8 way to get the response value (with lambda expression).

        if (result.isPresent()){
            return result.get();
        }
        return null;
    }

    private String genericAlertInput2(String title, String header, String contentText,String defaulValue) {
        TextInputDialog dialog = new TextInputDialog(defaulValue);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(contentText);
        Optional<String> result = dialog.showAndWait();
        String[] character = {new String()};
        // The Java 8 way to get the response value (with lambda expression).

        if (result.isPresent()){
            return result.get();
        }
        return null;
    }

    private void genericAlert(String title, String header, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    /**
     * Display an alert with a single text.
     * @param title represents the title of the alert message.
     */
    private void genericAlert(String title) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        //alert.setContentText(title);

        alert.showAndWait();
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

    private CubicCurve conectTo(Nodo c1, Nodo c2){
        CubicCurve curve = new CubicCurve();
        //aqui conectar a los nodos
        curve.startXProperty().bind(c1.centerXProperty());
        curve.startYProperty().bind(c1.centerYProperty());
        curve.endXProperty().bind(c2.centerXProperty());
        curve.endYProperty().bind(c2.centerYProperty());

        double x1= c1.getCenterX();
        double x2= c2.getCenterX();
        double y1= c1.getCenterY();
        double y2= c2.getCenterY();

        curve.setControlX1((x1+x2)/2);
        curve.setControlY1((y1+y2)/2);
        curve.setControlX2((x1+x2)/2);
        curve.setControlY2((y1+y2)/2);
        /*double distance= Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y2-y1,2));
        double tetha=Math.toDegrees(Math.asin((Math.sqrt(Math.pow(y2-y1,2))/distance)));
        double hipo= distance/Math.cos(Math.toRadians(45));
        System.out.println("distance: "+distance+" -tetha: "+tetha+" -hipo: "+hipo);
        if(x1<=x2 ){ //primer cuadrante
            curve.setControlX1((hipo * Math.cos(Math.toRadians(45 ))));
            curve.setControlY1((hipo * Math.sin(Math.toRadians(45 ))));
            curve.setControlX2((hipo * Math.cos(Math.toRadians(45 ))));
            curve.setControlY2((hipo * Math.sin(Math.toRadians(45 ))));}*/
        curve.setStroke(Color.GRAY);
        curve.setStrokeWidth(3);
        //curve.setStrokeLineCap(StrokeLineCap.SQUARE);
        curve.setFill(Color.TRANSPARENT);
        //curve.getStrokeDashArray().setAll(5.0, 5.0);
        return curve;
    }

    private CubicCurve conectTo2(Nodo c1, Nodo c2){
        CubicCurve curve = new CubicCurve();
        //aqui conectar a los nodos
        curve.startXProperty().bind(c1.centerXProperty());
        curve.startYProperty().bind(c1.centerYProperty());
        curve.endXProperty().bind(c2.centerXProperty());
        curve.endYProperty().bind(c2.centerYProperty());

        double x1= c1.getCenterX();
        double x2= c2.getCenterX();
        double y1= c1.getCenterY();
        double y2= c2.getCenterY();

        /*double distance= Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y2-y1,2));
        double tetha=Math.toDegrees(Math.asin((Math.sqrt(Math.pow(y2-y1,2))/distance)));
        double hipo= distance/Math.cos(Math.toRadians(45));
        System.out.println("distance: "+distance+" -tetha: "+tetha+" -hipo: "+hipo);*/

        curve.setControlX1(x1+40);
        curve.setControlY1(y1);
        curve.setControlX2(x2);
        curve.setControlY2(y2+40);

        curve.setStroke(Color.GRAY);
        curve.setStrokeWidth(3);
        //curve.setStrokeLineCap(StrokeLineCap.SQUARE);
        curve.setFill(Color.TRANSPARENT);
        //curve.getStrokeDashArray().setAll(5.0, 5.0);
        return curve;
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

    private boolean detectCollitionsTransition(Transicion transicion1,Transicion transicion2){

        return false;
    }


    private Image textToImage(String text, String color) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setMinSize(30, 30);
        label.setMaxSize(30, 30);
        label.setPrefSize(30, 30);
        label.setStyle("-fx-background-color: "+color+"; -fx-text-fill:black;");
        label.setWrapText(true);
        Scene scene = new Scene(new Group(label));
        WritableImage img = new WritableImage(30, 30) ;
        scene.snapshot(img);
        return img ;
    }

    /**
     * Comprueba si el String ingresado como alfabeto tiene el formato correcto.
     * @param alfabeto
     * @return true si es correcto, false si no lo es.
     */
    public boolean comprobarAlfabetoIngresado(String alfabeto){
        if(alfabeto.matches("((\\w;)|(\\s;))*((\\w)|(\\s))")){
            return true;
        }
        return false;
    }

    /**
     * Comprueba si la palabra ingresada tiene el formato correcto.
     * @param palabra
     * @return true si es correcto, false si no lo es.
     */
    public boolean comprobarPalabraIngresada(String palabra){
        if(palabra.matches("\\w*")){
            return true;
        }
        return false;
    }

    /**
     * Comprueba si la palabra ingresada es valida
     * @param newValue string a comprobar
     * @return un boolean
     */
    public boolean checkWordsInTransicion(String newValue){
        if(newValue==""|newValue==" ") {
            return false;
        }
        String[] alphabet = newValue.split(",");
        String[] alphabetChecked = checkAlphabet(alphabet);
        for (String a :alphabet) {
            if (a.length() > 1||a.equals(" ")|a.equals("")||a.length()==0){
                return false;
            }
        };
        return true;
    }

    /**
     * Toma un string, quita sus separadores (,), mide que tengan largo 1 y
     * entrega un arreglo de char listo para agregar a la transicion
     * @param newValue string a convertir
     * @return  Character List
     */
    public char[] getCharsForTransicion(String newValue){
        newValue= newValue.replace(" ","");
        String[] alphabet = newValue.split(",");
        String[] alphabetChecked = checkAlphabet(alphabet);
        char[] arrayOk= new char[alphabetChecked.length];
        int index= 0;
        for(String temp_str :alphabetChecked){
            arrayOk[index] = temp_str.charAt(0);
            index++;
        }
        return arrayOk;
    }

}
