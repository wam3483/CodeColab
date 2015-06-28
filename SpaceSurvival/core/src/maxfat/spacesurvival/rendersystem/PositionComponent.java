package maxfat.spacesurvival.rendersystem;

import com.badlogic.ashley.core.Component;

public class PositionComponent extends Component {
	public float x = 0;
	public float y = 0;

	public PositionComponent() {
		this(0, 0);
	}

	public PositionComponent(float x, float y) {
		this.x = x;
		this.y = y;
	}
}