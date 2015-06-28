package maxfat.spacesurvival.screens;

import com.badlogic.gdx.Game;

public class SpaceSurvivalGame extends Game {

	@Override
	public void create() {
		this.setScreen(new GameScreen());
	}
}