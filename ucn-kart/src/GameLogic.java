/**
 * Clase encargada de administrar los pilotos,
 * karts, pistas y calculos del programa.
 */
public class GameLogic {

    private String[] pilotos = new String[100];
    private String[] pistas = new String[100];

    private int cantidadPilotos = 0;
    private int cantidadPistas = 0;

    /**
     * Constructor encargado de cargar los archivos CSV.
     */
    public GameLogic(){

        In lectorA = new In("src/files/pilotos.csv");

        while (lectorA.hasNextLine() && cantidadPilotos < 100){

            String linea = lectorA.readLine();

            if (!linea.isEmpty()){

                pilotos[cantidadPilotos] = linea;
                cantidadPilotos++;
            }
        }

        lectorA.close();

        In lectorB = new In("src/files/pistas.csv");

        while (lectorB.hasNextLine() && cantidadPistas < 100){

            String linea = lectorB.readLine();

            if (!linea.isEmpty()){

                pistas[cantidadPistas] = linea;
                cantidadPistas++;
            }
        }

        lectorB.close();
    }

    /**
     * Método para buscar un piloto y obtener sus karts.
     * @param nombrePiloto Nombre del piloto.
     * @return Arreglo de karts o null si no existe.
     */
    public String[] buscarPiloto(String nombrePiloto){

        for (int i = 0; i < cantidadPilotos; i++){

            String[] datos = pilotos[i].split(",");

            if (datos[0].equalsIgnoreCase(nombrePiloto)){

                if (datos.length >= 2){

                    return datos[1].split(";");
                }

                return new String[0];
            }
        }

        return null;
    }

    /**
     * Método para registrar un nuevo piloto.
     * @param nombre Nombre del piloto.
     * @return true si se registro correctamente.
     */
    public boolean registrarPiloto(String nombre){

        nombre = nombre.trim();

        if (cantidadPilotos >= 100 || nombre.isEmpty()
                || nombre.contains(",")){

            return false;
        }

        if (buscarPiloto(nombre) != null){

            return false;
        }

        pilotos[cantidadPilotos] = nombre + ",";
        cantidadPilotos++;

        return true;
    }

    /**
     * Método para obtener las pistas disponibles.
     * @return Arreglo de pistas.
     */
    public String[] almacenPistas(){

        String[] resultados = new String[cantidadPistas];

        for (int i = 0; i < cantidadPistas; i++){

            resultados[i] = pistas[i];
        }

        return resultados;
    }

    /**
     * Método para obtener el nombre de una pista.
     * @param pista Datos de la pista.
     * @return Nombre de la pista.
     */
    public String nombrePistas(String pista){

        return pista.split(",")[0];
    }

    /**
     * Método para obtener la copa de una pista.
     * @param pista Datos de la pista.
     * @return Nombre de la copa.
     */
    public String obtenerCopa(String pista){

        String[] datos = pista.split(",");

        if (datos.length >= 2){

            return datos[1];
        }

        return "";
    }

    /**
     * Método para obtener el terreno de una pista.
     * @param pista Datos de la pista.
     * @return Tipo de terreno.
     */
    public String obtenerTerrereno(String pista){

        String[] datos = pista.split(",");

        if (datos.length >= 4){

            return datos[3].trim();
        }

        return "";
    }

    /**
     * Método para obtener el tiempo base de una pista.
     * @param pista Datos de la pista.
     * @return Tiempo base o -1 si no es valido.
     */
    public double obtenerTiempo(String pista){

        String[] datos = pista.split(",");

        if (datos.length >= 3){

            try {

                double tiempo = Double.parseDouble(datos[2]);

                if (tiempo > 0 && tiempo <= 300){

                    return tiempo;
                }

            } catch (NumberFormatException e){

                return -1;
            }
        }

        return -1;
    }

    /**
     * Método para obtener el nombre de un kart.
     * @param kart Datos del kart.
     * @return Nombre del kart.
     */
    public String nombreKart(String kart){

        return kart.split(":")[0];
    }

    /**
     * Método para obtener la categoria de un kart.
     * @param kart Datos del kart.
     * @return Categoria del kart.
     */
    public String tipoKart(String kart){

        String[] datos = kart.split(":");

        if (datos.length >= 2){

            return datos[1];
        }

        return null;
    }

