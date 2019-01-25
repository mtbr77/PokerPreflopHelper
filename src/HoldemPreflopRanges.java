public class HoldemPreflopRanges {
    //Harrington 6-max
    private static final String [] raiseFromPositionRangesString = {
        "22+, A2s+, K7s+, Q9s+, JTs, T9s, 98s, 87s, 76s, A2o+, KTo+, QTo+, JTo", //30% BU - 0
        "22+, A5s+, KTs+, QTs+, JTs, T9s, 98s, 87s, 76s, A8o+, KTo+, QTo+, JTo", //22.5% CO -1
        "22+, A8s+, KTs+, QTs+, JTs, T9s, 98s, ATo+, KJo+", //15.5% MP - 2
        "22+, ATs+, KQs, AJo+", //10% EP -3
        "88+, ATs+, KTs+, QTs+, JTs, ATo+, KTo+, QTo+, JTo" //BB -4
    };

    public static HoldemRange [] raiseFromPositionRanges = new HoldemRange[6];
    static {
        for (int i = 0; i <= 4; i++ ) {
            raiseFromPositionRanges[i] = new HoldemRange(raiseFromPositionRangesString[i]);
        }

        raiseFromPositionRanges[5] = raiseFromPositionRanges[0];

    }

    //public static final HoldemRange
}
