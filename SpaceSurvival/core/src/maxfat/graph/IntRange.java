package maxfat.graph;

public class IntRange {
	private final int min;
	private final int max;

	public IntRange(int min, int max) {
		if (max < min)
			throw new IllegalArgumentException("max must be greater than min");
		this.min = min;
		this.max = max;
	}

	public int range() {
		return this.max - this.min;
	}

	public int getMin() {
		return this.min;
	}

	public int getMax() {
		return this.max;
	}
}
