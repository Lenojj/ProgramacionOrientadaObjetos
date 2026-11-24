package domains;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {

            GameLogic logica = new GameLogic();

            Game juego = new Game(logica);

            juego.iniciar();

        } catch (IOException e) {

            System.out.println("Error al cargar los archivos: " + e.getMessage());
        }
    }
}


