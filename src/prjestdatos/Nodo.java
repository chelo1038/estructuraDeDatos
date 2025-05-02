//Inicio
package prjestdatos;

//Clase Nodo
public class Nodo {
    //Atributos
    private Tiquete tiquete;
    private Nodo siguiente;

    //Constructor
    public Nodo(Tiquete tiquete) {
        this.tiquete = tiquete;
    }
    
    //Getters And Setters
    public Tiquete getCliente() {
        return tiquete;
    }

    public void setCliente(Tiquete cliente) {
        this.tiquete = cliente;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
    
    //ToString
    @Override
    public String toString() {
        return "Nodo: \n\n" + "Cliente: " + tiquete;
    }
}