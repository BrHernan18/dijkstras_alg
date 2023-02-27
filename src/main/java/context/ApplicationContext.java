package context;

import shared.Node;
import shared.NodesBridge;
import shared.PairOfNodes;
import ui.UIState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContext {
    private final PropertyChangeSupport support;

    private UIState uiState = UIState.ADD_NODE;
    private boolean isCalculating;
    private final Map<Character, Node> nodes;
    private final Map<PairOfNodes, NodesBridge> nodesBridges;
    private char nextNodeId = 'A';

    public ApplicationContext() {
        support = new PropertyChangeSupport(this);
        isCalculating = false;
        nodes = new HashMap<>();
        nodesBridges = new HashMap<>();
    }

    public UIState getUiState() {
        return uiState;
    }

    public void setUiState(UIState uiState) {
        this.uiState = uiState;
    }

    public boolean isCalculating() {
        return isCalculating;
    }

    public void setCalculating(boolean calculating) {
        support.firePropertyChange(PropertyChangeEventsNames.IS_CALCULATING.getValue(), false, calculating);
        isCalculating = calculating;
    }

    public Map<Character, Node> getNodesMap() {
        return nodes;
    }

    public List<Node> getNodesList() {
        return nodes.values().stream().toList();
    }

    public void addNode(Node node) {
        this.nodes.put(node.getId(), node);
        support.firePropertyChange(new PropertyChangeEvent(this, PropertyChangeEventsNames.NODES.getValue(), 1, this.nodes));
    }

    public void addNode(int x, int y) {
        this.addNode(new Node(nextNodeId++, x, y));
    }

    public void removeNode(char nodeId) {
        this.nodes.remove(nodeId);
        this.removeBridgesOfNode(nodeId);

        support.firePropertyChange(new PropertyChangeEvent(
                this, PropertyChangeEventsNames.NODES_BRIDGES.getValue(),
                1, this.nodesBridges
        ));
        support.firePropertyChange(new PropertyChangeEvent(this, PropertyChangeEventsNames.NODES.getValue(), 1, this.nodes));
    }

    private void removeBridgesOfNode(int nodeId) {
        List<PairOfNodes> bridgesToDelete = new ArrayList<>();
        for (NodesBridge bridge : nodesBridges.values()) {
            if (bridge.pairOfNodes().firstNode().getId() == nodeId || bridge.pairOfNodes().secondNode().getId() == nodeId)
                bridgesToDelete.add(bridge.pairOfNodes());
        }

        for (PairOfNodes pairOfNodes : bridgesToDelete) {
            this.nodesBridges.remove(pairOfNodes);
        }
    }

    public void clearNodes() {
        this.nodes.clear();
        this.nodesBridges.clear();

        support.firePropertyChange(new PropertyChangeEvent(
                this, PropertyChangeEventsNames.NODES_BRIDGES.getValue(),
                1, this.nodesBridges
        ));
        support.firePropertyChange(new PropertyChangeEvent(
                this, PropertyChangeEventsNames.NODES.getValue(),
                1, this.nodes));
    }

    public Map<PairOfNodes, NodesBridge> getNodesBridgesMap() {
        return nodesBridges;
    }

    public List<NodesBridge> getNodesBridgesList() {
        return nodesBridges.values().stream().toList();
    }

    public void addNodeBridge(NodesBridge nodesBridge) {
        this.nodesBridges.put(nodesBridge.pairOfNodes(), nodesBridge);
        support.firePropertyChange(new PropertyChangeEvent(
                this, PropertyChangeEventsNames.NODES_BRIDGES.getValue(),
                1, this.nodesBridges
        ));
    }

    public void removeNodeBridge(PairOfNodes nodes) {
        this.nodesBridges.remove(nodes);
        support.firePropertyChange(new PropertyChangeEvent(
                this, PropertyChangeEventsNames.NODES_BRIDGES.getValue(),
                1, this.nodesBridges
        ));
    }

    public void removeNodeBridge(Node n1, Node n2) {
        this.removeNodeBridge(new PairOfNodes(n1, n2));
    }

    public void clearBridges() {
        this.nodesBridges.clear();

        support.firePropertyChange(new PropertyChangeEvent(
                this, PropertyChangeEventsNames.NODES_BRIDGES.getValue(),
                1, this.nodesBridges
        ));
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
