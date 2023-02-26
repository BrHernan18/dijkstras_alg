package shared;

public record PairOfNodes(Node firstNode, Node secondNode) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PairOfNodes that = (PairOfNodes) o;

        return firstNode.equals(that.firstNode) && secondNode.equals(that.secondNode) ||
                firstNode.equals(that.secondNode) && secondNode.equals(that.firstNode);
    }

    @Override
    public int hashCode() {
        return firstNode.hashCode() + secondNode.hashCode();
    }
}
