public class Board {
    public int world_size_x = 100;
    public int world_size_y = 50;
    public Cell world[][] = new Cell[world_size_y][world_size_x];
    public Cell world_initial[][] = new Cell[world_size_y][world_size_x];

    public Board() {
        for (int i = 0; i < world_size_y; i++) {
            for (int j = 0; j < world_size_x; j++) {
                world[i][j] = new Cell();
                world_initial[i][j] = new Cell();
            }
        }
    }

    public void init_clear() {
        for (int i = 0; i < world_size_y; i++) {
            for (int j = 0; j < world_size_x; j++) {
                world[i][j].is_live = false;
                world_initial[i][j].is_live = false;
            }
        }
    }

    public void init_random_field() {
        for (int i = 0; i < world_size_y; i++) {
            for (int j = 0; j < world_size_x; j++) {
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

    public void to_initial() {
        for (int i = 0; i < world_size_y; i++) {
            for (int j = 0; j < world_size_x; j++) {
                world[i][j].is_live = world_initial[i][j].is_live;
            }
        }
    }

    public void init_glider() {
        for (int i = 0; i < world_size_y; i++) {
            for (int j = 0; j < world_size_x; j++) {
                world[i][j].is_live = false;
                world_initial[i][j].is_live = false;
            }
        }

        world[0][1].is_live = true;
        world[1][2].is_live = true;

        world_initial[0][1].is_live = true;
        world_initial[1][2].is_live = true;
        for (int i = 0; i < 3; i++) {
            world[2][i].is_live = true;
            world_initial[2][i].is_live = true;
        }
    }

    public int mod(int coord, int size) {
        return (coord % size + size) % size; // прибавляем, т.к. может быть отрицательно
    }

    public int count_number_of_neighbours(int l, int s) {
        int number_of_neighbours = 0;
        int vertical[] =   {-1, -1, -1, 0, 1, 1,  1,  0};
        int horizontal[] = {-1,  0,  1, 1, 1, 0, -1, -1};

        for (int type = 0; type < 8; type++) {
            if (world[mod(l + vertical[type], world_size_y)][mod(s + horizontal[type], world_size_x)].is_live) {
                number_of_neighbours++;
            }
        }
        return number_of_neighbours;
    }

    public void reCount() {
        int neighbours[][] = new int[world_size_y][world_size_x];

        for (int i = 0; i < world_size_y; i++) {
            for (int j = 0; j < world_size_x; j++) {
                neighbours[i][j] = count_number_of_neighbours(i, j);
            }
        }

        for (int i = 0; i < world_size_y; i++) {
            for (int j = 0; j < world_size_x; j++) {
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
}
