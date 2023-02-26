package context;

public enum PropertyChangeEventsNames {
    IS_CALCULATING("isCalculating"),
    NODES("nodes"),
    NODES_BRIDGES("nodesBridges"),
    STATUS("status");

    private final String value;

    PropertyChangeEventsNames(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
