import java.util.*;

public class OmahaHoleCards extends HoleCards {
    private int score;
    private ArrayList<Card> cardsOnSuites = new ArrayList<Card>(cards);

    public OmahaHoleCards(Collection<String> cards) {
        super(cards);
        Collections.sort(cardsOnSuites, new CardSuitComparator());
        calculateHatchisonScore();
    }

    public OmahaHoleCards(String[] cards) {
        this(Arrays.asList(cards));
    }

    public OmahaHoleCards(String cards) {
        this(cards.split(",?\\s"));
    }

    public int getHatchisonScore() {
        System.out.println("Hatchison = " + score);
        return score;
    }

    private void calculateHatchisonScore() {
        calculateSuitedScore();
        calculatePairesScore();
        calculateStraightScore();
    }

    private void calculateStraightScore() {
        int n = cards.size();
        int numberOfConnectors = 1;
        //Set<Integer> pairCardsValue = new HashSet<Integer>();
        //HoldemHoleCards[] holdemCards = new HoldemHoleCards[n];
        for(int i = 0; i <= n - 2 ; i++) {
            //holdemCards[i] = new HoldemHoleCards(cardsOnSuites.get(i), cardsOnSuites.get(i + 1));
            int card1 = cards.get(i).getValue();
            int card2 = cards.get(i + 1).getValue();
            int diff = card1 - card2;
            if (diff <= 4 && diff >= 1) {
                numberOfConnectors++;
                if (card1 == 14) {
                    score -= 4;
                    int lastCard = cards.get(n - 1).getValue();
                    if (lastCard <= 5) {
                        score += 6;
                        int beforeLastCard = cards.get(n - 2).getValue();
                        if (beforeLastCard <= 5 && beforeLastCard - lastCard > 1) score += 6;
                    }
                }
                if (diff >= 2) score -= 2*(diff - 1);
            }
        }

        switch (numberOfConnectors) {
            case 2: score += 8; break;
            case 3: score += 18; break;
            case 4: score += 25; break;
        }
    }

    private void calculatePairesScore() {
        int n = cards.size();
        int numberOfPair = 0;
        Set<Integer> pairCardsValue = new HashSet<Integer>();
        HoldemHoleCards[] holdemCards = new HoldemHoleCards[n - 1];
        for(int i = 0; i <= n - 2 ; i++) {
            holdemCards[i] = new HoldemHoleCards(cards.get(i), cards.get(i + 1));
            if (holdemCards[i].isPair()) {
                numberOfPair++;
                if (!pairCardsValue.add(holdemCards[i].getHi()))
                    return;
            }
        }

        for (int i : pairCardsValue) {
            calculateCardValueScoreForPair(i);
        }
    }

    private void calculateCardValueScoreForPair(int cardValue) {
        if (cardValue <= 7) {
            score += 7;
        } else {
            switch(cardValue) {
                case 8 : score += 8; break;
                case 9 : score += 10; break;
                case 10 : score += 12; break;
                case 11 : score += 13; break;
                case 12 : score += 14; break;
                case 13 : score += 16; break;
                case 14 : score += 18; break;
            }
        }
    }

    private void calculateSuitedScore() { //TODO for 5 card omaha
        int n = cardsOnSuites.size();
        int numberOfSuted = 0;
        Set<Integer> suitedCardsValue = new HashSet<Integer>();
        HoldemHoleCards[] holdemCards = new HoldemHoleCards[n - 1];
        for(int i = 0; i <= n - 2 ; i++) {
            holdemCards[i] = new HoldemHoleCards(cardsOnSuites.get(i), cardsOnSuites.get(i + 1));
            if (holdemCards[i].isSuited()) {
                numberOfSuted++;
                suitedCardsValue.add(holdemCards[i].getHi());
            }
        }

        if (numberOfSuted == 0) return;

        boolean isDoubleSuited = numberOfSuted == 2 && !holdemCards[1].isSuited();
        if (isDoubleSuited) {
            calculateCardValueScoreForSuited(holdemCards[0].getHi());
            calculateCardValueScoreForSuited(holdemCards[2].getHi());
            return;
        }

        calculateCardValueScoreForSuited(Collections.max(suitedCardsValue));

        if (numberOfSuted >= 2)
            score = score - 2;
    }

    private void calculateCardValueScoreForSuited(int cardValue) {
        if (cardValue <= 7) {
            score++;
        } else {
            switch(cardValue) {
                case 8 : score += 2; break;
                case 9 :
                case 10 : score += 3; break;
                case 11 : score += 4; break;
                case 12 : score += 5; break;
                case 13 : score += 6; break;
                case 14 : score += 8; break;
            }
        }
    }


}
