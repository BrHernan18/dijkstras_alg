package ui;

import context.ApplicationContext;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private final ApplicationContext context;

    public MainWindow() {
        context = new ApplicationContext();
        JFrame jFrame = new JFrame("Dijkstras Algorithm");
        jFrame.getContentPane().setLayout(new BorderLayout());
        jFrame.setJMenuBar(new MenuFrame(context));
        jFrame.getContentPane().add(new CanvasPanel(context), BorderLayout.CENTER);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private void initDijkstraAlgorithm() {
        context.setCalculating(true);
        System.out.println("Dijkstra's Algorithm init");
    }

}
