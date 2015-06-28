package maxfat.spacesurvival.game.generator;

import maxfat.graph.FloatRange;
import maxfat.spacesurvival.gamesystem.PlanetComponent;
import maxfat.spacesurvival.gamesystem.PopulationComponent;
import maxfat.util.random.IRandom;

public class PopulationGenerator {

	PopulationParams params;
	private final PopulationNameService populationNameGen;

	public PopulationGenerator(PopulationParams params,
			PopulationNameService nameGenerator) {
		this.populationNameGen = nameGenerator;
		this.params = params;
	}

	public PopulationComponent generatePopulationComponent(
			PlanetComponent planetComp) {
		PopulationComponent pop = null;

		pop = new PopulationComponent();
		pop.birthPercentPerTurn = params.birthRange.getValue();
		pop.extrafoodBirthBonus = params.extrafoodBirthBonusRange.getValue();
		pop.foodEatenPerPersonPerTurn = params.foodEatenPerPersonPerTurnRange
				.getValue();
		pop.foodPerFarmerPerTurn = params.foodPerFarmerPerTurnRange.getValue();
		pop.resistienceToNaturalDeath = params.naturalDeathResistanceRange
				.getValue();
		pop.starveChancePerTurn = params.starveRange.getValue();
		pop.name = this.populationNameGen.getPopulationName(planetComp, pop);

		return pop;
	}

	public static class PopulationParams {
		RandomFloatProvider birthRange;
		RandomFloatProvider starveRange;
		RandomFloatProvider naturalDeathResistanceRange;
		RandomFloatProvider foodPerFarmerPerTurnRange;
		RandomFloatProvider foodEatenPerPersonPerTurnRange;

		RandomFloatProvider extrafoodBirthBonusRange;

		public PopulationParams(IRandom random) {
			this.birthRange = new RandomFloatProvider(random, new FloatRange(1,
					10));
			this.starveRange = new RandomFloatProvider(random, new FloatRange(
					1, 10));
			this.naturalDeathResistanceRange = new RandomFloatProvider(random,
					new FloatRange(1, 10));
			this.foodPerFarmerPerTurnRange = new RandomFloatProvider(random,
					new FloatRange(0, 1));
			this.foodEatenPerPersonPerTurnRange = new RandomFloatProvider(
					random, new FloatRange(1, 10));
			this.extrafoodBirthBonusRange = new RandomFloatProvider(random,
					new FloatRange(1, 10));
		}
	}
}
