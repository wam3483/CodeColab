package maxfat.spacesurvival.game.generator;

import maxfat.spacesurvival.gamesystem.PlanetComponent;
import maxfat.util.random.IRandom;

public class PlanetNameService {
	private final String[] names;
	private final IRandom random;

	public PlanetNameService(IRandom random, String[] names) {
		this.random = random;
		this.names = names;
	}

	public String getPlanetName(PlanetComponent p) {
		return this.names[random.nextInt(this.names.length)];
	}
}