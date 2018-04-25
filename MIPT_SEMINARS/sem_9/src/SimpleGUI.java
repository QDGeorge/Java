import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.concurrent.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class SimpleGUI extends JFrame { // JFrame - класс, представляющий собой окно с рамкой и с кнопками: "свернуть", "во весь экран", "закрыть"
    private ScheduledFuture future;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private Color color; // Объявили переменную типа Color, она отвечает за цвета
    private List<Point> fillCells; // Объявили список, содержащий клетки типа Point
    int cell_size = 5;
    private Game usefull_game = new Game();
    public SimpleGUI() { //Конструктор без параметров, который создает список с клетками и рисует кнопки
        usefull_game.init_field();
        fillCells = new ArrayList(usefull_game.world_size); // Создаем список с клетками, начальный размер списка = 10,
        setSize((usefull_game.world_size + 12) * cell_size, 70 + (usefull_game.world_size + 20) * cell_size); // устанавливаем размер окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // позволяет указать действие, которое необходимо выполнить, когда закрываем окно, нажатием на крестик (в данном случае мы закрываем окно)
        color = Color.BLACK;    // "Включаем" чёрный цвет для наших "рисунков"
        JPanel panel = new JPanel(); // Создаём панель (прямоугольное пространство, на котором можно размещать элементы)
        getContentPane().add(panel); // Добавляем панель на экран

        final JButton button = new JButton("Start"); // создаём кнопки с надписями и размещаем их на панели
        panel.add(button);
        //final JButton button1 = new JButton("Stop");
        //panel.add(button1);
        final JButton button2 = new JButton("Reset");
        panel.add(button2);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (future == null || future.isCancelled()) {
                    button.setText("Pause");
                    button2.setEnabled(false);
                    future = executor.scheduleAtFixedRate(new Runnable() {
                        @Override
                        public void run() {
                            usefull_game.refield();
                            SimpleGUI.this.rePaint(usefull_game);
                        }
                    }, 0, 1000, TimeUnit.MILLISECONDS);
                } else {
                    future.cancel(false);
                    button.setText("Run!");
                    button2.setEnabled(true);
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usefull_game.init_field();
                SimpleGUI.this.repaint();
            }
        });
    }

    public void paint(Graphics g) { // Функция, которая рисует клетки
        //Game usefull_game = new Game();
        super.paint(g); //
        Graphics2D g2 = (Graphics2D) g; // создали график на плоскости
        g2.setColor(Color.BLACK); // выбрали цвет для нашего графика
        Image offscreen = createImage((usefull_game.world_size ) * cell_size , 70+(usefull_game.world_size) * cell_size);
        Graphics2D g2d = (Graphics2D) offscreen.getGraphics();
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, (usefull_game.world_size ) * cell_size + 5, (usefull_game.world_size) * cell_size);

        for (int i = 2*cell_size; i <= usefull_game.world_size*cell_size + 55; i = i + cell_size) { // Рисуем вертикальные линии x: от 10 позиции слева до 500; y: от 70 позиции сверху до 510 (их размер как-то там автоматически определен)
            Line2D lin = new Line2D.Float(i, 70, i, (usefull_game.world_size ) * cell_size + 70);
            g2d.draw(lin);
        }
        for (int i = 70; i <= (usefull_game.world_size) * cell_size + 70; i = i + cell_size) { // Рисуем горизонтальные линии
            Line2D lin = new Line2D.Float(10, i, (usefull_game.world_size) * cell_size , i);
            g2d.draw(lin);
        }
        g2d.setColor(Color.BLACK);
        g2d.drawRect(10,70,(usefull_game.world_size ) * cell_size,(usefull_game.world_size) * cell_size); // рисуем прямоугольник в соответствии с расположением линий


        for (Point fillCell : fillCells) { // проходимся циклом по списку из клеток
            int cellX = 10 + (fillCell.x * cell_size); // координата x для нашей выбранной из списка клетки
            int cellY = 70 + (fillCell.y * cell_size); // координата y
            g2d.setColor(Color.BLACK);    // выбираем черный цвет для дальнейшего рисунка
            g2d.fillRect(cellX, cellY, cell_size, cell_size); // закрашиваем клетку
        }
        super.paint(g);
        Graphics2D g3 = (Graphics2D) g;
        g3.drawImage(offscreen, 10, 70, this);
    }

    public void fillCell(int x, int y) {
        fillCells.add(new Point(x, y)); // добавляем список клеток на график
    }

    public void rePaint(Game game) {
        // сначала очистим список
        fillCells.clear();

        for (int i = 0; i < game.world_size; i++) {
            for (int j = 0; j < game.world_size; j++) {
                if (game.world[i][j].is_live) {
                    fillCells.add(new Point(i, j));
                }
            }
        }

        repaint();
    }

    /*public static void main(String[] args) {
        SimpleGUI s = new SimpleGUI();
        //s.fillCell(7, 7); // размещаем клетку в точке (7,7)
        //s.fillCell(49, 49); // размещаем клетку в точке (30,30)
        s.setVisible(true);
    }*/
}
