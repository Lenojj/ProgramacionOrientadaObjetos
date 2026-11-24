package domains;

import java.util.Scanner;

public final class Game {

    private void cargarPistas(){

        pistasDisponibles = logica.almacenPistas();
    }

    private void buscarPiloto(){

        kartsPiloto = logica.buscarPiloto(nombrePiloto);

        if (kartsPiloto == null) {

            System.out.println("Piloto no encontrado");
        }

        //!!Podria ir un else (Piloto encontrado).
    }

    private void mostrarKarts(){

        if (kartsPiloto.length == 0) {

            System.out.println("El piloto no tiene karts.");
            return;
        }

        for (int i = 0; i < kartsPiloto.length && i < 10; i++) {

            String nombreKart = logica.nombreKart(kartsPiloto[i]);
            String tipoKart = logica.tipoKart(kartsPiloto[i]);

            System.out.println("[" + (i + 1) + "] " + nombreKart + " | " + tipoKart);
        }
    }

    private void mostrarPistas(){

        if (pistasDisponibles.length == 0) {

            System.out.println("No hay pistas para mostrar");
            return;
        }

        for (int i = 0; i < pistasDisponibles.length; i++) {

            String pista = pistasDisponibles[i];

            String nombre = logica.nombrePistas(pista);
            String terreno = logica.obtenerTerrereno(pista);
            double tiempo = logica.obtenerTiempo(pista);

            System.out.println("[" + (i + 1) + "] " + nombre 
                    + " | Terreno: " + terreno + " | " + tiempo + " s");
        }
    }

    private void consultarPiloto(){

        System.out.println("=== CONSULTAR PILOTO ===");
        System.out.println("Piloto: " + nombrePiloto);
        System.out.println("Karts disponibles:");

        mostrarKarts();
    }

    private void calcularTiempo(){

        System.out.println("=== TIEMPO MINIMO ===");

        if (kartsPiloto.length == 0) {

            System.out.println("No tienes Karts disponibles...");
            return;
        }

        if (pistasDisponibles.length == 0) {

            System.out.println("No hay pistas disponibles...");
            return;
        }

        mostrarPistas();

        System.out.println("Seleccione una pista: ");
        int indicePista = leerNumero() - 1;

        //Validamos que la pista exista
        if (indicePista < 0 || indicePista >= pistasDisponibles.length) {

            System.out.println("Pista no valida");
            return;
        }

        mostrarKarts();

        System.out.println("Seleccione un Kart: ");
        int indiceKart = leerNumero() - 1;

        //Validamos que el kart exista
        if (indiceKart < 0 || indiceKart >= kartsPiloto.length 
                || indiceKart >= 10) {

            System.out.println("Kart no valido");
            return;
        }

        //Obtenemos los datos seleccionados
        String pista = pistasDisponibles[indicePista];
        String kart = kartsPiloto[indiceKart];

        //Datos de la pista
        String nombrePista = logica.nombrePistas(pista);
        String terreno = logica.obtenerTerrereno(pista);
        double tiempoBase = logica.obtenerTiempo(pista);

        //Datos del Kart
        String nombreKart = logica.nombreKart(kart);
        String tipoKart = logica.tipoKart(kart);

        //Validamos los datos
        if (tiempoBase <= 0 || tiempoBase > 300 
                || terreno.isEmpty() || tipoKart == null) {

            System.out.println("Datos no validos");
            return;
        }

        //Calculamos el tiempo minimo
        double factor = logica.factor(tipoKart, terreno);
        double tiempoMinimo = logica.calcularTiempo(tiempoBase, factor);
        String ajuste = logica.obtenerAjuste(factor);

        //Mostramos los resultados
        System.out.println("=== RESULTADOS ===");
        System.out.println("Pista: " + nombrePista);
        System.out.println("Terreno: " + terreno);
        System.out.println("Kart: " + nombreKart);
        System.out.println("Categoria: " + tipoKart);
        System.out.println("Ajuste: " + ajuste);

        System.out.printf("Tiempo Base: %.2f s%n", tiempoBase);
        System.out.printf("Tiempo Minimo: %.2f s%n", tiempoMinimo);
    }

    //Metodo para validar que se ingrese un numero
    private int leerNumero(){

        try {

            return Integer.parseInt(input.nextLine());

        } catch (NumberFormatException e) {

            return -1;
        }
    }

    private void menu(){

        String opcion;

        do {

            System.out.println("== MENU PRINCIPAL ==");
            System.out.println("[1] Consultar Piloto");
            System.out.println("[2] Calcular Tiempo Minimo");
            System.out.println("[3] Salir");

            opcion = input.nextLine();

            if (opcion.equals("1")) {

                consultarPiloto();
            }

            else if (opcion.equals("2")) {

                calcularTiempo();
            }

            else if (opcion.equals("3")) {

                System.out.println("Saliendo...");
            }

            else {

                System.out.println("Opcion no valida");
            }

        } while (!opcion.equals("3"));
    }


    //==================== ATRIBUTOS ==============================

    private final GameLogic logica;
    private final Scanner input = new Scanner(System.in); //Metodo para escribir por teclado

    private String nombrePiloto;
    private String[] kartsPiloto;
    private String[] pistasDisponibles;

    //=============================================================

    public Game(GameLogic logica){

        this.logica = logica;

        cargarPistas();
    }

    public void iniciar(){

        System.out.println("==== UCN KART ====");

        while (kartsPiloto == null) {

            System.out.println("Ingrese el Nombre del Piloto: ");
            nombrePiloto = input.nextLine().trim();

            buscarPiloto();

            if (kartsPiloto == null) {

                System.out.println("[1] Volver a intentar: ");
                System.out.println("[2] Salir: ");

                String opcion = input.nextLine();

                if (opcion.equals("2")) {

                    return;
                }
            }
        }
        menu();
    }
}

