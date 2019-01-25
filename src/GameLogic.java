public abstract class GameLogic {
    private String tableId; //TODO private?
    public abstract void determineMove(GameTable table);

    public abstract boolean holeCardsIsPreliminaryBadPreflop(Game game);

    public void setTableId(String id) { tableId = id;}

    public synchronized void makePreliminaryXFDecision() {
        //System.out.println("start make Preliminary Decision");
        GameTable table = Tables.getTable(tableId);
        Game game = table.getGame();
        game.setRound(GameRound.PF);
        game.setMyPosition(-11);
        if (holeCardsIsPreliminaryBadPreflop(game)) {
            table.getMove().setGameAction(GameAction.FOLD); //TODO refactor
            System.out.println(tableId + " fold = " + game.getHoleCards().toString());
        } else
            if (game.getMyPosition() == 4)
                table.getMove().setGameAction(GameAction.CHECK);

        if (table.getMove().getActionType() != GameAction.NOACTION) {
            //System.out.println("make prelim move");
            Bot.makeMove(table);
            //System.out.println("finish prelim move");
        }
        //System.out.println("finish make Preliminary Decision");
        notify();
    }

    public synchronized void makeDecision() {
        try {
            wait(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GameTable table = Tables.getTable(tableId);
        if (table.getMove().getActionType() != GameAction.FOLD) {
            //System.out.println("start makeDecision");
            determineMove(table);
            //System.out.println("make move");
            if (table.getMove().getActionType() != GameAction.NOACTION)
                Bot.makeMove(table);
            //System.out.println("finish move");
        }
    }

}
