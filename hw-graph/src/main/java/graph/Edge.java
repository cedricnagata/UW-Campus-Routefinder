package graph;

/**
 * <b>Edge</B> represents an edge that contains a label and a node.
 * The Edge class is immutable
 *
 * Specification fields:
 * @spec.specfield: label: String //the edge label
 * @spec.specfield: childNode: Node //the end Node connected to this edge
 *
 * Abstract Invariant:
 *  Each edge is notnull and its end Node is also not null
 */
public class Edge<E extends Comparable<E>, T extends Comparable<T>> implements Comparable<Edge<E, T>> {

    private final E label;
    private final T childNode;
    private final T parentNode;

    // Abstract function: AF(this) = an edge that contains both an end node and a label
    // Representation invariant: childNode != null && label != null

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() throws RuntimeException {
        if (label == null || childNode == null) {
            throw new IllegalArgumentException("label and child Node can't be null");
        }
    }

    /**
     * Creates an Edge with a childNode and a label
     * @param label label stored in the edge
     * @param childNode node pointed to by the EDge
     * @spec.requires label != null, childNode != null
     * @spec.effects makes a new Edge with a childNode and a label
     */
    public Edge(E label, T parentNode, T childNode) {
        if (label == null || childNode == null) {
            throw new IllegalArgumentException("label and child Node can't be null");
        }

        this.label = label;
        this.childNode = childNode;
        this.parentNode = parentNode;
        checkRep();
    }

    /**
     * Returns the label of this Edge
     * @return the label of this Edge
     */
    public E getLabel() {
        return label;
    }

    /**
     * Returns the childNode of this Edge
     * @return childNode of this Edge
     */
    public T getChild() {
        return childNode;
    }

    /**
     * Returns the parentNode of this Edge
     * @return parentNode of this Edge
     */
    public T getParent() {
        return parentNode;
    }

    /**
     * Returns a string representation of the Edge
     * @return a String representation of the Edge
     */
    @Override
    public String toString() {
        return label.toString() + "(" + childNode.toString() + ")";
    }

    /**
     * Returns a hash code of the Edge
     * @return a hash code of the Edge
     */
    @Override
    public int hashCode() {
        return childNode.hashCode() + label.hashCode();
    }

    /**
     * Returns true if given Edge is the same as this Edge
     * @param obj object to be compared for equality
     * @return true if obj has same data as this node
     */
    @Override
    public boolean equals(Object obj) {
        checkRep();
        if (!(obj instanceof Edge)) {
            return false;
        }

        Edge e = (Edge) obj;
        checkRep();

        return childNode.equals(e.childNode) && label.equals(e.label);
    }

    @Override
    public int compareTo(Edge<E, T> o) {
        if(this.parentNode.compareTo(o.parentNode) != 0) {
            return this.parentNode.compareTo(o.parentNode);
        } else if(this.childNode.compareTo(o.childNode) != 0) {
            return this.childNode.compareTo(o.childNode);
        } else {
            return this.label.compareTo(o.label);
        }
    }
}
