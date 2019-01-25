public abstract class Range {
    protected Range [] ranges;
    //protected Set<HoleCards> range;
    //protected Set<HoldemSimpleRange> range;
    //Map


    public boolean contains(HoleCards holeCards) {
        for (Range range : ranges) {
            if (range.contains(holeCards))
                return true;
        }

        return false;
    }
}
