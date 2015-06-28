package maxfat.spacesurvival.screens;

import maxfat.graph.FloatRange;
import maxfat.graph.Graph;
import maxfat.graph.GraphGenerationParams;
import maxfat.graph.GraphGenerator;
import maxfat.graph.IntRange;
import maxfat.graph.Node;
import maxfat.graph.PlanetData;
import maxfat.spacesurvival.game.generator.CivilizationGenerator;
import maxfat.spacesurvival.game.generator.PlanetComponentGenerator;
import maxfat.spacesurvival.game.generator.PlanetDataGenerator;
import maxfat.spacesurvival.game.generator.PlanetNameService;
import maxfat.spacesurvival.game.generator.PopulationGenerator;
import maxfat.spacesurvival.game.generator.PopulationNameService;
import maxfat.spacesurvival.game.generator.RandomFloatProvider;
import maxfat.util.random.DefaultRandom;
import maxfat.util.random.IRandom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
	private FitViewport viewport;
	ViewController viewController;
	GameScreenInputManager inputManager;
	private SpaceRenderEngine level;

	TextureRegion clouds;
	GraphGenerator<PlanetData> generator;

	public GameScreen() {
		clouds = new TextureRegion(new Texture(
				Gdx.files.internal("planet_textures/ocean.png")));
		this.viewport = new FitViewport(GameConstants.ScreenWidth,
				GameConstants.ScreenHeight);
		this.viewController = new ViewController(viewport);
		this.initGenerator();
		this.initLevel();
		this.inputManager = new GameScreenInputManager(this.level.getGraph(),
				this.viewport);
		this.inputManager.listener = inputListener;
		Gdx.input.setInputProcessor(this.inputManager);
	}

	GameScreenInputManager.IGameUIListener inputListener = new GameScreenInputManager.IGameUIListener() {

		@Override
		public void zoom(float increment) {
			viewController.addZoom(increment);
		}

		@Override
		public void viewportDrag(Vector2 v) {
			viewController.translate(v.x, v.y);
		}

		@Override
		public void onPlanetClicked(PlanetData p) {

		}
	};

	private void initGenerator() {
		int numberNodes = 1000;
		IntRange edgeRange = new IntRange(2, 2);
		FloatRange distanceRange = new FloatRange(50, 2000);
		IntRange nodeExpansionRange = new IntRange(1, 1);
		FloatRange sizeRange = new FloatRange(50, 100);
		int edgesToAllNodesDistance = 2000;
		GraphGenerationParams graphParams = new GraphGenerationParams(
				numberNodes, edgeRange, distanceRange, nodeExpansionRange,
				sizeRange, edgesToAllNodesDistance);
		IRandom random = new DefaultRandom(0L);

		PlanetNameService planetNameService = new PlanetNameService(random,
				new String[] { "bob", "joe", "george", "earth" });
		PlanetComponentGenerator.PlanetParams planetParams = new PlanetComponentGenerator.PlanetParams();
		planetParams.birthBonusProvider = new RandomFloatProvider(random,
				new FloatRange(1, 10));
		planetParams.currentPopulationProvider = new RandomFloatProvider(
				random, new FloatRange(1, 10));
		planetParams.foodBonusProvider = new RandomFloatProvider(random,
				new FloatRange(1, 10));
		planetParams.maxPopProvider = new RandomFloatProvider(
				random,
				new FloatRange((float) Math.pow(10, 3), (float) Math.pow(10, 7)));
		planetParams.temperatureProvider = new RandomFloatProvider(random, //
				new FloatRange(GameConstants.PlanetConstants.LivableMinTemp,
						GameConstants.PlanetConstants.LivableMaxTemp));
		planetParams.waterProvider = new RandomFloatProvider(random,
				new FloatRange(0, 1));

		PlanetComponentGenerator planetGen = new PlanetComponentGenerator(
				planetNameService, planetParams);

		PopulationNameService popName = new PopulationNameService();
		PopulationGenerator.PopulationParams popParams = new PopulationGenerator.PopulationParams(
				random);
		PopulationGenerator popGen = new PopulationGenerator(popParams, popName);
		CivilizationGenerator civGen = new CivilizationGenerator(planetGen,
				popGen, random, .75f);

		PlanetDataGenerator factory = new PlanetDataGenerator(civGen);
		this.generator = new GraphGenerator<PlanetData>(graphParams, random,
				factory);
	}

	private void initLevel() {
		Node<PlanetData> node = generator.generate();
		Graph<PlanetData> graph = new Graph<PlanetData>(node);
		this.level = new SpaceRenderEngine(this.viewport, graph);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
		Gdx.graphics.getGL20().glClear(
				GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		viewController.update(delta);
		this.level.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		this.viewport.update(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		this.inputManager.dispose();
	}
}