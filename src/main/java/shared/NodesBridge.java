package shared;

import java.util.Objects;

public record NodesBridge(PairOfNodes pairOfNodes, int weight) {
    public boolean containsNode(Node node) {
        return pairOfNodes.firstNode().equals(node) || pairOfNodes.secondNode().equals(node);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (!(obj instanceof NodesBridge other)) return false;

        return pairOfNodes.equals(other.pairOfNodes);
    }

    @Override
    public int hashCode() {
        return pairOfNodes.hashCode();
    }
}
