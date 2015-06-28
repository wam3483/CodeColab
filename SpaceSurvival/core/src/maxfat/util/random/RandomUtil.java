package maxfat.util.random;

public class RandomUtil {
	private RandomUtil() {
	}

	public static boolean randomBool(IRandom random) {
		return randomBool(random, .5f);
	}

	public static boolean randomBool(IRandom random, float chanceWillBeTrue) {
		return random.nextDouble() < chanceWillBeTrue;
	}

	public static int randomSign(IRandom random) {
		return random.nextInt(2) == 0 ? -1 : 1;
	}

	public static double randomBetweenRanges(IRandom random, double min,
			double max) {
		double range = max - min;
		double value = random.nextDouble() * range + min;
		return value;
	}

	public static float randomBetweenRanges(IRandom random, float min, float max) {
		float range = max - min;
		float value = (float) random.nextDouble() * range + min;
		return value;
	}

	public static int randomBetweenRanges(IRandom random, int min, int max) {
		int range = max - min;
		int value = random.nextInt(range + 1) + min;
		return value;
	}
}