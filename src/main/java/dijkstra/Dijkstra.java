package dijkstra;

import context.ApplicationContext;
import context.PropertyChangeEventsNames;
import shared.Node;
import shared.NodesBridge;
import shared.PairOfNodes;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

public class Dijkstra implements PropertyChangeListener {
    private final ApplicationContext context;
    private final ResultsTable resultsTable;

    public Dijkstra(ApplicationContext context, ResultsTable resultsTable) {
        this.context = context;
        context.addPropertyChangeListener(this);
        this.resultsTable = resultsTable;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!(evt.getPropertyName().equals(PropertyChangeEventsNames.IS_CALCULATING.getValue()) && (Boolean) evt.getNewValue())) {
            return;
        }

        System.out.println("Dijkstra's algorithm calculation begins");

        if (!srcNodeRegistered()) {
            JOptionPane.showMessageDialog(
                    null,
                    "No root node specified. \nIt is required in order to proceed with Dijkstra's Algorithm.");
            return;
        }

        Map<Node, Integer> shortestDistancesFromSrc = calculate();

        resultsTable.updateValues(shortestDistancesFromSrc);
    }

    private boolean srcNodeRegistered() {
        try {
            context.getNodesList().stream().filter(Node::isSrc).findFirst().get();
        } catch (NoSuchElementException exception) {
            return false;
        }

        return true;
    }

    private Map<Node, Integer> calculate() {
        var unvisitedNodes = new ArrayList<>(context.getNodesMap().values()
                .stream()
                .filter(node -> !node.isSrc())
                .map(WeightedNode::new)
                .toList());

        Node sourceNode = context.getNodesMap().values()
                .stream()
                .filter(Node::isSrc)
                .findFirst()
                .get();

        var allBridges = context.getNodesBridgesMap().values().stream().toList();

        Map<Node, Integer> shortestDistancesToSrc = new HashMap<>();

        unvisitedNodes.add(new WeightedNode(sourceNode, 0));

        Collections.sort(unvisitedNodes);

        while (!unvisitedNodes.isEmpty()) {
            WeightedNode currentNode = unvisitedNodes.get(0);
            List<WeightedNode> neighbours = getUnvisitedNeighboursOfNode(
                    allBridges, unvisitedNodes.stream().toList(), currentNode.getNode()
            );

            System.out.println("Evaluating " + currentNode);

            for (WeightedNode neighbour : neighbours) {
                WeightedNode unvisitedNode = unvisitedNodes.get(unvisitedNodes.indexOf(neighbour));
                int newWeight = neighbour.getWeight() + // Weight of the link between the current node and this particular neighbour
                        currentNode.getWeight(); // The weight from src until the current node
                if (unvisitedNode.getWeight() > newWeight)
                    unvisitedNode.setWeight(newWeight);
            }

            unvisitedNodes.remove(currentNode);
            Collections.sort(unvisitedNodes);
            shortestDistancesToSrc.put(currentNode.getNode(), currentNode.getWeight());
        }

        return shortestDistancesToSrc;
    }

    private List<WeightedNode> getUnvisitedNeighboursOfNode(
            List<NodesBridge> bridges, List<WeightedNode> unvisitedNodes, Node node
    ) {
        var bridgesOfNode = bridges.stream().filter(nodesBridge -> nodesBridge.containsNode(node)).toList();

        return bridgesOfNode.stream()
                .map(bridge -> {
                    PairOfNodes pairOfNodes = bridge.pairOfNodes();
                    Node neighbour = pairOfNodes.firstNode().equals(node) ? pairOfNodes.secondNode() : pairOfNodes.firstNode();
                    return new WeightedNode(neighbour, bridge.weight());
                })
                .filter(unvisitedNodes::contains)
                .toList();
    }
}
