package maxfat.spacesurvival.game.generator;

import maxfat.spacesurvival.game.Civilization;
import maxfat.spacesurvival.gamesystem.PlanetComponent;
import maxfat.spacesurvival.gamesystem.PopulationComponent;
import maxfat.util.random.IRandom;
import maxfat.util.random.RandomUtil;

public class CivilizationGenerator {
	PlanetComponentGenerator planetGen;
	PopulationGenerator popGen;
	IRandom random;
	float chanceOfLife;

	public CivilizationGenerator(PlanetComponentGenerator planetGen,
			PopulationGenerator popGen, IRandom random, float chanceOfLife) {
		this.planetGen = planetGen;
		this.popGen = popGen;
		this.random = random;
		this.chanceOfLife = chanceOfLife;
	}

	public Civilization gerateCivilization() {
		PlanetComponent planet = planetGen.generatePlanetComponent();
		PopulationComponent pop = null;
		if (RandomUtil.randomBool(this.random, chanceOfLife)) {
			pop = popGen.generatePopulationComponent(planet);
		}
		Civilization civ = new Civilization(pop, planet);
		return civ;
	}

}
