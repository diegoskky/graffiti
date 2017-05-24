package sample;

import javafx.scene.control.Control;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

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
        this.estadoInicial = null;
    }

    public Afnd() {
        //this.alfabeto = alfabeto;
        this.estados = new ArrayList<>();
        this.estadoInicial = null;
    }

    public void setEstados(ArrayList<Nodo> estados) {
        this.estados = estados;
    }

    public String getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(String alfabeto) {
        this.alfabeto = alfabeto;
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

    /**
     * Funcion que consume una palabra y verifica si es valida
     * segun el lenguaje descrito por el automata
     * @param palabra String con la palabra a verificar
     * @return true si la palabra es valida
     */
    public boolean comprobarPalabra(String palabra){
        char letra;
        String aux = palabra;
        Nodo estadoActual = new Nodo();

        letra = palabra.charAt(0);
        aux = aux.substring(1);

        if(comprobarAlfabeto(letra)){
            if(estadoInicial!=null&&estadoInicial.comprobarTransicion(letra)){
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

    /**
     * Funcion que consume una palabra y verifica si es valida
     * segun el lenguaje descrito por el automata
     * @param palabra String con la palabra a verificar
     * @return true si la palabra es valida
     */
    public boolean comprobarPalabra2(String palabra){
        ArrayList<String> colaPalabras = new ArrayList<>();
        ArrayList<Nodo> colaNodos = new ArrayList<>();
        char letra;
        String aux, palabraActual;
        Nodo estadoActual = new Nodo();

        letra = palabra.charAt(0);
        aux = palabra.substring(1);
        if(comprobarAlfabeto(letra)){
            estadoActual = estadoInicial;
            palabraActual = aux;
            for(Transicion i : estadoActual.getTransiciones()){
                if(i.getTransiciones().contains(letra)){
                    colaNodos.add(i.getEstadoLlegada());
                    colaPalabras.add(aux);
                }
            }

            while(!aux.trim().isEmpty()){
                letra = aux.charAt(0);
                aux = aux.substring(1);
                while(colaPalabras.contains(palabraActual) && palabraActual != ""){
                    //System.out.println(colaPalabras);
                    for(Transicion i : colaNodos.get(colaPalabras.indexOf(palabraActual)).getTransiciones()){
                        if(i.getTransiciones().contains(letra) && comprobarAlfabeto(letra)){
                            colaNodos.add(i.getEstadoLlegada());
                            colaPalabras.add(aux);

                        }
                    }

                    colaNodos.remove(colaPalabras.indexOf(palabraActual));
                    colaPalabras.remove(colaPalabras.indexOf(palabraActual));
                }
                System.out.println(palabraActual+"!!");
                palabraActual = aux;
                System.out.println(colaPalabras+"???");
            }
        }
        System.out.println(colaPalabras);
        for(Nodo j : colaNodos){
            System.out.println(j.getEstado()+"???");
            if(j.getEsFinal()){
                return true;
            }
        }
        return false;
    }


    public void printTransiciones(){
        System.out.println(estados.size());
    }

    /**
     * Muerstra los nodos y sus transiciones por consola.
     */
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

    /**
     * Funcion usada para saber si existe algun nodo final o no
     * @return true si es que existe
     */
    public boolean existeFinal(){
        for(Nodo i : estados){
            if(i.getEsFinal()){
                return true;
            }
        }
        return false;
    }

    /**
     * Funcion que comprueba la integridad del automata
     * @return retorna true si es que esta correcto
     */
    public boolean comprobarAutomata(){
        ArrayList<String> visitados = new ArrayList<>();
        ArrayList<Nodo> cola = new ArrayList<>();
        Nodo revisando = new Nodo();

        if(estadoInicial != null && existeFinal()){
            cola.add(estadoInicial);
            visitados.add(estadoInicial.getEstado());

            while(!cola.isEmpty()){
                revisando = cola.get(0);
                cola.remove(0);
                if(revisando.getEsFinal()){
                    return true;
                }

                for(Transicion i : revisando.getTransiciones()){
                    if(!visitados.contains(i.getEstadoLlegada().getEstado())){
                        visitados.add(i.getEstadoLlegada().getEstado());
                        cola.add(i.getEstadoLlegada());
                    }
                }
            }
        }

        return false;
    }



    /**
     * Asigna una un alfabeto de caracteres (de uno en uno) desde una lista de String[]
     * con solo un caracter por posición en el arreglo.
     * @param alphabetValid un arreglo de String de solo una letra cada uno.
     */
    public void setAlfabeto(String[] alphabetValid) {

        String alphabet = new String("");
        for (int i = 0; i < alphabetValid.length; i++) {
            String s = alphabetValid[i];
            alphabet = alphabet + s;
        }
        this.alfabeto = alphabet;
    }

    /**
     * Retorna una lista con los estados
     * @return ArrayList<Nodo>
     */


    public ArrayList<Nodo> getEstados2(){
        ArrayList<Nodo> aux = this.estados;
        return aux;
    }

    public ArrayList<Nodo> getEstados(){
        return this.estados;
    }

    /*public ArrayList<String> getArrayEstados() {

        ArrayList<String> transitions = new ArrayList<>();

        if (estadoInicial != null) {
            transitions.add(estadoInicial.getEstado());
        }

        for (Nodo nodo : this.estados){

            String id = new String(nodo.getEstado());

            for (Transicion t : nodo.getTransiciones()) {
                id = id + " " + t.getTransiciones();
            }

            transitions.add(id);

        };

        return transitions;
    }*/
    /*
    public ArrayList<String> getArrayEstados(){
        ArrayList<String> lista = new ArrayList<>();
        String aux;
        String aux2;

        if(this.estadoInicial != null) {
            aux = this.estadoInicial.getEstado() + "=";
            for (Transicion i : this.estadoInicial.getTransiciones()) {
                aux2 = "(";
                for(Character j : i.getTransiciones()){
                    aux2 += j+",";
                }
                aux2 += ")";
                aux += " " + aux2 + i.getEstadoLlegada().getEstado() + " ;";
                aux2 = "";
            }
            lista.add(aux);
            aux = "";
        }

        if(this.estados != null) {
            for (Nodo i : this.estados) {
                aux = i.getEstado() + "= ";
                for (Transicion j : i.getTransiciones()) {
                    aux += " " + j.getTransiciones() + "(" + j.getEstadoLlegada().getEstado() + ") ;";
                }
                lista.add(aux);
                aux = "";
            }
        }
        return lista;
    }*/

    /**
     * Creates Strings with the nodes and it's transitions 
     * @return
     */
    public ArrayList<String> getArrayEstados() {
        ArrayList<String> lista = new ArrayList<>();
        StringBuilder aux = new StringBuilder();

        if (this.estadoInicial != null) {
            aux.append(this.estadoInicial.getEstado() + " →");
            for (Transicion i : this.estadoInicial.getTransiciones()) {
                aux.append(" " + i.getEstadoLlegada().getEstado() + i.getTransiciones().toString()+ " ;");
            }
            aux.deleteCharAt(aux.toString().length()-1);
            lista.add(aux.toString());
            aux.setLength(0);
            aux.trimToSize();
        }

        if (this.estados != null) {
            for (Nodo i : this.estados) {
                aux.append(i.getEstado() + " →");
                for (Transicion j : i.getTransiciones()) {
                    aux.append(" "  +  j.getEstadoLlegada().getEstado() +  j.getTransiciones().toString()+" ;");
                }
                aux.deleteCharAt(aux.toString().length()-1);
                lista.add(aux.toString());
                aux.setLength(0);
                aux.trimToSize();
            }
        }
        return lista;
    }

}
