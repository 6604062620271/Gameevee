package Character;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Game extends JPanel {
    private Mouse mouse;
    private Level currentLevel;
    private Image background;
    private Image cheeseImage;
    private Image restartImage;
    private Image startImage;
    private Image winImage; // ภาพชนะเกม
    private int score;
    private boolean isGameOver;
    private boolean isGameStarted;
    private boolean isGameWon; // สถานะชนะเกม

    public Game() {
        mouse = new Mouse(50, 300);
        currentLevel = new Level(1, 800, 600);
        score = 0;
        isGameOver = false;
        isGameStarted = false;
        isGameWon = false; // เริ่มต้นสถานะยังไม่ชนะเกม

        try {
            background = ImageIO.read(new File("assets/images/bg.png"));
            cheeseImage = ImageIO.read(new File("assets/images/seed.png"));
            restartImage = ImageIO.read(new File("assets/images/restart.png"));
            startImage = ImageIO.read(new File("assets/images/start.png"));
            winImage = ImageIO.read(new File("assets/images/win.png")); // โหลดภาพชนะเกม
        } catch (IOException e) {
            e.printStackTrace();
        }

        setFocusable(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isGameStarted) {
                    isGameStarted = true;
                    repaint();
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isGameStarted && !isGameOver && !isGameWon) {
                    if (e.getKeyCode() == KeyEvent.VK_W) {
                        mouse.jump();
                    }
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_A:
                            mouse.move(-1);
                            break;
                        case KeyEvent.VK_D:
                            mouse.move(1);
                            break;
                    }
                    if (currentLevel.checkCheeseCollection(mouse)) {
                        score++;
                    }
                    if (currentLevel.checkHouseCollision(mouse) && score % 3 == 0) {
                        nextLevel();
                    }
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isGameStarted) {
            showStartScreen(g);
        } else if (isGameWon) {
            showWinScreen(g);
        } else {
            if (background != null) {
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
            currentLevel.draw(g);
            mouse.draw(g);

            g.setColor(Color.BLACK);
            g.drawString("Score: " + score, 50, 20);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawImage(cheeseImage, 10, 5, 30, 30, null);

            if (isGameOver) {
                showRestartScreen(g);
            }
        }
    }

    private void showStartScreen(Graphics g) {
        if (startImage != null) {
            g.drawImage(startImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Click to Start", getWidth() / 2 - 100, getHeight() / 2);
        }
    }

    private void showWinScreen(Graphics g) {
        if (winImage != null) {
            g.drawImage(winImage, 0, 0, getWidth(), getHeight(), this); // แสดงภาพชนะเกมแบบเต็มจอ
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("You Win!", getWidth() / 2 - 100, getHeight() / 2 - 50);
        }
    }

    public void update() {
        if (isGameStarted && !isGameOver && !isGameWon) {
            mouse.update();

            if (currentLevel.checkPlatformCollision(mouse)) {
                for (Platform platform : currentLevel.getPlatforms()) {
                    if (platform.getBounds().intersects(mouse.getBounds())) {
                        mouse.setPositionOnPlatform(platform.getY());
                        break;
                    }
                }
            } else {
                mouse.fall();
            }

            if (currentLevel.checkCatCollision(mouse) || isOutOfBounds()) {
                isGameOver = true;
            }
            repaint();
        }
    }

    private void showRestartScreen(Graphics g) {
        if (restartImage != null) {
            g.drawImage(restartImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Game Over!", getWidth() / 2 - 100, getHeight() / 2 - 50);

            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Press R to Restart", getWidth() / 2 - 100, getHeight() / 2);
        }

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    restartGame();
                }
            }
        });
    }

    private void restartGame() {
        mouse = new Mouse(100, 300);
        currentLevel = new Level(1, 800, 600);
        score = 0;
        isGameOver = false;
        isGameWon = false; // รีเซ็ตสถานะชนะเกม
        repaint();
    }

    private void nextLevel() {
        if (currentLevel.getLevelNumber() == 2) {
            isGameWon = true; // หากผ่านเลเวล 2 ให้ถือว่าชนะเกม
        } else {
            currentLevel = new Level(currentLevel.getLevelNumber() + 1, 800, 600);
            score = 0;
        }
    }

    private boolean isOutOfBounds() {
        return mouse.getX() < 0 || mouse.getX() > getWidth() || mouse.getY() > getHeight();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mouse Game");
        Game game = new Game();
        frame.add(game);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        while (true) {
            game.update();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
