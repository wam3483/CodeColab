package maxfat.spacesurvival.screens;

import maxfat.graph.Graph;
import maxfat.graph.Node;
import maxfat.graph.PlanetData;
import maxfat.spacesurvival.gamesystem.PlanetComponent;
import maxfat.spacesurvival.gamesystem.SystemBattle;
import maxfat.spacesurvival.gamesystem.SystemPlayerGoldUpdater;
import maxfat.spacesurvival.gamesystem.SystemRefueling;
import maxfat.spacesurvival.gamesystem.SystemSpaceTravel;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public class GameStateEngine {
	private final Engine engine;
	private final Graph<PlanetData> graph;

	public GameStateEngine(Graph<PlanetData> graph) {
		this.graph = graph;

		this.engine = new Engine();
		this.engine.addSystem(new SystemBattle());
		this.engine.addSystem(new SystemSpaceTravel());
		this.engine.addSystem(new SystemRefueling());
		this.engine.addSystem(new SystemPlayerGoldUpdater());

		initPlanetEntities();
	}

	void initPlanetEntities() {
		for (Node<PlanetData> node : graph) {
			Entity e = new Entity();
			PlanetData data = node.getData();
			PlanetComponent planetComp = new PlanetComponent();
			e.add(planetComp);
			engine.addEntity(e);
		}
	}

	public void update(float time) {
		this.engine.update(time);
	}
	
}