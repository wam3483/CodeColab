package maxfat.spacesurvival.game;

import maxfat.spacesurvival.gamesystem.PlanetComponent;
import maxfat.spacesurvival.gamesystem.PopulationComponent;

public class Civilization {
	public PlanetComponent planetComp;
	public PopulationComponent popComponent;

	public Civilization(PopulationComponent pop, PlanetComponent planetComp) {
		this.planetComp = planetComp;
		this.popComponent = pop;
	}
}