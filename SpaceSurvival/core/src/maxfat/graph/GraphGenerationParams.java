package maxfat.graph;

public final class GraphGenerationParams {
	private final int numberNodes;

	/**
	 * each node will have a number of connections in this range.
	 */
	private final IntRange edgeRange;

	/**
	 * The center of each node will require a certain distance between
	 * themselves and all other nodes.
	 */
	private final FloatRange distanceRange;

	/**
	 * After new children are generated for a node, X children will be chosen to
	 * create new nodes from. X is a number in nodeExpansionRange
	 */
	private final IntRange nodeExpansionRange;

	/**
	 * Each node's size will be within this range.
	 */
	private final FloatRange sizeRange;

	/**
	 * Edges are added to all nodes within this distance from one another to a
	 * maximum of maxEdgesWithinNodeDistance.
	 */
	private final float createEdgesToAllNodesDistance;

	public GraphGenerationParams() {
		this(10, new IntRange(2, 4), new FloatRange(50, 200),
				new IntRange(2, 4), new FloatRange(20, 20), 200);
	}

	public GraphGenerationParams(int numNodes, IntRange edgeRange,
			FloatRange distanceRange, IntRange nodeExpansionRange,
			FloatRange sizeRange, float createEdgesToAllNodesDistance) {
		this.numberNodes = numNodes;
		this.edgeRange = edgeRange;
		this.distanceRange = distanceRange;
		this.nodeExpansionRange = nodeExpansionRange;
		this.createEdgesToAllNodesDistance = createEdgesToAllNodesDistance;
		this.sizeRange = sizeRange;
	}

	public int getNumberNodes() {
		return this.numberNodes;
	}

	public FloatRange getDistanceRange() {
		return this.distanceRange;
	}

	public FloatRange getSizeRange() {
		return this.sizeRange;
	}

	public IntRange getEdgeRange() {
		return this.edgeRange;
	}

	public IntRange getNodeExpansionRange() {
		return this.nodeExpansionRange;
	}

	public float getCreateEdgeToAllNodesDistance() {
		return this.createEdgesToAllNodesDistance;
	}
}