public class HoldemLogic extends GameLogic {
//    public HoldemLogic(String tableId) {
//        this.tableId = tableId;
//    }
    public static String foldFromAnyPositionRangeString = "K4o-,Q4o-,J6o-,T6o-,96o-,85o-,74o-,63o-,52o,43o-,32o";
    public static HoldemRange foldFromAnyPositionRange = new HoldemRange(foldFromAnyPositionRangeString);

    @Override
    public void determineMove(GameTable table) {
        //System.out.println("start DetermineMove");
        table.getMove().setGameAction(GameAction.NOACTION);
        if (table.getGame().getRound() == GameRound.PF) {
            analyzePreFlopSituation(table);
        }
    }

    private void analyzePreFlopSituation(GameTable table) {
        //System.out.println("start analyzePF");
        int myPosition = table.getGame().getMyPosition();

        if (wasRaise(table))
            if (was3Bet(table))
                if (isholeCardsIsGoodForCall3B(table))
                    table.getMove().setGameAction(GameAction.CALL);
                else
                    if (isholeCardsIsGoodFor4Bet(table))
                        table.getMove().setGameAction(GameAction.POT);
                    else
                        table.getMove().setGameAction(GameAction.FOLD);
            else
                if (isholeCardsIsGoodForCallRaise(table))
                    table.getMove().setGameAction(GameAction.CALL);
                else
                    if (isholeCardsIsGoodFor3Bet(table))
                        table.getMove().setGameAction(GameAction.POT);
                    else
                        table.getMove().setGameAction(GameAction.FOLD);
        else
            if (OCR.noRaisesBefore(table))
                if (holeCardsIsGoodForFirstRaise(table))
                    table.getMove().setGameAction(GameAction.POT);
                else
                    table.getMove().setGameAction(GameAction.FOLD);
            else
                if (isholeCardsIsGoodForLimp(table))
                    table.getMove().setGameAction(GameAction.CALL);
                else
                    if (isholeCardsIsGoodForRaiseLimpers(table))
                        table.getMove().setGameAction(GameAction.POT);
                    else
                        table.getMove().setGameAction(GameAction.FOLD);

    }

    private boolean isholeCardsIsGoodForCall3B(GameTable table) {
        //int myPosition = table.getGame().getMyPosition();
        boolean isInPosition = table.getGame().isInPosition();
        HoleCards holeCards = table.getGame().getHoleCards();
        return HoldemPreflopRanges.raiseFromPositionRanges[myPosition].contains(holeCards);
    }

    private boolean isholeCardsIsGoodFor4Bet(GameTable table) {
        return false;
    }

    private boolean isholeCardsIsGoodForCallRaise(GameTable table) {
        return false;
    }

    private boolean isholeCardsIsGoodFor3Bet(GameTable table) {
        return false;
    }

    private boolean isholeCardsIsGoodForRaiseLimpers(GameTable table) {
        return false;
    }

    private boolean isholeCardsIsGoodForLimp(GameTable table) {
        return false;
    }

    private boolean wasRaise(GameTable table) {
        return table.getCallAmount() >= table.getBb() * 2;
    }

    private boolean was3Bet(GameTable table) {
        return table.getCallAmount() >= table.getBb() * 6.5;
    }

    private boolean holeCardsIsGoodForFirstRaise(GameTable table) {
        int myPosition = table.getGame().getMyPosition();
        HoleCards holeCards = table.getGame().getHoleCards();
        return HoldemPreflopRanges.raiseFromPositionRanges[myPosition].contains(holeCards);
    }


    public boolean holeCardsIsPreliminaryBadPreflop(Game game) {
        HoldemHoleCards holeCards = (HoldemHoleCards) game.getHoleCards();
        if (foldFromAnyPositionRange.contains(holeCards)) {
            return true;
        }

        int position = game.getMyPosition();
        boolean isZoom = game.isZoom();
        //System.out.println("POS= " + position);

        if(position !=4 && !HoldemPreflopMaxRanges.raiseOrCallFromPositionRanges[position].contains(holeCards)) {
            return true;
        }

        return false;
    }


}
