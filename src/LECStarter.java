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

        //Bot bot = new Bot();
        //execService.execute(bot);
        //GameWindows pokerWindowsDetector = new GameWindows();
        GameWindows.detect(); //Tables.detect(); ?

        PokestarsLogParser pokestarsLogParser = new PokestarsLogParser();
        pokestarsLogParser.run();
    }

    public void RunHUD() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setSize(400,400);
                frame.setUndecorated(true);
                frame.setOpacity(0.55f);
                //frame.setShape();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
