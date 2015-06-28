package maxfat.spacesurvival.game.generator;

import maxfat.graph.PlanetData;
import maxfat.spacesurvival.game.Civilization;
import maxfat.spacesurvival.game.IFactory;

import com.badlogic.gdx.math.Vector2;

public class PlanetDataGenerator implements IFactory<PlanetData> {
	CivilizationGenerator gen;

	public PlanetDataGenerator(CivilizationGenerator gen) {
		this.gen = gen;
	}

	@Override
	public PlanetData create() {
		Civilization civ = gen.gerateCivilization();
		PlanetData data = new PlanetData(new Vector2(), 0, 0, civ);
		return data;
	}

}
