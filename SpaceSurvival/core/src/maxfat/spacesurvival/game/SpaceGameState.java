package maxfat.spacesurvival.game;

import java.util.ArrayList;
import java.util.List;

import maxfat.spacesurvival.gamesystem.SystemBattle;
import maxfat.spacesurvival.gamesystem.SystemRefueling;
import maxfat.spacesurvival.gamesystem.SystemSpaceTravel;

import com.badlogic.ashley.core.Engine;

public class SpaceGameState {
	private final List<Player> players;
	private Engine engine;

	public SpaceGameState() {
		this.players = new ArrayList<Player>();

		this.engine = new Engine();
		this.engine.addSystem(new SystemBattle());
		this.engine.addSystem(new SystemSpaceTravel());
		this.engine.addSystem(new SystemRefueling());
	}

	public void addPlayer(Player player) {
		if (!this.players.contains(player)) {
			this.players.add(player);
		}
	}
}