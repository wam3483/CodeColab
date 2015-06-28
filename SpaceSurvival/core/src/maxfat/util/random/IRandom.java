package maxfat.util.random;

public interface IRandom {
	double nextDouble();

	double nextGaussian();

	void nextBytes(byte[] bytes);

	int nextInt(int max);

	long getSeed();
}
