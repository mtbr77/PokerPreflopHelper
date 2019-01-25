import java.util.*;

public class Tables {
    private static Map<String, GameTable> tables = new HashMap<String, GameTable>();

//    public static void makePreliminaryXFDecision(String tableId) {
//        getTable(tableId).makePreliminaryXFDecision();
//    }

//    public static void makeDecision(String tableId) {
//        getTable(tableId).makeDecision();
//    }

    public static boolean isTableDetected(String id) {
        return tables.containsKey(id);
    }

    public static GameTable addTable(GameTable gameTable) {
        String tableId = gameTable.getId();
        tables.put(tableId, gameTable);
        System.out.println(tableId + " table added");
        return gameTable;
    }

    public static GameTable getTable(String id) {
        GameTable window = tables.get(id);
        if (window == null) {
            System.out.println(id + "tableID = " + tables.keySet().toString());
            System.out.println("values = " + tables.values().toString());
        }

        return window;
    }

    public static int getTablesAmount() {
        return tables.size();
    }

    public static void deleteTable(String id) {
        tables.remove(id);
    }

    public static boolean setHoleCardsOnTable(String tableId, ArrayList<String> cards) {
        GameTable table = getTable(tableId);
        return table == null ? false : getTable(tableId).getGame().setHoleCards(cards);
    }
}
