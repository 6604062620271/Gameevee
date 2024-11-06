package Character;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Mouse {
    private int x, y; // ตำแหน่งของหนู
    private int width, height; // ขนาดของหนู
    private int speed = 10; // ความเร็ว
    private Image image; // ภาพหนู
    private boolean isJumping; // สถานะการกระโดด
    private int jumpHeight; // ความสูงในการกระโดด
    private int initialJumpHeight = 20; // ความสูงเริ่มต้นในการกระโดด
    private boolean isStunned; // สถานะของการถูกสตั้น
    private long stunStartTime; // เวลาที่เริ่มถูกสตั้น
    private static final int STUN_DURATION = 5000; // ระยะเวลาของการสตั้น (5 วินาที)

    public Mouse(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 60; // ขนาดของหนู
        this.height = 60;
        this.speed = 40; // ความเร็วของหนู
        this.isJumping = false; // สถานะการกระโดดเริ่มต้น
        this.jumpHeight = initialJumpHeight; // กำหนดความสูงในการกระโดดเริ่มต้น
        this.isStunned = false; // สถานะไม่ถูกสตั้นเริ่มต้น
        this.stunStartTime = -1; // เวลาเริ่มต้นการสตั้นยังไม่ถูกตั้งค่า

        try {
            image = ImageIO.read(new File("assets/images/mouse.png")); // โหลดภาพหนู
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(int dx) {
        if (!isStunned) { // ถ้าหนูไม่ได้ถูกสตั้น
            x += dx * speed; // เคลื่อนที่ตามการควบคุม
        }
    }

    public void jump() {
        if (!isJumping && !isStunned) { // ถ้าหนูไม่ได้กระโดดและไม่ได้ถูกสตั้น
            isJumping = true; // เปลี่ยนสถานะเป็นกำลังกระโดด
            jumpHeight = initialJumpHeight; // กำหนดความสูงเริ่มต้นในการกระโดด
        }
    }

    public void fall() {
        if (!isStunned) { // ถ้าหนูไม่ได้ถูกสตั้น
            y += 5; // หนูจะตกลงเรื่อยๆ ถ้าไม่ได้กระโดด
        }
    }

    public void setPositionOnPlatform(int platformY) {
        this.y = platformY - this.height; // จัดตำแหน่งหนูให้อยู่บนแพลตฟอร์ม
        isJumping = false; // รีเซ็ตสถานะกระโดดเมื่ออยู่บนแพลตฟอร์ม
    }

    public void update() {
        if (isStunned) { // ถ้าหนูถูกสตั้น
            long currentTime = System.currentTimeMillis();
            if (currentTime - stunStartTime >= STUN_DURATION) {
                isStunned = false; // หลังจากครบเวลา 5 วินาทีให้หนูกลับมาเคลื่อนไหวได้
            }
        } else {
            if (isJumping) {
                y -= jumpHeight; // กระโดดขึ้นตามค่าความสูง
                jumpHeight -= 1; // ลดความสูงในการกระโดด
                if (jumpHeight <= 0) {
                    isJumping = false; // ถ้าความสูงเป็นศูนย์ ให้หยุดกระโดด
                }
            } else {
                fall(); // ถ้าไม่ได้กระโดด ให้หนูตกลง
            }
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y; // ตั้งค่าตำแหน่งใหม่
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null); // วาดภาพหนู
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height); // คืนค่า bounding box สำหรับการตรวจจับการชน
    }

    // Getter สำหรับตำแหน่งหนู
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // ฟังก์ชันที่ใช้ในการเริ่มการสตั้นหนู
    public void stun() {
        if (!isStunned) {
            isStunned = true; // ทำให้หนูถูกสตั้น
            stunStartTime = System.currentTimeMillis(); // ตั้งเวลาเริ่มต้นของการสตั้น
        }
    }
}
