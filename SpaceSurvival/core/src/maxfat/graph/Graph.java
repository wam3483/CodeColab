package maxfat.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Graph<T> implements Iterable<Node<T>> {
	private Node<T> node;

	public Graph(Node<T> node) {
		this.node = node;
	}

	public Node<T> getNode() {
		return this.node;
	}

	public IResetableIterator<Node<T>> iterator() {
		return new GraphIterable<T>(this.node);
	}

	public static interface IResetableIterator<T> extends Iterator<T> {
		void reset();
	}

	private static class GraphIterable<T> implements
			IResetableIterator<Node<T>> {
		private final HashSet<Node<T>> hashset = new HashSet<Node<T>>();
		private final Queue<Node<T>> queue = new LinkedList<Node<T>>();
		Node<T> firstNode;

		public GraphIterable(Node<T> node) {
			this.firstNode = node;
			this.queue.add(node);
		}

		public void reset() {
			this.hashset.clear();
			this.queue.clear();
			this.queue.add(this.firstNode);
		}

		@Override
		public boolean hasNext() {
			return !this.queue.isEmpty();
		}

		@Override
		public Node<T> next() {
			Node<T> nextNode = this.queue.remove();
			hashset.add(nextNode);
			for (Node<T> n : nextNode.getEdges()) {
				if (!this.hashset.contains(n)) {
					this.queue.add(n);
				}
			}
			return nextNode;
		}
	}
}