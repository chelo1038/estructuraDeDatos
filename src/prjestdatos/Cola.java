//Inicio
package prjestdatos;

//Clase cola
import javax.swing.JOptionPane;

public class Cola {
    
    //Atributos
    private Nodo frente, ultimo;
    private String id;

    //Constructor de cajas
    public Cola(String id) {    
        this.id = id;
    }

    //Getters And Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    //Metodos
    
    //Metodo es Vacia
    public boolean esVacia() {
        return frente == null;
    }

    //Metodo insertar Cliente
    public void agregarCliente(Tiquete nuevoCliente) {
        Nodo clienteEspera = new Nodo(nuevoCliente);

        if (this.esVacia()) {
            frente = ultimo = clienteEspera;
        } else {
            ultimo.setSiguiente(clienteEspera);
            ultimo = clienteEspera;
        }

        JOptionPane.showMessageDialog(null, clienteEspera);
    }

    //Metodo para eliminar de las colas
    public Tiquete eliminar() {
        Tiquete eliminado = null;

        if (!this.esVacia()) {
            eliminado = frente.getCliente();
            frente = frente.getSiguiente();
        }
        return eliminado;
    }

    //Metodo de posicion preferencial
    public int posicionPreferencial(Tiquete espacioCola) {
        int posicion = 1;

        if (!this.esVacia()) {
            Nodo aux = frente;
            
            while (aux != null) {
                if (aux.getCliente() == espacioCola) {
                    if (aux == frente) {
                        JOptionPane.showMessageDialog(null, "***CAJA PREFERENCIAL*** \n Es su turno de ser atentido.");
                    } else {
                        JOptionPane.showMessageDialog(null, "***CAJA PREFERENCIAL*** \n Turno: " + posicion + "\nClientes por atender: " + (posicion - 1));
                    }
                    return posicion;
                }
                aux = aux.getSiguiente();
                posicion++;
            }
        }
        return -1;
    }

    //Metodo de posicion tramite unico
    public int posicionTramiteUnico(Tiquete espacioCola) {
        int posicion = 1;

        if (!this.esVacia()) {
            Nodo aux = frente;
            ;
            while (aux != null) {
                if (aux.getCliente() == espacioCola) {
                    if (aux == frente) {
                        JOptionPane.showMessageDialog(null, "***CAJA DE TRAMITES UNICOS*** \n Es su turno de ser atentido.");
                    } else {
                        JOptionPane.showMessageDialog(null, "***CAJA DE TRAMITES UNICOS*** \n Turno: " + posicion + "\nClientes por atender: " + (posicion - 1));
                    }
                    return posicion;
                }
                aux = aux.getSiguiente();
                posicion++;
            }
        }
        return -1;
    }
    
   //Metodo de posicion tramite multiple
    public int posicionTramiteMultiple(Tiquete espacioCola) {
        int posicion = 1;

        if (!this.esVacia()) {
            Nodo aux = frente;
            while (aux != null) {
                if (aux.getCliente() == espacioCola) {
                    if (aux == frente) {
                        JOptionPane.showMessageDialog(null, "***" + this.id + "*** \n Es su turno de ser atentido.");
                    } else {
                        JOptionPane.showMessageDialog(null, "***" + this.id + "*** \n Turno: " + posicion + "\nClientes por atender: " + (posicion - 1));
                    }
                    return posicion;
                }
                aux = aux.getSiguiente();
                posicion++;
            }
        }
        return -1;
    }
    
    //Metodo de cantidad de clientes en cola
    public int clientesEnCola () {
        int contador = 0;

        if (!this.esVacia()) {
            Nodo aux = frente;
            while (aux != null) {
               contador++;     
               aux = aux.getSiguiente();
            }
        }
        return contador;
    }
    
    @Override
    public String toString() {
        String r = "Cola{\n";
        if (this.esVacia()) {
            r += "Vacia\n}";
        } else {
            Nodo aux = frente;
            while (aux != null) {
                r += aux + "\n";
                aux = aux.getSiguiente();
            }
            r += "}";
        }
        return r;
    }
}
