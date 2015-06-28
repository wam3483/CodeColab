package maxfat.spacesurvival.game;

import maxfat.spacesurvival.gamesystem.HostPlanetComponent;
import maxfat.spacesurvival.gamesystem.SpaceEngineComponent;
import maxfat.spacesurvival.gamesystem.SpaceTravelComponent;
import maxfat.spacesurvival.gamesystem.SpeedComponent;

import com.badlogic.ashley.core.Entity;

public class SpaceShip {
	Entity entity;
	IShipListener listener;

	public SpaceShip(String name, float speed, FuelDetails fuelDetails,
			Planet planet) {
		this.entity = new Entity();
		entity.add(new SpeedComponent(speed));
		entity.add(new SpaceEngineComponent(fuelDetails.currentFuel,
				fuelDetails.burnRate, fuelDetails.refuelRate,
				fuelDetails.maxFuel));
		entity.add(new HostPlanetComponent(planet));
	}

	public boolean travelToPlanet(Planet planet) {
		HostPlanetComponent hostComp = (HostPlanetComponent) this.entity
				.remove(HostPlanetComponent.class);
		if (hostComp == null)
			return false;
		else {
			this.entity.add(new SpaceTravelComponent(hostComp.hostPlanet,
					planet));
			return true;
		}
	}

	public Entity getEntity() {
		return this.entity;
	}

	public static class FuelDetails {
		public float currentFuel;
		public float maxFuel;
		public float burnRate;
		public float refuelRate;

		public FuelDetails(float current, float max, float burn, float refuel) {
			this.currentFuel = current;
			this.maxFuel = max;
			this.burnRate = burn;
			this.refuelRate = refuel;
		}
	}

	public interface IShipListener {
		void onArrivedAtPlanet(SpaceShip ship, Planet planet);
	}
}