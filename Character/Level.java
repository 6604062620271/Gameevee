package Character;

import java.awt.*;
import java.util.ArrayList;

public class Level {
    private int levelNumber;
    private ArrayList<Platform> platforms;
    private ArrayList<Cheese> cheeses;
    private ArrayList<Cat> cats;
    private House house;
    private int requiredCheeseCount;

    public Level(int levelNumber, int width, int height) {
        this.levelNumber = levelNumber;
        platforms = new ArrayList<>();
        cheeses = new ArrayList<>();
        cats = new ArrayList<>();
        house = new House(100, 110);
        setupLevel();
    }

    private void setupLevel() {
        switch (levelNumber) {
            case 1:
                setupLevel1();
                break;
            case 2:
                setupLevel2();
                break;
            // สามารถเพิ่มเงื่อนไขเพิ่มเติมสำหรับระดับอื่นๆ
        }
        requiredCheeseCount = levelNumber * 3;
    }

    private void setupLevel1() {
        platforms.add(new Platform(50, 400, 200, 20));
        platforms.add(new Platform(300, 300, 200, 20));
        platforms.add(new Platform(100, 160, 180, 20));
        platforms.add(new Platform(600, 130, 180, 20));
        cheeses.add(new Cheese(700, 100));
        cheeses.add(new Cheese(470, 260));
        cheeses.add(new Cheese(170, 130));
        cats.add(new Cat(170, 360));
        cats.add(new Cat(370, 260));
        house.setPosition(100, 100);
        // ----------------------------------

    }

    private void setupLevel2() {
        platforms.add(new Platform(50, 450, 300, 20));
        platforms.add(new Platform(400, 350, 200, 20));
        platforms.add(new Platform(60, 160, 180, 20));
        platforms.add(new Platform(600, 500, 150, 20));
        platforms.add(new Platform(600, 130, 180, 20));
        platforms.add(new Platform(600, 230, 180, 20));
        cheeses.add(new Cheese(150, 420));
        cheeses.add(new Cheese(450, 320));
        cheeses.add(new Cheese(700, 100));
        cats.add(new Cat(570, 80));
        cats.add(new Cat(250, 400));
        cats.add(new Cat(500, 250));
        house.setPosition(700, 430);
    }

    public void draw(Graphics g) {
        for (Platform platform : platforms) {
            platform.draw(g);
        }
        for (Cheese cheese : cheeses) {
            cheese.draw(g);
        }
        for (Cat cat : cats) {
            cat.draw(g);
        }

        house.draw(g);
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }

    public boolean checkPlatformCollision(Mouse mouse) {
        for (Platform platform : platforms) {
            if (platform.getBounds().intersects(mouse.getBounds())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCatCollision(Mouse mouse) {
        for (Cat cat : cats) {
            if (cat.getBounds().intersects(mouse.getBounds())) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCheeseCollection(Mouse mouse) {
        for (int i = 0; i < cheeses.size(); i++) {
            Cheese cheese = cheeses.get(i);
            if (cheese.getBounds().intersects(mouse.getBounds())) {
                cheeses.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean checkHouseCollision(Mouse mouse) {
        return house.getBounds().intersects(mouse.getBounds());
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getCheeseCount() {
        return cheeses.size();
    }

    public int getRequiredCheeseCount() {
        return requiredCheeseCount;
    }

}
