package game;

import java.awt.Graphics;

public class GoldenEgg {

    public int x, y, width = 10, height = 10, dir = 1;
    public boolean isVisible = false;

    public void draw(Graphics g) {
        g.drawImage(ImageLoader.EggImage, x, y, width, height, null);
    }
}
