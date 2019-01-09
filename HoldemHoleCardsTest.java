import junit.framework.TestCase;

public class HoldemHoleCardsTest extends TestCase {
    public void testInRange() throws Exception {
        HoldemHoleCards cards = new HoldemHoleCards("2h","14h");
        boolean inRange = cards.inRange(HoldemPreflopMaxRanges.raiseOrCallFromPositionRanges[5]);
        assertTrue(inRange);
    }

    public void testDouble() throws Exception {
        char c = 0;
        String s ="" + '1' + c + '2';
        System.out.println(s + "[123]".split("\\[|\\]")[1]);
        assertTrue(c == ' ');
    }
}