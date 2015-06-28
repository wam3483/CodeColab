package maxfat.util.random;

import java.util.Random;

public class DefaultRandom implements IRandom {

	private final Random random;
	private final long seed;

	public DefaultRandom(long seed) {
		this.random = new Random(seed);
		this.seed = seed;
	}

	@Override
	public double nextGaussian() {
		return this.random.nextGaussian();
	}

	@Override
	public double nextDouble() {
		return this.random.nextDouble();
	}

	@Override
	public int nextInt(int max) {
		return this.random.nextInt(max);
	}

	@Override
	public long getSeed() {
		return this.seed;
	}

	@Override
	public void nextBytes(byte[] bytes) {
		this.random.nextBytes(bytes);
	}
}