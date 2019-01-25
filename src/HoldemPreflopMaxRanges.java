public class HoldemPreflopMaxRanges  {
    private static final String [] raiseOrCallFromPositionRangesString = {
            "22+, A2s+, K2s+, Q2s+, J7s+, T7s+, 95s+, 85s+, 75s+, 64s+, 53s+, 43s, A2o+, KTo+, QTo+, JTo, T9o, 98o, 87o, 76o, 65o, 54o", // 43% BU
            "22+, A2s+, K5s+, Q8s+, J8s+, T8s+, 97s+, 87s, 76s, 65s, 54s, 43s, A5o+, K9o+, QTo+, JTo", //31.2% CO
            "22+, A2s+, K7s+, Q9s+, J9s+, T8s+, 98s, 87s, 76s, 65s, 54s, A7o+, KTo+, QTo+, JTo", //26.7%  MP
            "22+, A2s+, K9s+, Q9s+, J9s+, T9s, 98s, 87s, 76s, 65s, 54s, ATo+, KJo+, QJo", //20% UTG
            "22+, A2s+, K2s+, Q2s+, J2s+, T2s+, 92s+, 82s+, 72s+, 62s+, 52s+, 42s+, 32s, A2o+, K5o+, Q5o+, J7o+, T7o+, 97o+, 86o+, 75o+, 64o+, 53o+", //69.23 BB
            "22+, A2s+, K2s+, Q2s+, J2s+, T4s+, 95s+, 85s+, 74s+, 63s+, 53s+, 43s, A2o+, K4o+, Q8o+, J8o+, T8o+, 97o+, 87o, 76o, 65o, 54o" //57.7 SB
    };

    public static HoldemRange [] raiseOrCallFromPositionRanges = new HoldemRange[6];
    static {
        for (int i = 0; i < 6; i++ ) {
            raiseOrCallFromPositionRanges[i] = new HoldemRange(raiseOrCallFromPositionRangesString[i]);
        }
    }
}
