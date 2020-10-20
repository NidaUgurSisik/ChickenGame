package game;

import java.awt.HeadlessException;
import javax.swing.JFrame;

public class GameScreen extends JFrame{

    public GameScreen(String string) throws HeadlessException {
        super(string);
    }
    
    public static GameScreen screen;
    public static Game game;
    public static void main(String[] args) {
        screen = new GameScreen("Chicken Game");
        screen.setResizable(false);
        screen.setFocusable(false);
        
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setSize(Config.gameScreenWidth, Config.gameScreenHeight);

        game = new Game();
        game.requestFocus();
        
        game.addKeyListener(game);
        
        game.setFocusable(true);
        game.setFocusTraversalKeysEnabled(false );
        
        screen.add(game);
        
        screen.setVisible(true);
    }
}
