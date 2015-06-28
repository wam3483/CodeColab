package maxfat.graph;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
	private final List<Node<T>> edges;
	private T data;

	public Node(T data) {
		this.edges = new ArrayList<Node<T>>();
		this.data = data;
	}

	public List<Node<T>> getEdges() {
		return this.edges;
	}

	public T getData() {
		return this.data;
	}

	public void linkTo(Node<T> other) {
		if (other.edges.contains(this))
			throw new IllegalArgumentException("attempt to link nodes twice!");
		if (this.edges.contains(other)) {
			throw new IllegalArgumentException("attempt to link nodes twice!");
		}
		if (this == other)
			throw new IllegalArgumentException("cannot link to self");
		this.edges.add(other);
		other.edges.add(this);
	}
}