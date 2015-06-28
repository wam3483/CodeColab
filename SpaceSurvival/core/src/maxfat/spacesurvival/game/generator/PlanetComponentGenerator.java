package maxfat.spacesurvival.game.generator;

import maxfat.spacesurvival.gamesystem.PlanetComponent;

public class PlanetComponentGenerator {
	private final PlanetParams params;
	private final PlanetNameService nameProvider;

	public PlanetComponentGenerator(PlanetNameService nameProvider,
			PlanetParams params) {
		this.params = params;
		this.nameProvider = nameProvider;
	}

	public PlanetComponent generatePlanetComponent() {
		PlanetComponent comp = new PlanetComponent();
		comp.temperatureCelsius = params.temperatureProvider.getValue();
		comp.percentWater = params.waterProvider.getValue();
		comp.foodBonus = params.foodBonusProvider.getValue();
		comp.birthBonus = params.birthBonusProvider.getValue();
		comp.maxPopulation = params.maxPopProvider.getValue();
		comp.population = params.currentPopulationProvider.getValue();
		comp.name = this.nameProvider.getPlanetName(comp);
		return comp;
	}

	public static class PlanetParams {
		public RandomFloatProvider temperatureProvider;
		public RandomFloatProvider waterProvider;
		public RandomFloatProvider foodBonusProvider;
		public RandomFloatProvider maxPopProvider;
		public RandomFloatProvider birthBonusProvider;
		public RandomFloatProvider currentPopulationProvider;
	}
}
