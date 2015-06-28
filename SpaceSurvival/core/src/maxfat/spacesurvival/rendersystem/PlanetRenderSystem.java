package maxfat.spacesurvival.rendersystem;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlanetRenderSystem extends EntitySystem {
	private ImmutableArray<Entity> entities;
	PolygonSpriteBatch spriteBatch;
	private ComponentMapper<PositionComponent> pm = ComponentMapper
			.getFor(PositionComponent.class);
	private ComponentMapper<ColorComponent> vm = ComponentMapper
			.getFor(ColorComponent.class);
	private ComponentMapper<RadiusComponent> radiusMap = ComponentMapper
			.getFor(RadiusComponent.class);
	private ComponentMapper<AnimatingPolygonRegionComponent> regionMapper = ComponentMapper
			.getFor(AnimatingPolygonRegionComponent.class);

	Viewport viewport;

	public PlanetRenderSystem(Viewport viewport) {
		this.spriteBatch = new PolygonSpriteBatch();
		this.viewport = viewport;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(PositionComponent.class,
				RadiusComponent.class, AnimatingPolygonRegionComponent.class)
				.get());
	}

	@Override
	public void update(float deltaTime) {
		Camera camera = viewport.getCamera();
		this.spriteBatch.setProjectionMatrix(camera.projection);
		this.spriteBatch.setTransformMatrix(camera.view);
		this.spriteBatch.begin();

		for (int i = 0; i < entities.size(); ++i) {
			Entity entity = entities.get(i);
			PositionComponent position = pm.get(entity);
			AnimatingPolygonRegionComponent poly = this.regionMapper
					.get(entity);
			for (AnimatingPolygonRegionComponent.PolygonLayer layer : poly.layers) {
				this.spriteBatch.draw(layer.polygonRegion, position.x,
						position.y);
			}
		}
		this.spriteBatch.end();
	}

	public void dispose() {
		this.spriteBatch.dispose();
	}
}
