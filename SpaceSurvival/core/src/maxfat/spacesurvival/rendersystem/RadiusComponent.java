package maxfat.spacesurvival.rendersystem;

import com.badlogic.ashley.core.Component;

public class RadiusComponent extends Component {
	public float radius = 0;

	public RadiusComponent() {
		this(0);
	}

	public RadiusComponent(float radius) {
		this.radius = radius;
	}
}
