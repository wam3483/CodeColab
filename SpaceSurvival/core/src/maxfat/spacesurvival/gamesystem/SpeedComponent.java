package maxfat.spacesurvival.gamesystem;

import com.badlogic.ashley.core.Component;

public class SpeedComponent extends Component{
	public float speed;
	public SpeedComponent(float speed){
		this.speed = speed;
	}
}
