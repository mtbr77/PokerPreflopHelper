import java.io.*;
import java.util.*;

public class PokestarsLogParser implements Runnable {
    public static final String LOG_PATH = System.getenv("USERPROFILE")+"\\AppData\\Local\\PokerStars\\PokerStars.log.0";
    private File logFile;
    private long size;
    private BufferedReader logFileReader;
    private String prevLine;
    private String currentLine;
    //private String tableId;//not good for multitabling

    public PokestarsLogParser() {
        logFile = new File(LOG_PATH);

        try {
            logFileReader = new BufferedReader(new FileReader(logFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }

        size = logFile.length();
    }

    @Override
    public void run() {
        try {
            logFileReader.skip(size);
        } catch (IOException e) {
            e.printStackTrace();
        }

        do {
            parse();
        } while (true);
    }

    public void parse() {
        currentLine = getNextLine();

        if (!currentLine.equals("") && currentLine.length() >= 4) {
            //System.out.println(currentLine);
            parseLine();
        }
    }

    private void parseLine() {

        if (currentLine.startsWith("::: ")) {
            parseHoleCards();
        }

        if (currentLine.startsWith(" \'C\'")) {

        //if (currentLine.startsWith("configAdvActions") && prevLine.startsWith("<-") && getNextLine().startsWith("->")) {
            parseTableAction();
        }

        if (currentLine.startsWith(":::T")) {
            parseBoardCards();
        }

        if (currentLine.startsWith("table window")) {
            parseTableLifeCycle();
        }
    }

    private void parseTableAction() {
        Double bet = Integer.parseInt(currentLine.split(" ")[2]) * 0.01;
        getNextLine();
        String[] s = getNextLine().split(" ");
        String tableId = extractTableIdWithoutZeros(s[s.length - 1]);
        GameTable table = Tables.getTable(tableId);
        table.setCallAmount(bet);
        System.out.println("before start makeDecision");
        LECStarter.execService.execute(new Runnable() {
                public void run() {

//                    if (table.getThread() != null)
//                        try {
//                            System.out.println("thread state = " + table.getThread().getState().toString());
//                            table.getThread().join();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                    table.getLogic().makeDecision();
                }
            });
    }

    private String getNextLine() {
        String nextLine = null;
        try {
            nextLine = logFileReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        prevLine = currentLine;
        return nextLine == null ? "" : nextLine;
    }

    private void parseBoardCards() {
        String tableId = currentLine.split(" ")[3].split("\\[|\\]")[1];
        GameTable table = Tables.getTable(tableId);
        if (table != null) table.getGame().setRound(GameRound.F);
    }

    private void parseTableLifeCycle() {
        String tableId = extractTableIdWithoutZeros(currentLine.substring(13,21));
        if (currentLine.contains("created")) {
            System.out.println(currentLine);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            GameWindows.detect();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (currentLine.contains("destroyed")) {
            Tables.deleteTable(tableId);
        }
    }

    private void parseHoleCards() {
        ArrayList<String> cards = new ArrayList<>(7);
        String nextLine = currentLine;
        do {
            cards.add(nextLine.substring(4));
            nextLine = getNextLine();
        } while (nextLine.startsWith("::: "));

        String tableId = null;
        if (nextLine.startsWith("------")) {
            tableId = extractTableIdWithoutZeros(nextLine.substring(7));
            System.out.println("------ " + tableId);
        }

        System.out.println("::: " + cards);
        cards.trimToSize();
        if (Tables.setHoleCardsOnTable(tableId, cards)) {
            GameTable table = Tables.getTable(tableId);
            table.getMove().setGameAction(GameAction.NOACTION);
            System.out.println("before start makePrelimDecision");
            LECStarter.execService.execute(new Runnable() {
                public void run() {

                    //table.setThread(Thread.currentThread());
                    table.getLogic().makePreliminaryXFDecision();

                }
            });
        }
    }

    private String extractTableIdWithoutZeros(String rawTableId) {
        if (rawTableId.startsWith("000"))
            return rawTableId.substring(3);
        if (rawTableId.startsWith("00"))
            return rawTableId.substring(2);
        if (rawTableId.startsWith("0"))
            return rawTableId.substring(1);
        return rawTableId;
    }
}
