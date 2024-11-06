package Character;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

public class House {
    private int x, y; // ตำแหน่งของบ้าน
    private Image image; // ภาพบ้าน

    public House(int x, int y) {
        this.x = x;
        this.y = y;

        try {
            image = ImageIO.read(new File("assets/images/house.png")); // โหลดภาพบ้าน
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPosition(int x, int y) {

        this.x = x;

        this.y = y;

    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, 60, 60, null); // วาดภาพบ้าน
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 60, 60); // คืนค่า bounding box สำหรับการตรวจจับการชน
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
