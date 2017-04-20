package sample;

public class Transicion {
    private char transicion;
    private Nodo estadoLlegada;

    public Transicion(Nodo llegada){
        this.transicion = ' ';
        this.estadoLlegada = llegada;
    }

    public char getTransicion(){
        return transicion;
    }

    public void setTransicion(char transicion){
        this.transicion = transicion;
    }

    public Nodo getEstadoLlegada(){
        return estadoLlegada;
    }
}
