package maxfat.spacesurvival.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewController {
	Viewport viewport;
	OrthographicCamera camera;

	float targetZoom = 1;
	float zoomSpeedPerSecond = 10f;
	float minZoom = 1;
	float maxZoom = 20;

	public ViewController(Viewport viewport) {
		this.viewport = viewport;
		this.camera = (OrthographicCamera) this.viewport.getCamera();
		this.targetZoom = camera.zoom;
	}

	public void addZoom(float dZoom) {
		this.targetZoom += dZoom;
		this.targetZoom = MathUtils.clamp(this.targetZoom, minZoom, maxZoom);
	}

	public void setPosition(float x, float y) {
		camera.position.x = x;
		camera.position.y = y;
		camera.update();
	}

	public void translate(float x, float y) {
		camera.position.x += x;
		camera.position.y += y;
		camera.update();
	}

	public void update(float delta) {
		if (this.camera.zoom != this.targetZoom) {
			float beforeAddSign = (float) Math.signum(this.targetZoom
					- camera.zoom);
			float dzoom = (beforeAddSign * zoomSpeedPerSecond * delta);
			camera.zoom += dzoom;
			float afterAddSign = (float) Math.signum(this.targetZoom
					- camera.zoom);
			if (afterAddSign != beforeAddSign)
				camera.zoom = targetZoom;

			camera.zoom = MathUtils.clamp(camera.zoom, minZoom, maxZoom);
			camera.update();
		}
	}
}