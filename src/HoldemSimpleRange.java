//like AK, 22, AKs, AKo, Ax, Axs
//TODO pool of 169 objects
public class HoldemSimpleRange extends Range{
    private int card1value, card2value;
    private Boolean isSuited;
    private boolean isPair;

    public HoldemSimpleRange(int card1value, int card2value, Boolean isSuited) {
        this.card1value = card1value;
        this.card2value = card2value;
        this.isSuited = isSuited;
        this.isPair = card1value == card2value;
    }

    public HoldemSimpleRange(String simpleRange) {
        card1value = Card.valueToInt(simpleRange.charAt(0));
        card2value = Card.valueToInt(simpleRange.charAt(1));

        int lenght = simpleRange.length();
        switch (lenght) {
            case 2:
                //AK, Ax is ok
                isPair = card1value == card2value;
                isSuited = null;
                break;
            case 3:
                //Axs is ok
                isSuited = simpleRange.charAt(2) == 's';
                isPair = false;
                break;
        }
    }

    public int getHi() {
        return card1value;
    }

    public int getLo() {
        return card2value;
    }

    @Override
    public boolean contains(HoleCards cards) {
        HoldemHoleCards holdemHoleCards = (HoldemHoleCards)cards;
        boolean firstCardsAreEquals = card1value == holdemHoleCards.getHi();
        boolean secondCardsAreEquals = card2value == holdemHoleCards.getLo();
        boolean suitsAreEquals = isSuited == holdemHoleCards.isSuited();
        return firstCardsAreEquals && (secondCardsAreEquals || card2value == 0) && (isSuited == null || suitsAreEquals);
    }
}
