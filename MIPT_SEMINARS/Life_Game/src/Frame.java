import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Frame extends JFrame {

    private Board board = new Board();
    private ScheduledFuture future;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private Color color; // Объявили переменную типа Color, она отвечает за цвета
    private List<Point> fillCells; // Объявили список, содержащий клетки типа Point
    int cell_size = 10;
    boolean was_saved = false;

    public Frame() {
        board.init_clear();
        fillCells = new ArrayList(Math.max(board.world_size_x, board.world_size_y));

        setSize(cell_size * (board.world_size_x + 3) + cell_size, cell_size * (board.world_size_y + 10) + 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        color = Color.BLACK;    // "Включаем" чёрный цвет для наших "рисунков"
        JPanel panel = new JPanel(); // Создаём панель (прямоугольное пространство, на котором можно размещать элементы)
        getContentPane().add(panel); // Добавляем панель на экран

        final JButton runButton = new JButton("Run!");
        final JButton resetButton = new JButton("Reset");
        final JButton gliderButton = new JButton("Set glider");
        final JButton randomButton = new JButton("Set random");
        final JButton clearButton = new JButton("Clear field");
        final JButton loadButton = new JButton("Load from file");
        final JButton writeButton = new JButton("Save to file");

        panel.add(runButton);
        panel.add(resetButton);
        panel.add(gliderButton);
        panel.add(randomButton);
        panel.add(clearButton);
        panel.add(loadButton);
        panel.add(writeButton);
        loadButton.setEnabled(false);

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (future == null || future.isCancelled()) {
                    runButton.setText("Pause");
                    resetButton.setEnabled(false);
                    future = executor.scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            board.reCount();
                            rePaint(board);
                        }
                    }, 0, 100, TimeUnit.MILLISECONDS);
                } else {
                    future.cancel(false);
                    runButton.setText("Run!");
                    resetButton.setEnabled(true);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.to_initial();
                rePaint(board);
            }
        });

        gliderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.init_glider();
                rePaint(board);
            }
        });

        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.init_random_field();
                rePaint(board);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.init_clear();
                rePaint(board);
            }
        });

        writeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (future != null) {
                    future.cancel(false);
                }
                runButton.setText("Run!");
                resetButton.setEnabled(true);
                loadButton.setEnabled(true);

                FileOutputStream out = null;
                try {
                    out = new FileOutputStream("output.txt");
                    for (int i = 0; i < board.world_size_y; i++) {
                        for (int j = 0; j < board.world_size_x; j++) {
                            out.write(board.world[i][j].is_live ? '1' : '0');
                            //out.write(' ');
                        }
                        out.write('\n');
                    }
                } catch (IOException ignore) {

                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException ignore) {
                        }
                    }
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (future != null) {
                    future.cancel(false);
                }
                runButton.setText("Run!");
                resetButton.setEnabled(true);

                FileInputStream in = null;
                try {
                    in = new FileInputStream("output.txt");
                    int c;
                    int j = 0;
                    for (int i = 0; i < board.world_size_y; i++) {
                        while ((c = in.read()) != '\n') {
                            if (c == '1') {
                                board.world[i][j].is_live = true;
                                board.world_initial[i][j].is_live = true;
                            } else if (c == '0') {
                                board.world[i][j].is_live = false;
                                board.world_initial[i][j].is_live = false;
                            }
                            j++;
                        }
                        j = 0;
                    }

                    rePaint(board);
                } catch (IOException ignore) {

                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ignore) {
                        }
                    }
                }
            }

        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX();
                int y = e.getY();
                int x_cell = (x) / cell_size - 2;
                int y_cell = (y - 140) / cell_size;
                if (x_cell >= 0 && x_cell < board.world_size_x && y_cell >= 0 && y_cell < board.world_size_y) {
                    if (!board.world[y_cell][x_cell].is_live) {
                        board.world[y_cell][x_cell].is_live = true;
                    } else board.world[y_cell][x_cell].is_live = false;
                }

                fillCells.clear();
                for (int i = 0; i < board.world_size_y; i++) {
                    for (int j = 0; j < board.world_size_x; j++) {
                        if (board.world[i][j].is_live) {
                            fillCells.add(new Point(j, i));
                        }
                    }
                }
                repaint();
            }
        });
    }

    public void fillCell(int x, int y) {
        fillCells.add(new Point(x, y)); // добавляем список клеток на график
    }

    public void rePaint(Board board) {
        // сначала очистим список
        fillCells.clear();

        for (int i = 0; i < board.world_size_y; i++) {
            for (int j = 0; j < board.world_size_x; j++) {
                if (board.world[i][j].is_live) {
                    fillCells.add(new Point(j, i));
                }
            }
        }
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        //Graphics2D g2 = (Graphics2D) g; // создали график на плоскости
        //g2.setColor(Color.BLACK); // выбрали цвет для нашего графика
        Image offscreen = createImage((board.world_size_x) * cell_size + cell_size + 1, (board.world_size_y) * cell_size + 71);
        Graphics2D g2d = (Graphics2D) offscreen.getGraphics();
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, board.world_size_x * cell_size, board.world_size_y * cell_size);

        g2d.setColor(Color.BLACK);
        for (int i = cell_size; i <= (board.world_size_x) * cell_size + cell_size; i = i + cell_size) { // Рисуем вертикальные линии x: от 10 позиции слева до 500; y: от 70 позиции сверху до 510 (их размер как-то там автоматически определен)
            Line2D lin = new Line2D.Float(i, 70, i, board.world_size_y * cell_size + 70);
            g2d.draw(lin);
        }
        for (int i = 70; i <= (board.world_size_y) * cell_size + 70; i = i + cell_size) { // Рисуем горизонтальные линии
            Line2D lin = new Line2D.Float(cell_size, i, (board.world_size_x) * cell_size + cell_size, i);
            g2d.draw(lin);
        }
        g2d.setColor(Color.BLACK);
        g2d.drawRect(cell_size, 70, board.world_size_x * cell_size + cell_size, board.world_size_y * cell_size + 70); // рисуем прямоугольник в соответствии с расположением линий


        for (Point fillCell : fillCells) { // проходимся циклом по списку из клеток
            int cellX = cell_size + (fillCell.x * cell_size); // координата x для нашей выбранной из списка клетки
            int cellY = 70 + (fillCell.y * cell_size); // координата y
            g2d.setColor(Color.BLACK);    // выбираем черный цвет для дальнейшего рисунка
            g2d.fillRect(cellX, cellY, cell_size, cell_size); // закрашиваем клетку
        }
        Graphics2D g3 = (Graphics2D) g;
        g3.drawImage(offscreen, cell_size, 70, this);
    }

    public static void main(String[] arg) {
        Frame s = new Frame();
        s.setVisible(true);
    }
}
