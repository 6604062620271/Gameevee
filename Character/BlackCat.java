package Character;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BlackCat extends Cat {
    private Image blackCatImage;

    public BlackCat(int x, int y) {
        super(x, y);
        try {
            blackCatImage = ImageIO.read(new File("assets/images/blackcat.png")); // โหลดภาพ blackcat.png
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics g) {
        if (blackCatImage != null) {
            g.drawImage(blackCatImage, getX(), getY(), getWidth(), getHeight(), null); // วาดภาพ blackcat.png
        }
    }

    // get x
    public int getX() {
        return x;
    }

    // get y
    public int getY() {
        return y;
    }

    // get width
    public int getWidth() {
        return 42; // ขนาดของแมวสีดำ
    }

    // get height
    public int getHeight() {
        return 42; // ขนาดของแมวสีดำ
    }
}
