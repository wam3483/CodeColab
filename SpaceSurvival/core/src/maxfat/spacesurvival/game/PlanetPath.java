package maxfat.spacesurvival.game;

public class PlanetPath {
	private final Planet from;
	private final Planet to;
	private float distance;

	public PlanetPath(Planet from, Planet to, float distance) {
		this.from = from;
		this.to = to;
		this.distance = distance;
	}

	public float getDistance() {
		return this.distance;
	}

	public Planet getFrom() {
		return this.from;
	}

	public Planet getTo() {
		return this.to;
	}
}
