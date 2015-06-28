package maxfat.spacesurvival.rendersystem;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlanetAnimationSystem extends EntitySystem {

	private ImmutableArray<Entity> entities;
	private ComponentMapper<AnimatingPolygonRegionComponent> animateMapper = ComponentMapper
			.getFor(AnimatingPolygonRegionComponent.class);

	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(
				AnimatingPolygonRegionComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			AnimatingPolygonRegionComponent regionComp = this.animateMapper
					.get(e);
			for(AnimatingPolygonRegionComponent.PolygonLayer layer : regionComp.layers){

				float[] originalCoords = layer.originalTexCoords;
				PolygonRegion region = layer.polygonRegion;
				float animation = layer.animation;
				this.animateCoords(originalCoords, region, animation);
				layer.update(deltaTime);
			}
		}
	}

	private void animateCoords(float[] originalCoords, PolygonRegion region,
			float animation) {
		TextureRegion texRegion = region.getRegion();
		float[] coords = region.getTextureCoords();
		for (int j = 0; j < originalCoords.length; j += 2) {
			float uRange = texRegion.getU2() - texRegion.getU();
			float vRange = texRegion.getV2() - texRegion.getV();
			float uOffset = animation * uRange;
			float vOffset = animation * vRange;
			coords[j] = (originalCoords[j] + uOffset);
		}
	}
}