    /**
     * Método para agregar un kart a un piloto.
     * @param nombrePiloto Nombre del piloto.
     * @param nombreKart Nombre del nuevo kart.
     * @param tipoKart Categoria del kart.
     * @return true si se agrego correctamente.
     */
    public boolean agregarKart(String nombrePiloto,
                               String nombreKart,
                               String tipoKart){

        String[] karts = buscarPiloto(nombrePiloto);

        nombreKart = nombreKart.trim();
        tipoKart = tipoKart.trim();

        //Validamos la cantidad de karts
        if (karts == null || karts.length >= 10){

            return false;
        }

        //Validamos el nombre
        if (nombreKart.isEmpty() || nombreKart.contains(",")
                || nombreKart.contains(":")
                || nombreKart.contains(";")){

            return false;
        }

        //Validamos la categoria
        if (tipoKart.equalsIgnoreCase("Ligero")){

            tipoKart = "Ligero";
        }

        else if (tipoKart.equalsIgnoreCase("Mediano")){

            tipoKart = "Mediano";
        }

        else if (tipoKart.equalsIgnoreCase("Pesado")){

            tipoKart = "Pesado";
        }

        else {

            return false;
        }

        //Comprobamos que el kart no exista
        for (int i = 0; i < karts.length; i++){

            if (nombreKart(karts[i]).equalsIgnoreCase(nombreKart)){

                return false;
            }
        }

        //Buscamos al piloto dentro del arreglo
        for (int i = 0; i < cantidadPilotos; i++){

            String[] datos = pilotos[i].split(",");

            if (datos[0].equalsIgnoreCase(nombrePiloto)){

                if (karts.length == 0){

                    pilotos[i] = datos[0] + ","
                            + nombreKart + ":" + tipoKart;
                }

                else {

                    pilotos[i] = pilotos[i] + ";"
                            + nombreKart + ":" + tipoKart;
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Método para obtener el factor de rendimiento.
     * @param tipoKart Categoria del kart.
     * @param terreno Terreno de la pista.
     * @return Factor correspondiente o -1 si no es valido.
     */
    public double factor(String tipoKart, String terreno){

        if (tipoKart == null || terreno == null){

            return -1;
        }

        switch (tipoKart.toLowerCase()){

            case "ligero":

                switch (terreno.toLowerCase()){

                    case "hielo":
                    case "tierra":

                        return 0.85;

                    case "asfalto":
                    case "mixto":

                        return 1.05;

                    default:

                        return -1;
                }

            case "mediano":

                switch (terreno.toLowerCase()){

                    case "mixto":

                        return 0.85;

                    case "asfalto":
                    case "hielo":
                    case "tierra":

                        return 0.92;

                    default:

                        return -1;
                }

            case "pesado":

                switch (terreno.toLowerCase()){

                    case "asfalto":

                        return 0.85;

                    case "hielo":
                    case "tierra":
                    case "mixto":

                        return 1.05;

                    default:

                        return -1;
                }

            default:

                return -1;
        }
    }

    /**
     * Método para obtener la descripcion del ajuste.
     * @param factor Factor de rendimiento.
     * @return Descripcion del ajuste.
     */
    public String obtenerAjuste(double factor){

        if (factor == 0.85){

            return "Kart ideal (-15%)";
        }

        else if (factor == 0.92){

            return "Kart versatil (-8%)";
        }

        else if (factor == 1.05){

            return "Kart no ideal (+5%)";
        }

        return "Factor no valido";
    }

    /**
     * Método para calcular el tiempo minimo.
     * @param tiempoBase Tiempo base de la pista.
     * @param factor Factor de rendimiento.
     * @return Tiempo minimo calculado.
     */
    public double calcularTiempo(double tiempoBase, double factor){

        return tiempoBase * factor;
    }

    /**
     * Método para guardar los pilotos en el archivo CSV.
     */
    public void guardarPilotos(){

        Out escritor = new Out("src/files/pilotos.csv");

        for (int i = 0; i < cantidadPilotos; i++){

            escritor.println(pilotos[i]);
        }

        escritor.close();
    }
}