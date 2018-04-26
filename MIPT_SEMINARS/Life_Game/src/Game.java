import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    public static void main(String[] args) {
        /*Board g = new Board();
        g.init_glider();

        for (int i = 0; i < g.world_size_y; i++) {
            for (int j = 0; j < g.world_size_x; j++) {
                System.out.print(" " + ((g.world[i][j].is_live) ? 1 : 0));
            }
            System.out.println("");
        }
        System.out.println("");

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                g.reCount();

                for (int i = 0; i < g.world_size_y; i++) {
                    for (int j = 0; j < g.world_size_x; j++) {
                        System.out.print(" " + ((g.world[i][j].is_live) ? 1 : 0));
                    }
                    System.out.println("");
                }
                System.out.println("");

            }
        };
        int initialDelay = 0;
        int period = 1;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
        */
        Frame s = new Frame();
        //  startGame(s);
        s.setVisible(true);
    }
}
