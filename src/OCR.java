import java.awt.*;

import static java.awt.Color.WHITE;

public class OCR {

    public static int getHeroPosition(Rectangle rectangle) {
        int x0 = rectangle.x;
        int y0 = rectangle.y;
        int w = rectangle.width;
        int h = rectangle.height;

        do {
            for (int i = 0; i <= 5; i++) {
                int buttonX = x0 + (int) (w * PokerStars6maxTable.MINBUTTONX[i]);
                int buttonY = y0 + (int) (h * PokerStars6maxTable.MINBUTTONY[i]);
                Color buttonPlaceColor = Bot.getRobot().getPixelColor(buttonX, buttonY);
                System.out.println("button " + i + " = " + (buttonX - x0) + " " + (buttonY - y0) + " " + buttonPlaceColor);
                boolean buttonNotOnPosition = colorBetween(buttonPlaceColor, PokerStarsColors.MINGREENTABLE, PokerStarsColors.MAXGREENTABLE);
                //System.out.println("buttonNotOnPOs " + i + " = " + buttonNotOnPosition);
                if (!buttonNotOnPosition) {
                    return i;
                }
            }
        } while (true);

        //System.out.println("position was not defined!");
        //return -1;
    }

    private static boolean colorBetween(Color color, Color minColor, Color maxColor) {
        boolean redIsOk = color.getRed() >= minColor.getRed() && color.getRed() <= maxColor.getRed();
        boolean greenIsOk = color.getGreen() >= minColor.getGreen() && color.getGreen() <= maxColor.getGreen();
        boolean blueIsOk = color.getBlue() >= minColor.getBlue() && color.getBlue() <= maxColor.getBlue();
        return redIsOk && greenIsOk && blueIsOk;
    }

    private static boolean colorsAreClose(Color c1, Color c2) {
        int diff = 10;
        boolean redIsOk = c1.getRed() >= c2.getRed() - diff && c1.getRed() <= c2.getRed() + diff;
        boolean greenIsOk = c1.getGreen() >= c2.getGreen() - diff && c1.getGreen() <= c2.getGreen() + diff;
        boolean blueIsOk = c1.getBlue() >= c2.getBlue() - diff && c1.getBlue() <= c2.getBlue() + diff;
        return redIsOk && greenIsOk && blueIsOk;
    }


    public static boolean noRaisesBefore(GameTable table) { //TODO take account limps because KK- fold after several limps
        //System.out.println("start noRaisesBefore");
        int buttonAbsPosition = table.getGame().getMyPosition();
        int bbAbsPosition = (buttonAbsPosition + 2) % 6;
        for (int absPosition = 5; absPosition > bbAbsPosition ; absPosition--) {
            System.out.println("check position = " + absPosition);
            if (getBetSize(table, absPosition) > table.getBb()) {
                table.getGame().setRaiserPosition((6 + buttonAbsPosition - absPosition) % 6);
                return false;
            }

        }

        return true;
    }

    private static double getPotSize(GameTable table) {
        Rectangle rect = table.getRectangle();
        int w = rect.width;
        int h = rect.height;
        Point pos = getRoughPotSizePosition(w, h);
        int x = rect.x + pos.x;
        int y = rect.y + pos.y;
        Color c;
        do {
            x++;
            c = Bot.getRobot().getPixelColor(x, y);
        } while (!c.equals(WHITE));

        x += table.isPlayMoney() ? 25 : 32;
        return recognizePotDouble(x, y);
    }

    private static double recognizePotDouble(int x, int y) {
        char c;
        String s = "";
        do {
            c = recognizePotSymbol(x, y);
            if (c == ' ') {
                break;
            }

            if (c != ',') s += c;

            if (c == '.' || c == ',') {
                x += 3;
            } else {
                x += 7;
            }
        } while (true);
        System.out.println("potSize=" + s);
        return Double.parseDouble(s);
    }

    private static char recognizePotSymbol(int x, int y) {
        Color c18 = Bot.getRobot().getPixelColor(x, y);
        Color c12 = Bot.getRobot().getPixelColor(x, y - 6);

        if (c18.equals(WHITE))
            if (c12.equals(WHITE)) return '2';
            else
                if (Bot.getRobot().getPixelColor(x, y + 1).equals(WHITE)) return ','; else return '.';
        else
            if (c12.equals(WHITE)) //3 6 8 9 0
                if (Bot.getRobot().getPixelColor(x, y - 5).equals(WHITE)) //6 8 9 0
                    if (Bot.getRobot().getPixelColor(x, y - 4).equals(WHITE)) //6 9 0
                        if (Bot.getRobot().getPixelColor(x, y - 3).equals(WHITE)) //6 0
                            if (Bot.getRobot().getPixelColor(x + 5, y - 4).equals(WHITE)) return '0'; else return '6';
                        else return '9';
                    else return '8';
                else return '3';
            else { //1 4 5 7
                boolean is42White = Bot.getRobot().getPixelColor(x + 3, y - 6).equals(WHITE);
                if (Bot.getRobot().getPixelColor(x + 2, y - 6).equals(WHITE)) // 1 5
                    if (is42White) return '1'; else return '5';
                else
                    if (is42White) return '4';
                    else
                        if (Bot.getRobot().getPixelColor(x, y - 7).equals(WHITE)) return '7';
                        else return ' ';
            }
    }

