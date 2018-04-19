import javax.swing.*;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    public int world_size = 100;
    public point world[][] = new point[world_size][world_size];
    public point world_initial[][] = new point[world_size][world_size];

    public void init_field() {
        for (int i = 0; i < world_size; i++) {
            for (int j = 0; j < world_size; j++) {
                world[i][j] = new point();
                world_initial[i][j] = new point();
                if (Math.random() < 0.5) {
                    world[i][j].is_live = false;
                    world_initial[i][j].is_live = false;
                }
                else {
                    world[i][j].is_live = true;
                    world_initial[i][j].is_live = true;
                }
            }
        }
    }

    public int count_number_of_neighbours(int l, int s) {
        int number_of_neighbours = 0;
        int vertical[] =   {-1, -1, -1, 0, 1, 1,  1,  0};
        int horizontal[] = {-1,  0,  1, 1, 1, 0, -1, -1};

        for (int type = 0; type < 8; type++) {
            if ((0 <= l + vertical[type]) && (l + vertical[type] < world_size) &&
                    (0 <= s + horizontal[type]) && (s + horizontal[type] < world_size) &&
                    (world[l + vertical[type]][s + horizontal[type]].is_live)) {
                number_of_neighbours++;
            }
        }
        return number_of_neighbours;
    }

    public void refield() {
        int neighbours[][] = new int[world_size][world_size];

        for (int i = 0; i < world_size; i++) {
            for (int j = 0; j < world_size; j++) {
                neighbours[i][j] = count_number_of_neighbours(i, j);
            }
        }

        for (int i = 0; i < world_size; i++) {
            for (int j = 0; j < world_size; j++) {
                if (!world[i][j].is_live && neighbours[i][j] == 3) {
                    world[i][j].is_live = true;
                }
                if (world[i][j].is_live && (neighbours[i][j] == 2 || neighbours[i][j] == 3)) {
                    world[i][j].is_live = true;
                }
                if (world[i][j].is_live && neighbours[i][j] < 2) {
                    world[i][j].is_live = false;
                }
                if (world[i][j].is_live && neighbours[i][j] > 3) {
                    world[i][j].is_live = false;
                }
            }
        }
    }

    public static void startGame(SimpleGUI s) throws Exception{
        Game g = new Game();
        g.init_field();
        /*
        for (int i = 0; i < g.world_size; i++) {
            for (int j = 0; j < g.world_size; j++) {
                System.out.print(" " + ((g.world[i][j].is_live) ? 1 : 0));
            }
            System.out.println("");
        }
        System.out.println("");*/
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                g.refield();
                s.rePaint(g);
            }
        };
        int initialDelay = 0;
        int period = 1;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    /*public void init() {
        world = world_initial;
    }*/

    public static synchronized void main(String[] args) throws  Exception{
        SimpleGUI s = new SimpleGUI();
        startGame(s);
        s.setVisible(true);
    }
}

