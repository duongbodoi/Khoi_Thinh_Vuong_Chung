package engine;

public class Main {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    public static void main(String[] args) {
        // Ban bạc để làm sau khá rắc rối
        /**
         * Nôm na sẽ có methode start extend application
         * trong đó chứa handle là vòng lặp game
         */
        GameManager gm = new GameManager(GAME_WIDTH, GAME_HEIGHT);
        gm.startGame();
    }
}
