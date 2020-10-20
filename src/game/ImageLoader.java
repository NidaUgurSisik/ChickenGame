package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

public class ImageLoader {

    public static BufferedImage ChickenWithGunRightImage, ChickenWithGunLeftImage, CatImage, EggImage, PriceImage,DiamondImage,MoneyImage;

    public static void loadMedia() {
        try {
            ChickenWithGunRightImage = ImageIO.read(new FileImageInputStream(new File(Config.chickenRightImagePath)));
            ChickenWithGunLeftImage = ImageIO.read(new FileImageInputStream(new File(Config.chickenLeftImagePath)));
            CatImage = ImageIO.read(new FileImageInputStream(new File(Config.catImagePath)));
            EggImage = ImageIO.read(new FileImageInputStream(new File(Config.EggImagePath)));
            PriceImage = ImageIO.read(new FileImageInputStream(new File(Config.PriceImagePath)));
            DiamondImage = ImageIO.read(new FileImageInputStream(new File(Config.DiamondImagePath)));
            MoneyImage = ImageIO.read(new FileImageInputStream(new File(Config.MoneyImagePath)));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
