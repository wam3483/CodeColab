package maxfat.spacesurvival.rendersystem;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LineRenderSystem extends EntitySystem {
	private ShapeRenderer shapeRenderer;
	private ImmutableArray<Entity> entities;

	private ComponentMapper<ColorComponent> cm = ComponentMapper
			.getFor(ColorComponent.class);
	private ComponentMapper<LineComponent> vm = ComponentMapper
			.getFor(LineComponent.class);
	Viewport viewport;

	public LineRenderSystem(Viewport viewport) {
		this.shapeRenderer = new ShapeRenderer();
		this.viewport = viewport;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(Family.all(LineComponent.class,
				ColorComponent.class).get());
	}

	@Override
	public void update(float deltaTime) {
		this.shapeRenderer.begin(ShapeType.Line);
		Camera camera = viewport.getCamera();
		this.shapeRenderer.setProjectionMatrix(camera.projection);
		this.shapeRenderer.setTransformMatrix(camera.view);
		for (int i = 0; i < entities.size(); ++i) {
			Entity entity = entities.get(i);
			LineComponent line = vm.get(entity);
			ColorComponent position = cm.get(entity);
			Color c = position.color;
			this.shapeRenderer.line(line.x1, line.y1, line.x2, line.y2, c, c);
		}
		this.shapeRenderer.end();
	}

	public void dispose() {
		this.shapeRenderer.dispose();
	}
}
