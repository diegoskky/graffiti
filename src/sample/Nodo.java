package sample;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Nodo extends Circle {
    private String estado;
    private boolean esFinal;
    private ArrayList<Transicion> transiciones;

    public Nodo(){
        this.estado = " ";
        this.esFinal = false;
        this.transiciones = new ArrayList<>();
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean getEsFinal() {
        return esFinal;
    }

    public void setEsFinal(boolean esFinal) {
        this.esFinal = esFinal;
    }

    public void addTransicion(Transicion nuevo){
        this.transiciones.add(nuevo);
    }

    public boolean comprobarTransicion(char aux){
        for(Transicion i : transiciones){
            if(aux == i.getTransicion()){
                return true;
            }
        }
        return false;
    }

    public int getSize(){
        return transiciones.size();
    }

    public int getIndiceTransicion(char aux){
        for(int i = 0; i < transiciones.size(); i++){
            if(aux == transiciones.get(i).getTransicion()){
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Transicion> getTransiciones(){
        return transiciones;
    }

    public Nodo getEstadoLlegada(char aux){
        return transiciones.get(getIndiceTransicion(aux)).getEstadoLlegada();
    }

}
