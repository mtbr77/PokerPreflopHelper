import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LECStarter {
    public static ExecutorService execService = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        startPlaying();
    }

    public static void startPlaying() {
        GameWindows.detect();

        PokestarsLogParser pokestarsLogParser = new PokestarsLogParser();
        pokestarsLogParser.run();
    }

}
