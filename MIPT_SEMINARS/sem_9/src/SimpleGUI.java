import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimpleGUI extends JFrame { // JFrame - класс, представляющий собой окно с рамкой и с кнопками: "свернуть", "во весь экран", "закрыть"
    private Color color; // Объявили переменную типа Color, она отвечает за цвета
    private List<Point> fillCells; // Объявили список, содержащий клетки типа Point
    int cell_size = 5;
    public SimpleGUI() { //Конструктор без параметров, который создает список с клетками и рисует кнопки
        Game usefull_game = new Game();
        fillCells = new ArrayList(usefull_game.world_size); // Создаем список с клетками, начальный размер списка = 10,
        setSize((usefull_game.world_size + 2) * cell_size, 70 + (usefull_game.world_size + 8) * cell_size); // устанавливаем размер окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // позволяет указать действие, которое необходимо выполнить, когда закрываем окно, нажатием на крестик (в данном случае мы закрываем окно)

        color = Color.BLACK;    // "Включаем" чёрный цвет для наших "рисунков"
        JPanel panel = new JPanel(); // Создаём панель (прямоугольное пространство, на котором можно размещать элементы)
        getContentPane().add(panel); // Добавляем панель на экран

        JButton button = new JButton("Start"); // создаём кнопки с надписями и размещаем их на панели
        panel.add(button);
        JButton button1 = new JButton("Stop");
        panel.add(button1);
        JButton button2 = new JButton("Reset");
        panel.add(button2);
    }

    public void paint(Graphics g) { // Функция, которая рисует клетки
        Game usefull_game = new Game();
        super.paint(g); //
        Graphics2D g2 = (Graphics2D) g; // создали график на плоскости
        g2.setColor(Color.BLACK); // выбрали цвет для нашего графика

        /*
        for (int i = cell_size; i <= usefull_game.world_size*cell_size; i = i + cell_size) { // Рисуем вертикальные линии x: от 10 позиции слева до 500; y: от 70 позиции сверху до 510 (их размер как-то там автоматически определен)
            Line2D lin = new Line2D.Float(i, 70, i, (usefull_game.world_size + 7) * cell_size);
            g2.draw(lin);
        }
        for (int i = 70; i <= (usefull_game.world_size + 6) * cell_size; i = i + cell_size) { // Рисуем горизонтальные линии
            Line2D lin = new Line2D.Float(10, i, (usefull_game.world_size + 1) * cell_size, i);
            g2.draw(lin);
        }
        g.setColor(Color.BLACK);
        g.drawRect(10,70,(usefull_game.world_size) * cell_size,(usefull_game.world_size) * cell_size); // рисуем прямоугольник в соответствии с расположением линий*/

        for (Point fillCell : fillCells) { // проходимся циклом по списку из клеток
            int cellX = 10 + (fillCell.x * cell_size); // координата x для нашей выбранной из списка клетки
            int cellY = 70 + (fillCell.y * cell_size); // координата y
            g.setColor(Color.BLACK);    // выбираем зелёный цвет для дальнейшего рисунка
            g.fillRect(cellX, cellY, cell_size, cell_size); // закрашиваем клетку
        }
    }

    public void fillCell(int x, int y) {
        fillCells.add(new Point(x, y)); // добавляем список клеток на график
        repaint();
    }

    public void rePaint(Game game) {
        // сначала очистим список
        if (fillCells.size() != 0) {
            for (int i = 0; i < fillCells.size(); i++) {
                fillCells.remove(i);
            }
        }
        for (int i = 0; i < game.world_size; i++) {
            for (int j = 0; j < game.world_size; j++) {
                if (game.world[i][j].is_live) {
                    this.fillCell(i, j);
                    repaint();
                }
            }
        }
    }

    public static void main(String[] args) {
        SimpleGUI s = new SimpleGUI();
        s.fillCell(7, 7); // размещаем клетку в точке (7,7)
        s.fillCell(49, 49); // размещаем клетку в точке (30,30)
        s.setVisible(true);
    }
}
