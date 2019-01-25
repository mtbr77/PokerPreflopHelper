public class HoldemRange extends Range{
    //private int[][] rangeTable = new int[14][14];

    public HoldemRange(String compaundRange) {
        String [] lineRanges = compaundRange.split(",");
        int size = lineRanges.length;
        this.ranges = new HoldemLineRange [size];

        for(int i = 0; i < size; i++) {
            this.ranges[i]  = new HoldemLineRange(lineRanges[i].trim());
            //new HoldemLineRange(lineRanges[i]).fillTable(rangeTable);
        }
    }

//    public boolean wasParsed(HoldemHoleCards holeCards) {
//        return rangeTable[holeCards.rangeRow][holeCards.rangeColumn] > 0;
//    }
}
