package maxfat.spacesurvival.game.generator;

import maxfat.graph.FloatRange;
import maxfat.util.random.IRandom;
import maxfat.util.random.RandomUtil;

public class RandomFloatProvider {
	private final FloatRange range;
	private final IRandom random;

	public RandomFloatProvider(IRandom random, FloatRange range) {
		this.random = random;
		this.range = range;
	}

	public float getValue() {
		return RandomUtil.randomBetweenRanges(this.random, this.range.getMin(),
				range.getMax());
	}
}
