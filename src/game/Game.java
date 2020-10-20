package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public final class Game extends JPanel implements KeyListener, ActionListener {

    Timer timer = new Timer(Config.delay, this);
    private int movementYLocation = 0;
    int gameHeightOffset;
    int totalTravelled = 0;
    int level = 1;
    float speed = 1;
    int totalEggsHit = 0;
    int[] aimRightYLocations = new int[Config.numberOfAims + 1];
    int[] aimLeftYLocations = new int[Config.numberOfAims + 1];
    ArrayList<Column> cols;
    ArrayList<GoldenEgg> goldenEggs;
    int eggIndex = 0;
    int chickenYLocation = 10;
    int chickenXlocation = 0;
    int isChichkenFireMode = 0;

    public Game() {
        ImageLoader.loadMedia();
        this.gameHeightOffset = Config.totalLength - Config.gameScreenHeight;
        chickenXlocation = 20 + 2 * Config.laneLenght + (Config.laneLenght - Config.chickenWidth) / 2;

        this.movementYLocation = Config.totalLength;//start from end
        JOptionPane.showMessageDialog(this, "Use arrow keys to move chicken \n A and D keys to throw eggs left and right.", "How To Play?", JOptionPane.INFORMATION_MESSAGE);
        initEggs();
        initLeftLocations();
        initRightLocations();
        initColums();
        timer.start();

    }

    void initEggs() {
        goldenEggs = new ArrayList<>();
        for (int i = 0; Config.numberOfEggs >= i; i++) {
            goldenEggs.add(new GoldenEgg());
        }
    }

    void initColums() {
        cols = new ArrayList<>();
        int Low = 60, High = 80;
        for (int i = 0; i < Config.numberOfCats; i++) {
            int a = new Random().nextInt(High - Low) + Low;
            cols.add(new Column(a));
            High += 200;
            Low += 200;
        }

    }

    void initLeftLocations() {
        int Low = 60;
        int High = 80;
        for (int i = 0; i < Config.numberOfAims; i++) {
            int a = new Random().nextInt(High - Low) + Low;
            aimLeftYLocations[i] = a;
            int artis = new Random().nextInt(300 - 100) + 100;
            Low += artis;
            High += artis + 100;
        }
    }

    void initRightLocations() {
        int Low = 60;
        int High = 80;
        for (int i = 0; i < Config.numberOfAims; i++) {
            int a = new Random().nextInt(High - Low) + Low;
            aimRightYLocations[i] = a;
            int artis = new Random().nextInt(300 - 100) + 100;
            Low += artis;
            High += artis + 100;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int Key = e.getKeyCode();
        if (Key == KeyEvent.VK_RIGHT) {
            if (chickenXlocation < 300) {
                chickenXlocation += 69;
            }
        }
        if (Key == KeyEvent.VK_LEFT) {
            if (chickenXlocation > 50) {
                chickenXlocation -= 69;
            }
        }
        if (Key == KeyEvent.VK_DOWN) {
            if (chickenYLocation < 300) {
                chickenYLocation += Config.chickenHeight;
            }
        }
        if (Key == KeyEvent.VK_UP) {
            if (chickenYLocation > 20) {
                chickenYLocation -= Config.chickenHeight;
            }
        }
        if (Key == KeyEvent.VK_A || Key == KeyEvent.VK_D) {
            if (0 < Config.numberOfEggs) {
                GoldenEgg cAmmo = goldenEggs.get(eggIndex);
                cAmmo.isVisible = true;
                cAmmo.x = chickenXlocation;
                cAmmo.dir = Key == KeyEvent.VK_A ? -3 : 3;
                cAmmo.y = chickenYLocation + Config.chickenHeight / 2;
                isChichkenFireMode = cAmmo.dir > 0 ? 1 : -1;
                eggIndex += 1;
                Config.numberOfEggs--;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (totalTravelled <= Config.totalLength) {
            totalTravelled++;
            movementYLocation -= speed;
        } else {
            winGame();
        }

        if (totalTravelled % Config.eachLevelLength == 0) {
            level++;
            if (level > 3) {
                winGame();
            }
            timer.stop();
            Config.delay -= 2;
            timer = new Timer(Config.delay, this);
            timer.start();
        }
        for (GoldenEgg egg : goldenEggs) {
            if (egg.x > 400 || egg.x <= egg.height) {
                egg.isVisible = false;
            } else {
                egg.x += egg.dir;
            }
        }
        repaint();
    }

    public void drawChicken(Graphics g, int mode) {
        if (mode == -1) {
            g.drawImage(ImageLoader.ChickenWithGunRightImage, chickenXlocation, chickenYLocation, Config.chickenWidth, Config.chickenHeight, this);
        } else {
            g.drawImage(ImageLoader.ChickenWithGunLeftImage, chickenXlocation, chickenYLocation, Config.chickenWidth, Config.chickenHeight, this);
        }
    }

    public void drawPanes(Graphics g) {
        for (int i = 0; i < 5; i++) {
            g.setColor(Config.laneFirstColor);
            if (i % 2 == 0) {
                g.setColor(Config.laneSecondColor);
            }
            g.fillRect(Config.leftRightMargin + Config.laneLenght * i, 0, Config.laneLenght, Config.totalLength);
        }
    }

    public void stopGame() {

        System.out.println("Game Over " + totalTravelled);
        timer.stop();
        JOptionPane.showMessageDialog(this, "Oppss...\nTotal Travelled: " + totalTravelled + "\nYou Collect "  + totalEggsHit + " Points.", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);

    }

    public void winGame() {
        System.out.println("Game Over win" + totalTravelled);
        timer.stop();
        JOptionPane.showMessageDialog(this, "Congrats You Win...\nYou Collect " + totalEggsHit + " Points", "Winnn!", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public void drawScoreBoard(Graphics g) {
        int hitStringX = Config.leftRightMargin, hitStringY = 20, levelStringX = 300, levelStringY = 20;
        Color bgColor = Color.BLACK, stringColor = Color.GREEN;
        g.setFont(new Font(null, Font.BOLD, 13));
        FontMetrics fm = g.getFontMetrics();

        String hit = "HIT POINT: " + Integer.toString(totalEggsHit);
        Rectangle2D rect = fm.getStringBounds(hit, g);
        g.setColor(bgColor);
        g.fillRect(hitStringX, hitStringY - fm.getAscent(), (int) rect.getWidth(), (int) rect.getHeight());
        g.setColor(stringColor);
        g.drawString(hit, hitStringX, hitStringY);

        String levelS = "LEVEL: " + Integer.toString(level);
        Rectangle2D rect2 = fm.getStringBounds(levelS, g);
        g.setColor(bgColor);
        g.fillRect(levelStringX, levelStringY - fm.getAscent(), (int) rect2.getWidth(), (int) rect2.getHeight());
        g.setColor(stringColor);
        g.drawString(levelS, levelStringX, levelStringY);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        
        drawPanes(g);
        drawScoreBoard(g);
        drawChicken(g, isChichkenFireMode);
        if (totalTravelled < Config.totalLength - 100) {
            this.cols.forEach((col) -> {
                for (int i = 0; i < 5; i++) {
                    if (col.isColumnVisible[i]) {
                        int yLocation = col.catYLocation + movementYLocation - Config.totalLength + Config.gameScreenHeight;
                        int xLocation = Config.leftRightMargin + i * Config.laneLenght;
                        g.drawImage(ImageLoader.CatImage, xLocation, yLocation, Config.catWidth, Config.catHeight, this);
                        if (yLocation < Config.gameScreenWidth
                                && new Rectangle(chickenXlocation, chickenYLocation, Config.chickenWidth, Config.chickenHeight).intersects(
                                        new Rectangle(xLocation, yLocation, Config.catWidth, Config.catHeight))) {

                            stopGame();
                        }
                    }
                }
            });
            for (GoldenEgg egg : goldenEggs) {
                if (egg.isVisible) {
                    egg.draw(g);
                }
            }

            int hedefHeight = 25;
            int hedefWidth = 20;
            int hedefOffset = Config.gameScreenWidth - 35;
            for (int i = 0; i < Config.numberOfAims; i++) {
                int rYLocation = aimRightYLocations[i] + movementYLocation - gameHeightOffset;
                int lYLocation = aimLeftYLocations[i] + movementYLocation - gameHeightOffset;
                if (rYLocation < Config.gameScreenHeight || lYLocation < Config.gameScreenHeight) {
                    for (GoldenEgg egg : goldenEggs) {
                        if (egg.isVisible) {
                            boolean isHit = false;
                            if (new Rectangle(hedefOffset, rYLocation, hedefWidth, hedefHeight).intersects(
                                    new Rectangle(egg.x, egg.y, egg.width, egg.height))) {
                                aimRightYLocations[i] = Config.totalLength + 500;
                                isHit = true;
                            }
                            if (new Rectangle(0, lYLocation, hedefWidth, hedefHeight).intersects(
                                    new Rectangle(egg.x, egg.y, egg.width, egg.height))) {
                                aimLeftYLocations[i] = Config.totalLength + 500;
                                isHit = true;

                            }
                            if (isHit) {
                                totalEggsHit += level * 10;
                                System.err.println("HIT " + totalEggsHit);
                                egg.isVisible = false;
                                return;
                            }
                        }
                    }
                    if (level == 1) {
                        g.drawImage(ImageLoader.PriceImage, 0, lYLocation, hedefWidth, hedefHeight, this);

                        g.drawImage(ImageLoader.PriceImage, hedefOffset, rYLocation, hedefWidth, hedefHeight, this);
                    }
                    if (level == 2) {
                        g.drawImage(ImageLoader.DiamondImage, 0, lYLocation, hedefWidth, hedefHeight, this);

                        g.drawImage(ImageLoader.DiamondImage, hedefOffset, rYLocation, hedefWidth, hedefHeight, this);
                    }
                    if (level == 3) {
                        g.drawImage(ImageLoader.MoneyImage, 0, lYLocation, hedefWidth, hedefHeight, this);

                        g.drawImage(ImageLoader.MoneyImage, hedefOffset, rYLocation, hedefWidth, hedefHeight, this);
                    }

                }
            }
        }
    }

    @Override
    public void repaint() {
        super.repaint();
    }

}
