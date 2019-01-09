import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import static java.awt.event.KeyEvent.*;

public class Bot {
    //private final static Bot bot;
    private static Robot robot;
    private static Random random = new Random();

    //private Bot(){};

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        //gameLogic = new GameLogic();
    }

    public static Robot getRobot() {
        return robot;
    }

    public static synchronized void makeMove(GameTable gameTable) { //TODO safe thread
        int key = getKey(gameTable.getMove());
        Rectangle rect = gameTable.getRectangle();
        Point location = MouseInfo.getPointerInfo().getLocation();
        int mouseY = location.y;
        int mouseX = location.x;
        moveToRandomPoint(rect);
        System.out.println("press key = " + KeyEvent.getKeyText(key));
        pressKey(key);
        robot.mouseMove(mouseX, mouseY);
    }

    private static int getKey(GameMove move) {
        switch(move.getActionType()) {
            case FOLD:

                return VK_SPACE;
            case POT: return VK_P;
            case CALL: return VK_C;
            case CHECK: return VK_X;
            case RAISE:
            case BET:
                return getKey(move.getAmount());
            case ALLIN: return VK_A;
            case NOACTION:
                System.out.println("MakeMove w NOACTION");
        }

        return VK_ALT;
    }

    private static int getKey(int bb) {
        switch (bb) {
            case 2: return VK_NUMPAD2;
            case 3: return VK_NUMPAD3;
            case 4: return VK_NUMPAD4;
            case 5: return VK_NUMPAD5;
            case 6: return VK_NUMPAD6;
            case 7: return VK_NUMPAD7;
            case 8: return VK_NUMPAD8;
            case 9: return VK_NUMPAD9;
            case 0: return VK_NUMPAD0;
            case 30: return VK_3;
            case 40: return VK_4;
            case 50: return VK_5;
            case 60: return VK_6;
            case 70: return VK_7;
            case 80: return VK_8;
            case 90: return VK_9;
            case 100: return VK_P;
        }
        return VK_R;
    }

    private static void moveToRandomPoint(Rectangle rect) {
        robot.delay(500);
        int x = getRandomX(rect);
        int y = getRandomY(rect);
        assert rect.contains(x, y);
        robot.mouseMove(x,y); //TODO to slowly - may be broken by user
    }

    private static void pressKey(int keyCode) {
        robot.keyPress(keyCode);
        robot.delay(200);
        robot.keyRelease(keyCode);
    }

    private static int getRandomX(Rectangle rect) {
        int w = rect.width - 40;
        int randomdX0 = random.nextInt(w);
        int randomdX = random.nextInt(randomdX0);
        return rect.x + (int)((w - randomdX0)/2)  + randomdX;
    }

    private static int getRandomY(Rectangle rect) {
        int h = rect.height - 40;
        int randomdY0 = random.nextInt(h);
        int randomdY = random.nextInt(randomdY0);
        return rect.y + (int)((h - randomdY0)/2)  + randomdY;
    }


}
