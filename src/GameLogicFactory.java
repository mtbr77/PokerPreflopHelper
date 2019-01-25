public interface GameLogicFactory {
    static GameLogic getLogic(String gameType) {
        GameLogic logic = null;

        switch(gameType) {
            case GameTypes.HOLDEM:
                logic = new HoldemLogic();
                break;
            case GameTypes.OMAHA:
                logic = new OmahaLogic();
                break;
        }

        return logic;
    }
}
