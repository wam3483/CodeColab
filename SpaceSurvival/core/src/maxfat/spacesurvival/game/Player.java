package maxfat.spacesurvival.game;

import java.util.HashSet;

public class Player {
	private final HashSet<Planet> ownedPlanets;
	private final HashSet<Planet> basicDetailsKnown;
	private final HashSet<SpaceShip> ships;

	public Player() {
		this.ownedPlanets = new HashSet<Planet>();
		this.ships = new HashSet<SpaceShip>();
		this.basicDetailsKnown = new HashSet<Planet>();
	}

	private final SpaceShip.IShipListener shipListener = new SpaceShip.IShipListener() {
		@Override
		public void onArrivedAtPlanet(SpaceShip ship, Planet planet) {
			if (!basicDetailsKnown.contains(planet)) {
				basicDetailsKnown.add(planet);
			}
		}
	};

	public void addShip(SpaceShip ship) {
		if (!this.ships.contains(ship)) {
			ship.listener = this.shipListener;
			this.ships.add(ship);
		}
	}

	public boolean canViewPlanetDetails(Planet planet) {
		return this.ownedPlanets.contains(planet)
				|| this.basicDetailsKnown.contains(planet);
	}
}
