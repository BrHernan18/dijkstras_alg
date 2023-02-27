package dijkstra;

import shared.Node;

import java.util.Comparator;

public class WeightedNode implements Comparable<WeightedNode> {
    private final Node node;
    private int weight;

    public WeightedNode(Node node) {
        this(node, Integer.MAX_VALUE);
    }

    public WeightedNode(Node node, int weight) {
        this.node = node;
        this.weight = weight;
    }

    public Node getNode() {
        return node;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(WeightedNode o) {
        return getWeight() - o.getWeight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeightedNode that = (WeightedNode) o;

        return node.equals(that.node);
    }

    @Override
    public int hashCode() {
        return node.hashCode();
    }

    @Override
    public String toString() {
        return "WeightedNode{" +
                "node=" + node.getId() +
                ", weight=" + weight +
                '}';
    }
}
