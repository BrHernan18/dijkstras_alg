package shared;

public record PairOfNodes(Node firstNode, Node secondNode) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PairOfNodes that = (PairOfNodes) o;

        return firstNode.equals(that.firstNode) && secondNode.equals(that.secondNode);
    }

    @Override
    public int hashCode() {
        int result = firstNode.hashCode();
        result = 31 * result + secondNode.hashCode();
        return result;
    }
}
