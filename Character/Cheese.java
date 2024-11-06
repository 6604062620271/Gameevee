package Character;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle; // นำเข้าคลาส Rectangle
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Cheese {
    private int x, y; // ตำแหน่งของชีส
    private Image image; // ภาพชีส

    public Cheese(int x, int y) {
        this.x = x;
        this.y = y;

        try {
            image = ImageIO.read(new File("assets/images/seed.png")); // เปลี่ยนชื่อไฟล์ตามที่คุณมี
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, 30, 30, null); // วาดภาพชีส
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 30, 30); // คืนค่า bounding box สำหรับการตรวจจับการชน
    }
}
