package com.pixelatedmind.spacesurvival.desktop;

import maxfat.spacesurvival.screens.SpaceSurvivalGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.samples = 2;
		new LwjglApplication(new SpaceSurvivalGame(), config);
	}
}