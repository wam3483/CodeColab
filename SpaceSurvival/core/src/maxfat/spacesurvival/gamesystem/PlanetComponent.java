package maxfat.spacesurvival.gamesystem;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.MathUtils;

public class PlanetComponent extends Component {

	public String name;

	public float population;
	public float maxPopulation;
	public float farmingPopulation;

	public float naturalDeathPercent;
	public float birthBonus;
	public float foodBonus;
	public float goldBonus;

	public float percentWater;
	public float temperatureCelsius;

	public PlanetComponent() {

	}

	public float getIdlePopulation() {
		return this.population - this.farmingPopulation;
	}

	public boolean canSupportLife() {
		return true;
	}

	public float getBirthBonus() {
		return this.birthBonus;
	}

	public float getFoodBonus() {
		return this.foodBonus;
	}

	public float getNaturalDeathPercent() {
		return this.naturalDeathPercent;
	}

	public void setFarmingPopulation(float farmingPop) {
		farmingPop = MathUtils.clamp(farmingPop, 0, this.maxPopulation);
		this.farmingPopulation = farmingPop;
	}

	public void addPeople(float people) {
		this.population = MathUtils.clamp(this.population + people, 0,
				this.maxPopulation);
	}
}