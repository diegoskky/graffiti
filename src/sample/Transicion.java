
package sample;

import java.util.ArrayList;


public class Transicion {
    private ArrayList<Character> transiciones;
    private Nodo estadoLlegada;

    public Transicion(Nodo llegada){
        this.transiciones = new ArrayList<>();
        this.estadoLlegada = llegada;
    }

    public Transicion(Nodo llegada, String nameOfTheTransition) {
        this.transiciones = new ArrayList<>();
        this.estadoLlegada = llegada;

        transiciones.add(nameOfTheTransition.charAt(0)); // todo solo lee una wea.


    }



    public ArrayList<Character> getTransiciones(){
        return transiciones;
    }

    public void addTransicion(char transicion){
        transiciones.add(transicion);
    }

    public Nodo getEstadoLlegada(){
        return estadoLlegada;
    }
}
