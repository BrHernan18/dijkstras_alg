package ui;

import context.ApplicationContext;

import javax.swing.*;

public class MenuFrame extends JMenuBar {
    private final ApplicationContext context;

    public MenuFrame(ApplicationContext context) {
        this.context = context;
        this.add(getStateMenu());
        this.add(getActionsMenu());
    }

    private JMenu getStateMenu() {
        JMenu jMenu = new JMenu("State");

        JMenuItem addNodeItem = new JMenuItem("Add Node");
        addNodeItem.addActionListener(e -> context.setUiState(UIState.ADD_NODE));
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

    private JMenu getActionsMenu() {
        JMenu jMenu = new JMenu("Actions");

        JMenuItem initDijkstra = new JMenuItem("Init Dijkstra");
        initDijkstra.addActionListener(e -> context.setCalculating(true));
        JMenuItem clearNodes = new JMenuItem("Clear Nodes");
        clearNodes.addActionListener(e -> context.clearNodes());
        JMenuItem clearBridges = new JMenuItem("Clear Bridges");
        clearBridges.addActionListener(e -> context.clearBridges());

        jMenu.add(initDijkstra);
        jMenu.add(clearNodes);
        jMenu.add(clearBridges);

        return jMenu;
    }
}
