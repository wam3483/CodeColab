package maxfat.spacesurvival.rendersystem;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;

public class AnimatingPolygonRegionComponent extends Component {

	public PolygonLayer[] layers;

	public AnimatingPolygonRegionComponent(PolygonLayer... layers) {
		this.layers = layers;
	}

	public static class PolygonLayer {
		public float animation;
		public float animationSpeed;
		public PolygonRegion polygonRegion;
		public float[] originalTexCoords;

		public PolygonLayer(PolygonRegion region, float animationSpeed) {
			this.animation = 0;
			this.animationSpeed = animationSpeed;
			this.polygonRegion = region;
			float[] coords = region.getTextureCoords();
			this.originalTexCoords = new float[coords.length];
			System.arraycopy(coords, 0, originalTexCoords, 0, coords.length);
		}

		public void update(float time) {
			this.animation += (time * animationSpeed);
			if (animation > 1)
				animation = 0;
		}
	}
}
