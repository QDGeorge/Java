import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleGUI extends JFrame {
    private Color color;
    private int x_1 = 100, y_1 = 100, x_2 = 250, y_2 = 260;

    public SimpleGUI() {
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        color = Color.RED;
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        JButton button = new JButton("Run!");

        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = Color.BLUE;
                SimpleGUI.this.repaint();
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        Line2D lin = new Line2D.Float(100, 100, 250, 260);
        g2.draw(lin);
    }
    /*
    public static synchronized void main(String []args) throws Exception {
        SimpleGUI s = new SimpleGUI();
        s.setVisible(true);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = new Runnable() {
            @Override
            private int x_1 = 100, y_1 = 100, x_2 = 250, y_2 = 260;
            public void run() {
                x_1 = x_1*sin(t);
            }
        };

        int initialDelay = 0;
        int period = 1;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }*/
}
