/**
 * Clase principal encargada de ejecutar UCN Kart.
 */
public class Main {

    /**
     * Método principal del programa.
     * @param args Argumentos de ejecucion.
     */
    public static void main(String[] args){

        GameLogic logica = new GameLogic();

        Game juego = new Game(logica);

        juego.iniciar();
    }
}