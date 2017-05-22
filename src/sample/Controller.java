package sample;

import javafx.application.Platform;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    private @FXML TextField readLanguageTextField;
    private @FXML TextField inWordTF;
    private @FXML Button checkWordBtn;
    private @FXML ToggleButton addStartNode; // todo
    private @FXML ToggleButton addNode;
    private @FXML ToggleButton addTransition;
    private @FXML ToggleButton addFinal; // todo
    private @FXML Group groupPaint;
    private @FXML TreeView treeView;
    private @FXML Button integrityButton;

    private Nodo previous=null;
    private Line lineToConect,line=null;
    private Afnd afnd;
    private double orgSceneX,orgSceneY,previousX,previousY;
    private boolean inn,addNodeActivate,addTransicionActivate,addInitialNodeActivate,addFinalNodeActivate=false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.afnd= new Afnd();

        Circle circle= new Circle(0,0,10,Color.LIGHTGRAY);
        circle.setStroke(Color.BLACK);
        this.addNode.setGraphic(circle);

        Circle circleInitial= new Circle(15,10,10,Color.LIGHTGRAY);
        circleInitial.setStroke(Color.BLACK);
        Polygon poly= new Polygon(new double[]{(double)(circleInitial.getCenterX()-15),(double)(circleInitial.getCenterY()+5),
                (double)(circleInitial.getCenterX()-10),(double)(circleInitial.getCenterY()),(double)(circleInitial.getCenterX()-15),(double)(circleInitial.getCenterY()-5)});
        Pane graficInitialNode = new Pane();
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

        /**
         * Listener
         */
        this.groupPaint.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Nodo temp_circle= createCircle(event.getX(), event.getY(),false,false);
                String input = ""; // ignore me.

                if(addNodeActivate && !inn&&!detectCollitionsCircles(temp_circle)) { // falta agregar restricciones
                    input= genericAlertInput("Ingrese nombre del Nodo", null, "Nodo: ");
                    addNodeActivate = false;
                    addNode.setSelected(false);
                    circle.setFill(Color.LIGHTGRAY);
                    if (input != null) {
                        temp_circle.setEstado(input);
                        temp_circle.setFill(new ImagePattern(textToImage(input, "lightgray")));
                        afnd.addEstado(temp_circle); // adds a node to the AFND.
                        groupPaint.getChildren().addAll(temp_circle);

                    }
                } else if(addInitialNodeActivate &&!inn &&!detectCollitionsCircles(temp_circle)){
                    if(afnd.getEstadoInicial()==null) {
                        input= genericAlertInput("Ingrese nombre del Nodo", null, "Nodo: ");
                        addInitialNodeActivate = false;
                        addStartNode.setSelected(false);
                        circleInitial.setFill(Color.LIGHTGRAY);

                        if (input != null) {
                            temp_circle.setEstado(input);
                            temp_circle.setFill(new ImagePattern(textToImage(input, "lightgray")));
                            temp_circle.setEsInitial(true);
                            afnd.setEstadoInicial(temp_circle); // Sets the Automata initial state.
                            groupPaint.getChildren().addAll(temp_circle, temp_circle.getForInitial());
                        }
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
                    input= genericAlertInput("Ingrese nombre del Nodo final", null, "Nodo: ");
                    if (input != null) {
                        temp_circle.setEstado(input);
                        temp_circle.setFill(new ImagePattern(textToImage(input, "lightgray")));
                        groupPaint.getChildren().add(temp_circle);
                        afnd.addEstado(temp_circle);
                        event.consume();
                    }
                }
            }
        });



        /**
         * Reads dynamically from the language text box.
         */
        this.readLanguageTextField.textProperty().addListener((observable, oldValue, newValue) -> {


            String[] alphabet = newValue.split(";");

            String[] alphabetChecked = checkAlphabet(alphabet);

            this.afnd.setAlfabeto(alphabetChecked);
            System.out.println("Alphabet: " + this.afnd.getAlfabeto());


        });

        Tooltip tooltipReadAlphabet = new Tooltip();
        tooltipReadAlphabet.setText(
                "Para separar un caracter utilice (;)\n" +
                "Ej: hello;world!\n"
        );
        tooltipReadAlphabet.setFont(Font.font(13));
        tooltipReadAlphabet.contentDisplayProperty();
        tooltipReadAlphabet.setWrapText(true);

        /**
         * It adds a tooltip to the alphabet input textbox.
         */
        this.readLanguageTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                tooltipReadAlphabet.show(readLanguageTextField,
                        readLanguageTextField.getScene().getWindow().getX() + readLanguageTextField.getLayoutX() + readLanguageTextField.getWidth() + 110, //
                        readLanguageTextField.getScene().getWindow().getY() + readLanguageTextField.getLayoutY() + readLanguageTextField.getHeight() + 70 ) ;
            }
            else {
                tooltipReadAlphabet.hide();
            }
        });


        this.integrityButton.setOnAction(event -> {

            boolean integrityState = checkIntegrity(this.afnd);

            if (integrityState){
                autohideAlert("El Autómata es Válido.", 2000);
            }
            else {
                autohideAlert("El Autómata es inválido.", 2000 );
            }

        });

        this.inWordTF.textProperty().addListener((observable, oldValue, newValue) -> {

            System.out.println("Word: " + newValue);

            this.checkWordBtn.onActionProperty().setValue(e -> checkWord(newValue) );
        });


        // MATRIZ DE TRANSICIONES

        TreeItem<String> rootNode =
                new TreeItem<String>("Nodos");
        rootNode.setExpanded(true);

        ArrayList<String> lista = new ArrayList<>();

        for (int i=0; i<10; i++){
            TreeItem<String> leaf = new TreeItem<>("pico");
            rootNode.getChildren().add(leaf);
        }


    }

    /**
     * @param alphabet arreglo de String dividido por el caracter (;)
     * @return retorna un alfabeto valido en caso de si el input es correcto 7
     * y un alfabeto vacio de lo contrario.
     * También muestra una alerta.
     */
    private String[] checkAlphabet(String[] alphabet) {

        for (String a :
                alphabet) {
            if (a.length() > 1){
                genericAlert("No es un alfabeto válido, siga las instrucciones.");
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
                genericAlert("Palabra Valida", "Palabra Válida", "La palabra ingresada pertenece al lenguaje.");
            } else {
                genericAlert("Palabra invalida", "Palabra Invalida", "La palabra ingresada NO pertenece al autómata.");
            }
        }else{
            genericAlert("Formato Incorrecto", "La palabra ingresada no es valida", "");
        }
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
                    System.out.println(previous.getEstado());
                    String nameOfTheTransition = genericAlertInput(
                            "Ingrese el caracter de la Transición",
                            "Nodo Inicio: " + previous.getEstado() + " a Nodo llegada: " + circle.getEstado(),
                            "Caracter");

                    System.out.println("Transición: " + nameOfTheTransition);

                    if (nameOfTheTransition != null && nameOfTheTransition != "Ingrese caracter...") {
                        lineToConect = connect(previous,circle);
                        previous.addTransicion(new Transicion(circle, nameOfTheTransition)); // Adds a transition to the a Preview Node.
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

}
