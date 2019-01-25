import java.util.regex.Pattern;

//AJs+, 22+, AJs, AJ+, AK, 22, 99-33, 98s-65s, 97s-42s, AhKh, Ax, Axs
public class HoldemLineRange extends Range {

    public HoldemLineRange(String lineRange) {
        int rangeSize = lineRange.length();
        int card1value = Card.valueToInt(lineRange.charAt(0));
        int card2value = Card.valueToInt(lineRange.charAt(1));
        boolean isPair = card1value == card2value;

        switch (rangeSize) {
            case 2:
            case 3:
            case 4:
                if (rangeSize == 2 || (rangeSize == 3 && (lineRange.charAt(2) == 's' || lineRange.charAt(2) == 'o'))) { //22, AK , AKs
                    this.ranges = new HoldemSimpleRange [1];
                    ranges[0] = new HoldemSimpleRange(lineRange);
                } else { //KJo+, K3- , 22+, 33-
                    int size, start, finish;

                    if (lineRange.charAt(rangeSize - 1) == '-') {
                        start = card2value;
                        size = start - 2 + 1;
                        finish = 2;
                    } else {
                        start = isPair ? 14 : card1value - 1;
                        size = start - card2value + 1;
                        finish = card2value;
                    }

                    Boolean isSuited = false;
                    try {
                        isSuited = rangeSize == 3 ? null : lineRange.charAt(2) == 's';
                    } catch (NullPointerException e) {
                        System.out.println("NPE on " + lineRange);
                    }
                    this.ranges = new HoldemSimpleRange [size];
                    for (int i = start; i >= finish; i--) {
                        this.ranges[start - i] = new HoldemSimpleRange(isPair ? i : card1value , i, isSuited);
                    }
                }
                break;

            case 5: // 99-33
                int card3value = Card.valueToInt(lineRange.charAt(3));
                this.ranges = new HoldemSimpleRange [card1value - card3value + 1];
                for (int i = card1value; i >= card3value; i--) {
                    this.ranges[card1value - i] = new HoldemSimpleRange(i, i, null);
                }
                break;

            case 7: // 98s-65s, 97s-42s
                card3value = Card.valueToInt(lineRange.charAt(4));
                int diff = card1value - card2value;
                boolean isSuited = lineRange.charAt(2) == 's';
                this.ranges = new HoldemSimpleRange [card1value - card3value + 1];
                for (int i = card1value; i >= card3value; i--) {
                    this.ranges[card1value - i] = new HoldemSimpleRange(i, i - diff, isSuited);
                }
                break;
        }

    }

    public void fillTable(int[][] rangeTable) {

    }
}
