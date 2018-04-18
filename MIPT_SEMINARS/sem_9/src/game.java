import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class game {
    public int world_height = 20;
    public int world_weight = 20;
    public point world[][] = new point[world_height][world_weight]; // TODO: надо сделать world полем класа game

    public void init_field() {
        for (int i = 0; i < world_height; i++) {
            for (int j = 0; j < world_weight; j++) {
                world[i][j] = new point();
            }
        }
        world[0][1].is_live = true;
        world[1][2].is_live = true;
        world[2][0].is_live = true;
        world[2][1].is_live = true;
        world[2][2].is_live = true;
    }

    public int count_number_of_neighbours(int l, int s) {
        int number_of_neighbours = 0;
        int vertical[] = {1, 1, 1, 0, -1, -1, -1, 0};
        int horizontal[] = {-1, 0, 1, 1, 1, 0, -1, -1};

        for (int type = 0; type < 8; type++) {
            if ((0 <= l + vertical[type]) && (l + vertical[type] < world_height) &&
                    (0 <= s + horizontal[type]) && (s + horizontal[type] < world_weight) &&
                    (world[l + vertical[type]][s + horizontal[type]].is_live)) {
                number_of_neighbours++;
            }
        }
        return number_of_neighbours;
    }

    public void refield() {
        point new_world[][] = new point[world_height][world_weight];

        for (int i = 0; i < world_height; i++) {
            for (int j = 0; j < world_weight; j++) {
                new_world[i][j] = new point();
            }
        }

        for (int i = 0; i < world_height; i++) {
            for (int j = 0; j < world_weight; j++) {
                new_world[i][j].is_live = world[i][j].is_live;
            }
        }

        for (int i = 0; i < world_height; i++) {
            for (int j = 0; j < world_weight; j++) {
                if (!world[i][j].is_live && count_number_of_neighbours(i, j) == 3) {
                    new_world[i][j].is_live = true;
                }
                if (world[i][j].is_live && (count_number_of_neighbours(i, j) == 2 | count_number_of_neighbours(i, j) == 3)) {
                    new_world[i][j].is_live = true;
                }
                if (world[i][j].is_live && (count_number_of_neighbours(i, j) < 2 | count_number_of_neighbours(i, j) > 3)) {
                    new_world[i][j].is_live = false;
                }
            }
        }

        for (int i = 0; i < world_height; i++) {
            for (int j = 0; j < world_weight; j++) {
                world[i][j].is_live = new_world[i][j].is_live;
            }
        }
    }

    public static synchronized void main(String[] args) throws Exception {
        game g = new game();
        g.init_field();

        for (int i = 0; i < g.world_height; i++) {
            for (int j = 0; j < g.world_weight; j++) {
                System.out.print(" " + ((g.world[i][j].is_live) ? 1 : 0));
            }
            System.out.println("");
        }
        System.out.println("");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = new Runnable() {
            @Override
            public void run() {
                g.refield();
                for (int i = 0; i < g.world_height; i++) {
                    for (int j = 0; j < g.world_weight; j++) {
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
    }
}
