//Inicio
package prjestdatos;

//Librerias
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.JOptionPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

//Clase PrjEstDatos
public class PrjEstDatos {

    //Arreglos y variables globales
    static Tiquete nuevo = new Tiquete();
    static String nombreBanco;
    static int cantidadCajas;
    static int cajaPreferencial = 1;
    static Cola colaCajaPreferencial = new Cola("Caja Preferencial");//Fila
    static int cajaTramiteUnico = 1;
    static Cola colaCajaTramiteUnico = new Cola("Caja de Tramite Unico");//Fila
    static Cola[] cajas;//Filas
    static Cola ventanillaPreferencial = new Cola("Ventanilla Preferencial");//Ventanilla
    static Cola ventanillaTramiteUnico = new Cola("Ventanilla Tramite Unico");//Ventanilla
    static Cola[] ventanillaTramiteDos;//Ventanilla
    static boolean ocupado = false;

    //Main class inicio
    public static void main(String[] args) {
        //Verificación de existencia del archivo
        if (existenciaArchivo()) {
            cargarInformacion();
        } else {
            guardarConfiguracion();
            cargarInformacion();
        }
        crearCajas();
        crearVentanillas();
        programaGestion();
    }

    //Programa principal de gestión de tiquetes
    public static void programaGestion() {
        //Variables
        int opcionMenu = 0;

        //Menu
        while (opcionMenu != 5) {

            String textoMenu = "Gestión de tiquetes " + nombreBanco + "\n\n"
                    + "1. Creación de tiquetes \n"
                    + "2. Atención de tiquetes \n"
                    + "3. Reportes \n"
                    + "4. Tipo de cambio\n"
                    + "5. Salir \n\n";

            try {
                opcionMenu = Integer.parseInt(JOptionPane.showInputDialog(textoMenu + "Seleccione una opción: "));

                switch (opcionMenu) {

                    //Modulo de creación de tiquetes
                    case 1:
                        JOptionPane.showMessageDialog(null, "Bienvenido al modulo de creación de tiquetes.");
                        crearTiquete();
                        break;

                    //Modulo de atención de tiquetes    
                    case 2:
                        JOptionPane.showMessageDialog(null, "Bienvenido al modulo de atención de tiquetes.");
                        atencionTiquetes();
                        break;

                    //Modulo de reportes    
                    case 3:
                        Reportes.subMenu();
                        break;

                    //Modulo tipo de cambio
                    case 4:
                        JOptionPane.showMessageDialog(null, "Bienvenido al modulo de tipo de cambio.");
                        tipoCambio();
                        System.out.println("\n*******caja preferencial*********");
                        System.out.println(colaCajaPreferencial);
                        System.out.println("\n******caja tramite unico**********");
                        System.out.println(colaCajaTramiteUnico);
                        System.out.println("\n********caja tramite dos[0]********");
                        System.out.println(cajas[0]);
                        System.out.println("\n********caja tramite dos[1]********");
                        System.out.println(cajas[1]);
                        System.out.println("\n********caja tramite dos[2]********");
                        System.out.println(cajas[2]);

                        System.out.println("\n##########ventanilla preferencial#########");
                        System.out.println(ventanillaPreferencial);
                        System.out.println("\n##########ventanilla tramite unico#########");
                        System.out.println(ventanillaTramiteUnico);
                        System.out.println("\n##########caja tramite dos[0]##########");
                        System.out.println(ventanillaTramiteDos[0]);
                        System.out.println("\n##########caja tramite dos[1]##########");
                        System.out.println(ventanillaTramiteDos[1]);
                        System.out.println("\n##########caja tramite dos[2]##########");
                        System.out.println(ventanillaTramiteDos[2]);
                        break;

                    //Modulo de salida    
                    case 5:
                        JOptionPane.showMessageDialog(null, "Saliendo.");
                        break;
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Porfavor ingrese una opción valida.");
            }
        }
    }

    //Metodo para guardar información en un TXT
    public static void guardarConfiguracion() {
        try {
            nombreBanco = JOptionPane.showInputDialog("Ingrese el nombre de la identidad bancaria: ");
            cantidadCajas = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de cajas disponibles: "));
            cajaPreferencial = cajaPreferencial;
            cajaTramiteUnico = cajaTramiteUnico;

            BufferedWriter config = new BufferedWriter(new FileWriter("configuracion.txt"));
            config.write(nombreBanco);
            config.newLine();
            config.write(String.valueOf(cantidadCajas));
            config.newLine();
            config.write(String.valueOf(cajaPreferencial));
            config.newLine();
            config.write(String.valueOf(cajaTramiteUnico));
            config.newLine();
            config.close();

            JOptionPane.showMessageDialog(null, "Información: \n\n" + "Nombre de la identidad bancaria: " + nombreBanco + "\n" + "Cantidad de cajas disponibles: " + cantidadCajas + "\n" + "Cantidad de cajas preferenciales: " + cajaPreferencial + "\n" + "Cantidad de cajas de tramite unico: " + cajaTramiteUnico);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error, intentelo de nuevo.");
        }
    }

    //Metodo para verficar existencia del archivo
    public static boolean existenciaArchivo() {
        File archivo = new File("configuracion.txt");
        return archivo.exists();
    }

    //Metodo de carga de información
    public static void cargarInformacion() {
        if (existenciaArchivo()) {
            try {
                BufferedReader configExistencia = new BufferedReader(new FileReader("configuracion.txt"));

                nombreBanco = configExistencia.readLine();
                cantidadCajas = Integer.parseInt(configExistencia.readLine());
                cajaPreferencial = Integer.parseInt(configExistencia.readLine());
                cajaTramiteUnico = Integer.parseInt(configExistencia.readLine());
                configExistencia.close();

                JOptionPane.showMessageDialog(null, "Cargando base de datos.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //Metodo del tipo de tramite
    public static TipoTramite.tipoTramites seleccionarTramite() {
        //Submenu de tipo de tramite
        String textoTramite = "Seleccione el tipo de trámite: \n\n"
                + "1. Deposito: \n"
                + "2. Retiro: \n"
                + "3. Cambio de divisas:";

        try {
            int opcionTramite = Integer.parseInt(JOptionPane.showInputDialog(textoTramite));

            switch (opcionTramite) {

                case 1:
                    return TipoTramite.tipoTramites.Depositos;

                case 2:
                    return TipoTramite.tipoTramites.Retiros;

                case 3:
                    return TipoTramite.tipoTramites.CambioDivisas;

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Porfavor ingrese una opción valida.");
        }
        return null;
    }

    //Metodo del tipo de cliente
    public static TipoCliente.tipoClientes tipoCliente() {
        //Submenu de tipo de cliente
        String textoTipoCliente = "Seleccione el tipo de trámite: \n\n"
                + "1. Preferencial: \n"
                + "2. Tramite unico: \n"
                + "3. Multiples tramites:";

        try {
            int opcionTramite = Integer.parseInt(JOptionPane.showInputDialog(textoTipoCliente));

            switch (opcionTramite) {

                case 1:
                    return TipoCliente.tipoClientes.Preferencial;

                case 2:
                    return TipoCliente.tipoClientes.UnTramite;

                case 3:
                    return TipoCliente.tipoClientes.TramiteMultiple;

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Porfavor ingrese una opción valida.");
        }
        return null;
    }

    //Metodo para agregar cliente en cola
    public static void crearTiquete() {
        //Pedido de los datos
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del cliente: ");
        int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cedula del cliente: "));
        int edad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad del cliente: "));

        //Seteo de la hora de creación del tiquete
        String horaCreacion = nuevo.horaCreacion();
        JOptionPane.showMessageDialog(null, "Guardando fecha y hora.");

        //Seteo del tipo de tramite y cliente
        nuevo.setTipoTramite(seleccionarTramite());
        nuevo.setTipoCliente(tipoCliente());

        //Crear tiquete
        nuevo = new Tiquete(nombre, id, edad, horaCreacion, nuevo.getTipoTramite(), nuevo.getTipoCliente());

        //Asignacion de caja
        if (nuevo.getTipoCliente() == TipoCliente.tipoClientes.Preferencial) {
            //Agregar a la cola
            colaCajaPreferencial.agregarCliente(nuevo);
            //Se busca la posicion actual
            colaCajaPreferencial.posicionPreferencial(nuevo);

        } else if (nuevo.getTipoCliente() == TipoCliente.tipoClientes.UnTramite) {
            //Agregar a la cola
            colaCajaTramiteUnico.agregarCliente(nuevo);
            //Se busca la posicion actual
            colaCajaTramiteUnico.posicionPreferencial(nuevo);

        } else if (nuevo.getTipoCliente() == TipoCliente.tipoClientes.TramiteMultiple) {
            //Buscar la fila con menos clientes y agregar a la cola
            Cola colaMenor = recorrerCajasMenosClientes();
            colaMenor.agregarCliente(nuevo);
            //Se busca la posicion actual
            colaMenor.posicionTramiteMultiple(nuevo);
        }
    }

    //Metodo Web Scrapping
    public static void tipoCambio() {
        String url = "https://www.bccr.fi.cr/SitePages/Inicio.aspx";

        try {
            Document doc = Jsoup.connect(url).get();

            Element tipoCambioCompra = doc.selectFirst("#D317");
            Element tipoCambioVenta = doc.selectFirst("#D318");

            if (tipoCambioCompra != null && tipoCambioVenta != null) {
                String mensaje = "Tipo de cambio del Dólar:\n"
                        + "Compra USD: " + tipoCambioCompra.text() + "\n"
                        + "Venta USD: " + tipoCambioVenta.text();
                JOptionPane.showMessageDialog(null, mensaje);
            } else {
                JOptionPane.showMessageDialog(null, "Error: no se pudo encontrar el tipo de cambio");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: no se pudo conectar\n" + e.getMessage());
        }
    }

    //Metodo de crear cajas
    public static void crearCajas() {
        cajas = new Cola[cantidadCajas];

        for (int i = 0; i < cantidadCajas; i++) {
            String etiquetaCaja = "Caja " + (i + 1);
            cajas[i] = new Cola(etiquetaCaja);
        }
    }

    public static void crearVentanillas() {
        ventanillaTramiteDos = new Cola[cantidadCajas];

        for (int i = 0; i < cantidadCajas; i++) {
            String etiquetaCaja = "Ventanilla " + (i + 1);
            ventanillaTramiteDos[i] = new Cola(etiquetaCaja);
        }
    }

    //Metodo de recorrer cajas
    public static Cola recorrerCajasMenosClientes() {
        Cola cajaMenosClientes = cajas[0];
        int menosClientes = cajaMenosClientes.clientesEnCola();

        for (int i = 0; i < cantidadCajas; i++) {
            if (cajas[i].clientesEnCola() < menosClientes) {
                menosClientes = cajas[i].clientesEnCola();
                cajaMenosClientes = cajas[i];
            }
        }
        return cajaMenosClientes;
    }

    public static Cola recorrerCajasMasClientes() {
        Cola cajaMasClientes = cajas[0];
        int masClientes = cajaMasClientes.clientesEnCola();

        for (int i = 0; i < cantidadCajas; i++) {
            if (cajas[i].clientesEnCola() > masClientes) {
                masClientes = cajas[i].clientesEnCola();
                cajaMasClientes = cajas[i];
            }
        }
        return cajaMasClientes;
    }

    public static int indiceCajasMasClientes() {
        Cola cajaMasClientes = cajas[0];
        int masClientes = cajaMasClientes.clientesEnCola();
        int indice = 0;
        for (int i = 0; i < cantidadCajas; i++) {
            if (cajas[i].clientesEnCola() > masClientes) {
                masClientes = cajas[i].clientesEnCola();
                cajaMasClientes = cajas[i];
                indice = i;
            }
        }
        return indice;
    }

    //Metodo de atención de tiquetes   
    public static void atencionTiquetes() {
        //Variables
        int opcionMenu = 0;

        //Menu
        while (opcionMenu != 4) {

            String textoMenu = "Atención de tiquetes \n\n"
                    + "1. Caja preferencial \n"
                    + "2. Caja de un solo tramite \n"
                    + "3. Cajas de multiples tramites \n"
                    + "4. Salir \n\n";

            try {
                opcionMenu = Integer.parseInt(JOptionPane.showInputDialog(textoMenu + "Seleccione una opción: "));

                switch (opcionMenu) {

                    //Modulo de caja preferencial
                    case 1:
                        JOptionPane.showMessageDialog(null, "Bienvenido al modulo de atención de caja preferencial.");
                        Tiquete temporal;

                        if (!colaCajaPreferencial.esVacia() && ocupado == false) {
                            ocupado = true;
                            temporal = colaCajaPreferencial.eliminar();
                            temporal.horaAtencion();
                            JOptionPane.showMessageDialog(null, temporal.toString());
                            ventanillaPreferencial.agregarCliente(temporal);
                            
                            //prueba de reportes
                            Reportes.escribir(temporal, colaCajaPreferencial.getId()); 
                            
                            ocupado = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "Caja ocupada.");
                        }
                        break;

                    //Modulo de caja unica   
                    case 2:
                        JOptionPane.showMessageDialog(null, "Bienvenido al modulo de atención de caja tramite unico.");
                        if (!colaCajaTramiteUnico.esVacia() && ocupado == false) {
                            ocupado = true;
                            temporal = colaCajaTramiteUnico.eliminar();
                            temporal.horaAtencion();
                            JOptionPane.showMessageDialog(null, temporal.toString());
                            ventanillaTramiteUnico.agregarCliente(temporal);
                            
                            //prueba # 2 
                                Reportes.escribir(temporal, colaCajaTramiteUnico.getId()); 
                            ocupado = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "Caja ocupada.");
                        }
                        break;

                    //Modulo de cajas multiples    
                    case 3:
                        JOptionPane.showMessageDialog(null, "Bienvenido al modulo de atención de caja tramite dos o mas.");
                        Cola colaMayor = recorrerCajasMasClientes();
                        int indice = indiceCajasMasClientes();
                        if (!colaMayor.esVacia() && ocupado == false) {
                            ocupado = true;
                            temporal = colaMayor.eliminar();
                            temporal.horaAtencion();
                            JOptionPane.showMessageDialog(null, temporal.toString());
                            ventanillaTramiteDos[indice].agregarCliente(temporal); //<<<<<<<<<<<<<<<<<<<<<<<<<<
                            
                          //  prueba #3
                                  Reportes.escribir(temporal, colaMayor.getId()); 
                            ocupado = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "Caja ocupada.");
                        }
                        break;

                    case 5:
                        JOptionPane.showMessageDialog(null, "Saliendo.");
                        break;
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Porfavor ingrese una opción valida.");
            }
        }
    }
}
