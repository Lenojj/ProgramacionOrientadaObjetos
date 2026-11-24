package domains;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class GameLogic {

    /**
     * Establecemos el tamaño máximo de las listas
     */

    private String[] pilotos = new String[100];
    private String[] pistas = new String[100];

    private int cantidadPilotos = 0;
    private int cantidadPistas = 0;

    public GameLogic() throws IOException{

        /**
         * Método para leer archivos desde una carpeta raiz
         */

        try (Scanner lectorA = new Scanner(Paths.get("src/files/pilotos.csv"))) {

            while (lectorA.hasNextLine() && cantidadPilotos < 100) {

                String linea = lectorA.nextLine();

                if (!linea.isEmpty()) {

                    /**
                     * Si la linea es distinta a vacio agregamos
                     * la linea a la lista piloto
                     */

                    pilotos[cantidadPilotos] = linea;
                    cantidadPilotos++;
                }
            }
        }

        try (Scanner lectorA = new Scanner(Paths.get("src/files/pistas.csv"))) {

            while (lectorA.hasNextLine() && cantidadPistas < 100) {

                String linea = lectorA.nextLine();

                if (!linea.isEmpty()) {

                    pistas[cantidadPistas] = linea;
                    cantidadPistas++;
                }
            }
        }
    }

    //==================== Separacion Visual del codigo ====================

    public String[] buscarPiloto(String nombrePiloto) {

        /**
         * Método para buscar a un piloto por su nombre
         */

        for (int i = 0; i < cantidadPilotos; i++) {

            String[] datos = pilotos[i].split(",");

            /**
             * Comparamos el dato[0] con el nombre del piloto
             */

            if (datos[0].equalsIgnoreCase(nombrePiloto)) {

                if (datos.length >= 2) {

                    return datos[1].split(";");
                }

                //El piloto existe, pero no tiene karts
                return new String[0];
            }
        }

        //El piloto no existe
        return null;
    }

    /**
     * Método para almacenar todas las pistas
     */

    public String[] almacenPistas() {

        String[] resultados = new String[cantidadPistas];

        for (int i = 0; i < cantidadPistas; i++) {

            resultados[i] = pistas[i];
        }

        return resultados;
    }

    /**
     * Métodos para obtener: nombrePista, nombreKart y tipoKart
     */

    public String nombrePistas(String pista) {

        return pista.split(",")[0];
    }

    public String nombreKart(String kart) {

        return kart.split(":")[0];
    }

    public String tipoKart(String kart) {

        String[] datos = kart.split(":");

        if (datos.length > 1) {

            return datos[1];
        }

        return null;
    }

    public String obtenerTerrereno(String pista) {

        /**
         * Método para identificar el terreno de una pista
         */

        String[] datos = pista.split(",");

        for (String dato : datos) {

            switch (dato.trim().toLowerCase()) {

                case "asfalto":
                    return "Asfalto";

                case "hielo":
                    return "Hielo";

                case "tierra":
                    return "Tierra";

                case "mixto":
                    return "Mixto";

                default:
                    break;
            }
        }

        return "";
    }

    public double obtenerTiempo(String pista) {

        String[] datos = pista.split(",");

        for (int i = 1; i < datos.length; i++) {

            try {

                //Intentar convertir el campo a número
                double tiempo = Double.parseDouble(datos[i]);

                //Validamos que el tiempo sea mayor a 0 y máximo 300
                if (tiempo > 0 && tiempo <= 300) {

                    return tiempo;
                }

            } catch (NumberFormatException e) {

            }
        }

        return -1; //Si el tiempo no existe o no es valido
    }

    public double factor(String tipoKart, String terreno) {

        switch (tipoKart.toLowerCase()) {

            case "ligero":

                switch (terreno.toLowerCase()) {

                    case "hielo":
                    case "tierra":
                        return 0.85;

                    default:
                        return 1.05;
                }

            case "pesado":

                switch (terreno.toLowerCase()) {

                    case "asfalto":
                        return 0.85;

                    default:
                        return 1.05;
                }

            case "mediano":

                switch (terreno.toLowerCase()) {

                    case "mixto":
                        return 0.85;

                    default:
                        return 0.92;
                }

            default:

                return 1.05;
        }
    }

    //Obtener descripción del ajuste
    public String obtenerAjuste(double factor) {

        if (factor == 0.85) {

            return "Kart ideal -> -15%";
        }

        if (factor == 0.92) {

            return "Kart versátil -> -8%";
        }

        return "Kart no ideal -> +5%";
    }

    //Calcular el tiempo mínimo
    public double calcularTiempo(double tiempoBase, double factor) {

        return tiempoBase * factor;
    }
}