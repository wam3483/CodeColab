package maxfat.spacesurvival.screens;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import maxfat.graph.Graph;
import maxfat.graph.I2DData;
import maxfat.graph.Node;
import maxfat.graph.PlanetData;
import maxfat.spacesurvival.rendersystem.AnimatingPolygonRegionComponent;
import maxfat.spacesurvival.rendersystem.ColorComponent;
import maxfat.spacesurvival.rendersystem.LineComponent;
import maxfat.spacesurvival.rendersystem.LineRenderSystem;
import maxfat.spacesurvival.rendersystem.PlanetAnimationSystem;
import maxfat.spacesurvival.rendersystem.PlanetRenderSystem;
import maxfat.spacesurvival.rendersystem.PositionComponent;
import maxfat.spacesurvival.rendersystem.RadiusComponent;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ShortArray;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SpaceRenderEngine {
	private final Engine renderEngine;
	private final Graph<PlanetData> graph;

	TextureRegion clouds;
	TextureRegion land;
	TextureRegion ocean;

	public SpaceRenderEngine(Viewport viewport, Graph<PlanetData> graph) {
		this.renderEngine = new Engine();
		this.graph = graph;
		this.clouds = loadTexture("planet_textures/clouds.png");
		land = loadTexture("planet_textures/land.png");
		ocean = loadTexture("planet_textures/ocean.png");
		this.addNodesToEngine();
		this.addEntitySystems(viewport);
	}

	private TextureRegion loadTexture(String path) {
		TextureRegion t = new TextureRegion(new Texture(
				Gdx.files.internal(path)));
		t.getTexture().setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		return t;
	}

	public Graph<PlanetData> getGraph() {
		return this.graph;
	}

	private void addEntitySystems(Viewport viewport) {
		this.renderEngine.addSystem(new LineRenderSystem(viewport));
		this.renderEngine.addSystem(new PlanetRenderSystem(viewport));
		this.renderEngine.addSystem(new PlanetAnimationSystem());
	}

	private void addNodesToEngine() {
		Node<PlanetData> firstNode = graph.getNode();
		HashSet<Node<PlanetData>> hashset = new HashSet<Node<PlanetData>>();
		Queue<Node<PlanetData>> queue = new LinkedList<Node<PlanetData>>();
		queue.add(firstNode);
		hashset.add(firstNode);
		while (!queue.isEmpty()) {
			Node<PlanetData> nextNode = queue.poll();
			createNodeEntity(nextNode);
			for (Node<PlanetData> connectedNode : nextNode.getEdges()) {
				if (!hashset.contains(connectedNode)) {
					this.createLineEntity(nextNode, connectedNode);
					queue.add(connectedNode);
					hashset.add(connectedNode);
				}
			}
		}
	}

	private void createLineEntity(Node<PlanetData> n1, Node<PlanetData> n2) {
		Entity e = new Entity();
		Vector2 p1 = n1.getData().getPoint();
		Vector2 p2 = n2.getData().getPoint();

		e.add(new LineComponent((float) p1.x, (float) p1.y, (float) p2.x,
				(float) p2.y));
		e.add(new ColorComponent());
		this.renderEngine.addEntity(e);
	}

	private EarClippingTriangulator triangulation = new EarClippingTriangulator();

	private PolygonRegion createCircle(TextureRegion texture, int vertices,
			float radius) {
		float[] v = new float[vertices * 2 + 2];
		float degInc = 360 / vertices;
		float degrees = 0;
		for (int i = 0; i < vertices; i++) {
			int index = i * 2;
			v[index] = MathUtils.cosDeg(degrees) * radius;
			v[index + 1] = MathUtils.sinDeg(degrees) * radius;
			degrees += degInc;
		}
		v[v.length - 2] = v[0];
		v[v.length - 1] = v[1];
		ShortArray ary = triangulation.computeTriangles(v);
		short[] triangles = new short[ary.size];
		System.arraycopy(ary.items, 0, triangles, 0, ary.size);
		PolygonRegion region = new PolygonRegion(texture, v, triangles);
		return region;
	}

	private void createNodeEntity(Node<PlanetData> node) {
		Entity e = new Entity();
		I2DData data = node.getData();
		Vector2 p = data.getPoint();

		e.add(new PositionComponent((float) p.x, (float) p.y));
		e.add(new ColorComponent());
		e.add(new RadiusComponent((float) data.getSize()));

		PolygonRegion oceanRegion = createCircle(ocean, 30, data.getSize());
		PolygonRegion landRegion = createCircle(land, 30, data.getSize());
		PolygonRegion cloudRegion = createCircle(clouds, 30, data.getSize());
		AnimatingPolygonRegionComponent.PolygonLayer layer1 = new AnimatingPolygonRegionComponent.PolygonLayer(
				oceanRegion, 0);
		AnimatingPolygonRegionComponent.PolygonLayer layer2 = new AnimatingPolygonRegionComponent.PolygonLayer(
				landRegion, .05f);
		AnimatingPolygonRegionComponent.PolygonLayer layer3 = new AnimatingPolygonRegionComponent.PolygonLayer(
				cloudRegion, .1f);
		e.add(new AnimatingPolygonRegionComponent(layer1, layer2, layer3));
		this.renderEngine.addEntity(e);
	}

	public void render(float deltaTime) {
		this.renderEngine.update(deltaTime);
	}
}