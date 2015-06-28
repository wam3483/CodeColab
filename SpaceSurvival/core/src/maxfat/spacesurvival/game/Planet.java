package maxfat.spacesurvival.game;

import com.badlogic.gdx.math.Vector2;

public class Planet {
	String name;
	Vector2 position;

	float defense;

	public Planet(float defense) {
		this.defense = defense;
	}

	public SpaceShip createSpaceShip() {
		return null;
	}

	public Vector2 getPosition() {
		return this.position;
	}
}