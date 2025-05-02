//INICIO
package prjestdatos;

//Librerias 
import java.io.*;
import javax.swing.JOptionPane;

//Clase Reportes
public class Reportes {
    //Archivo
    public static String archivo = "Reportes.txt";

    //Metodo escribir
    public static void escribir(Tiquete tiquete, String idCaja) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {

            //separa los datos  
            String linea = tiquete.getNombre() + ","
                    + tiquete.getId() + ","
                    + tiquete.getEdad() + ","
                    + tiquete.getHoraCreacion() + ","
                    + tiquete.getHoraAtencion() + ","
                    + tiquete.getTipoTramite() + ","
                    + tiquete.getTipoCliente() + ","
                    + idCaja;

            writer.newLine();
            writer.write(linea);

            //comprobar 
            System.out.println("Transacción agregada correctamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al escribir." + e.getMessage());
        }

    }

    //Metodo cajaMayorClientess
    public static String cajaMayorClientes() {

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {

            String linea;
            String[] nombreCajas = new String[100];
            // Arreglos en paralelo
            int[] conteo = new int[100];
            // contador de cajas
            int numCajas = 0;

            while ((linea = reader.readLine()) != null) {
                //Filtrar con las que fueron atendidas
                if (!linea.isEmpty() && !linea.split(",")[4].equals("-1")) {
                    String[] datos = linea.split(",");
                    String idCaja = datos[7];
                    boolean encontrada = false;
                    for (int i = 0; i < numCajas; i++) {
                        if (nombreCajas[i].equals(idCaja)) {
                            //Si lacaja existe aumenta 
                            conteo[i] = conteo[i] + 1;
                            encontrada = true;
                            break;
                        }

                    }
                    if (!encontrada) {
                        //Registra la nueva caja si no la encontro
                        nombreCajas[numCajas] = idCaja;
                        conteo[numCajas] = 1;
                        numCajas++;
                    }
                }

            }
            if (numCajas == 0) {
                return "No hay transacciones registradas.";
            } else {
                int maxClientes = 0;
                String cajasMax = "";
                for (int i = 0; i < numCajas; i++) {
                    if (conteo[i] > maxClientes) {
                        maxClientes = conteo[i];
                        cajasMax = nombreCajas[i];
                    } else if (conteo[i] == maxClientes) {
                        cajasMax = cajasMax + " y " + nombreCajas[i];
                    }

                }
                return " La caja que atendio mas fue  " + cajasMax + ",  atendio a: " + maxClientes + "  cliente ";
            }

        } catch (IOException e) {
            return "Error al leer: " + e.getMessage();
        }

    }

    //Metodo totalClientes
    public static String totalClientes() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int total = 0;

            while ((linea = reader.readLine()) != null) {
                if (!linea.isEmpty() && !linea.split(",")[4].equals("-1")) {
                    total = total + 1;

                }
            }

            if (total == 0) {
                return "No hay transacciones.";
            } else {
                return "El total de clientes atendidos es de: " + total;
            }

        } catch (IOException e) {
            return "Error al leer el archivo" + e.getMessage();
        }

    }

    //Metodo cajaMenorTiempoPromedio
    public static String cajaMejorTiempoPromedio() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            String[] nombresCajas = new String[100];
            double[] tiemposTotales = new double[100];
            int[] conteos = new int[100];
            int numCajas = 0;

            while ((linea = reader.readLine()) != null) {
                if (!linea.isEmpty() && !linea.split(",")[4].equals("-1")) {
                    String[] datos = linea.split(",");
                    String idCaja = datos[7];
                    //Se extrae solo hora, minutos y segundos 
                    String horaCreacion = datos[3].split(" ")[1];
                    String horaAtencion = datos[4].split(" ")[1];

                    //Convertir horaCreacion a segundos
                    String[] creacionPartes = horaCreacion.split(":");
                    int creacionSegundos = Integer.parseInt(creacionPartes[0]) * 3600
                            + Integer.parseInt(creacionPartes[1]) * 60
                            + Integer.parseInt(creacionPartes[2]);

                    //Convertir horaAtencion a segundos
                    String[] atencionPartes = horaAtencion.split(":");
                    int atencionSegundos = Integer.parseInt(atencionPartes[0]) * 3600
                            + Integer.parseInt(atencionPartes[1]) * 60
                            + Integer.parseInt(atencionPartes[2]);

                    double tiempo = atencionSegundos - creacionSegundos;

                    //Agrupar por caja
                    boolean encontrada = false;
                    for (int i = 0; i < numCajas; i++) {
                        if (nombresCajas[i].equals(idCaja)) {
                            tiemposTotales[i] = tiemposTotales[i] + tiempo;
                            conteos[i] = conteos[i] + 1;
                            encontrada = true;
                            break;
                        }
                    }
                    if (!encontrada) {
                        nombresCajas[numCajas] = idCaja;
                        tiemposTotales[numCajas] = tiempo;
                        conteos[numCajas] = 1;
                        numCajas++;
                    }
                }
            }

            if (numCajas == 0) {
                return "No hay transacciones atendidas.";
            } else {
                double mejorPromedio = -1;
                String mejorCaja = "";
                for (int i = 0; i < numCajas; i++) {
                    double promedio = tiemposTotales[i] / conteos[i];
                    if (mejorPromedio == -1 || promedio < mejorPromedio) {
                        mejorPromedio = promedio;
                        mejorCaja = nombresCajas[i];

                    }
                }
                return mejorCaja + " con " + mejorPromedio + " segundos promedio.";
            }

        } catch (IOException e) {
            return "Error al leer: " + e.getMessage();
        }
    }

    //Metodo tiempoPromedioGeneral
    public static String tiempoPromedioGeneral() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            double tiempoTotal = 0;
            int conteo = 0;

            while ((linea = reader.readLine()) != null) {
                if (!linea.isEmpty() && !linea.split(",")[4].equals("-1")) {
                    String[] datos = linea.split(",");
                    String horaCreacion = datos[3].split(" ")[1];
                    String horaAtencion = datos[4].split(" ")[1];

                    String[] creacionPartes = horaCreacion.split(":");
                    int creacionSegundos = Integer.parseInt(creacionPartes[0]) * 3600
                            + Integer.parseInt(creacionPartes[1]) * 60
                            + Integer.parseInt(creacionPartes[2]);

                    String[] atencionPartes = horaAtencion.split(":");
                    int atencionSegundos = Integer.parseInt(atencionPartes[0]) * 3600
                            + Integer.parseInt(atencionPartes[1]) * 60
                            + Integer.parseInt(atencionPartes[2]);

                    double tiempo = atencionSegundos - creacionSegundos;
                    tiempoTotal = tiempoTotal + tiempo;
                    conteo = conteo + 1;
                }
            }

            if (conteo == 0) {
                return "No hay transacciones atendidas.";
            } else {
                double promedio = tiempoTotal / conteo;
                return "Tiempo promedio general: " + promedio + " segundos.";
            }
        } catch (IOException e) {
            return "Error al leer: " + e.getMessage();
        }
    }

    //Submenu
    public static void subMenu() {

        int ejecutar = 0;

        while (ejecutar != 5) {

            String mensaje = "Bienvenido al módulo de reportes, favor seleccione una opción\n\n"
                    + "1. Cuál caja es la que atendió mayor cantidad de clientes.\n"
                    + "2. Cuál fue el total de clientes atendidos.\n"
                    + "3. Cuál caja tiene el mejor tiempo de atención promedio de tiquetes.\n"
                    + "4. Cuál es el tiempo promedio de atención en general (todas las cajas).\n"
                    + "5. Salir\n\n";

            try {
                ejecutar = Integer.parseInt(JOptionPane.showInputDialog(mensaje + "Seleccione una opción: "));

                switch (ejecutar) {
                    case 1:
                        JOptionPane.showMessageDialog(null, cajaMayorClientes());

                        break;

                    case 2:
                        JOptionPane.showMessageDialog(null, totalClientes());

                        break;

                    case 3:
                        JOptionPane.showMessageDialog(null, cajaMejorTiempoPromedio());

                        break;

                    case 4:
                        JOptionPane.showMessageDialog(null, tiempoPromedioGeneral());

                        break;

                    case 5:
                        ejecutar = 5;
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "opción no valida, por favor seleccione una opcion entre 1 y 5");
                        break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "por favor, ingrese un numero valido");
            }
        }
    }
}
