package maxfat.spacesurvival.screens;

import maxfat.graph.Graph;
import maxfat.graph.I2DData;
import maxfat.graph.Node;
import maxfat.graph.PlanetData;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Fires events for input related to non-main screen game events like touching a
 * planet, or ship.
 * 
 * @author wmorrison
 *
 */
public class GameScreenInputManager extends InputMultiplexer {

	private static final IGameUIListener NullListener = new IGameUIListener() {
		@Override
		public void onPlanetClicked(PlanetData p) {
		}

		@Override
		public void viewportDrag(Vector2 v) {
		}

		@Override
		public void zoom(float increment) {
		}
	};

	IGameUIListener listener = NullListener;
	private final Stage stage;
	private final Viewport viewport;

	public GameScreenInputManager(Graph<PlanetData> graph, Viewport viewport) {
		this.viewport = viewport;
		this.stage = new Stage(viewport);
		this.initStage(graph);
		this.addProcessor(this.stage);
		this.addProcessor(this.inputProcessor);
	}

	void initStage(Graph<PlanetData> graph) {
		for (Node<PlanetData> node : graph) {
			final Actor a = new Actor();

			I2DData data = node.getData();
			Vector2 point = data.getPoint();
			float size = data.getSize() * 2;
			a.setBounds(point.x - size / 2, point.y - size / 2, size, size);
			a.setUserObject(node.getData());
			a.addListener(new ClickListener() {
				public void clicked(InputEvent event, float x, float y) {
					listener.onPlanetClicked((PlanetData) a.getUserObject());
				}
			});
			this.stage.addActor(a);
		}
	}

	InputProcessor inputProcessor = new InputProcessor() {
		double scrollSpeed = .2;
		private int startDragPointer;
		int startX;
		int startY;

		@Override
		public boolean keyDown(int keycode) {
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			this.startDragPointer = pointer;
			startX = screenX;
			startY = screenY;
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			this.startDragPointer = -1;
			return false;
		}

		final Vector3 zero = new Vector3();
		final Vector3 temp = new Vector3();
		final Vector2 dragVector = new Vector2();

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			if (pointer == startDragPointer) {
				int diffX = screenX - startX;
				int diffY = screenY - startY;
				startX = screenX;
				startY = screenY;
				zero.setZero();
				temp.set(diffX, diffY, 0);

				OrthographicCamera camera = (OrthographicCamera) viewport
						.getCamera();
				Vector3 v1 = camera.unproject(zero);
				Vector3 v2 = camera.unproject(temp);
				v1.sub(v2);
				dragVector.set(v1.x, v1.y);
				listener.viewportDrag(dragVector);
				return true;
			}
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			float scale = (float) scrollSpeed * amount;
			listener.zoom(scale);
			return true;
		}
	};

	public void update(float time) {
		this.stage.act(time);
	}

	public void dispose() {
		this.stage.dispose();
	}

	public interface IGameUIListener {
		void onPlanetClicked(PlanetData p);

		void viewportDrag(Vector2 v);

		void zoom(float increment);
	}
}
