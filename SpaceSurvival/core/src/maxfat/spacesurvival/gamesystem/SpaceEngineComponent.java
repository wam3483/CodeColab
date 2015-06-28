package maxfat.spacesurvival.gamesystem;

import com.badlogic.ashley.core.Component;

public class SpaceEngineComponent extends Component {
	public float fuel;
	public float burnRate;// fuel burned per unit of travel
	public float refuelRatePerSec;
	public float maxFuel;

	public SpaceEngineComponent(float fuel, float burnRate,
			float refuelRatePerSec, float maxFuel) {
		this.fuel = fuel;
		this.burnRate = burnRate;
		this.refuelRatePerSec = refuelRatePerSec;
		this.maxFuel = maxFuel;
	}

	public void fillTank(float amount) {
		this.fuel += amount;
		this.fuel = Math.min(fuel, maxFuel);
	}

	public void burnFuel(float amount) {
		this.fuel -= amount;
		this.fuel = Math.max(0, this.fuel);
	}
}
