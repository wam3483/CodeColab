package maxfat.spacesurvival.gamesystem;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class SystemRefueling extends IteratingSystem {

	private ComponentMapper<SpaceEngineComponent> fuelMapper = ComponentMapper
			.getFor(SpaceEngineComponent.class);

	// private ComponentMapper<HostPlanetComponent> hostPlanetMapper =
	// ComponentMapper
	// .getFor(HostPlanetComponent.class);

	@SuppressWarnings("unchecked")
	public SystemRefueling() {
		super(Family.all(SpaceEngineComponent.class, HostPlanetComponent.class)
				.get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		SpaceEngineComponent fuelComp = fuelMapper.get(entity);
		float amountRefuel = fuelComp.refuelRatePerSec * deltaTime;
		fuelComp.fillTank(amountRefuel);
	}
}
