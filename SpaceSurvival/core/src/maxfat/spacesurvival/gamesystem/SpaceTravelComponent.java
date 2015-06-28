package maxfat.spacesurvival.gamesystem;

import maxfat.spacesurvival.game.Planet;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class SpaceTravelComponent extends Component {
	public Planet from;
	public Planet to;
	public float distanceTraveled;
	public float distanceToTravel;

	public SpaceTravelComponent(Planet from, Planet to) {
		this.distanceTraveled = 0;
		this.from = from;
		this.to = to;
		Vector2 temp = new Vector2(from.getPosition());
		temp.sub(to.getPosition());
		this.distanceToTravel = temp.len();
	}

	public boolean travelIsComplete() {
		return this.distanceToTravel == this.distanceTraveled;
	}
}