import java.awt.Color;
import java.awt.Font;

/**
 * Clase encargada de administrar la interfaz grafica
 * y la interaccion con el usuario mediante StdDraw.
 */
public final class Game {

    /**
     * Método para configurar la ventana.
     */
    private void configurarVentana(){

        StdDraw.setCanvasSize(900, 650);

        StdDraw.setXscale(0, 900);
        StdDraw.setYscale(0, 650);

        StdDraw.enableDoubleBuffering();
    }

    /**
     * Método para dibujar el fondo de la pantalla.
     * @param titulo Titulo de la pantalla.
     */
    private void fondo(String titulo){

        StdDraw.clear(new Color(18, 24, 38));

        //Dibujamos el encabezado
        StdDraw.setPenColor(new Color(190, 30, 45));
        StdDraw.filledRectangle(450, 610, 450, 40);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 28));

        StdDraw.text(450, 610, titulo);

        //Dibujamos una bandera de cuadros
        for (int i = 0; i < 45; i++){

            for (int j = 0; j < 2; j++){

                if ((i + j) % 2 == 0){

                    StdDraw.setPenColor(StdDraw.WHITE);
                }

                else {

                    StdDraw.setPenColor(StdDraw.BLACK);
                }

                StdDraw.filledSquare(10 + i * 20,
                        560 - j * 20, 10);
            }
        }

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 18));
    }

    /**
     * Método para dibujar un boton.
     * @param x Coordenada horizontal.
     * @param y Coordenada vertical.
     * @param ancho Mitad del ancho del boton.
     * @param alto Mitad del alto del boton.
     * @param texto Texto del boton.
     */
    private void dibujarBoton(double x, double y,
                              double ancho, double alto,
                              String texto){

        //Cambiamos el color si el mouse esta sobre el boton
        if (mouseDentro(x, y, ancho, alto)){

            StdDraw.setPenColor(new Color(65, 100, 155));
        }

        else {

            StdDraw.setPenColor(new Color(35, 48, 68));
        }

        StdDraw.filledRectangle(x, y, ancho, alto);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.rectangle(x, y, ancho, alto);

        StdDraw.setFont(new Font("Arial", Font.PLAIN, 18));
        StdDraw.text(x, y, texto);
    }

    /**
     * Método para comprobar si el mouse esta dentro
     * de un rectangulo.
     * @param x Coordenada horizontal.
     * @param y Coordenada vertical.
     * @param ancho Mitad del ancho.
     * @param alto Mitad del alto.
     * @return true si el mouse esta dentro.
     */
    private boolean mouseDentro(double x, double y,
                                double ancho, double alto){

        double mouseX = StdDraw.mouseX();
        double mouseY = StdDraw.mouseY();

        return mouseX >= x - ancho
                && mouseX <= x + ancho
                && mouseY >= y - alto
                && mouseY <= y + alto;
    }

    /**
     * Método para detectar si se presiono un boton.
     * @param x Coordenada horizontal.
     * @param y Coordenada vertical.
     * @param ancho Mitad del ancho.
     * @param alto Mitad del alto.
     * @return true si se presiono el boton.
     */
    private boolean detectarBoton(double x, double y,
                                  double ancho, double alto){

        if (StdDraw.isMousePressed()
                && mouseDentro(x, y, ancho, alto)){

            //Esperamos que el usuario suelte el mouse
            while (StdDraw.isMousePressed()){

                StdDraw.pause(20);
            }

            return true;
        }

        return false;
    }

    /**
     * Método para mostrar un mensaje en la ventana.
     * @param mensaje Mensaje que se desea mostrar.
     */
    private void mostrarMensaje(String mensaje){

        while (true){

            fondo("UCN KART");

            StdDraw.text(450, 350, mensaje);

            dibujarBoton(450, 150, 180, 30, "Aceptar");

            StdDraw.show();

            if (detectarBoton(450, 150, 180, 30)){

                return;
            }

            StdDraw.pause(20);
        }
    }

    /**
     * Método para ingresar texto mediante el teclado.
     * @param mensaje Texto solicitado al usuario.
     * @return Texto ingresado o null si cancela.
     */
    private String leerTexto(String mensaje){

        String texto = "";

        //Eliminamos las teclas pendientes
        while (StdDraw.hasNextKeyTyped()){

            StdDraw.nextKeyTyped();
        }

        while (true){

            fondo("UCN KART");

            StdDraw.text(450, 450, mensaje);

            //Dibujamos el campo de texto
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(450, 350, 280, 30);

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(450, 350, texto + "|");

            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(450, 280, "Presione ENTER para confirmar");

            dibujarBoton(450, 190, 180, 28, "Aceptar");
            dibujarBoton(450, 110, 180, 28, "Cancelar");

            StdDraw.show();

            //Leemos las teclas ingresadas
            while (StdDraw.hasNextKeyTyped()){

                char tecla = StdDraw.nextKeyTyped();

                if (tecla == '\n' || tecla == '\r'){

                    return texto.trim();
                }

                else if (tecla == '\b'){

                    if (!texto.isEmpty()){

                        texto = texto.substring(0,
                                texto.length() - 1);
                    }
                }

                else if (!Character.isISOControl(tecla)
                        && texto.length() < 40){

                    texto += tecla;
                }
            }

            if (detectarBoton(450, 190, 180, 28)){

                return texto.trim();
            }

            else if (detectarBoton(450, 110, 180, 28)){

                return null;
            }

            StdDraw.pause(20);
        }
    }

    /**
     * Método para cargar las pistas disponibles.
     */
    private void cargarPistas(){

        pistasDisponibles = logica.almacenPistas();
    }

    /**
     * Método para buscar los karts del piloto.
     */
    private void buscarPiloto(){

        kartsPiloto = logica.buscarPiloto(nombrePiloto);
    }

    /**
     * Método para mostrar los datos del piloto.
     */
    private void consultarPiloto(){

        while (true){

            fondo("CONSULTAR PILOTO");

            StdDraw.text(450, 500, "Piloto: " + nombrePiloto);

            StdDraw.text(450, 465,
                    "Karts registrados: " + kartsPiloto.length + "/10");

            if (kartsPiloto.length == 0){

                StdDraw.text(450, 350,
                        "El piloto no tiene karts registrados");
            }

            double y = 420;

            //Mostramos los karts del piloto
            for (int i = 0; i < kartsPiloto.length && i < 10; i++){

                String nombreKart = logica.nombreKart(kartsPiloto[i]);
                String tipoKart = logica.tipoKart(kartsPiloto[i]);

                StdDraw.setPenColor(new Color(35, 48, 68));
                StdDraw.filledRectangle(450, y, 320, 15);

                StdDraw.setPenColor(StdDraw.WHITE);

                StdDraw.text(450, y, (i + 1) + ". "
                        + nombreKart + " | " + tipoKart);

                y -= 33;
            }

            dibujarBoton(450, 65, 180, 25, "Volver");

            StdDraw.show();

            if (detectarBoton(450, 65, 180, 25)){

                return;
            }

            StdDraw.pause(20);
        }
    }

    /**
     * Método para seleccionar una pista.
     * @return Indice de la pista o -1 si cancela.
     */
    private int seleccionarPista(){

        int pagina = 0;
        int pistasPorPagina = 6;

        int totalPaginas = (pistasDisponibles.length
                + pistasPorPagina - 1) / pistasPorPagina;

        while (true){

            fondo("SELECCIONAR PISTA");

            StdDraw.text(450, 505, "Seleccione una pista");

            int inicio = pagina * pistasPorPagina;

            int fin = inicio + pistasPorPagina;

            if (fin > pistasDisponibles.length){

                fin = pistasDisponibles.length;
            }

            double y = 445;

            //Dibujamos las pistas de la pagina actual
            for (int i = inicio; i < fin; i++){

                String pista = pistasDisponibles[i];

                String nombre = logica.nombrePistas(pista);
                String copa = logica.obtenerCopa(pista);
                String terreno = logica.obtenerTerrereno(pista);

                double tiempo = logica.obtenerTiempo(pista);

                String texto = (i + 1) + ". " + nombre
                        + " | " + copa
                        + " | " + terreno
                        + " | " + tiempo + " s";

                dibujarBoton(450, y, 390, 24, texto);

                y -= 58;
            }

            StdDraw.text(450, 90, "Pagina "
                    + (pagina + 1) + " de " + totalPaginas);

            if (pagina > 0){

                dibujarBoton(150, 90, 100, 24, "Anterior");
            }

            if (pagina < totalPaginas - 1){

                dibujarBoton(750, 90, 100, 24, "Siguiente");
            }

            dibujarBoton(450, 35, 120, 22, "Cancelar");

            StdDraw.show();

            //Comprobamos si se selecciono una pista
            y = 445;

            for (int i = inicio; i < fin; i++){

                if (detectarBoton(450, y, 390, 24)){

                    return i;
                }

                y -= 58;
            }

            //Controlamos el cambio de pagina
            if (pagina > 0
                    && detectarBoton(150, 90, 100, 24)){

                pagina--;
            }

            else if (pagina < totalPaginas - 1
                    && detectarBoton(750, 90, 100, 24)){

                pagina++;
            }

            else if (detectarBoton(450, 35, 120, 22)){

                return -1;
            }

            StdDraw.pause(20);
        }
    }

    /**
     * Método para seleccionar un kart del piloto.
     * @return Indice del kart o -1 si cancela.
     */
    private int seleccionarKart(){

        while (true){

            fondo("SELECCIONAR KART");

            StdDraw.text(450, 505, "Seleccione un kart");

            double y = 455;

            //Dibujamos un boton para cada kart
            for (int i = 0; i < kartsPiloto.length && i < 10; i++){

                String nombre = logica.nombreKart(kartsPiloto[i]);
                String categoria = logica.tipoKart(kartsPiloto[i]);

                dibujarBoton(450, y, 300, 17,
                        (i + 1) + ". " + nombre + " | " + categoria);

                y -= 39;
            }

            dibujarBoton(450, 45, 150, 25, "Cancelar");

            StdDraw.show();

            y = 455;

            //Comprobamos que kart fue seleccionado
            for (int i = 0; i < kartsPiloto.length && i < 10; i++){

                if (detectarBoton(450, y, 300, 17)){

                    return i;
                }

                y -= 39;
            }

            if (detectarBoton(450, 45, 150, 25)){

                return -1;
            }

            StdDraw.pause(20);
        }
    }

    /**
     * Método para calcular el tiempo minimo de una pista.
     */
    private void calcularTiempo(){

        if (kartsPiloto.length == 0){

            mostrarMensaje("No tienes karts disponibles");
            return;
        }

        if (pistasDisponibles.length == 0){

            mostrarMensaje("No hay pistas disponibles");
            return;
        }

        //Seleccionamos la pista
        int indicePista = seleccionarPista();

        if (indicePista == -1){

            return;
        }

        //Seleccionamos el kart
        int indiceKart = seleccionarKart();

        if (indiceKart == -1){

            return;
        }

        //Obtenemos los datos seleccionados
        String pista = pistasDisponibles[indicePista];
        String kart = kartsPiloto[indiceKart];

        String nombrePista = logica.nombrePistas(pista);
        String terreno = logica.obtenerTerrereno(pista);

        double tiempoBase = logica.obtenerTiempo(pista);

        String nombreKart = logica.nombreKart(kart);
        String tipoKart = logica.tipoKart(kart);

        //Validamos los datos
        if (tiempoBase <= 0 || tiempoBase > 300
                || terreno.isEmpty() || tipoKart == null){

            mostrarMensaje("Datos no validos");
            return;
        }

        double factor = logica.factor(tipoKart, terreno);

        if (factor <= 0){

            mostrarMensaje("Factor no valido");
            return;
        }

        //Calculamos el tiempo minimo
        double tiempoMinimo = logica.calcularTiempo(tiempoBase, factor);

        String ajuste = logica.obtenerAjuste(factor);

        //Mostramos los resultados
        while (true){

            fondo("RESULTADOS");

            StdDraw.text(450, 500, "Pista: " + nombrePista);
            StdDraw.text(450, 460, "Terreno: " + terreno);
            StdDraw.text(450, 420, "Kart: " + nombreKart);
            StdDraw.text(450, 380, "Categoria: " + tipoKart);
            StdDraw.text(450, 340, "Ajuste: " + ajuste);

            StdDraw.text(450, 290,
                    String.format("Tiempo Base: %.2f s", tiempoBase));

            //Dibujamos el resultado principal
            StdDraw.setPenColor(new Color(25, 100, 65));
            StdDraw.filledRectangle(450, 190, 280, 65);

            StdDraw.setPenColor(StdDraw.WHITE);

            StdDraw.text(450, 220, "TIEMPO MINIMO");

            StdDraw.setFont(new Font("Arial", Font.BOLD, 38));

            StdDraw.text(450, 170,
                    String.format("%.2f s", tiempoMinimo));

            dibujarBoton(450, 60, 180, 25, "Volver al menu");

            StdDraw.show();

            if (detectarBoton(450, 60, 180, 25)){

                return;
            }

            StdDraw.pause(20);
        }
    }

    /**
     * Método para seleccionar la categoria de un kart.
     * @return Categoria seleccionada o null si cancela.
     */
    private String seleccionarCategoria(){

        while (true){

            fondo("CATEGORIA DEL KART");

            dibujarBoton(450, 450, 250, 30, "Ligero");
            dibujarBoton(450, 350, 250, 30, "Mediano");
            dibujarBoton(450, 250, 250, 30, "Pesado");
            dibujarBoton(450, 100, 180, 28, "Cancelar");

            StdDraw.show();

            if (detectarBoton(450, 450, 250, 30)){

                return "Ligero";
            }

            else if (detectarBoton(450, 350, 250, 30)){

                return "Mediano";
            }

            else if (detectarBoton(450, 250, 250, 30)){

                return "Pesado";
            }

            else if (detectarBoton(450, 100, 180, 28)){

                return null;
            }

            StdDraw.pause(20);
        }
    }

    /**
     * Método para agregar un nuevo kart.
     */
    private void agregarKart(){

        if (kartsPiloto.length >= 10){

            mostrarMensaje("Has alcanzado el maximo de 10 karts");
            return;
        }

        String nombreKart = leerTexto("Ingrese el nombre del kart");

        if (nombreKart == null){

            return;
        }

        if (nombreKart.isEmpty()){

            mostrarMensaje("El nombre no puede estar vacio");
            return;
        }

        //Validamos que el kart no exista
        for (int i = 0; i < kartsPiloto.length; i++){

            String nombre = logica.nombreKart(kartsPiloto[i]);

            if (nombre.equalsIgnoreCase(nombreKart)){

                mostrarMensaje("Ya tienes un kart con ese nombre");
                return;
            }
        }

        String tipoKart = seleccionarCategoria();

        if (tipoKart == null){

            return;
        }

        if (logica.agregarKart(nombrePiloto, nombreKart, tipoKart)){

            //Actualizamos los karts del piloto
            buscarPiloto();

            mostrarMensaje("Kart agregado correctamente");
        }

        else {

            mostrarMensaje("No se pudo agregar el kart");
        }
    }

    /**
     * Método para guardar los pilotos y salir.
     * @return true si se guardaron los datos.
     */
    private boolean guardarYSalir(){

        try {

            logica.guardarPilotos();

            mostrarMensaje("Datos guardados correctamente");

            return true;

        } catch (Exception e){

            mostrarMensaje("Error al guardar los datos");

            return false;
        }
    }

    /**
     * Método para dibujar el menu principal.
     */
    private void dibujarMenu(){

        fondo("UCN KART");

        StdDraw.text(450, 495, "Bienvenido, " + nombrePiloto);

        dibujarBoton(450, 410, 250, 30, "Consultar Piloto");

        dibujarBoton(450, 320, 250, 30, "Calcular Tiempo Minimo");

        dibujarBoton(450, 230, 250, 30, "Agregar Kart");

        dibujarBoton(450, 140, 250, 30, "Guardar y Salir");

        StdDraw.setPenColor(StdDraw.WHITE);

        StdDraw.setFont(new Font("Arial", Font.PLAIN, 14));

        StdDraw.text(450, 35, "Universidad Catolica del Norte");

        StdDraw.show();
    }

    /**
     * Método para controlar el menu principal.
     */
    private void menu(){

        boolean salir = false;

        do {

            dibujarMenu();

            if (detectarBoton(450, 410, 250, 30)){

                consultarPiloto();
            }

            else if (detectarBoton(450, 320, 250, 30)){

                calcularTiempo();
            }

            else if (detectarBoton(450, 230, 250, 30)){

                agregarKart();
            }

            else if (detectarBoton(450, 140, 250, 30)){

                salir = guardarYSalir();
            }

            StdDraw.pause(20);

        } while (!salir);

        System.exit(0);
    }

    /**
     * Método para mostrar las opciones cuando
     * el piloto no existe.
     * @return Opcion seleccionada.
     */
    private int pilotoNoEncontrado(){

        while (true){

            fondo("PILOTO NO ENCONTRADO");

            StdDraw.text(450, 495,
                    "No se encontro al piloto: " + nombrePiloto);

            dibujarBoton(450, 400, 250, 30,
                    "Registrar nuevo piloto");

            dibujarBoton(450, 300, 250, 30,
                    "Volver a intentar");

            dibujarBoton(450, 200, 250, 30,
                    "Salir");

            StdDraw.show();

            if (detectarBoton(450, 400, 250, 30)){

                return 1;
            }

            else if (detectarBoton(450, 300, 250, 30)){

                return 2;
            }

            else if (detectarBoton(450, 200, 250, 30)){

                return 3;
            }

            StdDraw.pause(20);
        }
    }

    //Atributos

    private final GameLogic logica;

    private String nombrePiloto;
    private String[] kartsPiloto;
    private String[] pistasDisponibles;

    /**
     * Constructor encargado de inicializar el juego.
     * @param logica Objeto de la clase GameLogic.
     */
    public Game(GameLogic logica){

        this.logica = logica;

        cargarPistas();
    }

    /**
     * Método encargado de iniciar el programa,
     * buscar o registrar al piloto.
     */
    public void iniciar(){

        configurarVentana();

        while (kartsPiloto == null){

            nombrePiloto = leerTexto("Ingrese el nombre del piloto");

            if (nombrePiloto == null){

                System.exit(0);
                return;
            }

            if (nombrePiloto.isEmpty()){

                mostrarMensaje("Ingrese un nombre valido");
                continue;
            }

            buscarPiloto();

            if (kartsPiloto == null){

                int opcion = pilotoNoEncontrado();

                if (opcion == 1){

                    if (logica.registrarPiloto(nombrePiloto)){

                        buscarPiloto();

                        mostrarMensaje("Piloto registrado correctamente");
                    }

                    else {

                        mostrarMensaje("No se pudo registrar el piloto");
                    }
                }

                else if (opcion == 2){

                    continue;
                }

                else if (opcion == 3){

                    System.exit(0);
                    return;
                }
            }
        }

        menu();
    }
}