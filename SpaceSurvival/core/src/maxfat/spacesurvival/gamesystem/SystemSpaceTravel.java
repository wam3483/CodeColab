package maxfat.spacesurvival.gamesystem;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class SystemSpaceTravel extends IteratingSystem {

	private ComponentMapper<SpaceEngineComponent> fuelMapper = ComponentMapper
			.getFor(SpaceEngineComponent.class);
	private ComponentMapper<SpeedComponent> speedMapper = ComponentMapper
			.getFor(SpeedComponent.class);
	private ComponentMapper<SpaceTravelComponent> travelMapper = ComponentMapper
			.getFor(SpaceTravelComponent.class);

	@SuppressWarnings("unchecked")
	public SystemSpaceTravel() {
		super(Family.all(SpaceEngineComponent.class, SpeedComponent.class,
				SpaceTravelComponent.class).get());
	}

	@Override
	protected void processEntity(Entity e, float deltaTime) {
		SpaceEngineComponent fuelComp = fuelMapper.get(e);
		SpeedComponent speedComp = speedMapper.get(e);
		SpaceTravelComponent travelComp = travelMapper.get(e);
		float speed = speedComp.speed;
		if (fuelComp.fuel <= 0) {
			speed /= 2;
		}
		updateTravel(deltaTime, speed, travelComp, fuelComp);
		if (travelComp.travelIsComplete()) {
			e.remove(SpaceTravelComponent.class);
			e.add(new HostPlanetComponent(travelComp.to));
		}
	}

	void updateTravel(float deltaTime, float speed,
			SpaceTravelComponent travelState, SpaceEngineComponent fuelComp) {
		float traveledDistance = deltaTime * speed;
		float overTraveled = travelState.distanceTraveled + traveledDistance
				- travelState.distanceToTravel;
		if (overTraveled >= 0) {
			traveledDistance -= overTraveled;
			travelState.distanceTraveled = travelState.distanceToTravel;
		} else {
			travelState.distanceTraveled += traveledDistance;
		}

		fuelComp.fuel -= (fuelComp.burnRate * traveledDistance);
		if (fuelComp.fuel < 0) {
			fuelComp.fuel = 0;
		}
	}
}
