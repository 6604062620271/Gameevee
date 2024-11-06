package Character;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Cat {
    protected int x, y; // ตำแหน่งของแมว
    private Image image; // ภาพแมว

    public Cat(int x, int y) {
        this.x = x;
        this.y = y;

        try {
            image = ImageIO.read(new File("assets/images/cat1.png")); // เปลี่ยนชื่อไฟล์ตามที่คุณมี
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, 42, 42, null); // วาดภาพแมว
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 40, 40); // คืนค่า bounding box สำหรับการตรวจจับการชน
    }

}
