package maxfat.spacesurvival.gamesystem;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class SystemPlanetPopulationUpdater extends IteratingSystem {

	private ComponentMapper<PlanetComponent> planetMapper = ComponentMapper
			.getFor(PlanetComponent.class);
	private ComponentMapper<PopulationComponent> populationMapper = ComponentMapper
			.getFor(PopulationComponent.class);

	@SuppressWarnings("unchecked")
	public SystemPlanetPopulationUpdater() {
		super(Family.all(PlanetComponent.class, PopulationComponent.class)
				.get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PlanetComponent planetComp = planetMapper.get(entity);
		PopulationComponent popComp = populationMapper.get(entity);
		Civilization civ = new Civilization(planetComp, popComp);

		this.updateStarvation(civ);
		this.updateNaturalDeaths(civ);
		this.updateBirths(civ);
	}

	private void updateBirths(Civilization civ) {
		float peopleBorn = civ.getPopulation() * civ.getBirthRate();
		civ.addPopulation(peopleBorn);
	}

	private void updateNaturalDeaths(Civilization civ) {
		float naturalDeaths = civ.getPercentDeath();
		float deaths = civ.getPopulation() * naturalDeaths;
		civ.addPopulation(-deaths);
	}

	private void updateStarvation(Civilization civ) {
		float food = civ.getFoodProduced();
		float requiredFood = civ.getRequiredFood();
		if (food < requiredFood) {
			float fedPopulation = food / civ.getFoodNeededPerPerson();
			float unfedPopulation = civ.getPopulation() - fedPopulation;
			float starvationDeaths = unfedPopulation
					* civ.getStarvationPercent();
			civ.addPopulation(-starvationDeaths);
		}
	}

	private class Civilization {
		final float MIN_DEATH = .01f;
		PlanetComponent planetComp;
		PopulationComponent popComp;

		public float getFoodBirthBonus() {
			if (this.getFoodProduced() == this.getRequiredFood() * 2) {
				return this.popComp.extrafoodBirthBonus;
			} else {
				return 0;
			}
		}

		public Civilization(PlanetComponent planetComp,
				PopulationComponent popComp) {
			this.planetComp = planetComp;
			this.popComp = popComp;
		}

		public float getStarvationPercent() {
			return this.popComp.starveChancePerTurn;
		}

		public float getPercentDeath() {
			float death = this.planetComp.getNaturalDeathPercent();
			float popDeath = this.popComp.resistienceToNaturalDeath;
			death -= popDeath;
			if (death < 0)
				death = MIN_DEATH;
			return death;
		}

		public float getBirthRate() {
			return planetComp.birthBonus + popComp.birthPercentPerTurn
					+ this.getFoodBirthBonus();
		}

		public void addPopulation(float pop) {
			this.planetComp.addPeople(pop);
		}

		public float getPopulation() {
			return this.planetComp.population;
		}

		public float getFoodNeededPerPerson() {
			return this.popComp.foodEatenPerPersonPerTurn;
		}

		public float getRequiredFood() {
			return this.popComp.foodEatenPerPersonPerTurn
					* this.planetComp.population;
		}

		public float getFoodProduced() {
			float foodGeneratedPerFarmer = this.planetComp.getFoodBonus()
					+ popComp.foodPerFarmerPerTurn;
			float foodProduced = foodGeneratedPerFarmer
					* planetComp.farmingPopulation;
			return foodProduced;
		}
	}
}