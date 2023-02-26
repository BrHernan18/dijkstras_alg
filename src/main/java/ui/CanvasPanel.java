package ui;

import context.ApplicationContext;
import context.Constants;
import context.PropertyChangeEventsNames;
import shared.Node;
import shared.NodesBridge;
import shared.PairOfNodes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class CanvasPanel extends JPanel implements PropertyChangeListener {
    private final ApplicationContext context;

    private java.util.List<Node> nodes = new ArrayList<>();
    private java.util.List<NodesBridge> bridges = new ArrayList<>();

    private Node firstNodeSelected = null;

    private Point currentMousePoint;

    CanvasPanel(ApplicationContext context) {
        setPreferredSize(new Dimension(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT));
        setLayout(null);
        this.context = context;
        this.context.addPropertyChangeListener(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (context.getUiState()) {
                    case ADD_NODE -> {
                        Point point = e.getPoint();
                        context.addNode(point.x, point.y);
                    }

                    case ADD_BRIDGE -> {
                        if (firstNodeSelected == null) {
                            firstNodeSelected = getNodeSelected(e);
                            return;
                        }
                        Node secondNodeSelected = getNodeSelected(e);

                        if (secondNodeSelected == null) return;

                        if (secondNodeSelected.equals(firstNodeSelected)) return;

                        int weight;

                        while (true) {
                            try {
                                weight = Integer.parseInt(
                                        JOptionPane.showInputDialog("Enter bridge weight", "0")
                                );
                                break;
                            } catch (NumberFormatException ignored) {
                            }
                        }

                        context.addNodeBridge(
                                new NodesBridge(new PairOfNodes(firstNodeSelected, secondNodeSelected), weight));

                        firstNodeSelected = null;
                    }

                    case EDIT_NODE -> {
                        if (firstNodeSelected == null) {
                            firstNodeSelected = getNodeSelected(e);
                            return;
                        }

                        Node node = getNodeSelected(e);

                        if (node == null) return;

                        if (firstNodeSelected.equals(node)) {
                            setNoSrcAll();
                            firstNodeSelected.setSrc(true);
                            firstNodeSelected = null;
                            validate();
                            repaint();
                            return;
                        }

                        firstNodeSelected = node;
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (context.getUiState() == UIState.DRAG_NODE) {
                    Node node = getNodeSelected(e);
                    if (node == null) return;

                    Point point = e.getPoint();

                    node.setX(point.x);
                    node.setY(point.y);

                    validate();
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (context.getUiState() == UIState.ADD_BRIDGE && firstNodeSelected != null) {
                    currentMousePoint = e.getPoint();
                    validate();
                    repaint();
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);


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

        if (context.getUiState() == UIState.ADD_BRIDGE && firstNodeSelected != null) {
            graphics2D.drawLine(
                    firstNodeSelected.getX(), firstNodeSelected.getY(),
                    currentMousePoint.x, currentMousePoint.y
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
                    (int) (node.getX() - Constants.NODES_SIZE / 2.7),
                    (int) (node.getY() - Constants.NODES_SIZE / 2.7),
                    Constants.SRC_NODE_INTERNAL_SIZE,
                    Constants.SRC_NODE_INTERNAL_SIZE
            );
        }

        graphics2D.drawString(node.getId() + "",
                node.getX() - Constants.NODES_SIZE / 2,
                node.getY() - Constants.NODES_SIZE / 2);
    }

    private void paintBridge(Graphics2D graphics2D, Node from, Node to, int weight) {
        graphics2D.setColor(Color.BLACK);

        graphics2D.drawLine(
                from.getX(), from.getY(), to.getX(), to.getY()
        );

        graphics2D.drawString(weight + "",
                (from.getX() + to.getX()) / 2,
                (from.getY() + to.getY()) / 2);
    }

    private Node getNodeSelected(MouseEvent e) {
        e.getPoint();

        for (Node node : nodes) {
            if (node.getX() - Constants.NODES_SIZE / 2 <= e.getX() &&
                    node.getX() + Constants.NODES_SIZE / 2 >= e.getX() &&
                    node.getY() - Constants.NODES_SIZE / 2 <= e.getY() &&
                    node.getY() + Constants.NODES_SIZE / 2 >= e.getY()
            )
                return node;
        }

        return null;
    }

    private void setNoSrcAll() {
        for (Node node : nodes) {
            node.setSrc(false);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(PropertyChangeEventsNames.NODES.getValue())) {
            this.nodes = context.getNodesList();
            validate();
            repaint();
        }

        if (evt.getPropertyName().equals(PropertyChangeEventsNames.NODES_BRIDGES.getValue())) {
            this.bridges = context.getNodesBridgesList();
            validate();
            repaint();
        }

        if (evt.getPropertyName().equals(PropertyChangeEventsNames.STATUS.getValue())) {
            this.firstNodeSelected = null;
        }
    }
}
