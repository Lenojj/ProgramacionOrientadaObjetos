package domains;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameLogic {
    /*
    *
    * Establecemos el tamaño máximo de las listas 
    */

    private String[] pilotos = new String[100];
    private String[] pistas = new String[100];

    private int cantidadPilotos = 0;
    private int cantidadPistas = 0;

    public GameLogic() throws IOException{

        /*
        * Método para leer archivos desde una carpeta raiz
        */

        try (Scanner lectorA = new Scanner(Paths.get("src/files/pilotos.cvs"))) {

            while (lectorA.hasNextLine() && cantidadPilotos < 100) {
                String linea = lectorA.nextLine();

                if (!linea.isEmpty()) {

                    /*
                     * Si la linea es distinto a vacio agreamos la linea a la lista piloto
                     */

                    pilotos[cantidadPilotos] = linea; 
                    cantidadPilotos++; //Cada linea representa a un piloto, por lo tanto si en el archivo CSV hay 5 lineas significa que hay 5 pilotos
                }
            }
        }

        try (Scanner lectorA = new Scanner(Paths.get("src/files/pistas.cvs"))) {

            while (lectorA.hasNextLine() && cantidadPilotos < 100) { 

                String linea = lectorA.nextLine();

                if (!linea.isEmpty()) {

                    pistas[cantidadPistas] = linea;
                    cantidadPistas++;
                }
            }
        }
    }

    //============================================= Separacion Visual del codigo =============================================================

  public String[] buscarPilotos(String nombrePiloto) {

        /*
        * Método para buscar a un piloto por du nombre 
        */
        
        for (int i = 0; i < cantidadPilotos; i++) { 

            String[] datos = pilotos[i].split(","); 

            /*
            * Condicion que nos permite comparar el dato[0] (que en este caso seria el piloto) con el nombre del piloto
            */

            if (datos[0].equalsIgnoreCase(nombrePiloto)) { 

                if (datos.length >= 2 ) { //Validamos que el archivo cuente con los datos necesarios
                    return datos[1].split(";");
                }
            }
        }
            return null;

            /* Otra opcion seria:
            * if (datos[0].equalsIgnoreCase(nombrePiloto)) { 
            *        return datos[1].split(";");
            *    }      
            */
    }

    /*
     * Metodo para almacenar todas las pistas
     */

    
    public String[] encontrarPistas() {

        String [] resultados = new String[cantidadPistas];

        for (int i = 0; i < cantidadPistas; i ++) {

            resultados[i] = pistas[i];
        }
        return resultados;
    }
}
