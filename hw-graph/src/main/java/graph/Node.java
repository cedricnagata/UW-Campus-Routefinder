package graph;

/**
 * <b>Node</B> represents a node with a stored label. The Node class
 * is immutable.
 * Specification fields:
 * @spec.specfield data : String //String data to be stored in the node
 *
 * Abstract Invariant:
 *  The data stored is not null
 */
public class Node<E extends Comparable<E>> implements Comparable<Node<E>>{

    private E data;

    // Abstraction function: AF(this) = a node with data d, where d.data = this.data
    // Representation invariant: data != null

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() throws RuntimeException {
        if (data == null) {
            throw new RuntimeException("data can't be null");
        }
    }

    /**
     * Creates a new Node object with stored data
     * @param data Data stored in the Node
     * @spec.requires data cannot be null
     */
    public Node(E data) {
        if (data == null) {
            throw new IllegalArgumentException("data can't be null");
        }

        this.data = data;
        checkRep();
    }

    /**
     * Returns data stored in the Node
     * @return data Data stored in the Node
     */
    public E getData() {
        return data;
    }

    /**
     * Returns a string representation of the Node
     * @return a String representation of the Node
     */
    public String toString() {
        return data.toString();
    }

    /**
     * Returns a hashcode for the node
     * @return an int that all objects equal to this Node will also return
     */
    @Override
    public int hashCode() {
        return data.hashCode();
    }

    /**
     * Returns true if given Node is the same as this Node
     * @param obj object to be compared for equality
     * @return true if obj has same data as this node
     */
    @Override
    public boolean equals(Object obj) {
        checkRep();
        if (!(obj instanceof Node)) {
            return false;
        }

        Node n = (Node) obj;
        checkRep();

        return data.equals(n.data);
    }

    public int compareTo(Node<E> other) {
        return this.data.compareTo(other.data);
    }
}

