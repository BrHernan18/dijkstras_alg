package shared;

import context.Constants;

import java.util.Objects;

public class Node {
    private final char id;
    private int x;
    private int y;
    private boolean isSrc;

    public Node(char id) {
        this.id = id;
        this.isSrc = false;
        x = Constants.CANVAS_WIDTH / 2;
        y = Constants.CANVAS_HEIGHT / 2;
    }

    public Node(char id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isSrc = false;
    }

    public char getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isSrc() {
        return isSrc;
    }

    public void setSrc(boolean src) {
        isSrc = src;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        if (!(obj instanceof Node other)) return false;

        return other.getId() == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", isSrc=" + isSrc +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
