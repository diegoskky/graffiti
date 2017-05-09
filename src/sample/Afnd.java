package sample;

import java.util.ArrayList;

/**
 * It represents a non-deterministic finite automata.
 */
public class Afnd {
    private String alfabeto;
    private Nodo estadoInicial;
    private ArrayList<Nodo> estados;

    public Afnd(String alfabeto){
        this.alfabeto = alfabeto;
        this.estados = new ArrayList<>();
        this.estadoInicial = new Nodo();
    }

    public Nodo getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(Nodo estadoInicial) {//agregar
        this.estadoInicial = estadoInicial;
    }

    public void addEstado(Nodo nuevo){
        this.estados.add(nuevo);
    }

    public boolean comprobarAlfabeto(char letra){
        if(alfabeto.contains(Character.toString(letra))){
            return true;
        }
        return false;
    }

    public boolean comprobarPalabra(String palabra){
        char letra;
        String aux = palabra;
        Nodo estadoActual = new Nodo();

        letra = palabra.charAt(0);
        aux = aux.substring(1);

        if(comprobarAlfabeto(letra)){
            if(estadoInicial.comprobarTransicion(letra)){
                estadoInicial.getIndiceTransicion(letra);
                estadoActual = estadoInicial.getEstadoLlegada(letra);
                System.out.println("esta aqui: "+estadoActual.getEstado());

                while(!aux.trim().isEmpty()){
                    letra = aux.charAt(0);
                    aux = aux.substring(1);

                    if(estadoActual.comprobarTransicion(letra)){
                        estadoActual.getIndiceTransicion(letra);
                        estadoActual = estadoActual.getEstadoLlegada(letra);
                        System.out.println("esta aqui: "+estadoActual.getEstado());
                    }else{
                        return false;
                    }
                }
            }
        }

        if(estadoActual.getEsFinal()){
            return true;
        }
        return false;
    }

    public void matrizTransiciones(){
        ArrayList<Transicion> lista = new ArrayList<>();
        lista = estadoInicial.getTransiciones();
        System.out.print(estadoInicial.getEstado()+": ");
        for(Transicion i : lista){
            System.out.print(i.getEstadoLlegada().getEstado()+"; ");
        }
        System.out.println();
        for(Nodo j : estados){
            lista = j.getTransiciones();
            System.out.print(j.getEstado()+": ");
            for(Transicion k : lista){
                System.out.print(k.getEstadoLlegada().getEstado()+"; ");
            }
            System.out.println();
        }
    }


}
