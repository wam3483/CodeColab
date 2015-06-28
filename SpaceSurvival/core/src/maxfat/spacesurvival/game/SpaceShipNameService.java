package maxfat.spacesurvival.game;

import maxfat.util.random.IRandom;

public class SpaceShipNameService {
	private final String[] names;
	private final IRandom random;

	public SpaceShipNameService(IRandom random, String[] names) {
		this.random = random;
		this.names = names;
	}

	public String getSpaceShipName() {
		return this.names[random.nextInt(this.names.length)];
	}

}