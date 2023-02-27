package dijkstra;

import shared.Node;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.*;

public class ResultsTable implements TableModel {
    private final List<TableModelListener> listeners;
    private List<Node> nodes;
    private List<Integer> totalWeights;

    public ResultsTable() {
        this.listeners = new LinkedList<>();
        nodes = new ArrayList<>();
        totalWeights = new ArrayList<>();
    }

    public void updateValues(Map<Node, Integer> values) {
        List<Map.Entry<Node, Integer>> entryList = new ArrayList<>(values.entrySet());
        entryList.sort(Map.Entry.comparingByKey(Comparator.comparingInt(Node::getId)));

        this.nodes = new ArrayList<>();
        this.totalWeights = new ArrayList<>();

        for (Map.Entry<Node, Integer> entry : entryList) {
            this.nodes.add(entry.getKey());
            this.totalWeights.add(entry.getValue());
        }

        notifyListeners(new TableModelEvent(this));
    }

    @Override
    public int getRowCount() {
        return nodes.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Vertex";
            case 1 -> "Shortest distance from source";
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> String.class;
            case 1 -> Integer.class;
            default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return nodes.get(rowIndex).getId();
        } else if (columnIndex == 1) {
            return totalWeights.get(rowIndex);
        }

        throw new IllegalStateException("Unexpected value: " + columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    private void notifyListeners(TableModelEvent event) {
        for (TableModelListener listener : listeners) {
            listener.tableChanged(event);
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }
}
