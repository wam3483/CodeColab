package maxfat.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import maxfat.spacesurvival.game.IFactory;
import maxfat.util.random.IRandom;
import maxfat.util.random.RandomUtil;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GraphGenerator<T extends I2DData> implements IGraphGenerator<T> {
	private final GraphGenerationParams template;
	private final IRandom random;
	private final List<Node<T>> allNodes;
	private final IFactory<T> factory;

	public GraphGenerator(GraphGenerationParams template, IRandom random,
			IFactory<T> factory) {
		this.random = random;
		this.template = template;
		this.factory = factory;
		this.allNodes = new ArrayList<Node<T>>();
	}

	private void addExtraEdges(Node<T> node) {
		if (allNodes.size() == 1)
			return;
		int maxEdgesToAdd = template.getEdgeRange().getMax();
		double extraEdgesMaxDistance = template
				.getCreateEdgeToAllNodesDistance();

		for (Node<T> n1 : allNodes) {
			List<Node<T>> nearbyNodes = queryNearbyNodes(n1, n1.getData()
					.getPoint(), extraEdgesMaxDistance);
			if (n1.getEdges().size() < maxEdgesToAdd) {
				for (Node<T> nearby : nearbyNodes) {
					if (n1 != nearby && !n1.getEdges().contains(nearby)
							&& nearby.getEdges().size() < maxEdgesToAdd) {
						if (this.validatePath(n1, nearby)) {
							n1.linkTo(nearby);
						}
					}
				}
			}
		}
	}

	private void linkNodes(Node<T> n1, Node<T> newNode) {
		n1.linkTo(newNode);
		this.allNodes.add(newNode);
	}

	public List<Node<T>> queryNearbyNodes(Node<T> graph, Vector2 position,
			double radius) {
		double r2 = radius * radius;
		List<Node<T>> result = new ArrayList<Node<T>>();
		for (Node<T> node : allNodes) {
			double dx = node.getData().getPoint().x - position.x;
			double dy = node.getData().getPoint().y - position.y;
			double dist2 = dx * dx + dy * dy;
			if (dist2 < r2)
				result.add(node);
		}
		return result;
	}

	public Node<T> generate() {
		this.allNodes.clear();
		int numberNodes = template.getNumberNodes();
		T data = this.createData();
		Node<T> firstNode = new Node<T>(data);
		
		Node<T> currentNode = firstNode;
		Queue<Node<T>> generationPoints = new LinkedList<Node<T>>();
		int count = 1;
		int size = 1;
		generationPoints.add(currentNode);
		allNodes.add(currentNode);
		while (size < numberNodes) {
			if (generationPoints.isEmpty()) {
				generationPoints.add(this.allNodes.get(this.random
						.nextInt(this.allNodes.size())));
			}
			currentNode = generationPoints.remove();
			int maxEdges = template.getEdgeRange().getMin();
			int minEdges = template.getEdgeRange().getMax();
			int nodesToAdd = maxEdges;

			nodesToAdd = RandomUtil.randomBetweenRanges(this.random, minEdges,
					maxEdges);
			int nodesAllowed = maxEdges - currentNode.getEdges().size();
			if (nodesAllowed <= 0)
				nodesToAdd = 0;
			Node<T> nextNode = null;
			for (int i = 0; i < nodesToAdd; i++) {
				nextNode = createNewNode(currentNode, count + "");
				if (nextNode != null) {
					linkNodes(currentNode, nextNode);
					size++;
					count++;
				}
				if (size >= numberNodes)
					break;
			}
			enqueueGenerationPoints(generationPoints, currentNode);
			size = allNodes.size();
		}
		System.out.println("Done, generated " + allNodes.size() + " planets");
		addExtraEdges(firstNode);
		return firstNode;
	}

	private void enqueueGenerationPoints(Queue<Node<T>> queue,
			Node<T> currentNode) {
		int nodesToEnqueue = RandomUtil.randomBetweenRanges(this.random,
				template.getNodeExpansionRange().getMin(), template
						.getNodeExpansionRange().getMax());
		nodesToEnqueue = Math
				.min(currentNode.getEdges().size(), nodesToEnqueue);
		if (nodesToEnqueue > 0) {
			List<Node<T>> objs = new LinkedList<Node<T>>();
			for (Node<T> n : currentNode.getEdges()) {
				if (n.getEdges().size() <= 1) {
					objs.add(n);
				}
			}
			for (int i = 0; i < nodesToEnqueue && objs.size() > 0; i++) {
				int index = random.nextInt(objs.size());
				queue.add((Node<T>) objs.remove(index));
			}
		} else {
			System.out.println("\t***No nodes created***");
			if (currentNode.getEdges().size() > 0) {
				queue.clear();
				for (Node<T> temp : allNodes) {
					if (temp.getEdges().size() == 1) {
						queue.add(temp);
						break;
					}
				}
			}
		}
	}

	private float getRandomDistance() {
		FloatRange range = template.getDistanceRange();
		return RandomUtil.randomBetweenRanges(random, range.getMin(),
				range.getMax());
	}

	private float getRandomSize() {
		FloatRange range = template.getSizeRange();
		return RandomUtil.randomBetweenRanges(random, range.getMin(),
				range.getMax());
	}

	private T createData() {
		T data = factory.create();
		data.setMinDistance(getRandomDistance());
		data.setSize(getRandomSize());
		return data;
	}

	private Node<T> createNewNode(Node<T> currentNode, String name) {
		int attemptsToCreatePlanet = 10;
		Node<T> newNode = null;
		for (int attempts = 0; attempts < attemptsToCreatePlanet; attempts++) {
			Vector2 position = getProposedPosition(currentNode);
			T newData = createData();
			newData.setPoint(position);
			Node<T> proposedNode = new Node<T>(newData);
			if (validateLink(currentNode, proposedNode)) {
				newNode = proposedNode;
				break;
			}
		}
		return newNode;
	}

	/**
	 * Returns whether a link between two nodes is valid
	 * 
	 * @return
	 */
	boolean validatePath(Node<T> n1, Node<T> n2) {
		Stack<Node<T>> stack = new Stack<Node<T>>();
		stack.push(n1);
		HashSet<Node<T>> hashset = new HashSet<Node<T>>();
		while (!stack.isEmpty()) {
			Node<T> node = stack.pop();
			hashset.add(node);
			// new paths cannot intersect existing planets.
			if (n1 != node
					&& n2 != node
					&& Intersector.intersectSegmentCircle(n1.getData()
							.getPoint(), n2.getData().getPoint(), node
							.getData().getPoint(), (node.getData()
							.getMinDistance() - 1)
							* (node.getData().getMinDistance() - 1))) {
				return false;
			}
			for (Node<T> sibling : node.getEdges()) {
				if (!hashset.contains(sibling)) {
					// paths cannot intersect.
					if (n1 != node
							&& n2 != node
							&& n1 != sibling
							&& n2 != sibling
							&& Intersector.intersectSegments(n1.getData()
									.getPoint(), n2.getData().getPoint(), node
									.getData().getPoint(), sibling.getData()
									.getPoint(), null)) {
						return false;
					}
					stack.push(sibling);
				}
			}
		}
		return true;

	}

	boolean doLineSegmentsIntersect(Node<T> original, Node<T> newNode) {
		Stack<Node<T>> stack = new Stack<Node<T>>();
		stack.push(original);
		HashSet<Node<T>> hashset = new HashSet<Node<T>>();
		while (!stack.isEmpty()) {
			Node<T> node = stack.pop();
			hashset.add(node);

			I2DData newData = newNode.getData();
			float newMinDist = newData.getMinDistance() / 2;
			Vector2 newPoint = newData.getPoint();

			I2DData nodeData = node.getData();
			float nodeMinDist = nodeData.getMinDistance() / 2;
			Vector2 nodePoint = nodeData.getPoint();

			// planets can't overlap.
			if (Intersector.overlaps(
					new Circle(newData.getPoint(), newMinDist), new Circle(
							nodeData.getPoint(), nodeMinDist))) {
				return true;
			}
			// new paths cannot intersect existing planets.
			if (original != node
					&& Intersector.intersectSegmentCircle(original.getData()
							.getPoint(), newPoint, nodePoint, nodeMinDist
							* nodeMinDist)) {
				return true;
			}
			for (Node<T> sibling : node.getEdges()) {
				if (!hashset.contains(sibling)) {
					// paths cannot intersect.
					if (original != node
							&& original != sibling
							&& Intersector.intersectSegments(original.getData()
									.getPoint(), newNode.getData().getPoint(),
									node.getData().getPoint(), sibling
											.getData().getPoint(), null)) {
						return true;
					}
					float minDist = newNode.getData().getMinDistance() / 2;
					// new planets cannot overlap existing paths
					if (Intersector.intersectSegmentCircle(node.getData()
							.getPoint(), sibling.getData().getPoint(), newNode
							.getData().getPoint(), minDist * minDist)) {
						return true;
					}
					stack.push(sibling);
				}
			}
		}
		return false;
	}

	boolean validateLink(Node<T> original, Node<T> newNode) {
		return !doLineSegmentsIntersect(original, newNode);
	}

	Vector2 getProposedPosition(Node<T> node) {
		double scalar = 0;

		double min = template.getDistanceRange().getMin();
		double diff = template.getDistanceRange().range();
		double g1 = random.nextDouble();
		scalar = g1 * diff;
		scalar += scalar < 0 ? -min : min;

		float x = MathUtils.cos((float) random.nextDouble() * MathUtils.PI2);
		float y = MathUtils.sin((float) random.nextDouble() * MathUtils.PI2);
		double length = (float) Math.sqrt(x * x + y * y);
		if (length != 0) {
			x /= length;
			y /= length;
		}
		x *= scalar;
		y *= scalar;
		return new Vector2(x + node.getData().getPoint().x, y
				+ node.getData().getPoint().y);
	}

	double clamp(double value, double min, double max) {
		return Math.min(max, Math.max(min, value));
	}
}