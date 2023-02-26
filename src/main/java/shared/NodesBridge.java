package shared;

import java.util.Objects;

public record NodesBridge(PairOfNodes pairOfNodes, int weight) {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (!(obj instanceof NodesBridge other)) return false;

        return pairOfNodes.equals(other.pairOfNodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pairOfNodes, weight);
    }
}
