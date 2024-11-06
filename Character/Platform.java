package Character;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Platform {
    private int x, y; // ตำแหน่งของแพลตฟอร์ม
    private int width, height; // ขนาดของแพลตฟอร์ม
    private Image image; // ตัวแปรสำหรับภาพแพลตฟอร์ม

    public Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // โหลดภาพแพลตฟอร์ม
        try {
            image = ImageIO.read(new File("assets/images/platform1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getY() {
        return this.y;
    }
}
