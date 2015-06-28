package maxfat.graph;

public class FloatRange {
	private final float min;
	private final float max;

	public FloatRange(float min, float max) {
		if (max < min)
			throw new IllegalArgumentException("max must be greater than min");
		this.min = min;
		this.max = max;
	}

	public float range() {
		return this.max - this.min;
	}

	public float getMin() {
		return this.min;
	}

	public float getMax() {
		return this.max;
	}
}
