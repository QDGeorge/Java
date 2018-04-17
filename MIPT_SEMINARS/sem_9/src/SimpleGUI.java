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

    public SimpleGUI() {
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        color = Color.RED;
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        JButton button1 = new JButton("Start!");
        JButton button2 = new JButton("Stop!");
        JButton button3 = new JButton("Reset!");
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        /*
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                color = Color.BLUE;
                SimpleGUI.this.repaint();
            }
        });*/
    }

    public static synchronized void main(String []args) throws Exception {
        SimpleGUI s = new SimpleGUI();
        s.setVisible(true);
    }
}
