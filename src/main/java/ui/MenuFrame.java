package ui;

import context.ApplicationContext;
import shared.Node;

import javax.swing.*;

public class MenuFrame extends JMenuBar {
    private final ApplicationContext context;
    private int nextNodeId = 1;

    public MenuFrame(ApplicationContext context) {
        this.context = context;
        this.add(getStateMenu());
    }

    private JMenu getStateMenu() {
        JMenu jMenu = new JMenu("State");

        JMenuItem addNodeItem = new JMenuItem("Add Node");
        addNodeItem.addActionListener(e -> addNode());
        JMenuItem addBridgeItem = new JMenuItem("Add Bridge");
        addBridgeItem.addActionListener(e -> context.setUiState(UIState.ADD_BRIDGE));
        JMenuItem dragNodeItem = new JMenuItem("Drag Nodes");
        dragNodeItem.addActionListener(e -> context.setUiState(UIState.DRAG_NODE));
        JMenuItem editNodeItem = new JMenuItem("Edit Node");
        editNodeItem.addActionListener(e -> context.setUiState(UIState.EDIT_NODE));

        jMenu.add(addNodeItem);
        jMenu.add(addBridgeItem);
        jMenu.add(dragNodeItem);
        jMenu.add(editNodeItem);

        return jMenu;
    }

    private void addNode() {
        context.addNode(new Node(nextNodeId++));
    }
}
