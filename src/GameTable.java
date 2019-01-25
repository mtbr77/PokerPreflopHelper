import java.awt.Rectangle;

public class GameTable { //change to Table?
    private String title;
    private String id;
    private String tableName;
    private GameWindowType tableType;
    private Game game;
    private GameMove move;
    private boolean isZoom;
    private boolean isCashTable;
    private int playersAmount;
    private int tableSize;
    private String gameType; // Holdem, Omaha
    private String betType; // limit, NL
    private Double bb;
    private Double sb;
    private String ante;
    private boolean isPlayMoney;
    private GameLogic logic;
    private Double callAmount;

//    public Thread getThread() {
//        return thread;
//    }
//
//    public void setThread(Thread thread) {
//        this.thread = thread;
//    }
//
//    private Thread thread;

    public double getInitialPotSize() {
        return bb + sb;//+ playersAmount * Double.parseDouble(ante);
    }

    public GameTable(String tableId, String title){
        this.title = title;
        parseTitle(title);
        this.id = tableId;
        move = new GameMove();
        game = new Game(this);
        logic = GameLogicFactory.getLogic(gameType);
        logic.setTableId(id);
    }

    private void parseTitle(String title) {
        String [] titles = title.split(" - ");
        parseGameType(titles);
        parseStake(titles[1]);
    }

    private void parseGameType(String[] titles) {
        if (titles[0].startsWith("$")) {
            parseTournamentGameType(titles[0]);
            isCashTable = false;
        } else {
            isCashTable = true;
            parseCashGameType(titles);
        }
    }

    private void parseCashGameType(String[] titles) {
        tableName = titles[0];
        isZoom = PokerStarsInfo.ZOOMTABLES.contains(tableName);
        gameType = titles[2].split(" ")[2];
    }

    private void parseTournamentGameType(String title) {
        gameType = title.split(" ")[2];
    }

    private void parseStake(String title) {
        String[] parts = title.split(" ");
        String[] stakes;
        if (isCashTable) {
            stakes = parts[0].split("/");
        } else {
            stakes = parts[1].split("/");
            ante = parts[3].substring(1);
        }

        String sbString = stakes[0];
        String bbString = stakes[1];
        isPlayMoney = parts[1].startsWith("Play");
        if (!isPlayMoney) {
            sbString = sbString.substring(1);
            bbString = bbString.substring(1);
        }

        bb = Double.parseDouble(bbString);
        sb = Double.parseDouble(sbString);
    }

    public void setTableType(GameWindowType tableType) {
        this.tableType = tableType;
    }

    public String getTitle() {
        return title;
    }

    public String getGameType() {
        return gameType;
    }

    public boolean isPlayMoney() {
        return isPlayMoney;
    }


    public Rectangle getRectangle() {
        return GameWindows.getRectangle(id);
    }

//    public void makeDecision() {
//
//    }

    public Game getGame() {
        return game;
    }

    public String getId() {
        return id;
    }

    public GameMove getMove() {
        return move;
    }

    public boolean isZoom() {
        return isZoom;
    }

    public Double getBb() {
        System.out.println("bb = " + bb);
        return bb;
    }

    public GameLogic getLogic() {
        return logic;
    }

    public Double getCallAmount() {
        System.out.println("Call = " + callAmount);
        return callAmount;
    }

    public void setCallAmount(Double callAmount) {
        this.callAmount = callAmount;
    }
}