    private static Point getRoughPotSizePosition(int w, int h) {
        if (w == 483 && h == 354) {
            return new Point(161, 124);
        }

        if (w == 525 && h == 383) {
            return new Point(175, 132);
        }

        if (w == 568 && h == 413) {
            return new Point(189, 141);
        }

        return null;
    }

    private static double getBetSize(GameTable table, int position) {
        Rectangle rect = table.getRectangle();
        int w = rect.width;
        int h = rect.height;
        Point pos = getRoughBetSizePosition(w, h, position);
        int x = rect.x + pos.x;
        int y = rect.y + pos.y;
        boolean isBetFound = false;
        int i = -1;
        do {
            if (i == 60) return 0;
            i++;
            x += i;
            for (int j = 0; j < 8; j++) {
                if (Bot.getRobot().getPixelColor(x, y - j).equals(PokerStarsColors.BET)) {
                    isBetFound = true;
                    break;
                }
            }
        } while (!isBetFound);

        x += table.isPlayMoney() ? 0 : 6;
        System.out.println("bet found at " + (x - rect.x) + " " + (y - rect.y));
        return recognizeBetDouble(x, y);
    }

    private static double recognizeBetDouble(int x, int y) {
        char c;
        String s = "";
        do {
            c = recognizeBetSymbol(x, y);
            if (c == ' ') {
                break;
            }

            if (c != ',') s += c;

            if (c == '.' || c == ',') {
                x += 4;
            } else {
                x += 6;
            }
        } while (true);
        System.out.println("betSize=" + s);
        return Double.parseDouble(s);
    }

    private static char recognizeBetSymbol(int x, int y) {
        System.out.println("try recogn at " + x + " " + y);
        Color c18 = Bot.getRobot().getPixelColor(x, y);
        Color c12 = Bot.getRobot().getPixelColor(x, y - 6);

        if (Bot.getRobot().getPixelColor(x + 1, y + 1).equals(PokerStarsColors.BET)) return ',';

        if (c18.equals(PokerStarsColors.BET))
            if (c12.equals(PokerStarsColors.BET)) return '2';
            else ;
        else
            if (c12.equals(PokerStarsColors.BET)) //3 8 9 0 5
                if (Bot.getRobot().getPixelColor(x, y - 5).equals(PokerStarsColors.BET)) // 8 9 0 5
                    if (Bot.getRobot().getPixelColor(x, y - 4).equals(PokerStarsColors.BET)) // 9 0 5
                        if (Bot.getRobot().getPixelColor(x, y - 3).equals(PokerStarsColors.BET)) //6 0 5
                            if (Bot.getRobot().getPixelColor(x + 4, y - 4).equals(PokerStarsColors.BET)) return '0'; else ;
                        else if (Bot.getRobot().getPixelColor(x + 1, y - 3).equals(PokerStarsColors.BET)) return '9'; else return '5';
                    else return '8';
                else return '3';
            else { //1 4 7 6
                boolean is32White = Bot.getRobot().getPixelColor(x + 2, y - 6).equals(PokerStarsColors.BET);
                if (Bot.getRobot().getPixelColor(x + 1, y - 6).equals(PokerStarsColors.BET)) // 1
                    if (is32White) return '1'; else return '6';
                else
                    if (is32White) return '4';
                    else
                        if (Bot.getRobot().getPixelColor(x, y - 7).equals(PokerStarsColors.BET)) return '7';
                        else
                            if (Bot.getRobot().getPixelColor(x + 1, y).equals(PokerStarsColors.BET)) return '.';
                            else return ' ';
            }

        return ' ';
    }

    private static Point getRoughBetSizePosition(int w, int h, int position) {
        if (w == 483 && h == 354) {
            switch (position) {
                case 0: return new Point(200, 208);
                case 1: return new Point(122, 191);
                case 2: return new Point(135, 116);
                case 3: return new Point(233, 96);
                case 4: return new Point(300, 116);
                case 5: return new Point(300, 191);
            }

        }

        if (w == 525 && h == 383) {

        }

        if (w == 568 && h == 413) {

        }

        return null;
    }

    public static int getRaiserPosition(Rectangle rectangle) {
        return 0;
    }
}