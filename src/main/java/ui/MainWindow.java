package ui;

import context.ApplicationContext;
import dijkstra.Dijkstra;
import dijkstra.ResultsTable;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private Dijkstra dijkstra;


    public MainWindow() {
        final ApplicationContext context = new ApplicationContext();
        JFrame jFrame = new JFrame("Dijkstras Algorithm");
        jFrame.getContentPane().setLayout(new BorderLayout());
        jFrame.setJMenuBar(new MenuFrame(context));
        jFrame.getContentPane().add(new CanvasPanel(context), BorderLayout.CENTER);
        jFrame.getContentPane().add(getTablePanel(context), BorderLayout.EAST);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private JPanel getTablePanel(ApplicationContext context) {
        JPanel panel = new JPanel();

        ResultsTable resultsTable = new ResultsTable();

        dijkstra = new Dijkstra(context, resultsTable);

        JTable jTable = new JTable(resultsTable);

        JScrollPane scrollPane = new JScrollPane(jTable);

        panel.add(scrollPane);

        return panel;
    }

}
