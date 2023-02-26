package ui;

import context.ApplicationContext;
import context.Constants;
import context.PropertyChangeEventsNames;
import shared.Node;
import shared.NodesBridge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class CanvasPanel extends JPanel implements PropertyChangeListener {
    private final ApplicationContext context;

    private java.util.List<Node> nodes = new ArrayList<>();
    private java.util.List<NodesBridge> bridges = new ArrayList<>();

    CanvasPanel(ApplicationContext context) {
        setPreferredSize(new Dimension(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT));
        setLayout(null);
        this.context = context;
        this.context.addPropertyChangeListener(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (context.getUiState() == UIState.ADD_NODE) {
                    Point point = e.getPoint();
                    context.addNode(point.x, point.y);
                    System.out.println("Node added");
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics2D = (Graphics2D) g;

        for (Node node : nodes) {
            paintNode(graphics2D, node);
        }

        for (NodesBridge bridge : bridges) {
            paintBridge(
                    graphics2D,
                    bridge.pairOfNodes().firstNode(),
                    bridge.pairOfNodes().secondNode(),
                    bridge.weight()
            );
        }
    }

    private void paintNode(Graphics2D graphics2D, Node node) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawOval(
                node.getX() - Constants.NODES_SIZE / 2,
                node.getY() - Constants.NODES_SIZE / 2,
                Constants.NODES_SIZE,
                Constants.NODES_SIZE
        );

        if (node.isSrc()) {
            graphics2D.drawOval(
                    node.getX() - Constants.NODES_SIZE / 2,
                    node.getY() - Constants.NODES_SIZE / 2,
                    Constants.SRC_NODE_INTERNAL_SIZE,
                    Constants.SRC_NODE_INTERNAL_SIZE
            );
        }

        System.out.println(node);
    }

    private void paintBridge(Graphics2D graphics2D, Node from, Node to, int weight) {
        graphics2D.setColor(Color.BLACK);

        graphics2D.drawLine(
                from.getX() - Constants.NODES_SIZE / 2,
                from.getY() - Constants.NODES_SIZE / 2,
                to.getX() - Constants.NODES_SIZE / 2,
                to.getY() - Constants.NODES_SIZE / 2
        );

        graphics2D.drawString(weight + "",
                from.getX() - to.getX() - Constants.NODES_SIZE,
                from.getY() - to.getY() - Constants.NODES_SIZE);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Property change caught");

        if (evt.getPropertyName().equals(PropertyChangeEventsNames.NODES.getValue())) {
            System.out.println("Change on node list detected.");
            this.nodes = context.getNodesList();
            System.out.println("To repaint");
            validate();
            repaint();
        }

        if (evt.getPropertyName().equals(PropertyChangeEventsNames.NODES_BRIDGES.getValue())) {
            this.bridges = context.getNodesBridgesList();
            validate();
            repaint();
        }
    }
}
