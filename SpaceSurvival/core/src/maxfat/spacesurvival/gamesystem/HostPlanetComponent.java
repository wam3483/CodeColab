package maxfat.spacesurvival.gamesystem;

import maxfat.spacesurvival.game.Planet;

import com.badlogic.ashley.core.Component;

public class HostPlanetComponent extends Component {
	public Planet hostPlanet;

	public HostPlanetComponent(Planet planet) {
		this.hostPlanet = planet;
	}
}
